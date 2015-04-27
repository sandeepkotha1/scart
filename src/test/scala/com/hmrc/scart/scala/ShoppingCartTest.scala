package com.hmrc.scart.scala

import org.junit._
import Assert._

import org.json.JSONObject
import scala.io.Source;

import org.skyscreamer.jsonassert.JSONAssert;

import com.hmrc.scart.scala._


class ShoppingCartTest {

  
    
  @Test
  def testEmptyCart() =
  {
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/checkout/payloads/response/", "emptyCart.json");
    
    val cart : Cart = ShoppingCart.apply;
    JSONAssert.assertEquals(cart.checkOut().toString(), response.toString(), true);
  } 
  
  
  
  @Test
  def testOnlyBasePrice() =
  {
    val request : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/checkout/payloads/response/", "basePrice.json");
    
    val cart : Cart = ShoppingCart.apply;   
    
    JSONAssert.assertEquals(cart.products(request).products(request).products(request).checkOut().toString(), response.toString(), true);   
  }
  
  @Test
  def testWithPriceRule() =
  {
    val request : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/checkout/payloads/response/", "priceRule.json");
    
    val cart : Cart =  ShoppingCart.apply;
    
   
    JSONAssert.assertEquals(cart.products(request).products(request).products(request).checkOut().toString(), response.toString(), true);   
  }
  
  @Test
  def testPriceRuleWithComboSkus() =
  {
    val appleRequest : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
    val OrangeRequest : JSONObject = TestData.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
    val response : JSONObject =  TestData.getTestData("com/hmrc/scart/checkout/payloads/response/", "priceRuleWithComboSkus.json");
    
    val cart : Cart = ShoppingCart.apply;
    
    JSONAssert.assertEquals(cart.products(appleRequest).products(OrangeRequest).products(OrangeRequest).products(appleRequest).products(OrangeRequest).
    products(appleRequest).products(OrangeRequest).products(appleRequest).products(OrangeRequest).checkOut().toString(), response.toString(), true);    
  } 


}


