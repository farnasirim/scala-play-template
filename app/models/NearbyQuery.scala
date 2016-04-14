package models

import play.api.libs.json.Json

case class NearbyQuery(
  lng: Double,
  lat: Double,
  radius: Double,
  price: Int,
  count: Int,
  tags: Seq[Int]
)

object NearbyQuery {
  implicit val formatter = Json.format[NearbyQuery]
}
