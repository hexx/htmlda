package com.github.hexx.htmlda

import play.api.templates.Html

object PlayHtmldaDSL extends HtmldaDSL with DynamicHtmldaDSL with PlainHtmldaDSLImpl {
  def render(body: => Unit): Html = nodes.withValue(List()) {
    body
    Html(nodes.value.reverse.map(_.render).mkString)
  }

  def embed(html: Html) = raw(html.toString)
}
