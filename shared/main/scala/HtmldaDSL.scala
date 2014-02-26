package com.github.hexx.htmlda

import scala.language.dynamics

trait HtmldaDSL {
  type Node <: NodeApi
  type ElementNode <: Node with ElementNodeApi
  type TextNode <: Node with TextNodeApi
  type DSL <: DSLApi

  trait NodeApi {
    def render: String
  }

  trait ElementNodeApi extends NodeApi {
    def name: String
    def attributes: Map[String, String]
    def children: List[Node]
  }

  trait TextNodeApi extends NodeApi {
    def text: String
  }

  trait DSLApi extends Dynamic {
    def selectDynamic(name: String): Unit
    def applyDynamic(name: String)(body: => Unit): Unit
  }

  def % : DSL

  def $(attributes: (String, String)*): Unit

  def t(text: String): Unit

  def raw(html: String): Unit

  def dsl(body: => Unit): Node
}
