import React from 'react'
import { Button, Icon, Table, Loader, Menu, Divider } from 'semantic-ui-react'
import { Link } from 'react-router-dom';

class TableItem extends React.Component {
    constructor(props) {
        super(props);
        this.handleDelete = this.handleDelete.bind(this);
    }

    handleDelete(e) {
        const component = this;
        const entityName = 'customer';
        const url = "/rest/".concat(entityName).concat("/delete/").concat(this.props.item.id);
        return fetch(url, {
            method: 'delete'
        }).then(response => {
            if (response.ok) {
                component.props.rerenderPageFunction();
                alert('Entity deleted successfully!');
            } else {
                alert("Not deleted!");
            }
        });
    }

    render() {
        const columns = this.props.columns.map((columnProp, index) =>
            <Table.Cell>{this.props.item[columnProp]}</Table.Cell>
        );

        return(
            <Table.Row>
                {columns}
                <Table.Cell textAlign="right">
                    <button><Link to={{ pathname: '/edit/' + this.props.item.id }}><Icon name='edit' size='large' /></Link></button>
                    <button onClick={e => this.handleDelete(e)}><Icon color="red" name='delete' size='large' /></button>
                </Table.Cell>
            </Table.Row>
        )
    }
};

class HappyTable extends React.Component {
    constructor(props) {
        super(props);
        this.handleItemClick = this.handleItemClick.bind(this);
        this.handleItemClickFirst = this.handleItemClickFirst.bind(this);
        this.handleItemClickLast = this.handleItemClickLast.bind(this);
        this.handleSort = this.handleSort.bind(this);
        this.rerenderPage = this.rerenderPage.bind(this);
        this.state = {
            column: undefined,
            direction: undefined
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

    rerenderPage() {
        this.props.showPageFunction(1, this.state.column, this.state.direction);
    }

    render() {
        const columnLabels = this.props.columnLabels.map((columnName, index) =>
            <Table.HeaderCell sorted = {this.state.column === columnName ? this.state.direction === 'ASC' ? 'ascending' : 'descending' : null} onClick = {()=>this.handleSort(columnName)}>
                {columnName}
            </Table.HeaderCell>
        );
        
        if (this.props.data) {
            const listItems = this.props.data.map((item, index) =>
                <TableItem item={item} columns={this.props.columns} rerenderPageFunction={this.rerenderPage} />
            );

            const lastPage = Math.ceil(this.props.totalEntries / this.props.rowsPerPage);
            var paginationLinks = [];
            for (let i = Math.max(1, this.props.currentPage-1); i <= Math.min(lastPage, this.props.currentPage+1); i++) {
                paginationLinks.push(<Menu.Item active={this.props.currentPage == i} onClick={this.handleItemClick}>{i}</Menu.Item>);
            }
            const nbColumns = this.props.columns.length+1;

            return (
                <div>
                    <Divider horizontal>Search results</Divider>
                    <Table sortable>
                      <Table.Header>
                        <Table.Row>
                          {columnLabels}
                          <Table.HeaderCell />
                        </Table.Row>
                      </Table.Header>

                      <Table.Body>
                        {listItems}
                      </Table.Body>

                      <Table.Footer fullWidth>
                        <Table.Row>
                          <Table.HeaderCell colSpan={nbColumns}>
                              <Menu defaultActiveIndex="1" pagination>
                                  <Menu.Item onClick={this.handleItemClickFirst} icon>
                                      <Icon name='left chevron' />
                                  </Menu.Item>
                                  {paginationLinks}
                                  <Menu.Item onClick={this.handleItemClickLast} icon>
                                      <Icon name='right chevron' />
                                  </Menu.Item>
                              </Menu>
                              <Link to={{ pathname: '/new' }}>
                                    <Button floated='right' icon labelPosition='left' primary>
                                      <Icon name='add' /> New
                                    </Button>
                              </Link>
                          </Table.HeaderCell>
                        </Table.Row>
                      </Table.Footer>

                    </Table>
                </div>
              )
        } else {
            return (
                <Loader />
            )
        }
    }
}

export default HappyTable