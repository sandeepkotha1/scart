/**
 * 
 */
package com.hmrc.scart.checkout;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import com.hmrc.scart.constants.SCartConstants;

/**
 * @author skotha
 *
 */
public class CartImpl implements Cart {

	
	
	@Override
	public Cart products(final JsonObject sku) {
		return null;
	}

	@Override
	public JsonObject checkOut() {
		return null;
		
	}	
	
}
