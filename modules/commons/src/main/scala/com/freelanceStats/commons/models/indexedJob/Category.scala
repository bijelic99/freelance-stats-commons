package com.freelanceStats.commons.models.indexedJob

case class Category(
    id: String,
    name: String,
    topLevel: Boolean
) extends ReferencedByAlias
