import sbt.Keys.{name, version}
import sbtrelease.ReleasePlugin.autoImport._
import sbtrelease.{Version, versionFormatError}

object ReleaseConfig {
  lazy val get = Seq(
    releaseUseGlobalVersion := false,
    releaseTagComment := s"Releasing ${name.value} ${version.value}",
    releaseCommitMessage := s"Setting version of ${name.value} to ${version.value}",
    releaseNextCommitMessage := s"Setting version of ${name.value} to ${version.value}",
    releaseVersion := { ver => Version(ver.stripPrefix(name.value + "-")).map(_.withoutQualifier.string).map(v => s"${name.value}-$v").getOrElse(versionFormatError(ver)) },
    releaseNextVersion := {
      ver => Version(ver.stripPrefix(name.value + "-")).map(_.bump(releaseVersionBump.value).asSnapshot.string).map(v => s"${name.value}-$v").getOrElse(versionFormatError(ver))
    }
  )
}
