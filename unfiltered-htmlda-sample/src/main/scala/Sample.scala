import com.github.hexx.htmlda.unfiltered.Htmlda
import com.github.hexx.htmlda.unfiltered.Htmlda._

import unfiltered.request._
import unfiltered.response._

class Sample extends unfiltered.filter.Plan {
  def intent = {
    case GET(Path(p)) => Ok ~> view(p)
  }

  def view(path: String) = Htmlda {
    %html {
      %head {
        %link $("rel" -> "stylesheet", "href" -> "style.css")
        %title t("タイトルだよ")
      }
      %body {
        %p t(s"ここは $path です。")

        for (i <- 1 to 3) {
          %p t(s"${i}だよ")
        }
        %p {
          %a ($("href" -> "http://www.scala-lang.org/"), t("スカラ"))
        }
      }
    }
  }
}

object Sample {
  def main(args: Array[String]) {
    val http = unfiltered.jetty.Http.anylocal
    http.filter(new Sample).run(_ =>
      unfiltered.util.Browser.open(http.url)
    )
  }
}
