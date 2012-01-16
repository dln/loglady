Log Lady - Simple logging for Scala
===================================
"My log has something to tell you. Can you hear it?"

[Log Lady](http://github.com/dln/loglady) might be crazy, but she's also wise
and here to help.

Log Lady is a thin wrapper around slf4j, an easy-to-use logging trait
bearing great resemblence to
[Python's logging module](http://docs.python.org/library/logging.html).

Also, inspired by [Logula](http://github.com/codahale/logula), Log Lady does
not use pass-by-name parameters to logging methods, to avoid creating
a closure for every call.

By wrapping slf4j, the user may use whatever logging implementation fits
her application. Personally, I'd recommend [Logback](http://logback.qos.ch/)
or even simpler, Brian Clapper's [AVSL - A Very Simple Logger](https://github.com/bmc/avsl)


Copyright
---------

Copyright (c) 2012 Daniel Lundin

This software is licensed under the Apache License, Version 2.0. 
Please see LICENSE for details.
