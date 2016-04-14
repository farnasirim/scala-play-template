package models

import play.api.libs.json.Json

case class SignupQuery(
  name: String,
  email: String,
  password: String,
  country: String
)

object SignupQuery {
  implicit val signupModel = Json.format[SignupQuery]
}
