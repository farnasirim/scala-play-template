package models.query

import play.api.libs.json.Json

case class LoginQuery(
  email: String,
  password: String
)

object LoginQuery {
  implicit val loginQueryFormatter = Json.format[LoginQuery]
}
