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
public class LineItemTest extends TestCase {

	@Test
	public void testLineItemBasePrice() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "apple.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/lineItem/payloads/response/", "basePrice.json");
		
		JSONAssert.assertEquals(new LineItem(request).toJsonObject().toString(), response.toString(), true);		
	}
	
	@Test
	public void testLineItemBasePriceByAddingSKUs() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/lineItem/payloads/response/", "addsku.json");
		
		JSONAssert.assertEquals(new LineItem(request).updateQuantity(2).toJsonObject().toString(), response.toString(), true);		
	}
	
	@Test
	public void testLineItemPriceToHandleNegativeQty() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/lineItem/payloads/response/", "negativeqty.json");
		
		JSONAssert.assertEquals(new LineItem(request).updateQuantity(3).updateQuantity(-1).toJsonObject().toString(), response.toString(), true);		
	}
	
	@Test
	public void testWithPriceRule() throws JSONException, IOException
	{
		JsonObject request = TestDataHelper.getTestData("com/hmrc/scart/checkout/payloads/", "orange.json");
		JsonObject response =  TestDataHelper.getTestData("com/hmrc/scart/lineItem/payloads/response/", "priceRule.json");
		
		JSONAssert.assertEquals(new LineItem(request).updateQuantity(5).toJsonObject().toString(), response.toString(), true);		
	}	
	
}
