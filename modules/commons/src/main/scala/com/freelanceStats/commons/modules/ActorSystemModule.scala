package com.freelanceStats.commons.modules

import akka.actor.ActorSystem
import com.google.inject.{AbstractModule, Provides}
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import javax.inject.Singleton
import scala.util.Try

class ActorSystemModule extends AbstractModule {
  @Provides
  @Singleton
  def provideActorSystem(): ActorSystem = {
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
    actorSystemConfig.fold(
      ActorSystem.create(name)
    )(
      ActorSystem.create(name, _)
    )
  }
}
