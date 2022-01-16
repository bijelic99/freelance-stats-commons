package com.freelanceStats.commons.models.indexedJob

case class Budget(
    minimum: Double,
    maximum: Double,
    currency: Option[Currency]
)
