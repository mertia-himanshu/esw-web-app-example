package org.tmt.sample

import akka.http.scaladsl.server.Route
import esw.http.template.wiring.ServerWiring
import org.tmt.sample.core.RaImpl
import org.tmt.sample.http.SampleRoute

class SampleWiring(val port: Option[Int]) extends ServerWiring {
  override val actorSystemName: String = "sample-actor-system"
  lazy val raImpl                      = new RaImpl()
  import actorRuntime.ec
  override lazy val routes: Route = new SampleRoute(raImpl, securityDirectives).route
}
