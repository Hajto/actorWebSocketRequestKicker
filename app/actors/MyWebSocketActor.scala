package actors

import akka.actor._
import controllers.Application

object MyWebSocketActor {
  def props(out: ActorRef) = Props(new MyWebSocketActor(out))
}

class MyWebSocketActor(out: ActorRef) extends Actor {

  def receive = {
    case msg: String =>
      toOptInt(msg) match {
        case Some(v) =>
          Application.masterBanger ! registerPriest(v,self)
          out ! " Nawiazano polaczenie "
        case None => out ! msg
      }
    case id:Int =>
      out ! id.toString
  }

  def toOptInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }
}