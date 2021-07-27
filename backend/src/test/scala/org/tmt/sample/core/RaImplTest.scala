package org.tmt.sample.core

import org.scalatest.concurrent.ScalaFutures.convertScalaFuture
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.core.models.RaRequest
// #raToString-impl-test
class RaImplTest extends AnyWordSpec with Matchers {
  "RaImpl" must {
    "convert Ra to String" in {
      val raImpl     = new RaImpl()
      val raResponse = raImpl.raToString(RaRequest(2.13))
      raResponse.futureValue.formattedRa should ===("8h 8m 9.602487087684134s")
    }
  }
}
// #raToString-impl-test