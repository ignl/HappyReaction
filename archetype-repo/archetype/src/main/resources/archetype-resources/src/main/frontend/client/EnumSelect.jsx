import React from 'react'
import { Select } from 'semantic-ui-react'

class EnumSelect extends React.Component {

    constructor(props, context) {
        super(props);
        this.state = {enumValues: []}
    }

    componentDidMount() {
        const component = this;
        const entityName = this.props.entityName;
        const enumField = this.props.name;
        const url = "/rest/".concat(entityName).concat("/allEnumValues?fieldName=").concat(enumField);
        fetch(url).then(function(response) {
            if(response.ok) {
                return response.json();
            }
            throw new Error('Could load an entity');
        }).then(function(allEnumValues) {
            component.setState({enumValues: allEnumValues.map(enumValue => ({text: enumValue, value: enumValue}))});
        }).catch(function(error) {
            console.log('There has been a problem with your fetch operation: ' + error.message);
        });
    }
    render() {
        const props = this.props;
        const state = this.state;
        return(
            <Select name={props.name} value={props.value} onChange={props.onChange} placeholder='Select' options={state.enumValues} />
        )
    }
}

export default EnumSelect
