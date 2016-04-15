package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class LocationListItem (
  id: BSONObjectID,
  title: String,
  location: Location,
  distance: Double,
  checkins: Int,
  hasQrCode: Boolean,
  price: Double,
  tags: Seq[Double],
  pictures: Seq[BSONObjectID]
)

object LocationListItem{
  import Location.locationFormatter
  implicit val locationListItemFormatter = Json.format[LocationListItem]
}
