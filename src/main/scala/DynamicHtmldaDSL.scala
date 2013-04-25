package com.github.hexx.htmlda

import scala.util.DynamicVariable
import scala.xml.Utility

trait DynamicHtmldaDSL {
  self: HtmldaDSL =>

  val nodes = new DynamicVariable[List[Node]](null)
  val attributes = new DynamicVariable[List[(String, String)]](null)

  class DSL extends DSLApi {
    def selectDynamic(name: String) = applyDynamic(name)()
    def applyDynamic(name: String)(body: => Unit) {
      val (as, ns) =
        nodes.withValue(List()) {
          attributes.withValue(List()) {
            body
            (attributes.value.toMap, nodes.value.reverse)
          }
        }
      nodes.value = createElementNode(name, as, ns) :: nodes.value
    }
  }

  override object % extends DSL

  def $(as: (String, String)*) = attributes.value = attributes.value ++ as.map { case (l, r) => l -> r }

  def t(text: String) = nodes.value = createTextNode(text) :: nodes.value

  def dsl(body: => Unit) = nodes.withValue(List()) {
    body
    nodes.value.head
  }

  def createElementNode(name: String, attributes: Map[String, String], children: List[Node]): ElementNode

  def createTextNode(text: String): TextNode
}
