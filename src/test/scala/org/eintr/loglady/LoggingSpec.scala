package org.eintr.loglady

import org.eintr.loglady.examples.SimpleExample
import org.specs2.mutable._


class LoggingSpec extends SpecificationWithJUnit {
  "A class extending Logging" should {
    "have a Logger member named 'log'" in {
      val obj = new SimpleExample
      obj.log should beAnInstanceOf[Logger]
    }
  }

}
