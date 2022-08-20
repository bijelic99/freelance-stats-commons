package com.freelanceStats.jwtAuth.actions

import com.freelanceStats.jwtAuth.models.{AuthenticatedRequest, JwtToken}
import com.freelanceStats.jwtAuth.services.JwtService
import play.api.Logger
import play.api.mvc.Results.{Forbidden, Unauthorized}
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class JwtAuthActionBuilder @Inject() (
    jwtService: JwtService,
    val parser: BodyParsers.Default
)(implicit val executionContext: ExecutionContext)
    extends ActionBuilder[Request, AnyContent]
    with ActionRefiner[Request, AuthenticatedRequest] {

  private val log: Logger = Logger(getClass)

  override protected def refine[A](
      request: Request[A]
  ): Future[Either[Result, AuthenticatedRequest[A]]] =
    request.headers
      .get("Authorization")
      .map { headerText =>
        val token = JwtToken.fromHeader()(headerText)
        jwtService
          .decodeToken(token)
          .map {
            case (user, _, true) =>
              log.trace(
                s"User with id of: '${user.id}' authenticated successfully"
              )
              Right(AuthenticatedRequest(user, request))
            case (user, _, false) =>
              log.warn(
                s"User with id of: '${user.id}' tried to authenticate with invalid token"
              )
              Left(Forbidden)
          }
      }
      .getOrElse(Future.successful(Left(Unauthorized)))
}
