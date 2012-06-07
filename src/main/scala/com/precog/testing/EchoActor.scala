package com.precog.testing

import com.typesafe.config.ConfigFactory
import akka.actor.{ ActorRef, Props, Actor, ActorSystem }
import akka.pattern.ask
import akka.util.duration._

case class EchoRequest(message: String)
case class EchoResponse(message: String)

class EchoActor extends Actor {
  def receive = {
    case EchoRequest(msg)    => sender ! EchoResponse(msg)
    case h: HelloProto.Hello => sender ! EchoResponse(h.getGreeting() + " for " + h.getPeopleCount() + " people")
  }
}

object EchoActor {
  def main(args: Array[String]) {
    val system = ActorSystem("EchoSystem", ConfigFactory.load.getConfig("server"))
    val actor = system.actorOf(Props[EchoActor], "echoActor")
    println("Actor started at " + actor)
  }
}

object EchoSender {
  def main(args: Array[String]) {
    val system = ActorSystem("EchoClient", ConfigFactory.load.getConfig("client"))
    val actor = system.actorFor("akka://EchoSystem@127.0.0.1:2552/user/echoActor")

    //val msg = EchoRequest("Test")
    val builder = HelloProto.Hello.newBuilder().setGreeting(args(0))

    args(1).split(",").foreach {
      name => builder.addPeople(HelloProto.Hello.Person.newBuilder().setName(name))
    }

    val msg = builder.build()

    actor.ask(msg)(5 seconds).onComplete { resp => println("Echo = " + resp); system.shutdown() }

    system.awaitTermination()
  }
}
