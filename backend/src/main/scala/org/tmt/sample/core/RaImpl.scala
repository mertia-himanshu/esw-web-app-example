package org.tmt.sample.core

import csw.params.core.models.Angle
import org.tmt.sample.core.models.{RaRequest, RaResponse}
import org.tmt.sample.service.RaService

import java.util.UUID
import scala.concurrent.Future

class RaImpl extends RaService {

  // #raToString-impl
  override def raToString(raRequest: RaRequest): Future[RaResponse] = {
    val formattedRa = Angle.raToString(raRequest.raInDecimals)
    Future.successful(RaResponse(UUID.randomUUID().toString, formattedRa))
  }
  // #raToString-impl

  override def getRaValues: Future[List[RaResponse]] = ???
}
