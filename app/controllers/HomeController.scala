package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.modules.reactivemongo._
import play.api.i18n.{I18nSupport, Messages, MessagesApi}

import scala.concurrent.ExecutionContext

import core.actionbuilders._
import core.models.JSBaseModel

@Singleton
class HomeController @Inject() (
  configuration: Configuration,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  def index = SimpleLoggingAction {
    val json = configuration.getString("home.urls") match {
      case Some(urls) => Json.toJson(
        JSBaseModel(successful = true, message = None, data = Some( Json.parse(
          urls
        )))
      )
      case None => Json.toJson(
        JSBaseModel(successful = false, message = Some(Messages("home.urls.not.found")), data = None)
      )
    }
    Ok(json)
  }

}
