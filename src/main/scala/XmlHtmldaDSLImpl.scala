package com.github.hexx.htmlda

import scala.xml._
import scala.xml.{Node => SNode}

trait XmlHtmldaDSLImpl {
  self: HtmldaDSL with DynamicHtmldaDSL =>

  sealed trait Node extends NodeApi {
    val node: SNode
    def render = node.toString
  }

  case class ElementNode(
    override val name: String,
    override val attributes: Map[String, String],
    override val children: List[Node]
  ) extends Node with ElementNodeApi {
    val node = Elem(
      null,
      name,
      attributes.foldRight(Null: MetaData){ case ((k, v), r) => new UnprefixedAttribute(k, v, r) },
      TopScope,
      children.isEmpty,
      children.map(_.node):_*
    )
  }

  case class TextNode(override val text: String) extends Node with TextNodeApi {
    val node = Text(text)
  }

  def raw(html: String) = nodes.value = new Node { val node = XML.loadString(html) } :: nodes.value

  def createElementNode(name: String, attributes: Map[String, String], children: List[Node]) = ElementNode(name, attributes, children)

  def createTextNode(text: String) = TextNode(text)
}

object XmlHtmldaDSL extends HtmldaDSL with DynamicHtmldaDSL with XmlHtmldaDSLImpl
