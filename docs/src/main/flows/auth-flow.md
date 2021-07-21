# Adding Authentication

## Update Backend

#### Add new route with protection

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-secured-route }

#### Add test for newly added route

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-secured-route-test }

#### Add integration tests for newly added route

Add positive test for user with required role

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-test }

Add Failure test for user without required role

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-failure-test }

## Update Frontend

#### Add secured Fetch

Typescript
: @@snip [api.ts](../../../../frontend/src/utils/api.ts) { #secured-fetch-data }

#### Add our React component to consume secured fetch

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #add-component }

#### Use secured fetch in our component

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #use-fetch }


Add protected route

Typescript
: @@snip [App.tsx](../../../../frontend/src/App.tsx) { #add-protected-route }

Add menu item action for protected route

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







