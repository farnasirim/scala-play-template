package models

import play.api.libs.json.Json

case class LoginQuery(
  email: String,
  password: String
)

object LoginQuery {
  implicit val userFormatter = Json.format[LoginQuery]
}
