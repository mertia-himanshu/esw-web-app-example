package org.tmt.sample.http

import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post}
import akka.http.scaladsl.server.Route
import csw.aas.http.SecurityDirectives
import org.tmt.sample.core.RaImpl
import org.tmt.sample.core.models.RaRequest

import scala.concurrent.ExecutionContext

class SampleRoute(raImpl: RaImpl, securityDirectives: SecurityDirectives)(implicit
    ec: ExecutionContext
) extends HttpCodecs {

  val route: Route = post {
    path("formattedRa") {
      entity(as[RaRequest]) { raRequest =>
        println(raRequest)
        complete(raImpl.raToString(raRequest))
      }
    }
  }

}
