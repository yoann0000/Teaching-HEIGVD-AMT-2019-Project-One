# Implementation

## Business

This tier contains our authentication service witch allows us to hash and check user passwords to verify their identity.

## Datastore

This tier was not very well understood by our group as we didn't really use it apart for defining custom exceptions.

## integration

This tier contains all our DAOs(data access objects) witch allow us to access the elements stored in our database as needed without going through countless sql calls.

## Model

This tier contains the core classes of our application. These classes define the primary entities of the program and the relation between them.

## Presentation

This tier contains the servlets used to access our application and interact with it.

### Webapp

the webapp is part of the presentation tier and allows a visual representation and gui for our servlets.
