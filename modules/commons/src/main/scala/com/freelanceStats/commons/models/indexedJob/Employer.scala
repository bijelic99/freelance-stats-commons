package com.freelanceStats.commons.models.indexedJob

case class Employer(
    sourceId: String,
    source: String,
    username: String,
    location: Option[Location],
    primaryLanguage: Option[Language],
    primaryCurrency: Option[Currency],
    timezone: Option[Timezone]
)
