package model

import play.api.libs.json.{ Json}

case class kickRequest(id: Int, data: Int)

object Schemes{
  implicit val kickFormat = Json.format[kickRequest]
}