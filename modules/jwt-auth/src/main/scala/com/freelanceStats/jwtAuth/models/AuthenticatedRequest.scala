package com.freelanceStats.jwtAuth.models

import com.freelanceStats.commons.models.User
import play.api.mvc.{Request, WrappedRequest}

case class AuthenticatedRequest[A](user: User, request: Request[A])
    extends WrappedRequest[A](request)
