import scala.scalajs.js
import scala.scalajs.js.Dynamic.global
import scala.scalajs.test.JasmineTest
import com.github.hexx.htmlda.PlainHtmldaDSL._

object HtmldaJsJasmineTest extends JasmineTest {
  global.importScripts("jquery.js")

  describe("HtmldaJS") {
    it("should render HTML") {
      val jq = global.jQuery

      val node = dsl {
        %p {
          %a ($("href" -> "http://www.scala-js.org/"), t("Scala.js"))
        }
      }

      jq("body").append(jq(node.render))

      val body = global.document.getElementsByTagName("body").asInstanceOf[js.Array[js.Dynamic]].head

      val p = body.innerHTML.toString

      expect(p).toMatch(""".*<p><a href=\"http://www.scala-js.org/\">Scala.js</a></p>.*""")
    }
  }
}
