package com.freelanceStats.commons.modules

import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import java.util.concurrent.ForkJoinPool
import scala.concurrent.ExecutionContext
import scala.util.Try

class ExecutionContextModule extends AbstractModule {
  override def configure(): Unit = {
    val log = LoggerFactory.getLogger(classOf[ExecutionContextModule])
    val conf = ConfigFactory.load()
    val parallelism = Try(conf.getInt("application.parallelism")).getOrElse(1)
    log.debug(
      s"Loading '${getClass.getName}', parallelism was set to $parallelism"
    )
    val executionContext = ExecutionContext.fromExecutor(
      new ForkJoinPool(parallelism)
    )
    bind(classOf[ExecutionContext]).toInstance(
      executionContext
    )
  }
}
