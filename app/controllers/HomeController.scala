package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.libs.json._
import play.modules.reactivemongo._

import scala.concurrent.{ExecutionContext, Future}
import core.actionbuilders._
import core.models._
import models.{LoginModel, SignupModel, UserModel}
import reactivemongo.api.Cursor
import reactivemongo.play.json.collection.JSONCollection

@Singleton
class HomeController @Inject() (
  configuration: Configuration,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  def userCollection: Future[JSONCollection] = database map {
    defaultDb => defaultDb.collection[JSONCollection]("users")
  }

  def index = SimpleLoggingAction {
    val json = configuration.getString("home.urls") match {
      case Some(urls) => Json.toJson(
        JSBaseModel(successful = true, message = None, data = Some(Json.parse(urls)))
      )
      case None => Json.toJson(
        JSBaseModel(successful = false, message = Some(Messages("home.urls.not.found")), data = None)
      )
    }
    Ok(json)
  }

  def login = Action.async(parse.json) {
    implicit request =>
      val loginResult = request.body.validate[LoginModel]

      loginResult.fold(
        errors => {
          Future.successful(BadRequest(JSBaseModel(successful = false, message = Some(Messages("username.or.password.incorrect")), data = None)))
        },
        loginModel => {
          userCollection flatMap {
            collection =>
              val cursor: Cursor[UserModel] = collection.find(Json.obj("username" -> loginModel.username, "password" -> loginModel.password))
                  .cursor[UserModel]()
              val futureUsersList: Future[List[UserModel]] = cursor.collect[List]()
              futureUsersList map {
                users => Ok()
              }
          }
        }
      )
  }

  def signup = Action.async(parse.json) {
    implicit request =>
      val signupResult = request.body.validate[SignupModel]
      signupResult.fold(
        errors =>{
          Future.successful(BadRequest(JSBaseModel(successful = false, message = Some(Messages("bad.signup.body")), data = None)))
        },
        SignupModel =>{
          ???
        }
      )
      ???
  }

}
