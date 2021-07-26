# Adding Authentication

## Add protected route in backend

### Add new route with protection

We will add a new route in `SampleRoute.scala` which is protected, to access this route, request should contain a token containing role `esw-user`.
We have set up some [sample users](https://tmtsoftware.github.io/csw/apps/cswservices.html#predefined-users-) when we start `csw-services` with Authentication.

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-secured-route }

@@@note
tilda (~) is used as a path concatenator in akka dsl.
You can safely remove it for now. However, in the following section of this tutorial we are going to add new routes to this file. you can add it again.
@@@

### Add test for newly added route

Add test in `SampleRouteTest.scala` for protected route

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-secured-route-test }

### Add integration tests for newly added route

Add positive test for user with required role in `SampleAppIntegrationTest.scala`

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-test }

Add Failure test for user without required role in `SampleAppIntegrationTest.scala`

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-failure-test }

## Consume protected route in frontend

### Add secured Fetch

Add the following method in `api.ts` which post request to `/securedRaValues` backend route.

Typescript
: @@snip [api.ts](../../../../frontend/src/utils/api.ts) { #secured-fetch-data }

### Add our React component to consume secured fetch

* In `pages` folder create `SecuredRa.tsx`
* Add the following form to the `SecuredRa` react component

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #add-component }

### Use secured fetch in our component

Typescript
: @@snip [SecuredRa.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #use-fetch }

You would require locationService instance for getting backend url. This instance is available via context named `LocationServiceProvider`.
Add the following as first line inside the `SecuredRa` component.

Typescript
: @@snip [Ra.tsx](../../../../frontend/src/components/pages/SecuredRa.tsx) { #use-location-service-from-context }

Add protected route in `App.tsx`

Typescript
: @@snip [App.tsx](../../../../frontend/src/routes/Routes.tsx) { #add-protected-route }

Update Menubar

* Remove the `/greeting` menu item
* Update menu item action for protected route in `App.tsx` from `AdminGreeting` to `SecuredRa`.

Typescript
: @@snip [App.tsx](../../../../frontend/src/components/menu/MenuBar.tsx) { #add-protected-route-action }

Add `SecuredRa.test.tsx` in `test/pages`

Typescript
: @@snip [SecuredRa.test.tsx](../../../../frontend/test/pages/SecuredRa.test.tsx) { #add-test }

Make use of generated `Login` and `Logout` components .

Add menu items actions for `logging in` and `logging out` in `MenuBar.tsx`.

Typescript
: @@snip [App.tsx](../../../../frontend/src/components/menu/MenuBar.tsx) { #add-login-logout }

Use Auth Hook to get handle on auth store.

Typescript
: @@snip [App.tsx](../../../../frontend/src/components/menu/MenuBar.tsx) { #use-auth-hook }
