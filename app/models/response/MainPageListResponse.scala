package models.response

import models.LocationListItem
import play.api.libs.json.Json

case class MainPageListResponse(
  mainPageResponses: Seq[LocationListItem]
)

object MainPageListResponse {
    implicit val mainPageListResponseFormatter = Json.format[MainPageListResponse]
}
