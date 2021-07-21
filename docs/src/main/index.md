# Sample Web Application Tutorial

## Create Sample Application

```
g8 tmtsoftware/esw-web-app-template.g8 --name=sample
```

It will generate a sample folder with two sub-folders, `frontend` and `backend`

### Compile frontend

```
cd sample/frontend
npm install
npm run build
```

### Compile backend

```
cd sample/backend
sbt
sbt:backend> compile
```

### Steps to update backend

Open backend folder in you editor(e.g. Intellij)
e.g.

```
cd sample/backend
idea .
```

#### CleanUp existing sample

Existing sample contains a sample application, we will delete its code and add ours where appropriate.

Delete folder `src/main/java` ,`src/test/java`   
Delete file `JSampleImplWrapper.scala`, `JSampleImplWrapperTest.scala`, `SampleImpl.scala`, `SampleImplTest.scala`

Go to `SampleRouteTest`     
Remove existing tests   
Remove any references to deleted classes

Go to `SampleAppIntegrationTest`     
Remove existing tests

#### Add our Models classes

Go to `core/models` in `src`    
Delete Existing Model classes  
Add `RaRequest.scala` model class

Scala
: @@snip [RaRequest.scala](../../../backend/src/main/scala/org/tmt/sample/core/models/RaRequest.scala) { #request-model }

Add `RaResponse.scala` model class

Scala
: @@snip [RaResponse.scala](../../../backend/src/main/scala/org/tmt/sample/core/models/RaResponse.scala) { #response-model }

#### Add our custom implementation for method `raToString` and write test for it

Go to `core` in `src`   
Add `RaImpl.scala`  
Implement `raToString` method using our request and response model

Scala
: @@snip [RaImpl.scala](../../../backend/src/main/scala/org/tmt/sample/core/RaImpl.scala) { #raToString-impl }

Add `RaImpltest.scala` in corresponding `test` directory    
Implement test `convert Ra to String`

Scala
: @@snip [RaImplTest.scala](../../../backend/src/test/scala/org/tmt/sample/core/RaImplTest.scala) { #raToString-impl-test }

Try compiling code

```
sbt:backend> compile
```

It will give compilation errors hence, delete references to earlier deleted classes from `SampleWiring`     
Add a placeholder for route

```
override lazy val routes: Route = ???
```

Go to `SampleRoute`     
Delete existing route   
Add a placeholder for route

```
val route: Route = ???
```

Delete references to earlier deleted classes from `SampleRoute`, `HttpCodecs`, `SampleRoutTest`
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

Go to `SampleWiring`    
Add `raImpl` reference

Scala
: @@snip [SampleWiring.scala](../../../backend/src/main/scala/org/tmt/sample/SampleWiring.scala) { #raImpl-ref }

Add Route in placeholder

Scala
: @@snip [SampleWiring.scala](../../../backend/src/main/scala/org/tmt/sample/SampleWiring.scala) { #add-route }

Go to `SampleRoute`     
Add dependency to `raImpl`

Scala
: @@snip [SampleRoute.scala](../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #raImpl-ref }

Add `formattedRa` route

Scala
: @@snip [SampleRoute.scala](../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-route }

After we add the route, we need to add the codec to serialize/deserialize our request/response

#### Add Codecs in HttpCodecs

Scala
: @@snip [HttpCodecs.scala](../../../backend/src/main/scala/org/tmt/sample/http/HttpCodecs.scala) { #add-codec }

SampleRoute should compile now successfully

#### Add tests for newly added route

Go to `SampleRouteTest`     
Add raImpl mock

Scala
: @@snip [SampleRouteTest.scala](../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-mock }

Add its dependency in SampleRoute Instance in test

Scala
: @@snip [SampleRouteTest.scala](../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-mock-dep }

Lets implement route test

Scala
: @@snip [SampleRouteTest.scala](../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-route-test }

Go to `SampleAppIntegrationTest`    
Add integration test for our route

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-route-test }


Try running all tests

```
sbt:backend> test
```

#### Manually test our application

Start location service

```
csw-services start -k 
```

Try running our backend application

```
sbt:backend> run start
```

Update apptest.http and test your route
```
#### Request to test greeting endpoint
POST http://192.168.1.4:8084/formattedRa
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

### Steps to update frontend

npm i

Go to pages folder in src - delete all component files under this directory Go to pages folder in test - delete all test
files under this directory Delete Menu.tsx and Route.tsx , api.ts Delete folder form

Add models

go to models.ts , delete existing model interfaces Add

    export interface RaRequest {
      raInDecimals: number
    }

    export interface RaResponse {
      formattedRa: string
    }

create api.ts in utils folder

implement fetch call export const fetchFormattedRa = async (
baseUrl: string, raRequest: RaRequest
): Promise<RaResponse | undefined> =>
(await post<RaRequest, RaResponse>(baseUrl + 'formattedRa', raRequest))
.parsedBody

in pages dir create Ra.tsx Add a simple input form

    export const Ra = (): JSX.Element => {
      const onFinish = async (values: RaRequest) => {
        console.log(values)
      }

      return (
        <Form onFinish={onFinish}>
          <Form.Item label='RaInDecimals' name='raInDecimals'>
            <Input role='RaInDecimals' />
          </Form.Item>
          <Form.Item>
            <Button type='primary' htmlType='submit' role='Submit'>
              Submit
            </Button>
          </Form.Item>
        </Form>
      )
    }

implement onFinish

```
const onFinish = async (values: RaRequest) => { const backendUrl = await getBackendUrl(locationService)
const valueInDecimal = { raInDecimals: Number(values.raInDecimals) } if (backendUrl) 
{ const response = await
fetchFormattedRa(backendUrl, valueInDecimal)
console.log(response)
} }

```

in App.tsx remove <Routes/> and add our component route
<Route exact path='/' component={Ra} />
remove <MenuBar/> and add out component menu
<Menu mode='horizontal'>
<Menu.Item key='ra'>
<Link to='/'>Ra to String</Link>
</Menu.Item>
</Menu>


Add Ra.test.tsx

    describe('Ra', () => {
      const connection = HttpConnection(Prefix.fromString('ESW.sample'), 'Service')

      const httpLocation: HttpLocation = {
        _type: 'HttpLocation',
        uri: 'some-backend-url',
        connection,
        metadata: {}
      }
      when(locationServiceMock.find(deepEqual(connection))).thenResolve(
        httpLocation
      )

      it('should render Input form and sent in to backend', async () => {
        const raInDecimals = 2.13
        const raRequest = { raInDecimals }
        const response = new Response(JSON.stringify({ formattedRa: 'some-value' }))
        const fetch = mockFetch()

        when(fetch(anything(), anything())).thenResolve(response)

        renderWithRouter(<Ra />)

        const input = (await screen.findByRole('RaInDecimals')) as HTMLInputElement

        userEvent.type(input, raInDecimals.toString())

        const submitButton = (await screen.findByRole(
          'Submit'
        )) as HTMLButtonElement

        await waitFor(() => userEvent.click(submitButton))

        verify(locationServiceMock.find(deepEqual(connection))).called()
        const [firstArg, secondArg] = capture(fetch).last()
        expect(firstArg).to.equal(httpLocation.uri + 'formattedRa')

        const expectedReq = {
          method: 'POST',
          body: JSON.stringify(raRequest),
          headers: { 'Content-Type': 'application/json' }
        }

        expect(JSON.stringify(secondArg)).to.equal(JSON.stringify(expectedReq))
      })
    })


