import React from 'react'
import { Select } from 'semantic-ui-react'

class EntitySelect extends React.Component {

    constructor(props, context) {
        super(props);
        this.state = {selectEntities: []}
    }

    componentDidMount() {
        const component = this;
        const entityName = this.props.entityToLoad;
        const entityProperty = this.props.entityProperty;
        const url = "/rest/".concat(entityName).concat("/all");
        fetch(url).then(function(response) {
            if(response.ok) {
                return response.json();
            }
            throw new Error('Could load an entity');
        }).then(function(allEntities) {
            component.setState({selectEntities: allEntities.map(entity => ({text: entity[entityProperty], value: entity.id}))});
        }).catch(function(error) {
            console.log('There has been a problem with your fetch operation: ' + error.message);
        });
    }
    render() {
        const props = this.props;
        const state = this.state;
        return(
            <Select name={props.name} value={props.selected} onChange={props.onChange} placeholder='Select' options={state.selectEntities} />
        )
    }
}

export default EntitySelect
