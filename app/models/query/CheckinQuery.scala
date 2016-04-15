package models.query

/**
  * Created by colonelmo on 4/15/16.
  */

import models.Location
import play.api.libs.json.Json

case class CheckinQuery(
  locationId: String,
  userLocation: Location
)


object CheckinQuery{
  implicit val checkinQueryFormatter = Json.format[CheckinQuery]
}

