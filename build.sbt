name := "spreadsheet-scala"

organization := "com.franklinchen"

organizationHomepage := Some(url("http://franklinchen.com/"))

homepage := Some(url("http://github.com/FranklinChen/spreadsheet-scala"))

startYear := Some(2015)

description := "Spreadsheet demo in Scala"

version := "1.0.0"

scalaVersion := "2.11.8"

scalacOptions ++= Seq(
  "-deprecation",
  "-feature"
)

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.7.2",
  "org.specs2" %% "specs2-core" % "3.8.5" % Test
)
