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

        const searchFields = [
            {label: "Name", field: "name", type: "String"},
            {label: "Address", field: "address", type: "String"},
            {label: "Email", field: "email", type: "String"},
            {label: "Age", field: "age", type: "Integer", isRangedSearch: true},
            {label: "Phone", field: "phone", type: "String"},
            {label: "Date", field: "testDate", type: "Date", isRangedSearch: true},
            {label: "Date time", field: "testDateTime", type: "DateTime", isRangedSearch: true},
            {label: "Test number", field: "testBigDecimal", type: "Number", isRangedSearch: true},
            {label: "City", field: "city", type: "Object", entityToLoad: "city", entityProperty:"name"},
            {label: "Test enum", field: "testEnum", type: "Enum"},
            {label: "Test boolean", field: "testBoolean", type: "Boolean"}];

        const EditForm = ({ match }) => {
            return <HappyForm entityId={match.params.id} editFields={searchFields} />
        };

        const NewForm = ({ match }) => {
            return <HappyForm labelsAndFields={searchFields} />
        };

        return (

            <Grid>
                <Grid.Column width={3}>
                    <Menu fluid vertical tabular>
                        <Menu.Item name='home'><Link to={{ pathname: '/' }}>Home</Link></Menu.Item>
                        <Menu.Item name='list'><Link to={{ pathname: '/list/' }}>List</Link></Menu.Item>
                    </Menu>
                </Grid.Column>

                <Grid.Column stretched width={13}>

                    <Switch>
                        <Route exact path='/' component={Home}/>
                        <Route path='/list' component={() => <SearchForm searchFields={searchFields} columnFields={searchFields} />}/>
                        <Route path="/edit/:id" component={EditForm}/>
                        <Route path="/new" component={NewForm}/>
                    </Switch>

                </Grid.Column>
            </Grid>
        )
    }
}

export default App;