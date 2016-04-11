package services

import javax.inject._
import java.time.{Clock, Instant}

import play.api.inject.ApplicationLifecycle

import scala.concurrent.Future

@Singleton
class ApplicationTimer @Inject() (clock: Clock, appLifecycle: ApplicationLifecycle) {

  // This code is called when the application starts.
  private val start: Instant = clock.instant

  // When the application starts, register a stop hook with the
  // ApplicationLifecycle object. The code inside the stop hook wil
  // be run when the application stops.
  appLifecycle.addStopHook { () =>
    val stop: Instant = clock.instant
    val runningTime: Long = stop.getEpochSecond - start.getEpochSecond
    Future.successful(())
  }
}
