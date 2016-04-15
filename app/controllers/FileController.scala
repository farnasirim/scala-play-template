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
import models.{File, UserModel}
import reactivemongo.api.Cursor
import reactivemongo.play.json._
import reactivemongo.play.json.collection._
import core.utils.Utils
import models.query.{LoginQuery, SignupQuery}
import models.response.AuthResponse

@Singleton
class FileController @Inject()(
  configuration: Configuration,
  utils: Utils,
  val messagesApi: MessagesApi,
  val reactiveMongoApi: ReactiveMongoApi
)(implicit exec: ExecutionContext) extends Controller with MongoController with ReactiveMongoComponents with I18nSupport {

  def upload = Action.async(parse.multipartFormData) { request =>
    request.body.file("attachment").map { attachment =>
      import java.io.File
      utils.generateRandomNumber map { generatedNumber =>
        val filename = attachment.filename + generatedNumber
        val contentType = attachment.contentType
        attachment.ref.moveTo(new File(s"/tmp/picture/$filename"))
        Ok(
          Json.toJson(
            JSBaseModel(successful = true, message = None, data = Some(Json.toJson(models.File(filename))))
          ))
      }
    }.getOrElse {
      Future.successful {
        BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("missing.file")), data = None)))
      }
    }
  }

  def download = Action(parse.json) {
    implicit request =>
      val fileRequest = request.body.validate[File]

      fileRequest.fold(
        errors => {
          BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("bad.json.body")), data = None)))
        },
        file => {
          try {
            Ok.sendFile(
              content = new java.io.File(s"/tmp/picture/${file.name}"),
              fileName = _ => file.name,
              inline = true
            )
          } catch {
            case e: Exception => BadRequest(Json.toJson(JSBaseModel(successful = false, message = Some(Messages("file.does.not.exists")), data = None)))
          }
        }
      )
  }
}
