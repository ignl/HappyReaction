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

    static get defaultProps() {
        return {
            fetchFields: []
        }
    }

    handleChange(event, data) {
        const loadedSearchParams =  this.state.searchParams;
        const { name, value } = data;
        loadedSearchParams[name] = value;
        this.setState({searchParams: loadedSearchParams});
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
        const searchConfig = {firstRow: (this.state.page-1)*this.rowsPerPage+1, numberOfRows: this.rowsPerPage, filters: this.state.searchParams, sortField: this.state.sortBy, ordering: this.state.direction, fetchFields: this.props.fetchFields};
        const entityName = this.props.entityName;
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
        props.searchFields.forEach(function(fieldObj, index) {
            if (index % 3 === 0 && index !== 0) {
                formFields.push(<Form.Group key={fieldObj.field} widths='equal'> {group} </Form.Group>);
                group = [];
            }
            const fieldName = fieldObj.field;
            const booleanOptions = [{value: "true", text: 'Yes'}, {value: "false", text: 'No'}];
            const loadedSearchParams =  this.state.searchParams;

            const handleDate = function (date) {
                loadedSearchParams[fieldName] = date.utc();
                component.setState({searchParams: loadedSearchParams});
            };

            if (fieldObj.isRangedSearch) {
                if (fieldObj.type == "Integer") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Form.Group widths='2'>
                            <Input placeholder="from" type="number" name={'fromRange-' + fieldName} value={this.state.searchParams['fromRange-' + fieldName]} onChange={this.handleChange} />
                            <Input placeholder="to" type="number" name={'toRange-' + fieldName} value={this.state.searchParams['toRange-' + fieldName]} onChange={this.handleChange} />
                        </Form.Group>
                    </Form.Field>);
                } else if (fieldObj.type == "Number") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Form.Group widths='2'>
                            <Input placeholder="from" type="number" step="0.01" name={'fromRange-' + fieldName} value={this.state.searchParams['fromRange-' + fieldName]} onChange={(event, data) => {loadedSearchParams['fromRange-' + data.name] = data.value; component.setState({searchParams: loadedSearchParams})}} />
                            <Input placeholder="to" type="number" step="0.01" name={'toRange-' + fieldName} value={this.state.searchParams['toRange-' + fieldName]} onChange={(event, data) => {loadedSearchParams['toRange-' + data.name] = data.value; component.setState({searchParams: loadedSearchParams})}} />
                        </Form.Group>
                    </Form.Field>);
                } else if (fieldObj.type == "Date") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Form.Group widths='2'>
                            <DatePicker placeholderText="from" selected={this.state.searchParams['fromRange-' + fieldName]} onChange={(date) => {loadedSearchParams['fromRange-' + fieldName] = date.utc(); component.setState({searchParams: loadedSearchParams})}} utcOffset={moment().utcOffset()} />
                            <DatePicker placeholderText="to" selected={this.state.searchParams['toRange-' + fieldName]} onChange={(date) => {loadedSearchParams['toRange-' + fieldName] = date.utc(); component.setState({searchParams: loadedSearchParams})}} utcOffset={moment().utcOffset()} />
                        </Form.Group>
                    </Form.Field>);
                } else if (fieldObj.type == "DateTime") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Form.Group widths='2'>
                            <DatePicker placeholderText="from" selected={this.state.searchParams['fromRange-' + fieldName]} onChange={(date) => {loadedSearchParams['fromRange-' + fieldName] = date.utc(); component.setState({searchParams: loadedSearchParams})}} utcOffset={moment().utcOffset()} />
                            <DatePicker placeholderText="to" selected={this.state.searchParams['toRange-' + fieldName]} onChange={(date) => {loadedSearchParams['toRange-' + fieldName] = date.utc(); component.setState({searchParams: loadedSearchParams})}} utcOffset={moment().utcOffset()} />
                        </Form.Group>
                    </Form.Field>);
                }
            } else {
                if (fieldObj.type == "Integer") {
                    group.push(<Form.Field key={fieldObj.field} >
                        <Label>{fieldObj.label}</Label>
                        <Input type="number" name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                    </Form.Field>);
                } else if (fieldObj.type == "Number") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Input type="number" step="0.01" name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                    </Form.Field>);
                } else if (fieldObj.type == "Boolean") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Select name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} placeholder='Select' options={booleanOptions} />
                    </Form.Field>);
                } else if (fieldObj.type == "Date") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <DatePicker selected={this.state.searchParams[fieldName]} onChange={handleDate} utcOffset={moment().utcOffset()} />
                    </Form.Field>);
                } else if (fieldObj.type == "DateTime") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <DatePicker selected={this.state.searchParams[fieldName]} onChange={handleDate} utcOffset={moment().utcOffset()} />
                    </Form.Field>);
                } else if (fieldObj.type == "Object") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <EntitySelect name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} entityToLoad={fieldObj.entityToLoad} entityProperty={fieldObj.entityProperty} />
                    </Form.Field>);
                } else if (fieldObj.type == "Enum") {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <EnumSelect name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} entityName={this.props.entityName} />
                    </Form.Field>);
                } else {
                    group.push(<Form.Field key={fieldObj.field}>
                        <Label>{fieldObj.label}</Label>
                        <Input name={fieldName} value={this.state.searchParams[fieldName]} onChange={this.handleChange} />
                    </Form.Field>);
                }
            }

        }.bind(this));
        formFields.push(<Form.Group key="last" widths='3'> {group} </Form.Group>);

        return(
            <div>
                <Segment>
                    <Form onSubmit={this.handleSubmit}>
                        {formFields}
                        <Button type='submit' primary>Search</Button>
                    </Form>
                </Segment>
                <HappyTable columnFields={this.props.columnFields} data={this.state.data} showPageFunction={this.showPage} totalEntries={this.state.totalEntries} rowsPerPage={this.rowsPerPage} currentPage={this.state.page} entityName={this.props.entityName} />
            </div>
        )
    }
}

export default SearchForm
