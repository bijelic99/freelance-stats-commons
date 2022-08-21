import sbt.Keys.resolvers
import sbt.{Def, Resolver}
import sbtghpackages.GitHubPackagesPlugin.autoImport._

object GithubPackagesConfig {
  lazy val get: Seq[Def.Setting[_ >: String with TokenSource with Seq[Resolver] <: Object]] = Seq(
    githubOwner := "bijelic99",
    githubRepository := "freelance-stats-commons",
    githubTokenSource := TokenSource.GitConfig("github.token"),
    resolvers ++= Seq(
      Resolver.githubPackages("bijelic99")
    )
  )
}
