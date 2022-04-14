package com.freelanceStats.commons.models.indexedJob

case class Timezone(
    id: String,
    name: String,
    abbreviation: String,
    utcOffset: String,
    utcOffsetRaw: Long
) extends ReferencedByAlias
