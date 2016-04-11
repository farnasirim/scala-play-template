package core.models

import play.api.libs.json._

case class JSBaseModel(
  successful: Boolean,
  message: Option[String],
  data: Option[JsValue]
)

object JSBaseModel {
  implicit val jSBaseModelFormatter = Json.format[JSBaseModel]
}
