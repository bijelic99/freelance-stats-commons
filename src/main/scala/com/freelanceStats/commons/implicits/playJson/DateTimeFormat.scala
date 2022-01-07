package com.freelanceStats.commons.implicits.playJson

import org.joda.time.DateTime
import play.api.libs.json.{Format, JodaReads, JodaWrites}

object DateTimeFormat {
  implicit val dateTimeFormat: Format[DateTime] =
    Format.apply(
      JodaReads.DefaultJodaDateTimeReads,
      JodaWrites.JodaDateTimeWrites
    )
}
