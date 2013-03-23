package com.github.hexx.htmlda.unfiltered

import _root_.unfiltered.response._
import com.github.hexx.htmlda._

object Htmlda extends HtmldaDSL with DynamicHtmldaDSL with PlainHtmldaDSLImpl {
  def apply(body: => Unit) = nodes.withValue(List()) {
    body
    new ComposeResponse(HtmlContent ~> ResponseString(nodes.value.reverse.map(_.render).mkString))
  }
}
