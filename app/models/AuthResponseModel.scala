package models

import play.api.libs.json.Json

case class AuthResponseModel(
  userName: String,
  token: String
)

object AuthResponseModel {
  implicit val formatter = Json.format[AuthResponseModel]
}
