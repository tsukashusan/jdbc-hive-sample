import sbt._

object Dependencies {
    lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.3"  
    lazy val hiveDriver = "org.apache.hive" % "hive-jdbc" % "2.1.0.2.6.2.3-1" % "provided" exclude("org.apache.zookeeper", "zookeeper")
}
