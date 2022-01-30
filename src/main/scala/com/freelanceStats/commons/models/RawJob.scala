package com.freelanceStats.commons.models

import com.freelanceStats.commons.models.indexedJob.{
  Category,
  Employer,
  IndexedJob,
  Language,
  Location,
  Payment,
  PositionType
}
import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime

case class RawJob(
    sourceId: String,
    source: String,
    fileReference: FileReference
)

object RawJob {
  implicit class RawJobOps(rawJob: RawJob) {
    def toIndexedJob(
        id: String,
        created: DateTime,
        modified: DateTime,
        title: String,
        description: String,
        valid: Boolean,
        categories: Seq[Category],
        payment: Payment,
        language: Option[Language],
        positionType: Option[PositionType],
        location: Option[Location],
        employer: Option[Employer]
    ): IndexedJob =
      IndexedJob(
        id,
        rawJob.sourceId,
        rawJob.source,
        created,
        modified,
        rawJob.fileReference,
        title,
        description,
        valid,
        categories,
        payment,
        language,
        positionType,
        location,
        employer
      )
  }
}
