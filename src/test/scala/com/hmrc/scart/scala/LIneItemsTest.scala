/**
 *
 */
package com.hmrc.scart.scala

import org.junit._
import Assert._

import org.json.JSONObject
import scala.io.Source;

import org.skyscreamer.jsonassert.JSONAssert;

import com.hmrc.scart.scala._

/**
 * @author skotha
 *
 */
class LIneItemsTest {

  @Test
  def testLineItemBasePrice() =
  {
     val request : JSONObject  = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
     val response : JSONObject  =  TestData.getTestData("com/hmrc/scart/lineItem/payloads/response/", "basePrice.json");
    
    JSONAssert.assertEquals(LineItem.apply(request).toJsonObject, response, true);    
  }
  
  @Test
  def testLineItemBasePriceByAddingSKUs() =
  {
     val request : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/lineItem/payloads/response/", "addsku.json");
    
    JSONAssert.assertEquals(LineItem.apply(request).updateQuantity(2).toJsonObject, response, true);    
  }
  
  @Test
  def testLineItemPriceToHandleNegativeQty() =
  {
     val request : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/lineItem/payloads/response/", "negativeqty.json");
    JSONAssert.assertEquals(LineItem.apply(request).updateQuantity(3).updateQuantity(-1).toJsonObject, response, true);   
  }
  
  @Test
  def testWithPriceRule() =
  {
     val request : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/lineItem/payloads/response/", "priceRule.json");
    
    JSONAssert.assertEquals(LineItem.apply(request).updateQuantity(5).toJsonObject, response, true);    
  } 
  
}