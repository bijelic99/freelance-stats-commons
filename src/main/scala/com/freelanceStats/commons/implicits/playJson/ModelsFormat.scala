package com.freelanceStats.commons.implicits.playJson

import com.freelanceStats.commons.models.{IndexedJob, RawJob}
import com.freelanceStats.s3Client.models.FileReference
import play.api.libs.json.{Format, Json}

object ModelsFormat {
  import DateTimeFormat._

  implicit val fileReferenceFormat: Format[FileReference] =
    Json.format[FileReference]
  implicit val rawJobFormat: Format[RawJob] = Json.format[RawJob]
  implicit val indexedJobFormat: Format[IndexedJob] = Json.format[IndexedJob]
}
