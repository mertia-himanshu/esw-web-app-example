import com.typesafe.sbt.site.SitePlugin.autoImport.siteDirectory
import org.scalafmt.sbt.ScalafmtPlugin.autoImport.scalafmtOnCompile
import org.tmt.sbt.docs.DocKeys.{docsParentDir, docsRepo, gitCurrentRepo}
import sbt.Keys._
import sbt._

object Common {

  lazy val DocsSettings = Seq(
    docsRepo := "https://github.com/tmtsoftware/tmtsoftware.github.io.git",
    docsParentDir := "sample",
    gitCurrentRepo := "https://github.com/tmtsoftware/sample"
  )

  lazy val CommonSettings: Seq[Setting[_]] = DocsSettings ++ Seq(
    scalaVersion := "2.13.6",
    organization := "com.github.tmtsoftware.sample",
    organizationName := "TMT Org",
    concurrentRestrictions in Global += Tags.limit(Tags.All, 1),
    homepage := Some(url("https://github.com/tmtsoftware/sample")),
    resolvers ++= Seq("jitpack" at "https://jitpack.io"),
    scmInfo := Some(
      ScmInfo(url("https://github.com/tmtsoftware/sample"), "git@github.com:tmtsoftware/sample.git")
    ),
    licenses := Seq(("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))),
    scalacOptions ++= Seq(
      "-encoding",
      "UTF-8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-Xlint",
      //      "-Yno-adapted-args",
      "-Ywarn-dead-code"
    ),
    Compile / doc / javacOptions ++= Seq("-Xdoclint:none"),
    version := {
      sys.props.get("prod.publish") match {
        case Some("true") => version.value
        case _            => "0.1.0-SNAPSHOT"
      }
    },
    commands += Command.command("openSite") { (state) =>
      val uri = s"file://${Project.extract(state).get(siteDirectory)}/${docsParentDir.value}/${version.value}/index.html"
      state.log.info(s"Opening browser at $uri ...")
      java.awt.Desktop.getDesktop.browse(new java.net.URI(uri))
      state
    },
    isSnapshot := !sys.props.get("prod.publish").contains("true"),
    scalafmtOnCompile := true,
    fork := true,
    autoCompilerPlugins := true,
    cancelable in Global := true // allow ongoing test(or any task) to cancel with ctrl + c and still remain inside sbt
  )
}
