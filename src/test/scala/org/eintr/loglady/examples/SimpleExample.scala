package org.eintr.loglady.examples

import org.eintr.loglady.Logging


/**
 * Demonstrate the API
 */
class SimpleExample extends Logging {

  log.trace("No formatted parameters")

  log.debug("Some random stuff: %d %s ", 42, List(1, 2, 3, 4))

  log.info("The %s is so very %s", "fox", "fancy")

  log.warn("A float: %4.2f", 3.141592)

  log.error("The date is %1$tm %1$te,%1$tY", new java.util.Date)

  try {
    require (false)  // Throws an IllegalArgumentException
  } catch {
    case exc: Exception => {
      log.error(exc, "An exception occurred")
    }
  }
  
  log.info("We're ok though...")

  log.warn("Is debug enabled? %s", log.isDebugEnabled)

  def getLog = log   // Used for testing
}

