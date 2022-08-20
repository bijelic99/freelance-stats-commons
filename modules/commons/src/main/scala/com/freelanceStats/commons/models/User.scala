package com.freelanceStats.commons.models

import org.joda.time.DateTime

case class User(
    id: String,
    username: String,
    email: String,
    firstName: String,
    lastName: String,
    birthDate: DateTime,
    deleted: Boolean
)
