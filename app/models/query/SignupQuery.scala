package models.query

import play.api.libs.json.Json

case class SignupQuery(
  name: String,
  email: String,
  password: String,
  country: String
)

object SignupQuery {
  implicit val signupQueryFormatter = Json.format[SignupQuery]
}
