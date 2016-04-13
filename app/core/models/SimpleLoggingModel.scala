package core.models

import org.joda.time.DateTime

case class SimpleLoggingModel(
  dateTime: DateTime,
  method: String,
  path: String
) {
  override def toString = s"DATE: $dateTime | METHOD: $method | PATH: $path"
}
