package org.tmt.sample.core

import org.scalatest.matchers.must.Matchers
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.wordspec.AnyWordSpec
import org.tmt.sample.core.models.{RaRequest, RaResponse}

class RaImplTest extends AnyWordSpec with Matchers {

  "RaImpl" must {
    "convert Ra to String" in {
      val raImpl = new RaImpl()
      raImpl.raToString(RaRequest(2.13)) should ===(RaResponse("8h 8m 9.602487087684134s"))
    }
  }
}
