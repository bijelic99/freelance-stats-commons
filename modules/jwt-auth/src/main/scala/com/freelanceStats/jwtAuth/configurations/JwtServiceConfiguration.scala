package com.freelanceStats.jwtAuth.configurations

import pdi.jwt.JwtAlgorithm
import play.api.Configuration

import java.nio.file.{Files, Path}
import javax.inject.Inject
import scala.concurrent.duration.{DurationInt, FiniteDuration}

class JwtServiceConfiguration @Inject() (configuration: Configuration) {
  val keyFile: String = configuration
    .getOptional[String]("jwt.keyFile")
    .getOrElse("./jwt-auth-key.txt")
  val key: String = Files.readString(Path.of(keyFile))
  val algorithm: JwtAlgorithm = JwtAlgorithm.fromString(
    configuration.getOptional[String]("jwt.algorithm").getOrElse("HS256")
  )
  val issuer: Option[String] = configuration.getOptional[String]("jwt.issuer")
  val tokenDuration: Option[FiniteDuration] =
    configuration.getOptional[FiniteDuration]("jwt.tokenDuration")
}
