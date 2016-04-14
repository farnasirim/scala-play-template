package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class LocationModel(
  _id: BSONObjectID,
  location: Location,
  title: String
)

object LocationModel {
  import Location.formatter
  implicit val formatter = Json.format[LocationModel]
}
