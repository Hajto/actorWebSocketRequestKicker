package actors

import akka.actor.{ActorRef, Actor}
import play.api.libs.json.JsObject

case class registerPriest(id: Int,ref: ActorRef)
case class sendActualMessage(id: Int, message: Int)

class ManagerActor extends Actor {
  var priestMap = Map.empty[Int,ActorRef]

  override def receive: Receive = {
    case registerPriest(id: Int,ref: ActorRef) =>
      priestMap = priestMap + (id -> ref)
      println(id)
    case sendActualMessage(id: Int, message: Int) =>
      priestMap.get(id) match{
        case Some(ref) => ref ! message
        case None => println("Nie ma takiego ksiedza")
      }
    case "debug" => priestMap.foreach{
      case (index,actorRef) => actorRef ! "Priests "+priestMap.size+" "+priestMap.keys
    }
    case _ => println("Priests "+priestMap.size+" "+priestMap.keys)
  }
}
