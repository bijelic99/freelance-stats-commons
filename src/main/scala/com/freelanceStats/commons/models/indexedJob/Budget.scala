package com.freelanceStats.commons.models.indexedJob

case class Budget(
    minimumUsd: Option[Double],
    maximumUsd: Option[Double],
    minimum: Option[Double],
    maximum: Option[Double],
    currency: Option[Currency]
)
