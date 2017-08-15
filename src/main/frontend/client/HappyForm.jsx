import React from 'react'
import { Input, Label, Button, Checkbox, Segment, Form } from 'semantic-ui-react'
import {withRouter} from "react-router-dom";

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import moment from 'moment';

import EntitySelect from './EntitySelect.jsx';
import EnumSelect from './EnumSelect.jsx';

class HappyForm extends React.Component {

    constructor(props, context) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleInputChange = this.handleInputChange.bind(this);
        this.goBack = this.goBack.bind(this);
        this.state = {entity: undefined, editedProperties: {}}
    }

    componentDidMount() {
        const component = this;
        if (this.props.entityId) {
            const entityName = 'customer';
            const url = "/rest/".concat(entityName).concat("/findById/").concat(this.props.entityId).concat("?fetchFields=city").concat("&fetchFields=accounts");
            fetch(url).then(function(response) {
                if(response.ok) {
                    return response.json();
                }
                throw new Error('Could not load an entity');
            }).then(function(loadedEntity) {
                component.setState({entity: loadedEntity});
            }).catch(function(error) {
                console.log('There has been a problem with your fetch operation: ' + error.message);
            });
        } else {
            component.setState({entity: {}});
        }
    }

    handleInputChange(event, data) {
        var { name, value } = data;
        if (data.type == 'checkbox') {
            value = data.checked;
        }

        var updatedEditedProperties = this.state.editedProperties;
        updatedEditedProperties[name] = value;
        var entity = this.state.entity;
        entity[name] = value;
        this.setState({
            editedProperties: updatedEditedProperties,
            entity: entity
        });
    }

    handleSubmit(event) {
        const entityName = 'customer';

        if (this.state.entity.id) {
            const url = "/rest/".concat(entityName).concat("/update/").concat(this.state.entity.id);
            fetch(url, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(this.state.editedProperties)
            });
        } else {
            const url = "/rest/".concat(entityName).concat("/add");
            fetch(url, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(this.state.editedProperties)
            });
        }

        alert('Form saved successfully!');
    }

    goBack(event) {
        this.props.history.goBack();
        event.preventDefault();
    }

    render() {
        const component = this;
        const props = this.props;
        const state = this.state;
        const formFields = props.labelsAndFields.map(function(labelAndField, index) {

            const handleDate = function (date) {
                var updatedEditedProperties = component.state.editedProperties;
                updatedEditedProperties[labelAndField.field] = date.utc();
                var entity = component.state.entity;
                entity[labelAndField.field] = date.utc();
                component.setState({
                    editedProperties: updatedEditedProperties,
                    entity: entity
                });
            };

            if (state.entity) {
                if (labelAndField.type == "Integer") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <Input type="number" name={labelAndField.field} defaultValue={state.entity[labelAndField.field]} onChange={component.handleInputChange}/>
                        </Form.Field>
                    )
                } if (labelAndField.type == "Number") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <Input type="number" step="0.01" name={labelAndField.field} defaultValue={state.entity[labelAndField.field]} onChange={component.handleInputChange}/>
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Boolean") {
                    return (
                        <Form.Field>
                            <span>
                                <Label>{labelAndField.label}</Label><br/>
                                <Segment compact>
                                    <Checkbox name={labelAndField.field} checked={state.entity[labelAndField.field]} onChange={component.handleInputChange} />
                                </Segment>
                            </span>
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Date") {
                    var loadedDate = state.entity[labelAndField.field];
                    if (loadedDate &&  (typeof loadedDate === 'string' || loadedDate instanceof String)) {
                        loadedDate = moment(state.entity[labelAndField.field]);
                    }
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <DatePicker selected={loadedDate} onChange={handleDate} utcOffset={moment().utcOffset()} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "DateTime") {
                    var loadedDate = state.entity[labelAndField.field];
                    if (loadedDate &&  (typeof loadedDate === 'string' || loadedDate instanceof String)) {
                        loadedDate = moment(state.entity[labelAndField.field]);
                    }
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <DatePicker selected={loadedDate} onChange={handleDate} utcOffset={moment().utcOffset()} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Object") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <EntitySelect name={labelAndField.field} value={state.entity[labelAndField.field].id} onChange={component.handleInputChange} entityToLoad={labelAndField.entityToLoad} entityProperty={labelAndField.entityProperty} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Enum") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <EnumSelect name={labelAndField.field} onChange={component.handleInputChange} value={state.entity[labelAndField.field]} />
                        </Form.Field>
                    )
                } else {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <Input name={labelAndField.field} defaultValue={state.entity[labelAndField.field]} onChange={component.handleInputChange} />
                        </Form.Field>
                    )
                }
            }
        });

        return(
          <Form onSubmit={this.handleSubmit}>
            {formFields}
            <Button type='submit'>Submit</Button>
            <Button onClick={this.goBack}>Back</Button>
          </Form>
        )
    }
}

export default withRouter(HappyForm)
