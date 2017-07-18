import React from 'react'
import { Input, Label, Button, Checkbox, Form, Segment } from 'semantic-ui-react'
import HappyTable from './HappyTable.jsx';

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

    handleChange(event) {
        this.setState({searchParams: {[event.target.name]: event.target.value}});
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
        const searchConfig = {firstRow: (this.state.page-1)*this.rowsPerPage+1, numberOfRows: this.rowsPerPage, filters: this.state.searchParams, sortField: this.state.sortBy, ordering: this.state.direction};
        const entityName = 'customer';
        const url = "/rest/".concat(entityName).concat("/search?searchConfig=").concat(encodeURIComponent(JSON.stringify(searchConfig)));
        const countUrl = "/rest/".concat(entityName).concat("/count?searchConfig=").concat(encodeURIComponent(JSON.stringify(searchConfig)));

        const component = this;

        fetch(countUrl).then(function(response) {
            if(response.ok) {
                return response.json();
            }
            throw new Error('Could noy count number of entities');
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

        var formFields = [];
        var group = [];
        props.labelsAndFields.forEach(function(labelAndField, index) {
            if (index % 3 === 0 && index !== 0) {
                formFields.push(<Form.Group widths='equal'> {group} </Form.Group>);
                group = [];
            }
            const fieldName = labelAndField.field;
            group.push(<Form.Field>
                            <Input name={fieldName} value={this.state.searchParams[fieldName]} label={labelAndField.label} onChange={this.handleChange} />
                        </Form.Field>);
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
