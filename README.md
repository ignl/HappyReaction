# HappyReaction
Maven archetype to quickly create React-Java app with all CRUD and search functionality out of box. It is based on Spring/Hibernate/QueryDsl stack on the backend and React/Semantic-UI on the front end. Adding a new entity with CRUD and search functionality will take only a few minutes and I hope to cut that down even more with code generation in the future.

## Quick start
### Start project
```bash
mvn archetype:generate -DarchetypeGroupId=org.happyreaction -DarchetypeArtifactId=HappyReaction-archetype -DarchetypeVersion=1.0.0 -DgroupId=com.test -DartifactId=TestProject -DarchetypeRepository=https://github.com/ignl/HappyReaction/tree/mvn-repo/archetype
```
### Build project
```bash
cd your_new_project_dir
mvn clean install
```
### Setup database
Install postgresql<br/>
Create database with name: happyreactiondb<br/>
Setup user with username/password: postgres/admin<br/>
### Deploy
Load project to your favorite IDE<br/>
Deploy it on apache tomcat and start it up.<br/>
### Setup NPM watch
Open command prompt, go to src/main/frontend and run 'npm run watch' if you want to have your javascript modifications reloaded (you might need to install npm on your machine separately)

# Features
* Search out of box
* Data table with pagination out of box
* Create, remove, update, delete out of box

# How to

To add a new entity all you have to do is the following:
* Create a new JPA Entity
```java
@Entity
@Table(name = "CUSTOMER")
public class Customer extends BaseEntity {
    @Column(name = "CUSTOMER_NAME", nullable = false)
    private String name;
}
```
* Create a new Spring Data Repository for new entity
```java
public interface CustomerRepository extends GenericRepository<Customer, Long> {
}
```

* Create a new Service that extends BaseService (where the all the logic is already implemented for you)
```java
@Service("customerService")
public class CustomerService extends BaseService<Customer> {
    @Autowired
    private CustomerRepository repository;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    protected JpaRepository<Customer, Long> getRepository() {
        return (JpaRepository) repository;
    }
}
```
* Create a new REST endpoint
```java
@Controller
@RequestMapping("/customer")
public class CustomerRestController extends CrudController<Customer> {
    @Autowired
    private CustomerService customerService;

    @Override
    public Service<Customer> getService() {
      return customerService;
    }
}
```
* And at last create a page in React UI
```jsx
// fields to show for search (and edit forms in this case).
const customersSearchFields = [
  {label: "Name", field: "name", type: "String"}
];
// Edit and Create new forms
const CustomerEditForm = ({ match }) => {
    return <HappyForm entityId={match.params.id} editFields={customersSearchFields} entityName="customer" />
};
const CustomerNewForm = ({ match }) => {
    return <HappyForm editFields={customersSearchFields} entityName="customer" />
};
// Final step is to add our SearchForm and HappyForm components to react router
<Switch>
   <Route path='/customers' component={() => <SearchForm entityName='customer' searchFields={customersSearchFields} columnFields={customersSearchFields} fetchFields={['city']} />}/>
   <Route path="/customer/edit/:id" component={CustomerEditForm}/>
   <Route path="/customer/new" component={CustomerNewForm}/>
</Switch>
```

After you do that and deploy your app you will be able to create a new Customer in GUI, edit it, delete it, and search for it by its name right away. You don't need to write any logic for that as it is all handled by BaseService and SearchForm and HappyForm react components. Hopefully in the future all those steps will be automated.

