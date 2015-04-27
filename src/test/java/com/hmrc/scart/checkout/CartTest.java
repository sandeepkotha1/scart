/**
 * 
 */
package com.hmrc.scart.checkout;

import java.io.IOException;

import javax.json.JsonObject;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.hmrc.scart.java.utill.TestDataHelper;

import junit.framework.TestCase;

/**
 * @author skotha
 *
 */
public class CartTest extends TestCase
{

	
	@Test
	public void testEmptyCart() throws JSONException, IOException
	{
		
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/response/", "emptyCart.json");
		
		Cart cart = new CartImpl();
		JSONAssert.assertEquals(cart.checkOut().toString(), response.toString(), true);
	}
	
	@Test
	public void testOnlyBasePrice() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/response/", "basePrice.json");
		
		Cart cart = new CartImpl();		
		JSONAssert.assertEquals(cart.products(request).products(request).products(request).checkOut().toString(), response.toString(), true);		
	}
	
	@Test
	public void testWithPriceRule() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/response/", "priceRule.json");
		
		Cart cart = new CartImpl();	
		JSONAssert.assertEquals(cart.products(request).products(request).products(request).checkOut().toString(), response.toString(), true);		
	}
	
	@Test
	public void testPriceRuleWithComboSkus() throws JSONException, IOException
	{
		JsonObject appleRequest = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
		JsonObject OrangeRequest = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/response/", "priceRuleWithComboSkus.json");
		
		Cart cart = new CartImpl();	
		JSONAssert.assertEquals(cart.products(appleRequest).products(OrangeRequest).products(OrangeRequest).products(appleRequest).products(OrangeRequest).
		products(appleRequest).products(OrangeRequest).products(appleRequest).products(OrangeRequest).checkOut().toString(), response.toString(), true);		
	}
	
	
	
}
