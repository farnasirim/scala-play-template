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
import models.{AuthResponseModel, LoginModel, SignupModel, UserModel}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import core.utils.Utils

@Singleton
class HomeController @Inject()(
  configuration: Configuration,
  utils: Utils,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  protected def usersCollection = reactiveMongoApi.db.collection[JSONCollection]("users")

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
      val loginRequest = request.body.validate[LoginModel]

      loginRequest.fold(
        errors => {
          Future.successful(BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("bad.json.body")), data = None))))
        },
        loginModel => {
          val cursor: Cursor[UserModel] = usersCollection.find(Json.obj("email" -> loginModel.email, "password" -> loginModel.password)).cursor[UserModel]()
          val futureUsersList: Future[List[UserModel]] = cursor.collect[List]()
          futureUsersList map {
            _.headOption match {
              case Some(user) => Ok(Json.toJson(JSBaseModel(successful = true,
                message = None,
                data = Some(Json.toJson(AuthResponseModel(user.email, user.token)))))
              )
              case None => Ok(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("email.or.password.is.incorrect")), data = None)))
            }
          }
        }
      )
  }

  def signup = Action.async(parse.json) {
    implicit request =>
      val signupRequest = request.body.validate[SignupModel]

      signupRequest.fold(
        errors => {
          Future.successful(BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("bad.json.body")), data = None))))
        },
        signupModel => {
          val cursor: Cursor[UserModel] = usersCollection.find(Json.obj("email" -> signupModel.email))
            .cursor[UserModel]()
          val futureUsersList: Future[List[UserModel]] = cursor.collect[List]()
          futureUsersList flatMap {
            _.headOption match {
              case Some(user) =>
                Future.successful(Ok(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("user.already.exists")), data = None))))
              case None =>
                utils.generateActivationCode flatMap { generatedCode =>
                  // TODO: hash password before inserting to db
                  val newUser = new UserModel(generatedCode.toString, signupModel.name, signupModel.email, signupModel.password, signupModel.country)
                  usersCollection.insert(newUser) map {
                    user => Ok(Json.toJson(JSBaseModel(successful = true,
                      message = None,
                      data = Some(Json.toJson(AuthResponseModel(newUser.email, newUser.token)))))
                    )
                  }
                }
            }
          }
        }
      )
  }

}
