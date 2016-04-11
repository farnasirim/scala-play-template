package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.modules.reactivemongo._

import scala.concurrent.ExecutionContext

import core.actionbuilders._
import core.utils._

@Singleton
class HomeController @Inject() (
  utils: Utils,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents {

  def index = SimpleLoggingAction {
    Ok(views.html.index("Your new application is ready."))
  }

}
