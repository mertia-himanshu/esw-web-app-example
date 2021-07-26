# Creating a Web Application

## Overview
This template contains steps to create an example application using the template    
It has three sections:

* Basic flow - Creating a Web Application
* Adding Authentication
* Adding Persistence

Basic flow will show you how to add a route to backend application and consume it in your frontend.
Next two sections are extensions, if you need to add authentication in your application or
if you need to save your data to a database.

---

Basic flow starts here.

## Generate application

First we need to generate a scaffolding application using our gitter8 template

```
g8 tmtsoftware/esw-web-app-template.g8 --name=sample
```

It will generate a sample folder with two sub-folders, `frontend` and `backend`

## Compile frontend

This is where your frontend application is present, it uses Typescript, React and node.     
Make sure node is installed in your machine. Let's compile our generated application.

```
cd sample/frontend
npm install
npm run build
```

## Compile backend

This is where your backend application is present, it uses Scala ecosystem.     
Make sure [coursier](https://get-coursier.io/docs/sbt-coursier), openjdk 11 and latest sbt version is installed in your machine.       
Let's compile our generated application.

```
cd sample/backend
sbt
sbt:backend> compile
```

## Add route to backend

Open backend folder in you editor(e.g. Intellij)
e.g.

```
cd sample/backend
idea .
```

#### CleanUp existing sample

Generated code contains a sample application, we will delete its code and add ours where appropriate.

Delete folder `src/main/java` ,`src/test/java`   
Delete file `JSampleImplWrapper.scala`, `JSampleImplWrapperTest.scala`, `SampleImpl.scala`, `SampleImplTest.scala`

Go to `SampleRouteTest.scala`     
Remove existing tests   
Remove any references to deleted classes

Go to `SampleAppIntegrationTest.scala`     
Remove existing tests

#### Add our Models classes

These model classes will be used to serialized and deserialized your request and response.

Go to `core/models` in `src`    
Delete Existing Model classes  
Add `RaRequest.scala` model class

Scala
: @@snip [RaRequest.scala](../../../../backend/src/main/scala/org/tmt/sample/core/models/RaRequest.scala) { #request-model }

Add `RaResponse.scala` model class

Scala
: @@snip [RaResponse.scala](../../../../backend/src/main/scala/org/tmt/sample/core/models/RaResponse.scala) { #response-model }

#### Add our custom implementation for method `raToString` and write test for it

Add contract    
Go to `src`, add package folder with name `service`      
Add file `RaService.scala` and add our `raToString` contract, using our request and response model  

Scala
: @@snip [RaService.scala](../../../../backend/src/main/scala/org/tmt/sample/service/RaService.scala) { #raToString-contract }


Go to `core` in `src`   
Add `RaImpl.scala`  
Extend `RaService.scala` to implement `raToString` contract

Scala
: @@snip [RaImpl.scala](../../../../backend/src/main/scala/org/tmt/sample/core/RaImpl.scala) { #raToString-impl }

Add `RaImpltest.scala` in corresponding `test` directory    
Implement test `convert Ra to String`

Scala
: @@snip [RaImplTest.scala](../../../../backend/src/test/scala/org/tmt/sample/core/RaImplTest.scala) { #raToString-impl-test }

Try compiling code

```
sbt:backend> compile
```

It will give compilation errors hence, delete references to earlier deleted classes from `SampleWiring.scala`     
Add a placeholder for route

```
override lazy val routes: Route = ???
```

Go to `SampleRoute.scala`     
Delete existing route   
Add a placeholder for route

```
val route: Route = ???
```

Delete references to earlier deleted classes from scala files `SampleRoute`, `HttpCodecs`, `SampleRoutTest`
and `SampleAppIntegrationTest`

Try compiling code again, this time it should compile

```
sbt:backend> compile
```

Try running the newly added test

```
sbt:backend> testOnly org.tmt.sample.core.RaImplTest
```

#### Add Route for our implementation

We are using akka dsl to compose our http routes.   

Go to `SampleWiring.scala`    
Add `raImpl` reference

Scala
: @@snip [SampleWiring.scala](../../../../backend/src/main/scala/org/tmt/sample/SampleWiring.scala) { #raImpl-ref }

Add Route in placeholder

Scala
: @@snip [SampleWiring.scala](../../../../backend/src/main/scala/org/tmt/sample/SampleWiring.scala) { #add-route }

Go to `SampleRoute.scala`     
Add dependency to `raImpl`

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #raImpl-ref }

Add route

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-route }

After we add the route, we need to add the codec to serialize/deserialize our request/response

#### Add Codecs

We are using [borer](https://sirthias.github.io/borer/) to serialize/deserialize. It has support for two formats Json and Cbor(binary format)

Add codecs in `HttpCodecs.scala`

Scala
: @@snip [HttpCodecs.scala](../../../../backend/src/main/scala/org/tmt/sample/http/HttpCodecs.scala) { #add-codec }

SampleRoute should compile now successfully

#### Add tests for newly added route

Go to `SampleRouteTest.scala`     
Add raImpl mock

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-mock }

Add its dependency in SampleRoute Instance in `SampleRouteTest.scala`

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-mock-dep }

Lets implement route test

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-route-test }

Go to `SampleAppIntegrationTest.scala`    
Add integration test for our route

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-route-test }


Try running all tests

```
sbt:backend> test
```

#### Manually test our application

Start location service with authentication service (we will use auth in next section of tutorial)

```
cs install csw-services:v3.0.0-M1
csw-services start -k 
```

Try running our backend application

```
sbt:backend> run start
```

Update apptest.http and test your route

```
#### Request to test greeting endpoint
POST http://192.168.1.4:8084/raValues
Content-Type: application/json

{
  "raInDecimals": 2.13
}

```

You should receive response like

```
{
"formattedRa": "8h 8m 9.602487087684134s"
}
```

## Consume route in frontend

Go to `pages` folder in `src`   
Delete all component files under this directory     
Go to `pages` folder in `test`  
Delete all test files under this directory   
Delete `Menu.tsx`, `Route.tsx`, `api.ts`    
Delete folder `form`

#### Add models

Go to `Models.ts`   
Delete existing model interfaces Add our request and response models

Typescript
: @@snip [Models.ts](../../../../frontend/src/models/Models.ts) { #add-models }

#### Add Fetch

Create `api.ts` in `utils` folder Implement method to fetch data from backend endpoint

Typescript
: @@snip [api.ts](../../../../frontend/src/utils/api.ts) { #fetch-data }

#### Add our React component

In `pages` folder create `Ra.tsx`   
Add a simple input form

Typescript
: @@snip [Ra.tsx](../../../../frontend/src/components/pages/Ra.tsx) { #add-component }

#### Use fetch in our component

Typescript
: @@snip [Ra.tsx](../../../../frontend/src/components/pages/Ra.tsx) { #use-fetch }

In `App.tsx` remove `<Routes />` and map our component to route

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-route }

remove `<MenuBar/>` and add our route to menu item action

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-route-action }

Add `Ra.test.tsx` in `test/pages`

Typescript
: @@snip [Ra.test.tsx](../../../../frontend/test/pages/Ra.test.tsx) { #add-test }

Run frontend Application

```
npm start
```

