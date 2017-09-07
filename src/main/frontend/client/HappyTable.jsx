import React from 'react'
import { Button, Icon, Table, Loader, Menu, Divider } from 'semantic-ui-react'
import { Link } from 'react-router-dom';

const styles = {
    cursor: "pointer"
};


class TableItem extends React.Component {

    constructor(props) {
        super(props);
        this.handleDelete = this.handleDelete.bind(this);
    }

    render() {

        const component = this;
        const columns = this.props.columnFields.map(function(fieldObj, index) {
            const fieldName = fieldObj.field;
            if (fieldObj.type == 'Object') {
                return (<Table.Cell key={fieldName}>{component.props.item[fieldName][fieldObj.entityProperty]}</Table.Cell>);
            } else if (fieldObj.type == "Boolean") {
                if (component.props.item[fieldName]) {
                    return (<Table.Cell key={fieldName}>Yes</Table.Cell>);
                } else {
                    return (<Table.Cell key={fieldName}>No</Table.Cell>);
                }
            } else {
                return (<Table.Cell key={fieldName}>{component.props.item[fieldName]}</Table.Cell>);
            }
        });

        return(
            <Table.Row>
                {columns}
                <Table.Cell textAlign="right">
                    <Link style={styles} to={{ pathname: '/' + component.props.entityName + '/edit/' + this.props.item.id }}><Icon name='edit' size='large' /></Link>
                    <a style={styles} onClick={e => this.handleDelete(e)}><Icon color="red" name='delete' size='large' /></a>
                </Table.Cell>
            </Table.Row>
        )
    }

    handleDelete(e) {
        const component = this;
        const entityName = this.props.entityName;
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
        const columnLabels = this.props.columnFields.map((fieldObj, index) =>
            <Table.HeaderCell key={fieldObj.field} sorted={this.state.column === fieldObj.field ? this.state.direction === 'ASC' ? 'ascending' : 'descending' : null} onClick = {()=>this.handleSort(fieldObj.field)}>
                {fieldObj.label}
            </Table.HeaderCell>
        );
        
        if (this.props.data) {
            const listItems = this.props.data.map((item, index) =>
                <TableItem item={item} key={item.id} columnFields={this.props.columnFields} rerenderPageFunction={this.rerenderPage} entityName={this.props.entityName} />
            );

            const lastPage = Math.ceil(this.props.totalEntries / this.props.rowsPerPage);
            var paginationLinks = [];
            for (let i = Math.max(1, this.props.currentPage-1); i <= Math.min(lastPage, this.props.currentPage+1); i++) {
                paginationLinks.push(<Menu.Item key={i} active={this.props.currentPage == i} onClick={this.handleItemClick}>{i}</Menu.Item>);
            }
            const nbColumns = this.props.columnFields.length+1;

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
                              <Link to={{ pathname: '/' + this.props.entityName + '/new' }}>
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