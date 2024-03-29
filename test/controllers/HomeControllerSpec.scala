package controllers

import play.api.test._
import play.api.test.Helpers._

import org.scalatestplus.play._

import core.models.JSBaseModel

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class HomeControllerSpec extends PlaySpec with OneAppPerTest {

    "Routes" should {

      "send 404 on a bad request" in  {
        route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
      }

    }

    "HomeController" should {

      "render the index page" in {
        val home = route(app, FakeRequest(GET, "/")).get

        status(home) mustBe OK
        contentType(home) mustBe Some("application/json")
        contentAsJson(home).as[JSBaseModel].successful must be(true)
      }

    }
}
