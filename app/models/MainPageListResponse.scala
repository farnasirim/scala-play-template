package models

import play.api.libs.json.Json

import reactivemongo.bson.BSONObjectID

case class MainPageListResponse (
  mainPageResponses: Seq[LocationListItem]
                                )

object MainPageListResponse {
    implicit val mainPageListResponseFormatter = Json.format[MainPageListResponse]
}
