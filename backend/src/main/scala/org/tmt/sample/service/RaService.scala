package org.tmt.sample.service

import org.tmt.sample.core.models.{RaRequest, RaResponse}

import scala.concurrent.Future

trait RaService {
  // #raToString-contract
  def raToString(raRequest: RaRequest): Future[RaResponse]
  // #raToString-contract

  // #getRaValues-contract
  def getRaValues: Future[List[RaResponse]]
  // #getRaValues-contract

}
