package com.freelanceStats.commons.models.indexedJob

import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime

case class IndexedJob(
    id: String,
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    fileReference: FileReference,
    title: String,
    description: String,
    categories: Seq[Category],
    payment: Payment,
    language: Option[Language],
    positionType: Option[PositionType],
    location: Option[Location],
    employer: Option[Employer]
)
