package com.freelanceStats.commons.streamMaintainer

import scala.concurrent.duration.{DurationInt, FiniteDuration}

trait StreamMaintainerConfiguration {
  def minBackoff: FiniteDuration
  def maxBackoff: FiniteDuration
  def randomFactor: Double
}

object StreamMaintainerConfiguration {
  object Default extends StreamMaintainerConfiguration {
    override val minBackoff: FiniteDuration = 1.second
    override val maxBackoff: FiniteDuration = 1.minute
    override val randomFactor: Double = 0.2
  }
}
