package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.libs.json._
import play.modules.reactivemongo._
import play.api.libs.functional.syntax._

import models.query._
import scala.concurrent.{ExecutionContext, Future}
import core.actionbuilders._
import core.models._
import models.UserModel
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import core.utils.Utils
import models.query.{LoginQuery, SignupQuery}
import models.response.AuthResponse
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
import models.response.MainPageListResponse
import reactivemongo.bson.BSONObjectID

@Singleton
class LocationController @Inject()(
  configuration: Configuration,
  utils: Utils,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  protected def locationsCollection = reactiveMongoApi.db.collection[JSONCollection]("locations")
  protected def usersCollection = reactiveMongoApi.db.collection[JSONCollection]("users")


  def nearby = Action.async(parse.json) {
    implicit request =>
      val nearbyQueryRequest = request.body.validate[NearbyQuery]
      nearbyQueryRequest.fold(
        errors => {
          Future.successful(BadRequest(Json.toJson(JSBaseModel(
            successful = false, message = Some(Messages("bad.json.body")), data = None))))
        },
        query => {
          val earthRadiusInMeters = 6371000.0
          val futureLocations = locationsCollection.find(
            Json.obj("location" ->
              Json.obj("$geoWithin" ->
                Json.obj("$centerSphere" ->
                  Json.toJson(Seq(Json.toJson(Seq(query.lng, query.lat)), Json.toJson(query.radius/earthRadiusInMeters))))))
          ).sort(Json.obj("checkins" -> -1)).cursor[LocationModel]().collect[Seq]()
          futureLocations.map {
            locations => locations.filter(_.price < query.price)
          }.map { locations =>
            locations.filter(_.tags.zip(query.tags).exists {
              case (locationTag, queryTag) => queryTag == 1 && locationTag >= 0.5
            })
          }.map { locations =>
            val locationListItemstoReturn: Seq[LocationListItem] = locations.take(query.count).map {
              locationModel => {
                val lngPerson = query.lng
                val latPerson = query.lat
                val lngLocation = locationModel.location.coordinates.head
                val latLocation = locationModel.location.coordinates.tail.head

                LocationListItem(
                  locationModel._id.stringify,
                  locationModel.title,
                  locationModel.location,
                  utils.metersFromLatitudal(lngPerson, latPerson, lngLocation, latLocation),
                  locationModel.checkins.size,
                  locationModel.hasQrCode,
                  locationModel.price,
                  locationModel.tags,
                  locationModel.pictures
                )
              }
            }
              Ok(Json.toJson(JSBaseModel(
                successful = true,
                message = None,
                data = Some(Json.toJson(MainPageListResponse(locationListItemstoReturn)))))
              )
          }
        }
      )
  }

//  def checkin= Action.async(parse.json) {
//    implicit request =>{
//     val checkinRequest = request.body.validate[CheckinQuery]
//      checkinRequest.fold(
//          errors => {
//            Future.successful(BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("bad.json.body")), data = None)))) },
//        checkinQuery => {
//          val cursor: Cursor[UserModel] = usersCollection.find(Json.obj("email" -> loginModel.email, "password" -> loginModel.password)).cursor[UserModel]()
//          val futureUsersList: Future[List[UserModel]] = cursor.collect[List]()
//          request.headers.get("token") match {
//            case None =>
//              Future.successful(BadRequest(Json.toJson(JSBaseModel(
//                successful = false, message = Some(Messages("invalid.token")), data = None))))
//            case Some(token) => {
//
//            }
//          }
//        }
//      )
//    }
//  }
}
