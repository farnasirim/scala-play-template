package core.actions

import core.models.SimpleLoggingModel
import org.joda.time.DateTime
import play.api.Logger
import play.api.mvc._

import scala.concurrent.Future

object SimpleLoggingAction extends ActionBuilder[Request]{

  def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    Logger.info(SimpleLoggingModel(dateTime = DateTime.now(), method = req.method, path = req.path).toString)
    block(req)
  }
}

object MongoDBLogginAction extends ActionBuilder[Request] {

  // TODO: Log all incoming requests to MongoDB
  def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    block(req)
  }
}

object PostgreSQLLoggingAction extends ActionBuilder[Request] {

  // Optional Action to log all incoming requests to PostgreSQL
  def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    // Code here
    block(req)
  }
}
