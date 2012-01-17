Log Lady - Crazy Simple Logging in Scala
========================================

<img src="http://i.imgur.com/jPZF7.jpg" style="float:right" />

> "My log has something to tell you. Can you hear it?"


loglady is a thin wrapper around [slf4j](http://slf4j.org/), providing a simple API 
similar to [Python's logging module](http://docs.python.org/library/logging.html).

With slf4j, you may use whatever logging implementation fits your application.
[Logback](http://logback.qos.ch/) is a good choice.

Other influences include [Logula](http://github.com/codahale/logula) and
[slf4s](http://github.com/weiglewilczek/slf4s).

Like logula, it doesn't use pass-by-name parameters to logging methods, 
avoiding needless closures.


Features
--------

 * Thin wrapper around slf4j
 * Simple API
 * Focus on formatting for common use cases, like Python
 * No configuration
 * Supports Scala 2.8.1, 2.8.2, 2.9.0-1 and 2.9.1


Getting Log Lady
----------------

### sbt

```scala
libraryDependencies += "org.eintr.loglady" %% "loglady" % "1.0.0"
```

### Maven

```xml
<dependency>
  <groupId>org.eintr.loglady</groupId>
  <artifactId>loglady_2.9.1</artifactId>
  <version>1.0.0</version>
</dependency>
```

Replace the scala version according to your project.


Usage
-----

```scala
import org.eintr.loglady.Logging

class MyClass extends Logging {
  log.info("Hello, Log Lady")

  log.warn("We all float (%.4f) down here", 3.141592)
  
  log.debug("Some random stuff: %d %s %x", 42, List(0, 1, 1, 2, 3, 5), -559038737)
  
  log.error("Formatted date: %1$tm %1$te,%1$tY", new java.util.Date)

  try {
    throw new Exception("Oops!")
  } catch {
    case exc: Exception => {
      log.error(exc, "Something bad happened")
    }
  }
}
```

To get logging output, you also need a concrete logging library with slf4j.
Simply adding `logback-classic` as a dependency to your project is a simple
way. See the [logback website](http://logback.qos.ch/) for more information.


Copyright
---------

Copyright (c) 2012 Daniel Lundin

This software is licensed under the Apache License, Version 2.0. 
Please see LICENSE for details.
