package front

import cats.*
import cats.effect.*
import cats.implicits.*
import scala.scalajs.js.annotation.JSExportTopLevel
import tyrian.*
import tyrian.cmds.Logger
import tyrian.Html.*

object LearnTyrian {

  sealed trait Msg
  case object NoOp                          extends Msg
  case class Inc(amount: Int)               extends Msg
  case class Dec(amount: Int)               extends Msg
  case class LogRouterLocation(l: Location) extends Msg

  case class Model(counter: Int) {
    def inc(n: Int): Model = copy(counter = counter + n)
    def dec(n: Int): Model = copy(counter = counter - n)
  }

  object Model {
    def start: Model = Model(0)
  }

}

@JSExportTopLevel("App")
class LearnTyrian extends TyrianApp[LearnTyrian.Msg, LearnTyrian.Model] {

  import LearnTyrian.*

  override def init(flags: Map[String, String]): (Model, Cmd[IO, Msg]) =
    Model.start -> Cmd.None

  override def update(model: Model): Msg => (Model, Cmd[IO, Msg]) = {
    case Inc(x)               => model.inc(x) -> Cmd.None
    case Dec(x)               => model.dec(x) -> Cmd.None
    case LogRouterLocation(l) => model        -> Logger.consoleLog[IO]("router message:", l.toString)
    case NoOp                 => model        -> Cmd.None
  }

  override def subscriptions(model: Model): Sub[IO, Msg] = Sub.None
//    Sub.every[IO](1.second).map(_ => Inc(1))

  override def view(m: Model): Html[Msg] =
    div(
      button(onClick(Inc(1)))("inc"), // emit on click
      button(onClick(Dec(1)))("dec"), // emit on click
      div(s"Tyrian is running: ${m.counter}")
    )

  // required since 0.7.x, but I didn't get the idea of yet...
  override def router: Location => Msg = l => LogRouterLocation(l)
}
