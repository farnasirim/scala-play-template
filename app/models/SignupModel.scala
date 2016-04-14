package models

import play.api.libs.json.Json

case class SignupModel (
  name: String,
  email: String,
  password: String,
  country: String
)

object SignupModel {
  implicit val signupModel = Json.format[SignupModel]
}
