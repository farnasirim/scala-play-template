package core.actionbuilders

import play.api._
import play.api.mvc._

import scala.concurrent.Future

import core.models._

import org.joda.time.DateTime

object SimpleLoggingAction extends ActionBuilder[Request] {

  def invokeBlock[A](req: Request[A], block: (Request[A]) => Future[Result]): Future[Result] = {
    Logger.info(SimpleLoggingModel(dateTime = DateTime.now(), method = req.method, path = req.path).toString)
    block(req)
  }
}

object MongoDBLoggingAction extends ActionBuilder[Request] {

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
