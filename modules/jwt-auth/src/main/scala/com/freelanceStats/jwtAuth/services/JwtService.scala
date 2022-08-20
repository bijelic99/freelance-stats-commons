package com.freelanceStats.jwtAuth.services

import com.freelanceStats.commons.models.User
import com.freelanceStats.jwtAuth.configurations.JwtServiceConfiguration
import com.freelanceStats.jwtAuth.models.JwtToken
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtJson}
import play.api.libs.json.Json

import java.time.Clock
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class JwtService @Inject() (
    val configuration: JwtServiceConfiguration
) {
  import com.freelanceStats.commons.implicits.playJson.ModelsFormat._

  implicit val clock: Clock = Clock.systemUTC()

  def encodeToken(
      user: User
  )(implicit ec: ExecutionContext): Future[JwtToken] = Future {
    val claim = JwtClaim(
      issuer = configuration.issuer,
      content = Json.toJson(user).toString(),
      expiration = configuration.tokenDuration.map(duration =>
        (clock.millis() + duration.toMillis) / 1000
      )
    )
    JwtToken(JwtJson.encode(claim, configuration.key, configuration.algorithm))
  }

  def decodeToken(
      token: JwtToken
  )(implicit ec: ExecutionContext): Future[(User, JwtClaim, Boolean)] = Future {
    val claim = JwtJson
      .decode(token.token, configuration.key, JwtAlgorithm.allHmac())
      .get
    val user = Json.parse(claim.content).as[User]
    val isValidClaim =
      configuration.issuer.fold(claim.isValid)(claim.isValid(_))
    (user, claim, isValidClaim)
  }

}
