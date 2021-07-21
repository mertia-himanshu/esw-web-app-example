package org.tmt.sample.http

import csw.location.api.codec.LocationCodecs
import io.bullet.borer.Codec
import io.bullet.borer.compat.AkkaHttpCompat
import io.bullet.borer.derivation.MapBasedCodecs.deriveCodec
import org.tmt.sample.core.models.{RaRequest, RaResponse}

object HttpCodecs extends HttpCodecs

trait HttpCodecs extends AkkaHttpCompat with LocationCodecs {
  // #add-codec
  implicit lazy val raResponseCodec: Codec[RaResponse] = deriveCodec
  implicit lazy val raRequestCodec: Codec[RaRequest]   = deriveCodec
  // #add-codec
}
