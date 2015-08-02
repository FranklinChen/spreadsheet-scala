name := "spreadsheet-scala"

organization := "com.franklinchen"

organizationHomepage := Some(url("http://franklinchen.com/"))

homepage := Some(url("http://github.com/FranklinChen/spreadsheet-scala"))

startYear := Some(2015)

description := "Spreadsheet demo in Scala"

version := "1.0.0"

scalaVersion := "2.11.7"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature"
)

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.3",
  "org.scalacheck" %% "scalacheck" % "1.12.4" % Test,
  "org.specs2" %% "specs2-core" % "3.6.4" % Test,
  "org.specs2" %% "specs2-scalacheck" % "3.6.4" % Test
)
