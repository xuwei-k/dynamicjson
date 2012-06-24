package dynamicJSON

import org.junit.Test
import org.junit.Assert._
import language._

class JSONspec {

  @Test
  def test = {
    assertEquals( DynamicJSON("""{"a":1}""").a.typed[Double] , Some(1.0) )
    assertEquals( DynamicJSON("""{"a":1}""").a.typed[String] , None )
    assertEquals( DynamicJSON("""{"a":false}""").a.typed[Boolean] , Some(false) )
    assertEquals( DynamicJSON("""{"a":null}""").a.typed[Null] , None )
    assertEquals( DynamicJSON("""{"a":"b"}""").a.typed[String] , Some("b") )
    assertEquals( DynamicJSON("""{"a":[1,2,3]}""").a.typed[List[Double]] , Some(List(1.0,2.0,3.0)) )
    assertEquals( DynamicJSON("""{"a":[1,2,3]}""").a.array.flatMap{_.typed[Double]} , List(1.0,2.0,3.0) )
    assertEquals( DynamicJSON("""{"a":[1,2,3,"x","y","z"]}""").a.array.collect{case ValueJSON(j) => j.toString} , List("1.0","2.0","3.0","x","y","z") )

    val j = DynamicJSON("""{"a":[{"foo":1,"bar":true,"baz":"x"},{"foo":2,"bar":false,"baz":"y"}]}""")
    val parsedObj = j.a.array.map{o => ( o.foo.typed[Double] ,o.bar.typed[Boolean] ,o.baz.typed[String] ) }
    assertEquals( parsedObj , List( (Some(1.0),Some(true),Some("x")) , (Some(2.0),Some(false),Some("y")) ) )

    assertEquals( DynamicJSON("""{"aa":{"bb":{"cc":{"dd":"ee"}}}}""").aa.bb.cc.dd.typed[String] , Some("ee") )
    assertEquals( DynamicJSON("""{"aa":[123,"hoge"]}""").aa.typed[List[String]] , None )
    assertEquals( DynamicJSON("""{"aa":["foo","bar"]}""").aa.typed[List[String]] , Some(List("foo","bar")) )
  }

  @Test
  def testTwitter = {
    val query = "scala"
    val j = DynamicJSON(scala.io.Source.fromURL("http://search.twitter.com/search.json?q=" + query ).mkString )
    val o = j.results.array.head

    assertTrue(j.completed_in         .typed[Double].isDefined)
    assertTrue(j.max_id               .typed[Double].isDefined)
    assertTrue(j.max_id_str           .typed[String].isDefined)
    assertTrue(j.page                 .typed[Double].isDefined)
    assertTrue(j.query                .typed[String].isDefined)
    assertTrue(j.refresh_url          .typed[String].isDefined)
    assertTrue(o.created_at           .typed[String].isDefined)
    assertTrue(o.from_user            .typed[String].isDefined)
    assertTrue(o.from_user_id         .typed[Double].isDefined)
    assertTrue(o.from_user_id_str     .typed[String].isDefined)
    assertTrue(o.id                   .typed[Double].isDefined)
    assertTrue(o.id_str               .typed[String].isDefined)
    assertTrue(o.iso_language_code    .typed[String].isDefined)
    assertTrue(o.metadata.result_type .typed[String].isDefined)
    assertTrue(o.profile_image_url    .typed[String].isDefined)
    assertTrue(o.source               .typed[String].isDefined)
    assertTrue(o.text                 .typed[String].isDefined)
  }
}

