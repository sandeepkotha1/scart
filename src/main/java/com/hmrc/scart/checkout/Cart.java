/**
 * 
 */
package com.hmrc.scart.checkout;

import javax.json.JsonObject;

/**
 * @author skotha
 *
 */
public interface Cart {

	public Cart products(JsonObject sku);
	
	public JsonObject checkOut();
	
}
