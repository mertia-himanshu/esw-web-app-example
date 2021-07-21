# Adding Authentication

## Add new route with protection

Scala
: @@snip [SampleRoute.scala](../../../../backend/src/main/scala/org/tmt/sample/http/SampleRoute.scala) { #add-secured-route }

## Add test for newly added route

Scala
: @@snip [SampleRouteTest.scala](../../../../backend/src/test/scala/org/tmt/sample/http/SampleRouteTest.scala) { #add-secured-route-test }

## Add integration tests for newly added route

Add positive test for user with required role

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-test }

Add Failure test for user without required role

Scala
: @@snip [SampleAppIntegrationTest.scala](../../../../backend/src/test/scala/org/tmt/sample/integration/SampleAppIntegrationTest.scala) { #add-secured-route-failure-test }




