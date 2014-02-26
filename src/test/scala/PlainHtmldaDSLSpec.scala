import org.scalatest.{WordSpec, Matchers}

class PlainHtmldaDSLSpec extends WordSpec with Matchers {
  "PlainHtmldaDSL" must {
    import com.github.hexx.htmlda.PlainHtmldaDSL._

    "render HTML" in {
      val node = dsl {
        %html {
          %head {
            %link $("rel" -> "stylesheet", "href" -> "style.css")
            %title t("タイトルだよ")
          }
          %body {
            for (i <- 1 to 3) {
              %p t(s"${i}だよ")
            }
            %p {
              %a ($("href" -> "http://ubiregi.com"), t("ユビレジ"))
            }
          }
        }
      }

      val html = """<html><head><link rel="stylesheet" href="style.css"></link><title>タイトルだよ</title></head><body><p>1だよ</p><p>2だよ</p><p>3だよ</p><p><a href="http://ubiregi.com">ユビレジ</a></p></body></html>"""

       node.render shouldBe html
    }

    "escape text in TextNode" in {
      val node = dsl {
        %p t("<html></html>")
      }

      val html = """<p>&lt;html&gt;&lt;/html&gt;</p>"""

      node.render shouldBe html
    }

    "escape text in attributes" in {
      val node = dsl {
        %a ($("href" -> "<br>"), t("text"))
      }

      val html = """<a href="&lt;br&gt;">text</a>"""

      node.render shouldBe html
    }
  }
}
