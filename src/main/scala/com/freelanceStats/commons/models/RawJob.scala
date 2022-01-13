package com.freelanceStats.commons.models

import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime

case class RawJob(
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    fileReference: FileReference
)
