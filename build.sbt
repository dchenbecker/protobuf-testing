import sbtprotobuf.{ProtobufPlugin=>PB}

name := "remoting_test"

version := "0.1-SNAPSHOT"

scalaVersion := "2.9.2"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/")

libraryDependencies ++= Seq(
  "ch.qos.logback"              %  "logback-classic"    % "1.0.0",
  "com.weiglewilczek.slf4s"     %  "slf4s_2.9.1"        % "1.0.7",
  "com.typesafe.akka"           %  "akka-actor"         % "2.0",
  "com.typesafe.akka"           %  "akka-remote"        % "2.0",
  "com.typesafe.akka"           %  "akka-testkit"       % "2.0" % "test"
)

seq(PB.protobufSettings: _*)