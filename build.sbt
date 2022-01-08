import sbt.Keys.resolvers

lazy val sharedSettings = Seq(
  organization := "com.freelance-stats",
  scalaVersion := "2.13.6",
  scalafmtOnCompile := true
)

lazy val githubPackagesConfig = Seq(
  githubOwner := "bijelic99",
  githubRepository := "freelance-stats-commons",
  githubTokenSource := TokenSource.GitConfig("github.token"),
  resolvers ++= Seq(
    Resolver.githubPackages("bijelic99")
  )
)

val AkkaVersion = "2.6.14"

lazy val root =
  (project in file("."))
    .settings(
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
          "ch.qos.logback" % "logback-classic" % "1.2.10",
        )
      ) ++ sharedSettings ++ githubPackagesConfig: _*
    )