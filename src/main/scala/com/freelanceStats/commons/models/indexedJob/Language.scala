package com.freelanceStats.commons.models.indexedJob

case class Language(
    id: String,
    shortName: String,
    names: Seq[String]
) extends ReferencedByAlias
