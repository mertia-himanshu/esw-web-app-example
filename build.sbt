import Common.CommonSettings
import org.tmt.sbt.docs.Settings

inThisBuild(
  CommonSettings
)

lazy val aggregatedProjects: Seq[ProjectReference] = Seq(
  `docs`
)

/* ================= Root Project ============== */
lazy val `sample` = project
  .in(file("."))
  .enablePlugins(GithubPublishPlugin)
  .aggregate(aggregatedProjects: _*)
  .settings(Settings.makeSiteMappings(docs))

/* ================= Paradox Docs ============== */
lazy val docs = project
  .enablePlugins(ParadoxMaterialSitePlugin)
