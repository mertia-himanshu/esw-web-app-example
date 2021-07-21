package org.tmt.sample.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import csw.aas.http.AuthorizationPolicy.RealmRolePolicy
import csw.aas.http.SecurityDirectives
import org.tmt.sample.core.RaImpl
import org.tmt.sample.core.models.RaRequest

import scala.concurrent.ExecutionContext

//#raImpl-ref
class SampleRoute(raImpl: RaImpl, securityDirectives: SecurityDirectives)(implicit
    ec: ExecutionContext
) extends HttpCodecs {
//#raImpl-ref

  val route: Route = post {
    // #add-route
    path("formattedRa") {
      entity(as[RaRequest]) { raRequest =>
        complete(raImpl.raToString(raRequest))
      }
    } ~
    // #add-route
    // #add-secured-route
    path("securedFormattedRa") {
      securityDirectives.sPost(RealmRolePolicy("Esw-user")) { _ =>
        entity(as[RaRequest]) { raRequest =>
          complete(raImpl.raToString(raRequest))
        }
      }
    }
    // #add-secured-route
  }
}
