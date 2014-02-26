package com.github.hexx.htmlda

import scala.language.dynamics

import scala.util.DynamicVariable
import scala.xml.Utility

trait PlainHtmldaDSLImpl {
  self: HtmldaDSL with DynamicHtmldaDSL =>

  sealed trait Node extends NodeApi

  case class ElementNode(
    override val name: String,
    attributes0: Map[String, String],
    override val children: List[Node]
  ) extends Node with ElementNodeApi {
    override val attributes = attributes0.mapValues(Utility.escape(_))

    def render = {
      val as = attributes.mapValues("\"" + _ + "\"")
      s"""<${name}${as.map { case (l, r) => s" $l=$r" }.mkString}>${children.map(_.render).mkString}</${name}>"""
    }
  }

  case class TextNode(override val text: String) extends Node with TextNodeApi {
    def render = text
  }

  def raw(html: String) = nodes.value = TextNode(html) :: nodes.value

  def createElementNode(name: String, attributes: Map[String, String], children: List[Node]) = ElementNode(name, attributes, children)

  def createTextNode(text: String) = TextNode(Utility.escape(text))
}

object PlainHtmldaDSL extends HtmldaDSL with DynamicHtmldaDSL with PlainHtmldaDSLImpl
