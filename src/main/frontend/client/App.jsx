import React from 'react';
import { Grid, Menu } from 'semantic-ui-react';
import { Link, Switch, Route } from 'react-router-dom';
import Home from './Home.jsx';
import SearchForm from './SearchForm.jsx';
import HappyForm from './HappyForm.jsx';

class App extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {

        const customersSearchFields = [
            {label: "Name", field: "name", type: "String"},
            {label: "Address", field: "address", type: "String"},
            {label: "Email", field: "email", type: "String"},
            {label: "Age", field: "age", type: "Integer", isRangedSearch: true},
            {label: "Phone", field: "phone", type: "String"},
            {label: "City", field: "city", type: "Object", entityToLoad: "city", entityProperty:"name"},
        ];

        const accountsSearchFields = [
            {label: "Account number", field: "accountNumber", type: "String"},
            {label: "Is active", field: "active", type: "Boolean"},
            {label: "Customer", field: "customer", type: "Object", entityToLoad: "customer", entityProperty:"name"},
            {label: "Opening date", field: "openingDate", type: "Date", isRangedSearch: true}];

        const operationsSearchFields = [
            {label: "Operation name", field: "operationName", type: "String"},
            {label: "Ammount", field: "amount", type: "Number"},
            {label: "Type", field: "operationType", type: "Enum"},
            {label: "Account", field: "account", type: "Object", entityToLoad: "account", entityProperty: "accountNumber"},
            {label: "Operation date", field: "operationDate", type: "DateTime", isRangedSearch: true},
            {label: "Comment", field: "comment", type: "String"}
        ];

        const CustomerEditForm = ({ match }) => {
            return <HappyForm entityId={match.params.id} editFields={customersSearchFields} entityName="customer" />
        };
        const CustomerNewForm = ({ match }) => {
            return <HappyForm editFields={customersSearchFields} />
        };
        const AccountEditForm = ({ match }) => {
            return <HappyForm entityId={match.params.id} editFields={accountsSearchFields} entityName="account" />
        };
        const AccountNewForm = ({ match }) => {
            return <HappyForm editFields={accountsSearchFields} />
        };
        const OperationEditForm = ({ match }) => {
            return <HappyForm entityId={match.params.id} editFields={operationsSearchFields} entityName="operation" />
        };
        const OperationNewForm = ({ match }) => {
            return <HappyForm editFields={operationsSearchFields} />
        };

        return (

            <Grid>
                <Grid.Column width={3}>
                    <Menu fluid vertical tabular>
                        <Menu.Item name='customers'><Link to={{ pathname: '/customers/' }}>Customers</Link></Menu.Item>
                        <Menu.Item name='accounts'><Link to={{ pathname: '/accounts/' }}>Accounts</Link></Menu.Item>
                        <Menu.Item name='operations'><Link to={{ pathname: '/operations/' }}>Operations</Link></Menu.Item>
                    </Menu>
                </Grid.Column>

                <Grid.Column stretched width={13}>

                    <Switch>
                        <Route exact path='/' component={Home}/>
                        <Route path='/customers' component={() => <SearchForm entityName='customer' searchFields={customersSearchFields} columnFields={customersSearchFields} fetchFields={['city']} />}/>
                        <Route path="/customer/edit/:id" component={CustomerEditForm}/>
                        <Route path="/customer/new" component={CustomerNewForm}/>

                        <Route path='/accounts' component={() => <SearchForm entityName='account' searchFields={accountsSearchFields} columnFields={accountsSearchFields} fetchFields={['customer']} />}/>
                        <Route path="/account/edit/:id" component={AccountEditForm}/>
                        <Route path="/account/new" component={AccountNewForm}/>

                        <Route path='/operations' component={() => <SearchForm entityName='operation' searchFields={operationsSearchFields} columnFields={operationsSearchFields} fetchFields={['account']} />}/>
                        <Route path="/operation/edit/:id" component={OperationEditForm}/>
                        <Route path="/operation/new" component={OperationNewForm}/>
                    </Switch>

                </Grid.Column>
            </Grid>
        )
    }
}

export default App;