package dynamicJSON

import java.lang.{Boolean => JBool,Double => JDouble}
import language._

trait DynamicJSON extends Dynamic{
  def selectDynamic(name:String):DynamicJSON
  def typed[A : Manifest]: Option[A]
  def array:List[DynamicJSON] = Nil
}

object DynamicJSON{
  import scala.util.parsing.json.JSON.parseFull

  def apply(s:String):DynamicJSON = parseFull(s) match {
    case Some(j:Map[String,Any]) => new DynamicJSONObj(j)
    case None => EmptyJSON
  }

  val cast:PartialFunction[Any,DynamicJSON] = {
    case null | None => EmptyJSON
//  case i: Int     => ValueJSON(i) // Maybe , Scala standard JSON library not return Int or Long .
//  case l: Long    => ValueJSON(l)
    case d: Double  => ValueJSON(d)
//    case d: JDouble => ValueJSON(d.doubleValue) //TODO is this need ?
    case b: Boolean => ValueJSON(b)
//    case b: JBool   => ValueJSON(b.booleanValue)//TODO is this need ?
    case s: String  => ValueJSON(s)
    case a: List[_] => {
      val classes = a.foldLeft(Set[Class[_]]()){ (set,elem) =>
        set + {
          if(elem == null) classOf[Null]
          else elem.asInstanceOf[AnyRef].getClass
        }
      }
      if(classes.size == 1){
        a.head match{
          case _:Double  => ValueJSON(a.asInstanceOf[List[Double]] )
//        case _:JDouble => ValueJSON(a.asInstanceOf[List[Double]] )//TODO is this need ?
          case _:Boolean => ValueJSON(a.asInstanceOf[List[Boolean]])
//        case _:JBool   => ValueJSON(a.asInstanceOf[List[Boolean]])//TODO is this need ?
          case _:String  => ValueJSON(a.asInstanceOf[List[String]] )
          case _         => ValueJSON(a.asInstanceOf[List[Any]] )
        }
      }else{
        ValueJSON[List[Any]](a)
      }
    }
    case map:Map[String,Any] => new DynamicJSONObj(map)
  }

}

case class DynamicJSONObj(obj:Map[String,Any]) extends DynamicJSON {

  override def typed[A: Manifest] = None

  override def selectDynamic(name: String): DynamicJSON = DynamicJSON.cast(obj(name))

}

case object EmptyJSON extends DynamicJSON{
  override def selectDynamic(name: String): DynamicJSON = this
  override def typed[A: Manifest] = None
}

case class ValueJSON[V : Manifest](value: V) extends DynamicJSON {

  override def selectDynamic(name: String): DynamicJSON = EmptyJSON
  override def typed[A: Manifest] =
    if(implicitly[Manifest[V]] == implicitly[Manifest[A]])
      Some(value.asInstanceOf[A])
    else
      None

  // debug method
  def manifestString:String = implicitly[Manifest[V]].toString

  override def array = value match{
    case l: List[_] => l.map{DynamicJSON.cast}
    case _          => Nil
  }

}

trait Imports{
  val  DynamicJSON    = dynamicJSON.DynamicJSON
  type DynamicJSON    = dynamicJSON.DynamicJSON
  val  DynamicJSONObj = dynamicJSON.DynamicJSONObj
  type DynamicJSONObj = dynamicJSON.DynamicJSONObj
  val  EmptyJSON      = dynamicJSON.EmptyJSON
  type EmptyJSON      = dynamicJSON.EmptyJSON.type
  val  ValueJSON      = dynamicJSON.ValueJSON
  type ValueJSON[V]   = dynamicJSON.ValueJSON[V]
}

object Imports extends Imports


