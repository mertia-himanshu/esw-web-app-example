package org.tmt.sample.db

import csw.database.scaladsl.JooqExtentions.{RichQuery, RichResultQuery}
import csw.params.core.models.Angle
import org.jooq.DSLContext
import org.tmt.sample.core.models.{RaRequest, RaResponse}
import org.tmt.sample.service.RaService

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

// #add-dsl-context
class RaImpl(dsl: DSLContext)(implicit ec: ExecutionContext) extends RaService {
  // #add-dsl-context

  // #raToString-impl
  override def raToString(raRequest: RaRequest): Future[RaResponse] = {
    val formattedRa = Angle.raToString(raRequest.raInDecimals)
    insertRa(formattedRa).map(id => RaResponse(id, formattedRa))
  }
  // #raToString-impl

  // #get-raValues-from-db
  override def getRaValues: Future[scala.List[RaResponse]] =
    dsl.resultQuery("SELECT * from RaValues").fetchAsyncScala[RaResponse]
  // #get-raValues-from-db

  // #insert-raValue-in-db
  private def insertRa(formattedRa: String): Future[String] = {
    val id = UUID.randomUUID().toString
    dsl
      .query(s"INSERT INTO RaValues (id,formattedRa) values (?,?)", id, formattedRa)
      .executeAsyncScala()
      .map {
        case x if x < 0 => throw new RuntimeException(s"Failed to insert the ra value $formattedRa")
        case _          => id
      }
  }
  // #insert-raValue-in-db
}
