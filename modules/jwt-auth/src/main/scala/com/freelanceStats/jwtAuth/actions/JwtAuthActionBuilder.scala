package com.freelanceStats.jwtAuth.actions

import com.freelanceStats.jwtAuth.models.{AuthenticatedRequest, JwtToken}
import com.freelanceStats.jwtAuth.services.JwtService
import play.api.Logger
import play.api.mvc.Results.Unauthorized
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class JwtAuthActionBuilder @Inject() (
    jwtService: JwtService,
    val parser: BodyParsers.Default
)(implicit val executionContext: ExecutionContext)
    extends ActionBuilder[AuthenticatedRequest, AnyContent] {

  private val log: Logger = Logger(getClass)

  override def invokeBlock[A](
      request: Request[A],
      block: AuthenticatedRequest[A] => Future[Result]
  ): Future[Result] =
    request.headers
      .get("Authorization")
      .map { headerText =>
        val token = JwtToken.fromHeader()(headerText)
        jwtService
          .decodeToken(token)
          .flatMap {
            case (user, _, true) =>
              log.trace(
                s"User with id of: '${user.id}' authenticated successfully"
              )
              block(AuthenticatedRequest(user, request))
            case (user, _, false) =>
              log.warn(
                s"User with id of: '${user.id}' tried to authenticate with invalid token"
              )
              Future.successful(Unauthorized)
          }
          .recover{ case t =>
            log.warn("Unexpected error", t)
            Unauthorized
          }
      }
      .getOrElse(Future.successful(Unauthorized))
}
