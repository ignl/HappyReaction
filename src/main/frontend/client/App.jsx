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

        const labelsAndFields = [
            {label: "Name", field: "name", type: "String"},
            {label: "Address", field: "address", type: "String"},
            {label: "Email", field: "email", type: "String"},
            {label: "Age", field: "age", type: "Integer"},
            {label: "Phone", field: "phone", type: "Boolean"}];

        const EditForm = ({ match }) => {
            return <HappyForm entityId={match.params.id} labelsAndFields={labelsAndFields} />
        };

        const NewForm = ({ match }) => {
            return <HappyForm labelsAndFields={labelsAndFields} />
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
                        <Route path='/list' component={() => <SearchForm labelsAndFields={labelsAndFields} />}/>
                        <Route path="/edit/:id" component={EditForm}/>
                        <Route path="/new" component={NewForm}/>
                    </Switch>

                </Grid.Column>
            </Grid>
        )
    }
}

export default App;