package controllers

import actors.{sendActualMessage, ManagerActor, MyWebSocketActor}
import akka.actor.Props
import model.kickRequest
import model.Schemes.kickFormat

import play.api._
import play.api.libs.json.Json
import play.api.mvc._
import play.api.Play.current
import play.libs.Akka

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object Application extends Controller {

  lazy val masterBanger = {
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

  def bangMessage = Action.async { implicit req =>
    Json.fromJson[kickRequest](req.body.asJson.get).map { kick =>
      masterBanger ! sendActualMessage(kick.id,kick.data)
      Future.successful(Ok("Success"))
    } getOrElse Future.successful(BadRequest("Wrong json"))
  }

}