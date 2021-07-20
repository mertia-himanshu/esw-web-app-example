package org.tmt.sample.core

import csw.params.core.models.Angle
import org.tmt.sample.core.models.{RaRequest, RaResponse}

class RaImpl {
  def raToString(raRequest: RaRequest): RaResponse = {
    val formattedRa = Angle.raToString(raRequest.raInDecimals)
    RaResponse(formattedRa)
  }
}
