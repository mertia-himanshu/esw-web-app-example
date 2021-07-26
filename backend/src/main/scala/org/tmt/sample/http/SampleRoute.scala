package org.tmt.sample.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import csw.aas.http.AuthorizationPolicy.RealmRolePolicy
import csw.aas.http.SecurityDirectives
import org.tmt.sample.core.models.RaRequest
import org.tmt.sample.service.RaService

import scala.concurrent.ExecutionContext

//#raImpl-ref
class SampleRoute(raService: RaService, securityDirectives: SecurityDirectives)(implicit
    ec: ExecutionContext
) extends HttpCodecs {
//#raImpl-ref

  val route: Route = {
    // #add-route
    path("raValues") {
      post {
        entity(as[RaRequest]) { raRequest =>
          complete(raService.raToString(raRequest))
        }
      }
    } ~
    // #add-route
    // #add-secured-route
    path("securedRaValues") {
      post {
        securityDirectives.sPost(RealmRolePolicy("Esw-user")) { _ =>
          entity(as[RaRequest]) { raRequest =>
            complete(raService.raToString(raRequest))
          }
        }
      }
    } ~
    // #add-secured-route
    // #add-get-values-route
    path("raValues") {
      get {
        complete(raService.getRaValues)
      }
    }
    // #add-get-values-route
  }
}
