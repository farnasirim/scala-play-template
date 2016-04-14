package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.libs.json._
import play.modules.reactivemongo._
import play.api.libs.functional.syntax._

import scala.concurrent.{ExecutionContext, Future}
import core.actionbuilders._
import core.models._
import models._
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import core.utils.Utils

import reactivemongo.bson.BSONObjectID

@Singleton
class LocationController @Inject()(
  configuration: Configuration,
  utils: Utils,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  protected def usersCollection = reactiveMongoApi.db.collection[JSONCollection]("locations")

  def nearby  = Action.async(parse.json) {
    implicit request =>
      val nearbyQueryModelRequest =  request.body.validate[NearbyQueryModel]
      nearbyQueryModelRequest.fold(
        errors => {
          Future.successful(BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("bad.json.body")), data = None))))
        },
        query => {
          val cursor: Cursor[LocationModel] = usersCollection.find(Json.obj())
            .cursor[LocationModel]()
        }
      )
      ???
  }
}
