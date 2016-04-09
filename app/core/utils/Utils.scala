package core.utils

import java.security.SecureRandom
import javax.inject.{Inject, Singleton}

import org.hashids._
import play.api.Configuration
import scala.concurrent.{Future, ExecutionContext}

@Singleton
class Utils @Inject() (
  val config: Configuration) (implicit ec: ExecutionContext) {

  private final val secureRandom = new SecureRandom()
  private final val hashIds = Hashids.reference(salt = config.getString("hashids.salt").get, minHashLength = 6)

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
