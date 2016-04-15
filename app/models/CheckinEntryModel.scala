package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class CheckinEntryModel(
  _id: BSONObjectID = BSONObjectID.generate(),
  locationId: String,
  title: String,
  price: Int= 0,
  tags: Seq[Int] = Seq(0,0,0,0,0,0),
  pictures: Seq[String],
  desc: String
)

object CheckinEntryModel{
  implicit val checkinEntryFormatter = Json.format[CheckinEntryModel]
}

