import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
import sbt.Keys.{organization, scalaVersion}

object CommonConfig {
  lazy val get = Seq(
    organization := "com.freelance-stats",
    scalaVersion := "2.13.6",
    scalafmtOnCompile := true
  )
}
