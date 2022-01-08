package com.freelanceStats.commons.modules

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.util.Try

class ActorSystemModule extends AbstractModule {
  override def configure(): Unit = {
    val log = LoggerFactory.getLogger(classOf[ActorSystemModule])
    val conf = ConfigFactory.load()
    val name = Try(conf.getString("application.actorSystem.name"))
      .getOrElse("application-actor-system")
    val actorSystemConfig = Try(
      conf.getConfig("application.actorSystem")
    ).toOption
    log.debug(
      s"Loading ${getClass.getName}, 'application.actorSystem' picked up ${actorSystemConfig.isDefined}"
    )
    bind(classOf[ActorSystem]).toInstance(
      actorSystemConfig.fold(
        ActorSystem.create(name)
      )(
        ActorSystem.create(name, _)
      )
    )
  }
}
