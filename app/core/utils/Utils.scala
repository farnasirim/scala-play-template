package core.utils

import javax.inject._
import java.security.SecureRandom

import play.api.Configuration

import scala.concurrent.{Future, ExecutionContext}

import org.hashids._

@Singleton
class Utils @Inject() (
  configuration: Configuration
)(implicit exc: ExecutionContext) {

  private final val secureRandom = new SecureRandom()
  private final val hashIds = Hashids.reference(salt = configuration.getString("hashids.salt").get, minHashLength = 6)

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

  def metersFromLatitudal(lng1: Double, lat1: Double, lng2: Double, lat2: Double)={
    val earthRadius = 6371000.0

    val dLat = Math.toRadians(lat2-lat1)
    val dLng = Math.toRadians(lng2-lng1)
    val a = Math.sin(dLat/2) * Math.sin(dLat/2) +
      Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
        Math.sin(dLng/2) * Math.sin(dLng/2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
    earthRadius * c
  }
}
