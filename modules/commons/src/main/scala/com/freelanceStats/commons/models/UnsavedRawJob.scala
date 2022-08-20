package com.freelanceStats.commons.models

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.freelanceStats.s3Client.models.FileReference

case class UnsavedRawJob(
    sourceId: String,
    source: String,
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
        fileReference = fileReference
      )
  }
}
