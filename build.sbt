lazy val root = (project in file("."))
  .settings(
    name := "spreadsheet-scala",
    organization := "com.franklinchen",
    organizationHomepage := Some(url("http://franklinchen.com/")),
    homepage := Some(url("http://github.com/FranklinChen/spreadsheet-scala")),
    startYear := Some(2015),
    description := "Spreadsheet demo in Scala",
    version := "1.0.0",
    scalaVersion := "2.12.8",
    scalacOptions ++= Seq(
      "-Ypartial-unification",
      "-deprecation",
      "-feature"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.6.0",
      "org.specs2" %% "specs2-core" % "4.4.1" % Test,
      "org.typelevel" %% "cats-laws" % "1.6.0" % Test,
      "com.github.alexarchambault" %% "scalacheck-shapeless_1.14" % "1.2.0" % Test
    )
  )
