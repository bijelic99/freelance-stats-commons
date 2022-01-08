package com.freelanceStats.commons.modules

import com.google.inject.AbstractModule
import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory

import scala.jdk.CollectionConverters._
import scala.util.Try

class ModuleLoader extends AbstractModule {
  override def configure(): Unit = {
    val log = LoggerFactory.getLogger(classOf[ModuleLoader])
    log.debug("Loading modules")
    val conf = ConfigFactory.load()
    val moduleNamesList = Try(conf.getStringList("application.modules"))
      .map(_.asScala.toSeq)
      .getOrElse(Nil)
    log.debug(s"Trying to load the following modules: $moduleNamesList")
    moduleNamesList
      .map(Class.forName)
      .collect {
        case cls if classOf[AbstractModule].isAssignableFrom(cls) =>
          cls
        case cls =>
          val message =
            s"Cannot load module '${cls.getName}', it does not extend ${classOf[AbstractModule].getName}"
          log.error(message)
          throw new Exception(message)
      }
      .map(_.getConstructor().newInstance().asInstanceOf[AbstractModule])
      .foreach(install)
  }
}
