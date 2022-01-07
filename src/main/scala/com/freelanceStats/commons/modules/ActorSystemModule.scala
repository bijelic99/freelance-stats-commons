package com.freelanceStats.commons.modules

import akka.actor.ActorSystem
import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory

import scala.util.Try

class ActorSystemModule extends AbstractModule {
  override def configure(): Unit = {
    val conf = ConfigFactory.load()
    val name = Try(conf.getString("application.actorSystem.name"))
      .getOrElse("application-actor-system")
    val actorSystemConfig = Try(
      conf.getConfig("application.actorSystem")
    ).toOption
    bind(classOf[ActorSystem]).toInstance(
      actorSystemConfig.fold(
        ActorSystem.create(name)
      )(
        ActorSystem.create(name, _)
      )
    )
  }
}
