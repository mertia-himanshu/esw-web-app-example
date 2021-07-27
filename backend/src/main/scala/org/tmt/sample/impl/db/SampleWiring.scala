package org.tmt.sample.impl.db

import akka.http.scaladsl.server.Route
import csw.database.DatabaseServiceFactory
import csw.network.utils.SocketUtils
import esw.http.template.wiring.ServerWiring
import org.jooq.DSLContext
import org.tmt.sample.http.SampleRoute

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class SampleWiring(val port: Option[Int]) extends ServerWiring {
  override val actorSystemName: String = "sample-actor-system"
  import actorRuntime.ec

  // #db-wiring-setup
  private lazy val databaseServiceFactory = new DatabaseServiceFactory(actorRuntime.typedSystem)
  private val dbName                      = settings.config.getString("dbName")
  private val dbUsernameHolder            = settings.config.getString("dbUsernameHolder")
  private val dbPasswordHolder            = settings.config.getString("dbPasswordHolder")
  private lazy val dslContext: DSLContext =
    Await.result(
      databaseServiceFactory.makeDsl(cswServices.locationService, dbName, dbUsernameHolder, dbPasswordHolder),
      10.seconds
    )
  // #db-wiring-setup

  // #raImpl-db-ref
  lazy val raImpl = new RaImpl(dslContext)
  // #raImpl-db-ref

  // #add-route
  override lazy val routes: Route = new SampleRoute(raImpl, securityDirectives).route
  // #add-route
}
