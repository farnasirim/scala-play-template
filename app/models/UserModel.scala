package models

import play.api.libs.json.Json

case class UserModel(
  token: String,
  name: String,
  email: String,
  password: String,
  country: String,
  visited: Seq[String] = Seq()
)

object UserModel {
  implicit val userModelFormatter = Json.format[UserModel]
}
