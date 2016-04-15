package models

import play.api.libs.json.Json

case class File(
  name: String
)

object File {
  implicit val fileFormatter = Json.format[File]
}
