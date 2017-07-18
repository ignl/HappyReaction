import React from 'react'
import { Input, Label, Button, Checkbox, Form } from 'semantic-ui-react'

class HappyForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.state = {entity: undefined}
    }

    componentDidMount() {
        const component = this;
        const entityName = 'customer';
        const url = "/rest/".concat(entityName).concat("/findById/").concat(this.props.entityId);
        fetch(url).then(function(response) {
            if(response.ok) {
                return response.json();
            }
            throw new Error('Could count number of entities');
        }).then(function(loadedEntity) {
            component.setState({entity: loadedEntity});
        }).catch(function(error) {
            console.log('There has been a problem with your fetch operation: ' + error.message);
        });
    }


    handleSubmit(event) {
        alert('A name was submitted:');
    }

    render() {
        
        var props = this.props;
        var state = this.state;
        const formFields = props.labelsAndFields.map(function(labelAndField, index) {
            if (state.entity) {
                if (labelAndField.type == "Integer") {
                    return (
                        <Form.Field>
                            <Label>Integer label</Label>
                            <Input value={state.entity[labelAndField.field]} />
                        </Form.Field>
                    )
                } else if (labelAndField.type == "Boolean") {
                    return (
                        <Checkbox label='Boolean label' />
                    )
                } else if (labelAndField.type == "Object") {
                    return (
                        <Input
                            icon={<Icon name='search' inverted circular link />}
                            placeholder='Search...'
                        />
                    )
                } else {
                    return (
                        <Form.Field>
                            <Label>{labelAndField.label}</Label>
                            <Input value={state.entity[labelAndField.field]} />
                        </Form.Field>
                    )
                }
            }
        });
        
        return(
          <Form>
            {formFields}
            <Button type='submit'>Submit</Button>
            <Button>Back</Button>
          </Form>
        )
    }
}

export default HappyForm
