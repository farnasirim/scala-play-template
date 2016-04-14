package models

import play.api.libs.json.Json

case class AuthResponseModel(
  token: String,
  email: String
)

object AuthResponseModel {
  implicit val formatter = Json.format[AuthResponseModel]
}
