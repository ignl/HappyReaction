import React from 'react'
import { Input, Label, Button, Checkbox, Form } from 'semantic-ui-react'
import {withRouter} from "react-router-dom";

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';

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
            const url = "/rest/".concat(entityName).concat("/findById/").concat(this.props.entityId);
            fetch(url).then(function(response) {
                if(response.ok) {
                    return response.json();
                }
                throw new Error('Could load an entity');
            }).then(function(loadedEntity) {
                component.setState({entity: loadedEntity});
            }).catch(function(error) {
                console.log('There has been a problem with your fetch operation: ' + error.message);
            });
        } else {
            component.setState({entity: {}});
        }
    }

    handleInputChange(event) {
        const target = event.target;
        const value = target.type === 'checkbox' ? target.checked : target.value;
        const name = target.name;

        var updatedEditedProperties = this.state.editedProperties;
        updatedEditedProperties[name] = value;
        this.setState({
            editedProperties: updatedEditedProperties
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
                updatedEditedProperties[labelAndField.field] = date;
                component.setState({
                    editedProperties: updatedEditedProperties
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
                } else if (labelAndField.type == "Boolean") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <Checkbox toggle />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Date") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <DatePicker selected={state.entity[labelAndField.field]}
                                        onChange={handleDate} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "DateTime") {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <DatePicker selected={state.entity[labelAndField.field]}
                                        onChange={handleDate} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Object") {
                    return (
                        <Input name={labelAndField.field} onChange={component.handleInputChange}
                               icon={<Icon name='search' inverted circular link />}
                            placeholder='Search...'
                        />
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
