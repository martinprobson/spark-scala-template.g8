val sparkVersion = "$sparkVersion$"

// NOTE: Remove the Provided from both dependencies below to allow running locally!!!
val spark = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided
)

val logging = Seq(
  "org.slf4j" % "slf4j-api" % "2.0.17",
  "ch.qos.logback" % "logback-classic" % "1.5.18",
  "ch.qos.logback" % "logback-core" % "1.5.18",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
)

val config = Seq("com.typesafe" % "config" % "1.4.4", "com.github.andr83" %% "scalaconfig" % "0.7")

val test = Seq(
  "org.scalactic" %% "scalactic" % "3.2.19" % Test,
  "org.scalatest" %% "scalatest" % "3.2.19" % Test
)

lazy val spark_example = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "$name$",
    version := "0.0.1-SNAPSHOT",
    libraryDependencies ++= logging,
    libraryDependencies ++= spark,
    libraryDependencies ++= config,
    libraryDependencies ++= test,
    scalaVersion := "2.13.16"
  )

//set spark_example / Test / classLoaderLayeringStrategy := ClassLoaderLayeringStrategy.Flat

scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-explaintypes", // Explain type errors in more detail.
  "-Xfatal-warnings", // Fail the compilation if there are any warnings.
  "-Xsource:3", // Warn for Scala 3 features
  "-Ywarn-dead-code" // Warn when dead code is identified.
)

// Bypass Java module system....
Test / fork := true
Test / javaOptions += "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"
run / fork := true
run / javaOptions += "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"

javacOptions ++= Seq("-source", "17", "-target", "17", "-Xlint")

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*)       => MergeStrategy.discard
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case _                                   => MergeStrategy.first
}
