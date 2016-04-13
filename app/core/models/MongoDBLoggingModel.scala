package core.models

import play.api.libs.json.Json

import org.joda.time.DateTime

case class MongoDBLoggingModel(
  dateTime: DateTime,
  method: String,
  path: String
)

object MongoDBLoggingModel {
  implicit val ongoDBLoggingModelFormatter = Json.format[MongoDBLoggingModel]
}
