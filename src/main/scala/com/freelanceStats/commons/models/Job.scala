package com.freelanceStats.commons.models

import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime
import play.api.libs.json.JsObject

case class Job(
    id: Option[String],
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    rawData: Option[JsObject] = None,
    indexedData: Option[JsObject] = None,
    fileReference: Option[FileReference]
)
