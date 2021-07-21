package org.tmt.sample.http

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.BasicDirectives
import akka.http.scaladsl.testkit.ScalatestRouteTest
import csw.aas.http.SecurityDirectives
import csw.location.api.models.ComponentType.Service
import csw.location.api.models.Connection.HttpConnection
import csw.location.api.models._
import csw.prefix.models.Prefix
import io.bullet.borer.compat.AkkaHttpCompat
import msocket.security.models.AccessToken
import org.mockito.MockitoSugar.{mock, reset, verify, when}
import org.scalatest.BeforeAndAfterEach
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.TestHelper
import org.tmt.sample.core.RaImpl
import org.tmt.sample.core.models.{RaRequest, RaResponse}

class SampleRouteTest extends AnyWordSpec with ScalatestRouteTest with AkkaHttpCompat with BeforeAndAfterEach with HttpCodecs {

  // #add-mock
  private val service1: RaImpl                       = mock[RaImpl]
  // #add-mock
  private val securityDirectives: SecurityDirectives = mock[SecurityDirectives]
  private val token: AccessToken                     = mock[AccessToken]
  private val accessTokenDirective                   = BasicDirectives.extract(_ => token)

  // #add-mock-dep
  private val route: Route = new SampleRoute(service1, securityDirectives).route
  // #add-mock-dep

  override protected def beforeEach(): Unit = reset(securityDirectives)

  "SampleRoute" must {
    // #add-route-test
    "formattedRa must call raToString" in {
      val response  = RaResponse("some-value")
      val raRequest = RaRequest(2.13)
      when(service1.raToString(raRequest)).thenReturn(response)

      Post("/formattedRa", raRequest) ~> route ~> check {
        verify(service1).raToString(raRequest)
        responseAs[RaResponse] should ===(response)
      }
    }
    // #add-route-test
  }

  val connection: Connection.HttpConnection = HttpConnection(ComponentId(Prefix(TestHelper.randomSubsystem, "sample"), Service))
}
