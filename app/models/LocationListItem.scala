package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID

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
  implicit val LocationListItemFormatter = Json.format[LocationListItem]
}

