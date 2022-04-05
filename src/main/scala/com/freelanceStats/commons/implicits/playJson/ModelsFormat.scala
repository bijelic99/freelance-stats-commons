package com.freelanceStats.commons.implicits.playJson

import com.freelanceStats.commons.models.RawJob
import com.freelanceStats.commons.models.indexedJob._
import com.freelanceStats.s3Client.models.FileReference
import play.api.libs.json._

import scala.concurrent.duration.{Duration, FiniteDuration}

object ModelsFormat {
  import DateTimeFormat._

  implicit val finiteDurationFormat: Format[FiniteDuration] =
    new Format[FiniteDuration] {
      override def reads(json: JsValue): JsResult[FiniteDuration] =
        json match {
          case JsString(duration) =>
            Duration(duration) match {
              case _: Duration.Infinite => JsError("Cant be infinite duration")
              case finiteDuration: FiniteDuration => JsSuccess(finiteDuration)
            }
          case _ =>
            JsError("Incorrect format")
        }

      override def writes(o: FiniteDuration): JsValue =
        JsString(o.toString())
    }

  implicit val fileReferenceFormat: Format[FileReference] =
    Json.format[FileReference]
  implicit val rawJobFormat: Format[RawJob] = Json.format[RawJob]
  implicit val currencyFormat: Format[Currency] = Json.format[Currency]
  implicit val budgetFormat: Format[Budget] = Json.format[Budget]
  implicit val categoryFormat: Format[Category] = Json.format[Category]
  implicit val cityFormat: Format[City] = Json.format[City]
  implicit val countryFormat: Format[Country] = Json.format[Country]
  implicit val locationFormat: Format[Location] = Json.format[Location]
  implicit val languageFormat: Format[Language] = Json.format[Language]
  implicit val hourlyFormat: Format[Hourly] = Json.format[Hourly]
  implicit val fixedPriceFormat: Format[FixedPrice] = Json.format[FixedPrice]
  implicit val paymentFormat: Format[Payment] = Json.format[Payment]
  implicit val positionTypeFormat: Format[PositionType] = Format(
    fjs = Reads {
      case JsString("InPerson") => JsSuccess(InPerson)
      case JsString("Remote")   => JsSuccess(Remote)
      case _                    => JsError("Unknown position type")
    },
    tjs = Writes {
      case InPerson => JsString("InPerson")
      case Remote   => JsString("Remote")
    }
  )
  implicit val timezoneFormat: Format[Timezone] = Json.format[Timezone]
  implicit val employerFormat: Format[Employer] = Json.format[Employer]
  implicit val indexedJobFormat: Format[IndexedJob] = Json.format[IndexedJob]
}
