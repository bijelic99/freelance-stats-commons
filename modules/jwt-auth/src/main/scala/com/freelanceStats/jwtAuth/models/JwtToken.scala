package com.freelanceStats.jwtAuth.models

case class JwtToken(
    token: String
)

object JwtToken {
  def fromHeader(schema: String = "Bearer")(headerText: String): JwtToken =
    JwtToken(headerText.stripPrefix(s"$schema "))
}
