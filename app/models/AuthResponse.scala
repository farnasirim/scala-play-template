package models

import play.api.libs.json.Json

case class AuthResponse(
  token: String,
  email: String
)

object AuthResponse {
  implicit val authResponseModelFormatter = Json.format[AuthResponse]
}
