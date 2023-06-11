# Learning Report Day 3 - ComprasAPI
I am creating this .md file in order to recap all the troubleshooting's and new things that I possibly learn during this first day.
Writing helps me review and I feel I learn more when I write down what I went through to reach the goal.

### _Holy testing Batman!_

## Unit Testing with Multiny and Mockito
Yes, day 3 is about doing unit testing. 

As I could read in Quarkus, usually testing is focused in the endpoints in order to check if there is a 200, 404 or 500 answer from the REST operation. 
This is usually called API testing and could become Integration Testing if you include client and API server-side. The way I learnt to do it was in the Service layer, 
mocking the repository and finally checking the final answer as the expected one. This is called unit testing.


I used to do reactive unit testing in Springboot Webflux using Mockito and Stepverifier but here in Quarkus, Stepverifier does not exist as a dependency,
so, I was already resigned to using JUnit and applying Assertions everywhere unit I started reading Multiny [documentation](https://smallrye.io/smallrye-mutiny/2.1.0/guides/testing/) 
that just as Stepverifier, handles better the asynchronous behavior with Uni and Multi without blocking the data flow.

So, the dependency that you will need is the Mockito one, because Multiny is already set

```
<dependency>
      <groupId>io.quarkus</groupId>
      <artifactId>quarkus-junit5-mockito</artifactId>
      <scope>test</scope>
</dependency>
```

### Writing a unit test
At first glance, I did not know how to start, so I took as a guide the unit testing that I did with Stepverifier and Mockito.
So, in the test folder generate a file that represents the Service layer but for doing testing. In my case, is SolicitudServiceTest.java.
Once you did that, let's start adding some lines and annotations.
```
@QuarkusTest
class SolicitudServiceTest {

    @InjectMock
    SolicitudRepository mongoDbMock;

    SolicitudServiceImpl service;

    SolicitudMapper mapper;
```

**@QuarkusTest** -  Enables the class to be a Testing class to be considered when you compile the project with maven. When you execute
the project with _mvn compile quarkus:dev_ it told you to press r to execute the unit testing before the application itself. So, the class 
to be considered for the testing scenario will be which is marked with this annotation.

A little parenthesis: The cmd in maven to execute just testing without launching the whole application is
```
mvn quarkus:test
```


Because I am going to do a Unit Testing, I need 3 elements to acomplish the testing:

1. The Repository to Mock with **@InjectMock**
2. The Service to use for testing
3. The configured mapper

Now, I will use a void tagged with **@BeforeEach** that makes a preload before any testing starts.

```
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        mapper = new SolicitudMapperImpl();
        service = new SolicitudServiceImpl(mongoDbMock, mapper);

    }
```

Why this preload is so important? Well, believe me: You don't want your tests fail because of NullPointerExceptions or the Mocking not _turned_ on. Remember 
that I applied constructor injection for the service so, I need to initialize the instance yes or yes, as well the mapper. By the other hand, as I understood
@InjectMock need a bit of help to enable the mocking status, so line 60 helps with that.

Now, I am not going to explain how to set up a unit test, I will focus more in the usage of Multiny to do the assertion part.
As an overview, It's pretty easy to implement. Just to keep in mind what are you looking for as the final result for the assertion. Multiny gives you the option
based on the success or failure, whatever you are expecting. Plus, if it is a Multi or Uni. 

Let's check this piece of code as example:

```
result.subscribe()
    .withSubscriber(AssertSubscriber.create(2))
    .awaitCompletion()
    .assertItems(solicitudDTO1,solicitudDTO2);
```
This piece of code you can find it in the getAllTestSuccess() testing method. 'result' stores the calling to the service, and as we know, we can receive Uni or Multi.
Over any of them, we made the call to _subscribe()_ in order to generate after using 'withSubscriber()' a AssertSubscriber that receives and asserts the elements received. In this case, 2.
The important point with _awaitCompletion()_ that waits for the subscription to complete/emit all its items. Finally, _assertItems()_ asserts that the received items 
match the expected items.

I highly recommend to read Multiny [documentation](https://smallrye.io/smallrye-mutiny/2.1.0/guides/testing/) if you want to know how to apply when you get Multi, Uni or want to
trigger a failure. Plus, check my SolicitudServiceTest. I did mostly of the unit testing per service. All of them passed.
If I missed out to mention something in next branches I will re-do it again.

And that's it with day 3 (I think). Let's keep going with day 4~








