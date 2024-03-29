package com.freelanceStats.commons.models.indexedJob

case class Country(
    id: String,
    name: String,
    officialName: String,
    alpha2Code: String,
    alpha3Code: String,
    numeric: Option[String],
    region: String,
    subregion: Option[String],
    continents: Seq[String],
    startOfWeek: String,
    coordinates: Coordinates,
    timezones: Seq[String]
) extends ReferencedByAlias
