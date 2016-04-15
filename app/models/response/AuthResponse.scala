package models.response

import play.api.libs.json.Json

case class AuthResponse(
  token: String,
  email: String
)

object AuthResponse {
  implicit val authResponseFormatter = Json.format[AuthResponse]
}
