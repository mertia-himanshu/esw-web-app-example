package org.tmt.sample.impl

import csw.params.core.models.Angle
import org.tmt.sample.core.models.{RaRequest, RaResponse}
import org.tmt.sample.service.RaService

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.Future

// #raToString-impl
class RaImpl extends RaService {

  var raValues = mutable.ListBuffer[RaResponse]()

  override def raToString(raRequest: RaRequest): Future[RaResponse] = {
    val formattedRa = Angle.raToString(raRequest.raInDecimals)
    val raResponse  = RaResponse(UUID.randomUUID().toString, formattedRa)
    raValues.append(raResponse)
    Future.successful(raResponse)
  }
  // #raToString-impl

  // #getRaValues-impl
  override def getRaValues: Future[List[RaResponse]] = Future.successful(raValues.toList)
  // #getRaValues-impl
  // #raToString-impl
}
// #raToString-impl
