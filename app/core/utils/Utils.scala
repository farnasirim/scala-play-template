package core.utils

import javax.inject._
import java.security.SecureRandom

import play.api.Configuration

import scala.concurrent.{Future, ExecutionContext}

import org.hashids._

@Singleton
class Utils @Inject() (
  val configuration: Configuration
)(implicit exc: ExecutionContext) {

  private val secureRandom = new SecureRandom()
  private val hashIds = Hashids.reference(salt = configuration.getString("hashids.salt").get, minHashLength = 6)

  def generateActivationCode: Future[Long] = {
    Future {
      111111 + secureRandom.nextInt(888888)
    }
  }

  def hashIdEncode(from: Long): Future[String] = {
    Future {
      hashIds.encode(from)
    }
  }

  def hashIdDecode(from: String): Future[Long] = {
    Future {
      hashIds.decode(from).head
    }
  }
}
