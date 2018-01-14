import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    resolvers ++= Seq("hortonworks release" at "http://repo.hortonworks.com/content/repositories/",
                      "hortonworks public" at "http://repo.hortonworks.com/content/groups/public/"),
    libraryDependencies ++= Seq(scalaTest % Test,
                                hiveDriver),
    assemblyMergeStrategy in assembly := {
      case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
      case "application.conf"                            => MergeStrategy.concat
      case PathList(ps @ _*) if ps.last endsWith ".properties" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".xml" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".types" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".class" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".conf" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".dat" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".gif" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".css" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".dtd" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".xsd" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".mf" => MergeStrategy.first
      case PathList(ps @ _*) if ps.last endsWith ".js" => MergeStrategy.concat
      case PathList(ps @ _*) if ps.last endsWith ".ico" => MergeStrategy.concat
      case PathList(ps @ _*) if ps.last endsWith ".txt" => MergeStrategy.concat
      case "unwanted.txt"                                => MergeStrategy.discard
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )
