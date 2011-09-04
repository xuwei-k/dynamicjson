package dynamicJSON

import org.specs2.Specification

class JSONspec extends Specification {

  def is = "DynamicJSON should" ^
    "number , typed[Double] should return Some" ! {
      DynamicJSON("""{"a":1}""").a.typed[Double] mustEqual Some(1.0)
    }^
    "number , typed[String] should return None" ! {
      DynamicJSON("""{"a":1}""").a.typed[String] mustEqual None
    }^
    "bool , typed[Boolean] should return Some" ! {
      DynamicJSON("""{"a":false}""").a.typed[Boolean] mustEqual Some(false) 
    }^
    "null , typed[Null] should return None" ! {
      DynamicJSON("""{"a":null}""").a.typed[Null] mustEqual None 
    }^
    "String , typed[String] should return Some" ! { 
      DynamicJSON("""{"a":"b"}""").a.typed[String] mustEqual Some("b")
    }^
    "number only list , typed[List[Double]] should return Some" ! {
      DynamicJSON("""{"a":[1,2,3]}""").a.typed[List[Double]] mustEqual Some(List(1.0,2.0,3.0))
    }^
    "deep nest string map" ! {   
      DynamicJSON("""{"aa":{"bb":{"cc":{"dd":"ee"}}}}""").aa.bb.cc.dd.typed[String] mustEqual Some("ee")
    }^
    "string and number mix, typed[List[String]] should return None" ! {
      DynamicJSON("""{"aa":[123,"hoge"]}""").aa.typed[List[String]] mustEqual None
    }^  
    "string only list, typed[List[String]] should return Some" ! {
      DynamicJSON("""{"aa":["foo","bar"]}""").aa.typed[List[String]] mustEqual Some(List("foo","bar"))
    }  
}

