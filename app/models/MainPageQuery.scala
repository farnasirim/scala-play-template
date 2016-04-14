package models

import play.api.libs.json.Json

case class MainPageQuery(
  location: Location,
  price: Int,
  tags: Seq[Int]
)

object MainPageQuery {
  implicit val mainPageQueryModelFormatter = Json.format[MainPageQuery]
}
