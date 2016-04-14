package models

import play.api.libs.json.Json

case class NearbyQueryModel(
  lat: Double,
  lng: Double,
  radius: Double,
  count: Int
)

object NearbyQueryModel {
  implicit val formatter = Json.format[NearbyQueryModel]
}
