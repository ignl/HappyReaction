import React from 'react'
import { Input, Label, Button, Form, Segment, Select } from 'semantic-ui-react'
import HappyTable from './HappyTable.jsx';

import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import moment from 'moment';

import EntitySelect from './EntitySelect.jsx';
import EnumSelect from './EnumSelect.jsx';

class SearchForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {data: undefined, page: 1, totalEntries: 0, searchParams: {}, sortBy: undefined, direction: undefined};
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
        this.doSearch = this.doSearch.bind(this);
        this.showPage = this.showPage.bind(this);
        this.rowsPerPage = 5;
    }

    handleChange(event, data) {
        var { name, value } = data;
        this.setState({searchParams: {[name]: value}});
    }

    showPage(page, sortBy, direction) {
        this.setState({page: parseInt(page), sortBy: sortBy, direction: direction}, () => {
            this.handleSubmit();
        });
    }

    handleSubmit(event) {
        this.doSearch();
        if (event) {
            event.preventDefault();
        }
    }

    doSearch() {
        const searchConfig = {firstRow: (this.state.page-1)*this.rowsPerPage+1, numberOfRows: this.rowsPerPage, filters: this.state.searchParams, sortField: this.state.sortBy, ordering: this.state.direction, fetchFields: ["city"]};
        const entityName = 'customer';
        const url = "/rest/".concat(entityName).concat("/search?searchConfig=").concat(encodeURIComponent(JSON.stringify(searchConfig)));
        const countUrl = "/rest/".concat(entityName).concat("/count?searchConfig=").concat(encodeURIComponent(JSON.stringify(searchConfig)));

        const component = this;

        fetch(countUrl).then(function(response) {
            if(response.ok) {
                return response.json();
            }
            throw new Error('Could not count number of entities');
        }).then(function(count) {
            component.setState({totalEntries: count});

            fetch(url).then(function(response) {
                if(response.ok) {
                    return response.json();
                }
                throw new Error('Could not load entities');
            }).then(function(jsonObj) {
                component.setState({data: jsonObj});
            }).catch(function(error) {
                console.log('There has been a problem with your fetch operation: ' + error.message);
            });

        }).catch(function(error) {
            console.log('There has been a problem with your fetch operation: ' + error.message);
        });

    }

    render() {

        const props = this.props;
        const component = this;

        var formFields = [];
        var group = [];
        props.labelsAndFields.forEach(function(labelAndField, index) {
            if (index % 3 === 0 && index !== 0) {
                formFields.push(<Form.Group widths='equal'> {group} </Form.Group>);
                group = [];
            }
            const fieldName = labelAndField.field;
            const booleanOptions = [{value: true, text: 'Yes'}, {value: false, text: 'No'}];

            const handleDate = function (date) {
                component.setState({searchParams: {[fieldName]: date.utc()}});
            };

            if (labelAndField.type == "Integer") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <Input type="number" name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                </Form.Field>);
            } else if (labelAndField.type == "Number") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <Input type="number" step="0.01" name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                </Form.Field>);
            } else if (labelAndField.type == "Boolean") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <Select name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} placeholder='Select' options={booleanOptions} />
                </Form.Field>);
            } else if (labelAndField.type == "Date") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <DatePicker selected={this.state.searchParams[fieldName]} onChange={handleDate} utcOffset={moment().utcOffset()} />
                </Form.Field>);
            } else if (labelAndField.type == "DateTime") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <DatePicker selected={this.state.searchParams[fieldName]} onChange={handleDate} utcOffset={moment().utcOffset()} />
                </Form.Field>);
            } else if (labelAndField.type == "Object") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <EntitySelect name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} entityToLoad={labelAndField.entityToLoad} entityProperty={labelAndField.entityProperty} />
                </Form.Field>);
            } else if (labelAndField.type == "Enum") {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <EnumSelect name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                </Form.Field>);
            } else {
                group.push(<Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <Input name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                </Form.Field>);
            }
        }.bind(this));
        formFields.push(<Form.Group widths='3'> {group} </Form.Group>);

        const columnLabels = ["Name", "Address", "Email", "Phone"];
        const columns = ["name", "address", "email", "phone"];


        return(
            <div>
                <Segment>
                    <Form onSubmit={this.handleSubmit}>
                        {formFields}
                        <Button type='submit' primary>Search</Button>
                    </Form>
                </Segment>
                <HappyTable columnLabels={columnLabels} columns={columns} data={this.state.data} showPageFunction={this.showPage} totalEntries={this.state.totalEntries} rowsPerPage={this.rowsPerPage} currentPage={this.state.page} />
            </div>
        )
    }
}

export default SearchForm
