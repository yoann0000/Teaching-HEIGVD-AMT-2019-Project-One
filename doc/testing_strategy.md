# testing Strategy

## Unit tests

We use juint to unit test our classes to verify that they function in a vacuum.

## Mockito

When testing classes that requires data from another class we used Mockito to generate mock classes that can provide static data so that we don't rely on other classes working to test these.

## Jmeter

We attempted to use Jmeter to load and performance test our app but, as of this writing, these tests are currently incomplete.

### Opinion

With more time and more expertise we could have been able to do more about our current tests. However, as it stands right now our tests can only be called satisfactory in that they are used to test a minimalist application.

This is not to say that our tests are bad but for larger and longer projects we would need a better testing range perhaps even some form of test automation using tools like **travis** or having more tests that test the interaction between classes and servlets.
