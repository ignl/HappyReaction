import React from 'react'
import { Button, Checkbox, Icon, Table } from 'semantic-ui-react'

var TableItem = React.createClass({
    render: function() {
        const columns = this.props.columns.map((columnProp, index) =>
            <Table.Cell>{this.props.item[columnProp]}</Table.Cell>
        );
        
        return(
            <Table.Row>
                <Table.Cell collapsing>
                  <Checkbox />
                </Table.Cell>
                {columns}
            </Table.Row>
        )
    }
  });

class HappyTable extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const columnLabels = this.props.columnLabels.map((columnName, index) =>
            <Table.HeaderCell>{columnName}</Table.HeaderCell>
        );
        
        const listItems = this.props.loadedList.map((item, index) =>
            <TableItem item={item} columns={this.props.columns} />
        );
        
        const nbColumns = this.props.columns.length;
        
        return (
            <Table compact celled definition>
              <Table.Header>
                <Table.Row>
                  <Table.HeaderCell />
                  {columnLabels}
                </Table.Row>
              </Table.Header>
    
              <Table.Body>
                {listItems}
              </Table.Body>
    
              <Table.Footer fullWidth>
                <Table.Row>
                  <Table.HeaderCell />
                  <Table.HeaderCell colSpan='{nbColumns}'>
                    <Button floated='right' icon labelPosition='left' primary size='small'>
                      <Icon name='user' /> New
                    </Button>
                    <Button disabled size='small'>Delete All</Button>
                  </Table.HeaderCell>
                </Table.Row>
              </Table.Footer>
            </Table>
          )
    }
}

export default HappyTable