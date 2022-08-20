package com.freelanceStats.commons.models.indexedJob

import scala.concurrent.duration.FiniteDuration

sealed trait Payment {
  def budget: Budget
}

case class Hourly(
    workDuration: FiniteDuration,
    repeating: Boolean,
    repeatInterval: Option[FiniteDuration],
    budget: Budget
) extends Payment

case class FixedPrice(
    budget: Budget
) extends Payment
