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


object Logger {
  def apply(clazz: Class[_]): Logger = {
    LoggerFactory.getLogger(clazz) match {
      case logger: Slf4jLocationAwareLogger => new LocationAwareLogger(logger)
      case logger @ _ => new BasicLogger(logger)
    }
  }
}


abstract class Logger {
  protected type LogFunc = (String, Throwable) => Unit

  lazy val name = logger.getName

  protected val logger: Slf4jLogger
  protected val logTrace: LogFunc
  protected val logDebug: LogFunc
  protected val logInfo: LogFunc
  protected val logWarn: LogFunc
  protected val logError: LogFunc

  @inline final def isTraceEnabled = logger.isTraceEnabled
  @inline final def isDebugEnabled = logger.isDebugEnabled
  @inline final def isInfoEnabled = logger.isInfoEnabled
  @inline final def isWarnEnabled = logger.isWarnEnabled
  @inline final def isErrorEnabled = logger.isErrorEnabled

  @inline protected final def format(fmt: String, values: Seq[Any]) = {
    if (values.nonEmpty) fmt.format(values:_*) else fmt
  }

  def trace(message: String,  params: Any*) {
    if (isTraceEnabled) logTrace(format(message, params), null)
  }

  def trace(thrown: Throwable, message: String,  params: Any*) {
    if (isTraceEnabled) logTrace(format(message, params), thrown)
  }

  def debug(message: String,  params: Any*) {
    if (isDebugEnabled) logDebug(format(message, params), null)
  }

  def debug(thrown: Throwable, message: String,  params: Any*) {
    if (isDebugEnabled) logDebug(format(message, params), thrown)
  }

  def info(message: String,  params: Any*) {
    if (isInfoEnabled) logInfo(format(message, params), null)
  }

  def info(thrown: Throwable, message: String,  params: Any*) {
    if (isInfoEnabled) logInfo(format(message, params), thrown)
  }

  def warn(message: String,  params: Any*) {
    if (isWarnEnabled) logWarn(format(message, params), null)
  }

  def warn(thrown: Throwable, message: String,  params: Any*) {
    if (isWarnEnabled) logWarn(format(message, params), thrown)
  }

  def error(message: String,  params: Any*) {
    if (isErrorEnabled) logError(format(message, params), null)
  }

  def error(thrown: Throwable, message: String,  params: Any*) {
    if (isErrorEnabled) logError(format(message, params), thrown)
  }
}


private final class BasicLogger(protected val logger: Slf4jLogger) extends Logger {
  protected val logTrace: LogFunc = logger.trace(_, _)
  protected val logDebug: LogFunc = logger.debug(_, _)
  protected val logInfo: LogFunc  = logger.info(_, _)
  protected val logWarn: LogFunc  = logger.warn(_, _)
  protected val logError: LogFunc = logger.error(_, _)
}


private final class LocationAwareLogger(protected val logger: Slf4jLocationAwareLogger) extends Logger {
  import Slf4jLocationAwareLogger.{ERROR_INT, WARN_INT, INFO_INT, DEBUG_INT, TRACE_INT}
  private val fqcn = classOf[LocationAwareLogger].getCanonicalName
  protected val logTrace: LogFunc = logger.log(null, fqcn, TRACE_INT, _, null, _)
  protected val logDebug: LogFunc = logger.log(null, fqcn, DEBUG_INT, _, null, _)
  protected val logInfo: LogFunc  = logger.log(null, fqcn, INFO_INT,  _, null, _)
  protected val logWarn: LogFunc  = logger.log(null, fqcn, WARN_INT,  _, null, _)
  protected val logError: LogFunc = logger.log(null, fqcn, ERROR_INT, _, null, _)
}

