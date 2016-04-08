package core.models

import org.joda.time.DateTime

case class SimpleLoggingModel(
  dateTime: DateTime,
  method: String,
  path: String
) {
  override def toString = s"\nDATE: $dateTime\nMETHOD: $method\nPATH: $path\n"
}
