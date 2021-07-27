package org.tmt.sample.service

import org.tmt.sample.core.models.{RaRequest, RaResponse}

import scala.concurrent.Future

  // #raToString-contract
trait RaService {
  def raToString(raRequest: RaRequest): Future[RaResponse]
  // #raToString-contract

  // #getRaValues-contract
  def getRaValues: Future[List[RaResponse]]
  // #getRaValues-contract

  // #raToString-contract
}
  // #raToString-contract
