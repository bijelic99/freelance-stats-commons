package com.freelanceStats.commons.models.indexedJob

case class City(
    id: String,
    name: String,
    coordinates: Coordinates
) extends ReferencedByAlias
