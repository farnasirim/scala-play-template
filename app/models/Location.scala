package models

import play.api.libs.json.Json

case class Location(
  coordinates: Seq[Double],
  `type`: String = "Point"
)

object Location {
  implicit val locationFormatter = Json.format[Location]
}
