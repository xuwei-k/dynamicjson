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
    "number only list , `array` method should return List[DynamicJSON]" ! {
      DynamicJSON("""{"a":[1,2,3]}""").a.array.flatMap{_.typed[Double]} mustEqual List(1.0,2.0,3.0)
    }^
    "number and String list , `array` method should return all elements" ! {
      DynamicJSON("""{"a":[1,2,3,"x","y","z"]}""").a.array.collect{case ValueJSON(j) => j.toString} mustEqual List("1.0","2.0","3.0","x","y","z") 
    }^
    "json object array" ! {
      val j = DynamicJSON("""{"a":[{"foo":1,"bar":true,"baz":"x"},{"foo":2,"bar":false,"baz":"y"}]}""")
      val parsedObj = j.a.array.map{o => ( o.foo.typed[Double] ,o.bar.typed[Boolean] ,o.baz.typed[String] ) } 
      parsedObj mustEqual List( (Some(1.0),Some(true),Some("x")) , (Some(2.0),Some(false),Some("y")) )
    }^
    "deep nest string map" ! {   
      DynamicJSON("""{"aa":{"bb":{"cc":{"dd":"ee"}}}}""").aa.bb.cc.dd.typed[String] mustEqual Some("ee")
    }^
    "string and number mix, typed[List[String]] should return None" ! {
      DynamicJSON("""{"aa":[123,"hoge"]}""").aa.typed[List[String]] mustEqual None
    }^  
    "string only list, typed[List[String]] should return Some" ! {
      DynamicJSON("""{"aa":["foo","bar"]}""").aa.typed[List[String]] mustEqual Some(List("foo","bar"))
    }^
    "twitter serach API" ! {

      val query = "scala"
      val j = DynamicJSON(scala.io.Source.fromURL("http://search.twitter.com/search.json?q=" + query ).mkString )
      val o = j.results.array.head

      {j.completed_in         .typed[Double] must beSome}and
      {j.max_id               .typed[Double] must beSome}and
      {j.max_id_str           .typed[String] must beSome}and
      {j.page                 .typed[Double] must beSome}and
      {j.query                .typed[String] must beSome}and
      {j.refresh_url          .typed[String] must beSome}and
      {o.created_at           .typed[String] must beSome}and
      {o.from_user            .typed[String] must beSome}and
      {o.from_user_id         .typed[Double] must beSome}and
      {o.from_user_id_str     .typed[String] must beSome}and
      {o.id                   .typed[Double] must beSome}and
      {o.id_str               .typed[String] must beSome}and
      {o.iso_language_code    .typed[String] must beSome}and
      {o.metadata.result_type .typed[String] must beSome}and
      {o.profile_image_url    .typed[String] must beSome}and
      {o.source               .typed[String] must beSome}and
      {o.text                 .typed[String] must beSome}
// optional fields ?
//    {o.to_user              .typed[String] must beSome}and
//    {o.to_user_id           .typed[Double] must beSome}and
//    {o.to_user_id_str       .typed[String] must beSome}and
//    {o.geo                  .typed[String] must beSome}and
    } 
}

