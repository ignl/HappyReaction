import React from 'react';


import SearchForm from './SearchForm.jsx';
import HappyForm from './HappyForm.jsx';

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        const labelsAndFields = [
            {label: "Name", field: "name", type: "String"},
            {label: "Address", field: "address", type: "String"},
            {label: "Email", field: "email", type: "String"},
            {label: "Age", field: "age", type: "Integer"},
            {label: "Phone", field: "phone", type: "Boolean"}];

        return (
            <div>
                <SearchForm labelsAndFields={labelsAndFields} />
            </div>
        )
    }
}

export default App;