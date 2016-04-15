package models.query

import models.Location
import play.api.libs.json.Json

case class MainPageQuery(
  location: Location,
  price: Int,
  tags: Seq[Int]
)

object MainPageQuery {
  implicit val mainPageQueryFormatter = Json.format[MainPageQuery]
}
