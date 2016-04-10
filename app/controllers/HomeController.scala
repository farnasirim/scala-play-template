package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.modules.reactivemongo._

import scala.concurrent.ExecutionContext

import core.actionbuilders._
import core.utils.Utils

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject() (
  utils: Utils,
  loggingActions: LoggingActions,
  val reactiveMongoApi: ReactiveMongoApi
  )(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents {

  import loggingActions._
  /**
   * Create an Action to render an HTML page with a welcome message.
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = SimpleLoggingAction {
    Ok(views.html.index("Your new application is ready."))
  }

}
