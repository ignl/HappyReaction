import React from 'react'
import { Input, Label, Button, Checkbox, Form } from 'semantic-ui-react'

class HappyForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        alert('A name was submitted:');
    }

    render() {
        
        var props = this.props;
        const formFields = props.labelsAndFields.map(function(labelAndField, index) {
            if (labelAndField.type == "Integer") {
                return (
                    <Form.Field>
                        <Label>Integer label</Label>
                        <Input value={props.entity[labelAndField.field]} />
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
                        <Input value={props.entity[labelAndField.field]} />
                    </Form.Field>
                )
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
