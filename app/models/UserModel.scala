package models

import play.api.libs.json.Json

case class UserModel(
  token: String,
  name: String,
  email: String,
  password: String,
  country: String
)

object UserModel {
  implicit val userFormatter = Json.format[UserModel]
}
