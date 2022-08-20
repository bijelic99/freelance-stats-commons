package com.freelanceStats.jwtAuth.actions

import akka.util.LineNumbers.Result
import com.freelanceStats.jwtAuth.models.JwtToken
import com.freelanceStats.jwtAuth.services.JwtService
import play.api.Logger
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc.{Action, BodyParser, Request}

import scala.concurrent.{ExecutionContext, Future}

case class JwtAuthAction[A](action: Action[A])(implicit jwtService: JwtService)
    extends Action[A] {

  private val log: Logger = Logger(getClass)

  override def parser: BodyParser[A] = action.parser

  override def executionContext: ExecutionContext = action.executionContext

  def apply(request: Request[A]): Future[Result] =
    request.headers
      .get("Authorization")
      .fold(Future.successful(Unauthorized)) { headerText =>
        val token = JwtToken.fromHeader()(headerText)
        jwtService
          .decodeToken(token)(executionContext)
          .flatMap {
            case (_, _, true) =>
              action(request)
            case (user, _, false) =>
              log.warn(
                s"User with if of: '${user.id}' tried to log in with invalid token"
              )
              Future.successful(Forbidden)
          }(executionContext)
      }
}
