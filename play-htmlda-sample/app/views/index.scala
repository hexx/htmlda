package views

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._

import com.github.hexx.htmlda.PlayHtmldaDSL._

object Index {
  def apply(message: String, form: Form[(String, String)]) = views.html.main("Welcome to Htmlda") {
    import views.html._
    render {
      %h1 t("play-htmlda")

      %p t(message)

      %ul {
        %li t("scala")
        %li t("play")
        %li t("htmlda")
      }

      embed(helper.form(action = controllers.routes.Application.index) {
        render {
          embed(helper.inputText(form("email")))
          embed(helper.inputPassword(form("password")))
        }
      })
    }
  }
}
