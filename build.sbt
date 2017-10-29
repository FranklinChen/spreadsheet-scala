lazy val root = (project in file("."))
  .settings(
    name := "spreadsheet-scala",
    organization := "com.franklinchen",
    organizationHomepage := Some(url("http://franklinchen.com/")),
    homepage := Some(url("http://github.com/FranklinChen/spreadsheet-scala")),
    startYear := Some(2015),
    description := "Spreadsheet demo in Scala",
    version := "1.0.0",
    scalaVersion := "2.12.4",
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.0.0-MF",
      "org.specs2" %% "specs2-core" % "4.0.1" % Test
    )
  )
