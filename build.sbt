ThisBuild / scalaVersion := "3.5.0"
ThisBuild / githubWorkflowPublishTargetBranches := Seq()

lazy val root = (project in file("."))
  .settings(
    name := "spreadsheet-scala",
    organization := "com.franklinchen",
    organizationHomepage := Some(url("https://franklinchen.com/")),
    homepage := Some(url("https://github.com/FranklinChen/spreadsheet-scala")),
    startYear := Some(2015),
    description := "Spreadsheet demo in Scala",
    version := "1.0.0",
    crossScalaVersions := List("3.5.0"),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "2.12.0",
      "org.scalatest" %% "scalatest" % "3.2.19" % Test
    )
  )
