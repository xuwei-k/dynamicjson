import org.specs2.mutable._

class JSONspec extends Specification {

  "JSON" should {
    
    "" in {

      DynamicJSON("""{"a":1}""").a.typed[Double] mustEqual Some(1.0)

      DynamicJSON("""{"a":1}""").a.typed[String] mustEqual None

      DynamicJSON("""{"a":"b"}""").a.typed[String] mustEqual Some("b")

      DynamicJSON("""{"a":[1,2,3]}""").a.typed[List[Double]] mustEqual Some(List(1.0,2.0,3.0))
     
      // DynamicJSON("""{"a":{"b":{"c":{"d":"e"}}}}""").a.b.c.d.typed[String] mustEqual Some("e")
      // can't compile this code. :(  
      // why ?
    
    }

  }

}



// vim: set ts=2 sw=2 et:
