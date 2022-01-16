package com.freelanceStats.commons.models

import com.freelanceStats.s3Client.models.FileReference

case class RawJob(
    sourceId: String,
    source: String,
    fileReference: FileReference
)
