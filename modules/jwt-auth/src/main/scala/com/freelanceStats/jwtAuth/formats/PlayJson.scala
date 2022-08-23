package com.freelanceStats.jwtAuth.formats

import com.freelanceStats.jwtAuth.models.JwtToken
import play.api.libs.json.{Json, OFormat}

object PlayJson {
  implicit val tokenFormat: OFormat[JwtToken] = Json.format[JwtToken]
}
