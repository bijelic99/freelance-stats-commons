import sbtrelease.ReleasePlugin.autoImport.ReleaseKeys.releaseCommand
import sbtrelease.ReleasePlugin.autoImport.releaseStepCommand

val AkkaVersion = "2.6.14"

lazy val root =
  (project in file("."))
    .settings(
      GithubPackagesConfig.get ++ CommonConfig.get ++
        Seq(
          name := "freelance-stats-commons"
        ): _*
    )

lazy val commons =
  (project in file("./modules/commons"))
    .settings(
      GithubPackagesConfig.get ++ CommonConfig.get ++ ReleaseConfig.get ++
        Seq(
          name := "commons",
          libraryDependencies ++= Seq(
            "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
            "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
            "com.github.nscala-time" %% "nscala-time" % "2.30.0",
            "com.typesafe.play" %% "play-json" % "2.9.2",
            "com.typesafe.play" %% "play-json-joda" % "2.9.2",
            "com.freelance-stats" %% "s3-client" % "0.0.3",
            "com.google.inject" % "guice" % "5.0.1",
            "com.typesafe" % "config" % "1.4.1",
            "ch.qos.logback" % "logback-classic" % "1.2.10"
          ),
          releaseVersionFile := file("./modules/commons/version.sbt")
        ): _*
    )

lazy val jwtAuth =
  (project in file("./modules/jwt-auth"))
    .settings(
      GithubPackagesConfig.get ++ CommonConfig.get ++ ReleaseConfig.get ++
        Seq(
          name := "jwt-auth",
          libraryDependencies ++= Seq(
            "com.typesafe.play" %% "play" % "2.8.16",
            "at.favre.lib" % "bcrypt" % "0.9.0",
            "com.github.jwt-scala" %% "jwt-play-json" % "9.0.5"
          ),
          releaseVersionFile := file("./modules/jwt-auth/version.sbt")
        ): _*
    )
    .dependsOn(commons)
