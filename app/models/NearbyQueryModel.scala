package models

import play.api.libs.json.Json

case class NearbyQueryModel(
  lng: Double,
  lat: Double,
  radius: Double,
  count: Int
)

object NearbyQueryModel {
  implicit val formatter = Json.format[NearbyQueryModel]
}
