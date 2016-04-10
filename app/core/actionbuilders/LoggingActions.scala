package core.actionbuilders

import javax.inject.{Inject, Singleton}

import play.api._
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.modules.reactivemongo.ReactiveMongoApi

import scala.concurrent.Future

import core.models.SimpleLoggingModel

import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection

import org.joda.time.DateTime

@Singleton
class LoggingActions @Inject() (
  application: Application) {

  object SimpleLoggingAction extends ActionBuilder[Request]{

    def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
      Logger.info(SimpleLoggingModel(dateTime = DateTime.now(), method = req.method, path = req.path).toString)
      block(req)
    }
  }

  object MongoDBLoggingAction extends ActionBuilder[Request] {
    lazy val reactiveMongoApi = application.injector.instanceOf[ReactiveMongoApi]
    def collection(name: String): JSONCollection = reactiveMongoApi.db.collection[JSONCollection](name)

    // TODO: Log all incoming requests to MongoDB
    def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
      block(req)
    }
  }

  // Optional ActionBuilder to log all incoming requests to PostgreSQL
  object PostgreSQLLoggingAction extends ActionBuilder[Request] {

    def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
      // Write database driver code for logging
      block(req)
    }
  }

}
