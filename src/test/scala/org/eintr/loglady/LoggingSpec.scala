package org.eintr.loglady

import org.specs2.mutable._

private class LoggingExample extends Logging {
  var i = 0
  while (i < 100000) {
    log.debug(i + "Some random stuff: %d %s ", i, List(1,2,3,i))
    log.info("Hello, world")
    log.warn("A float: %4.2f", i * 3.141592)
    log.error("The date is %1$tm %1$te,%1$tY", new java.util.Date)
    i += 1
  }
  def getLog = log
}

class LoggingSpec extends SpecificationWithJUnit {
  "A class extending Logging" should {
    "have a Logger member named 'log'" in {
      val obj = new LoggingExample
      obj.getLog should beAnInstanceOf[Logger]
    }
  }

}