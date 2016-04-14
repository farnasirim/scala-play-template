package models

import play.api.libs.json.Json

case class Location(
  coordinates: Seq[Double],
  `type`: String = "Point"
)

object Location {
  implicit val formatter = Json.format[Location]
}
