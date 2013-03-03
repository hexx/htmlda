package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

object Application extends Controller {
  val loginForm = Form(
    tuple(
      "email" -> text,
      "password" -> text
    )
  )

  def index = Action {
    Ok(views.Index("Htmlda is ready.", loginForm))
  }
}
