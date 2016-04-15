package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class LocationModel(
  _id: BSONObjectID = BSONObjectID.generate(),
  location: Location,
  title: String,
  price: Double = 0,
  checkins: Seq[CheckinEntryModel],
  votes: Int = 0,
  hasQrCode: Boolean,
  tags: Seq[Double] = Seq(0,0,0,0,0,0),
  pictures: Seq[String]
)

object LocationModel {
  import Location.locationFormatter
  import CheckinEntryModel.checkinEntryFormatter
  implicit val locationModelFormatter = Json.format[LocationModel]
}
