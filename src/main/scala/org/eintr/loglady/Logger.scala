/*
 * Copyright (C) 2012 Daniel Lundin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.eintr.loglady

import org.slf4j.{ Logger => Slf4jLogger, LoggerFactory }
import org.slf4j.spi.{ LocationAwareLogger => Slf4jLocationAwareLogger }


/**
 * Factory for concrete Logger instances
 */
object Logger {

  /** Returns a Logger for the given class */
  def apply(clazz: Class[_]): Logger = {
    LoggerFactory.getLogger(clazz) match {
      case logger: Slf4jLocationAwareLogger => new LocationAwareLogger(logger)
      case logger @ _ => new BasicLogger(logger)
    }
  }
}


/**
 * Main Logger API. Base class for loggers
 *
 * Note: Don't use this class directly, use the [[org.eintr.loglady.Logging]] trait instead.
 */
abstract class Logger {
  /** Name of the underlying logger */
  lazy val name = logger.getName

  /** The underlying logger as provided by slf4j */
  protected val logger: Slf4jLogger

  /** A function that logs a message with an exception */
  protected type LogFunc = (String, Throwable) => Unit

  // Concrete implementations of logger functions
  protected val logTrace: LogFunc
  protected val logDebug: LogFunc
  protected val logInfo: LogFunc
  protected val logWarn: LogFunc
  protected val logError: LogFunc

  // Exposes some config of underlying logger
  def isTraceEnabled = logger.isTraceEnabled
  def isDebugEnabled = logger.isDebugEnabled
  def isInfoEnabled = logger.isInfoEnabled
  def isWarnEnabled = logger.isWarnEnabled
  def isErrorEnabled = logger.isErrorEnabled

  /** Format a string using params, if any, otherwise use the string as-is */
  @inline protected final def format(fmt: String, params: Seq[Any]) = {
    if (params.nonEmpty) fmt.format(params:_*) else fmt
  }

  /** Log trace message */
  def trace(message: String,  params: Any*) {
    if (logger.isTraceEnabled) logTrace(format(message, params), null)
  }

  /** Log trace message with an exception */
  def trace(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isTraceEnabled) logTrace(format(message, params), thrown)
  }

  /** Log debug message */
  def debug(message: String,  params: Any*) {
    if (logger.isDebugEnabled) logDebug(format(message, params), null)
  }

  /** Log debug message with an exception */
  def debug(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isDebugEnabled) logDebug(format(message, params), thrown)
  }

  /** Log info message */
  def info(message: String,  params: Any*) {
    if (logger.isInfoEnabled) logInfo(format(message, params), null)
  }

  /** Log info message with an exception */
  def info(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isInfoEnabled) logInfo(format(message, params), thrown)
  }

  /** Log warn message */
  def warn(message: String,  params: Any*) {
    if (logger.isWarnEnabled) logWarn(format(message, params), null)
  }

  /** Log warning message with an exception */
  def warn(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isWarnEnabled) logWarn(format(message, params), thrown)
  }

  /** Log error message */
  def error(message: String,  params: Any*) {
    if (logger.isErrorEnabled) logError(format(message, params), null)
  }

  /** Log error message with an exception */
  def error(thrown: Throwable, message: String,  params: Any*) {
    if (logger.isErrorEnabled) logError(format(message, params), thrown)
  }
}


/**
 * Logger without location awareness
 */
protected final class BasicLogger(protected val logger: Slf4jLogger) extends Logger {
  protected val logTrace: LogFunc = logger.trace(_, _)
  protected val logDebug: LogFunc = logger.debug(_, _)
  protected val logInfo: LogFunc  = logger.info(_, _)
  protected val logWarn: LogFunc  = logger.warn(_, _)
  protected val logError: LogFunc = logger.error(_, _)
}


/**
 * Logger using slf4j's location aware logger.
 */
protected final class LocationAwareLogger(protected val logger: Slf4jLocationAwareLogger) extends Logger {
  import Slf4jLocationAwareLogger.{ERROR_INT, WARN_INT, INFO_INT, DEBUG_INT, TRACE_INT}
  private val fqcn = classOf[LocationAwareLogger].getCanonicalName
  protected val logTrace: LogFunc = logger.log(null, fqcn, TRACE_INT, _, null, _)
  protected val logDebug: LogFunc = logger.log(null, fqcn, DEBUG_INT, _, null, _)
  protected val logInfo: LogFunc  = logger.log(null, fqcn, INFO_INT,  _, null, _)
  protected val logWarn: LogFunc  = logger.log(null, fqcn, WARN_INT,  _, null, _)
  protected val logError: LogFunc = logger.log(null, fqcn, ERROR_INT, _, null, _)
}

