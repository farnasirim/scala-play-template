package models

import play.api.libs.json.Json

case class LoginModel(
  username: String,
  password: String
)

object LoginModel {
  implicit val userFormatter = Json.format[LoginModel]
}
