import React from 'react';
import Button from 'semantic-ui-react'

import { Input, Divider, Loader } from 'semantic-ui-react'
import HappyTable from './HappyTable.jsx';
import HappyForm from './HappyForm.jsx';

class App extends React.Component {
    constructor(props) {
        super(props);
        this.state = {customers: []};
        this.loaded = false;
    }

    componentDidMount() {
        const component = this;
        fetch('/rest/customer/all').then(function(response) {
            if(response.ok) {
              return response.json();
            }
            throw new Error('Could not load entities');
          }).then(function(jsonObj) { 
              component.setState({customers: jsonObj});
              component.setState({loaded: true});
          }).catch(function(error) {
              console.log('There has been a problem with your fetch operation: ' + error.message);
          });
    }

    render() {
        const columnLabels = ["Name", "Address", "Email", "Phone"];
        const columns = ["name", "address", "email", "phone"];
        
        const entity = this.state.customers[0];
        const labelsAndFields = [
                                    {label: "Name", field: "name"}, 
                                    {label: "Address", field: "address"}, 
                                    {label: "Email", field: "email"}, 
                                    {label: "Age", field: "age"}, 
                                    {label: "Phone", field: "phone"}]; 
       
        if (this.state.loaded) {
            return (
                <HappyForm entity={entity} labelsAndFields={labelsAndFields} />
            )
        } else {
            return (
                <Loader />
            )
        }
    }
}

export default App;