package com.freelanceStats.commons.models

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime

case class UnsavedRawJob(
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    contentType: String,
    contentSize: Long,
    data: Source[ByteString, _]
)

object UnsavedRawJob {
  implicit class UnsavedRawJobOps(job: UnsavedRawJob) {
    def toRawJob(fileReference: FileReference) =
      RawJob(
        sourceId = job.sourceId,
        source = job.sourceId,
        created = job.created,
        modified = job.modified,
        fileReference = fileReference
      )
  }
}
