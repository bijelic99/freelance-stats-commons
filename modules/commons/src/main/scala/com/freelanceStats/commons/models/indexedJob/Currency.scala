package com.freelanceStats.commons.models.indexedJob

case class Currency(
    id: String,
    name: String,
    shortName: String,
    numeric: String,
    precision: Option[Int],
    countries: Seq[String]
) extends ReferencedByAlias
