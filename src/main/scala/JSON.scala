trait DynamicJSON extends Dynamic{
  def applyDynamic(name:String)(args:Any*):DynamicJSON
  def typed[A : Manifest]: Option[A]
}

object DynamicJSON{
  import scala.util.parsing.json.JSON.parseFull
  
  def apply(s:String):DynamicJSON = parseFull(s) match {
    case Some(j:Map[String,Any]) => new DynamicJSONObj(j)
    case None => EmptyJSON
  }

}

case class DynamicJSONObj(obj:Map[String,Any]) extends DynamicJSON {

  override def typed[A: Manifest] = None

  override def applyDynamic(name: String)(args: Any*): DynamicJSON = obj(name) match {
      case null | None => EmptyJSON
//    case i: Int => ValueJSON(i)
//    case l: Long => ValueJSON(l)
      case d: Double => ValueJSON(d)
      case b: Boolean => ValueJSON(b)
      case s: String => ValueJSON(s)
      case a: List[_] => {
        val classes = a.foldLeft(Set[Class[_]]()){ (set,elem) =>
          set + {
            if(elem == null) classOf[Null]
            else elem.asInstanceOf[AnyRef].getClass
          }
        }
        if(classes.size == 1){
          a.head match{
            case _:Double  => ValueJSON(a.asInstanceOf[List[Double]])
            case _:Boolean => ValueJSON(a.asInstanceOf[List[Boolean]] )
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

case object EmptyJSON extends DynamicJSON{
  override def applyDynamic(name: String)(args: Any*): DynamicJSON = this
  override def typed[A: Manifest] = None
}

case class ValueJSON[V : Manifest](value: V) extends DynamicJSON {

  override def applyDynamic(name: String)(args: Any*): DynamicJSON = EmptyJSON 
  override def typed[A: Manifest] = 
    if(manifest[V] == manifest[A])
      Some(value.asInstanceOf[A])
    else
      None

  // debug method
  def manifestString:String = manifest[V].toString

}
