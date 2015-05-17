package controllers

import actors.{ManagerActor, MyWebSocketActor}
import akka.actor.Props
import play.api._
import play.api.mvc._
import play.api.Play.current
import play.libs.Akka
import scala.concurrent.duration._

object Application extends Controller {

  lazy val masterBanger = {
    import scala.concurrent.ExecutionContext.Implicits.global
    val actor = Akka.system.actorOf(Props[ManagerActor])
    Akka.system.scheduler.schedule(0 seconds,10 seconds, actor, "debug")

    actor
  }

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    MyWebSocketActor.props(out)
  }

}