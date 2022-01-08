package com.freelanceStats.commons.modules

import akka.actor.ActorSystem
import akka.stream.Materializer
import com.google.inject.{AbstractModule, Provides}

import javax.inject.Singleton

class MaterializerModule extends AbstractModule {
  @Provides
  @Singleton
  def provideMaterializer(actorSystem: ActorSystem): Materializer =
    Materializer.matFromSystem(actorSystem)

}
