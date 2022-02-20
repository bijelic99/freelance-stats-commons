package com.freelanceStats.commons.models.indexedJob

sealed trait PositionType extends ReferencedByAlias

case object InPerson extends PositionType
case object Remote extends PositionType
