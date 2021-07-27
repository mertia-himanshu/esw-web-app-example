package org.tmt.sample.impl

import akka.http.scaladsl.server.Route
import esw.http.template.wiring.ServerWiring
import org.tmt.sample.http.SampleRoute

class SampleWiring(val port: Option[Int]) extends ServerWiring {
  override lazy val actorSystemName: String = "sample-actor-system"

  // #raImpl-ref
  lazy val raImpl = new RaImpl()
  // #raImpl-ref

  // #add-route
  import actorRuntime.ec
  override lazy val routes: Route = new SampleRoute(raImpl, securityDirectives).route
  // #add-route
}
