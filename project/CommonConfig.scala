import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
import sbt.Keys.{name, organization, scalaVersion, version}
import sbtrelease.ReleasePlugin.autoImport.{releaseCommitMessage, releaseNextCommitMessage, releaseTagComment}

object CommonConfig {
  lazy val get = Seq(
    organization := "com.freelance-stats",
    scalaVersion := "2.13.6",
    scalafmtOnCompile := true,
    releaseTagComment := s"Releasing ${name.value} ${version.value}",
    releaseCommitMessage := s"Setting version of ${name.value} to ${version.value}",
    releaseNextCommitMessage := s"Setting version of ${name.value} to ${version.value}",
  )
}
