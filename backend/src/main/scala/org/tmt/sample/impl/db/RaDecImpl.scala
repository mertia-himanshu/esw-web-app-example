package org.tmt.sample.impl.db

import csw.database.scaladsl.JooqExtentions.{RichQuery, RichResultQuery}
import csw.params.core.models.Angle
import org.jooq.DSLContext
import org.tmt.sample.core.models.{RaDecRequest, RaDecResponse}
import org.tmt.sample.service.RaDecService

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

// #add-dsl-context
class RaDecImpl(dsl: DSLContext)(implicit ec: ExecutionContext) extends RaDecService {
  // #add-dsl-context

  // #raDecToString-impl
  override def raDecToString(raDecRequest: RaDecRequest): Future[RaDecResponse] = {
    val formattedRa  = Angle.raToString(raDecRequest.raInDecimals)
    val formattedDec = Angle.deToString(raDecRequest.decInDecimals)
    insertRaDec(formattedRa, formattedDec).map(id => RaDecResponse(id, formattedRa, formattedDec))
  }
  // #raDecToString-impl

  // #get-raDecValues-from-db
  override def getRaDecValues: Future[scala.List[RaDecResponse]] =
    dsl.resultQuery("SELECT * from RaDecValues").fetchAsyncScala[RaDecResponse]
  // #get-raDecValues-from-db

  // #insert-raDecValue-in-db
  private def insertRaDec(formattedRa: String, formattedDec: String) = {
    val id = UUID.randomUUID().toString
    dsl
      .query(s"INSERT INTO RaDecValues (id,formattedRa,formattedDec) values (?,?)", id, formattedRa, formattedDec)
      .executeAsyncScala()
      .map {
        case x if x < 0 => throw new RuntimeException(s"Failed to insert the (ra ,dec) value ($formattedRa, $formattedDec )")
        case _          => id
      }
  }
  // #insert-raDecValue-in-db
}
