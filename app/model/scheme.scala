package model

import play.api.libs.json.Json

case class kickRequest(id: Int, data: String)

object Schemes{
  implicit val kickFormat = Json.format[kickRequest]
}