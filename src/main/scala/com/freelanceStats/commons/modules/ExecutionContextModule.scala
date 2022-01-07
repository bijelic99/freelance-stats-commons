package com.freelanceStats.commons.modules

import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory

import java.util.concurrent.ForkJoinPool
import scala.concurrent.ExecutionContext
import scala.util.Try

class ExecutionContextModule extends AbstractModule {
  override def configure(): Unit = {
    val conf = ConfigFactory.load()
    val parallelism = Try(conf.getInt("application.parallelism")).getOrElse(1)
    val executionContext = ExecutionContext.fromExecutor(
      new ForkJoinPool(parallelism)
    )
    bind(classOf[ExecutionContext]).toInstance(
      executionContext
    )
  }
}
