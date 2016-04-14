package models

import play.api.libs.json.Json

case class SignupModel (
  username: String,
  password: String,
  country: String
)

object SignupModel {
  implicit val signupModel = Json.format[SignupModel]
}
