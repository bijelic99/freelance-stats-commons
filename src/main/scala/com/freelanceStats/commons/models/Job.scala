package com.freelanceStats.commons.models

import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.freelanceStats.s3Client.models.FileReference
import org.joda.time.DateTime

sealed trait Job {
  def id: Option[String]
  def sourceId: String
  def source: String
  def created: DateTime
  def modified: DateTime
}

case class UnsavedRawJob(
    id: Option[String] = None,
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    data: Source[ByteString, _]
) extends Job

case class RawJob(
    id: Option[String] = None,
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    fileReference: FileReference
) extends Job

case class IndexedJob(
    id: Option[String] = None,
    sourceId: String,
    source: String,
    created: DateTime,
    modified: DateTime,
    fileReference: FileReference
) extends Job
