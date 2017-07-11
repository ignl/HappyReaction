import React from 'react'
import { Button, Checkbox, Icon, Table, Loader, Menu } from 'semantic-ui-react'

var TableItem = React.createClass({
    render: function() {
        const columns = this.props.columns.map((columnProp, index) =>
            <Table.Cell>{this.props.item[columnProp]}</Table.Cell>
        );
        
        return(
            <Table.Row>
                {columns}
            </Table.Row>
        )
    }
  });

class HappyTable extends React.Component {
    constructor(props) {
        super(props);
        this.handleItemClick = this.handleItemClick.bind(this);
        this.handleItemClickFirst = this.handleItemClickFirst.bind(this);
        this.handleItemClickLast = this.handleItemClickLast.bind(this);
        this.handleSort = this.handleSort.bind(this);
        this.state = {
            column: null,
            direction: null
        }
    }

    handleItemClick(event) {
        this.props.showPageFunction(event.target.text, this.state.column, this.state.direction);
        event.preventDefault();
    }

    handleItemClickFirst(event) {
        this.props.showPageFunction(1, this.state.column, this.state.direction);
        event.preventDefault();
    }

    handleItemClickLast(event) {
        const lastPage = Math.ceil(this.props.totalEntries / this.props.rowsPerPage);
        this.props.showPageFunction(lastPage, this.state.column, this.state.direction);
        event.preventDefault();
    }

    handleSort(clickedColumn) {
        if (this.state.column !== clickedColumn) {
            this.setState({
                column: clickedColumn,
                direction: 'ASC',
            }, () => {
                this.props.showPageFunction(1, this.state.column, this.state.direction);
            });
        } else {
            this.setState({
                direction: this.state.direction === 'ASC' ? 'DESC' : 'ASC',
            }, () => {
                this.props.showPageFunction(1, this.state.column, this.state.direction);
            });
        }
    }

    render() {
        const columnLabels = this.props.columnLabels.map((columnName, index) =>
            <Table.HeaderCell sorted = {this.state.column === columnName ? this.state.direction === 'ASC' ? 'ascending' : 'descending' : null} onClick = {()=>this.handleSort(columnName)}>
                {columnName}
            </Table.HeaderCell>
        );
        
        if (this.props.data) {
            const listItems = this.props.data.map((item, index) =>
                <TableItem item={item} columns={this.props.columns} />
            );

            const lastPage = Math.ceil(this.props.totalEntries / this.props.rowsPerPage);
            var paginationLinks = [];
            for (let i = Math.max(1, this.props.currentPage-1); i <= Math.min(lastPage, this.props.currentPage+1); i++) {
                paginationLinks.push(<Menu.Item active={this.props.currentPage == i} onClick={this.handleItemClick}>{i}</Menu.Item>);
            }
            const nbColumns = this.props.columns.length;

            return (
                <Table sortable>
                  <Table.Header>
                    <Table.Row>
                      {columnLabels}
                    </Table.Row>
                  </Table.Header>

                  <Table.Body>
                    {listItems}
                  </Table.Body>

                  <Table.Footer fullWidth>
                    <Table.Row>
                      <Table.HeaderCell colSpan={nbColumns}>
                          <Menu floated='center' defaultActiveIndex="1" pagination>
                              <Menu.Item onClick={this.handleItemClickFirst} icon>
                                  <Icon name='left chevron' />
                              </Menu.Item>
                              {paginationLinks}
                              <Menu.Item onClick={this.handleItemClickLast} icon>
                                  <Icon name='right chevron' />
                              </Menu.Item>
                          </Menu>
                        <Button floated='right' icon labelPosition='left' primary size='small'>
                          <Icon name='user' /> New
                        </Button>
                      </Table.HeaderCell>
                    </Table.Row>
                  </Table.Footer>

                </Table>
              )
        } else {
            return (
                <Loader />
            )
        }
    }
}

export default HappyTable