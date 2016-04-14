package models

import play.api.libs.json.Json

case class LoginModel(
  email: String,
  password: String
)

object LoginModel {
  implicit val userFormatter = Json.format[LoginModel]
}
