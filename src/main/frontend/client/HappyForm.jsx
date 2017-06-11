import React from 'react'
import { Input, Label, Button, Checkbox, Form } from 'semantic-ui-react'

class HappyForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        
        var props = this.props;
        const formFields = props.labelsAndFields.map(function(labelAndField, index) {

            return (
                <Form.Field>
                    <Label>{labelAndField.label}</Label>
                    <Input value={props.entity[labelAndField.field]} />
                </Form.Field>
            )
            
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
