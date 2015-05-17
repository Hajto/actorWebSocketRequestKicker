package actors

import akka.actor.{ActorRef, Actor}
import akka.actor.Actor.Receive

case class registerPriest(id: Int,ref: ActorRef)
case class sendActualMessage(id: Int, message: String)

class ManagerActor extends Actor {
  var priestMap = Map.empty[Int,ActorRef]

  override def receive: Receive = {
    case registerPriest(id: Int,ref: ActorRef) =>
      priestMap = priestMap + (id -> ref)
    case sendActualMessage(id: Int, message: String) =>
      priestMap.get(id) match{
        case Some(ref) => ref ! message
        case None => sender ! "NOOP"
      }
  }
}