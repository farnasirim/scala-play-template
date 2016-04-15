package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class LocationModel(
  _id: BSONObjectID,
  location: Location,
  title: String,
  price: Double = 0,
  checkins: Int = 0,
  votes: Int = 0,
  hasQrCode: Boolean,
  tags: Seq[Double] = Seq(0,0,0,0,0,0),
  pictures: Seq[BSONObjectID]
)

object LocationModel {
  import Location.locationFormatter
  implicit val locationModelFormatter = Json.format[LocationModel]
}
