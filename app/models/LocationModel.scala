package models

import play.api.libs.json.Json
import reactivemongo.bson.BSONObjectID
import reactivemongo.play.json._

case class LocationModel(
  _id: BSONObjectID,
  location: Location,
  title: String,
  price: Double = 0,
  tags: Seq[Double] = Seq(0,0,0,0,0,0),
  likes: Int = 0,
  hasQrCode: Boolean
)

case class MainPageResponse (
  locationModels: Seq[LocationModel]
)

object LocationModel {
  import Location.locationFormatter
  implicit val locationFormatter = Json.format[LocationModel]
}

object MainPageResponse {
  import LocationModel.locationFormatter
  implicit val mainPageResponseFormatter = Json.format[MainPageResponse]
}
