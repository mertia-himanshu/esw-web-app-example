# Adding Authentication

## Add protected route in backend

#### Add new route with protection

We will add a new route in `SampleRoute.scala` which is protected, to access this route, request should contain a token containing role `esw-user`.
We have set up some [sample users](https://tmtsoftware.github.io/csw/apps/cswservices.html#predefined-users-) when we start `csw-services` with Authentication.

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-secured-route }

#### Add test for newly added route

Add test in `SampleRouteTest.scala` for protected route

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-secured-route-test }

#### Add integration tests for newly added route

Add positive test for user with required role in `SampleAppIntegrationTest.scala`

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-test }

Add Failure test for user without required role in `SampleAppIntegrationTest.scala`

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-failure-test }

## Consume protected route in frontend

#### Add secured Fetch

Add method in `api.ts`
Typescript
: @@snip [api.ts](../../../../frontend/src/utils/api.ts) { #secured-fetch-data }

#### Add our React component to consume secured fetch

Add new file `SecuredRa.tsx` and add component

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #add-component }

#### Use secured fetch in our component

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #use-fetch }


Add protected route in `App.tsx`

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-protected-route }

Add menu item action for protected route in `App.tsx`

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-protected-route-action }


Add `SecuredRa.test.tsx` in `test/pages`

Typescript
: @@snip [SecuredRa.test.tsx](../../../../frontend/test/pages/SecuredRa.test.tsx) { #add-test }

Add `Login` and `Logout` functionality

Use Auth Hook

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #use-auth-hook }

Add menu items actions

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-login-logout }







