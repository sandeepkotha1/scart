/**
 *
 */
package com.hmrc.scart.scala

import org.json.JSONObject
import scala.io.Source

/**
 * @author skotha
 *
 */
object TestData {

  
  def getTestData(relPath : String, file :String) : JSONObject =
  {
    
    val source = Source.fromFile(getClass.getClassLoader().getResource(relPath).getPath()+file);
    
    val testData :JSONObject = new JSONObject(source.getLines.mkString)     
    source.close()
    testData  
  }
}