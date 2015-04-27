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

	private final ConcurrentMap<String,LineItem> lineItems;
	private final BigDecimal basePrice;
	private final BigDecimal youSave;
	private final BigDecimal yourPrice;
	/**
	 * 
	 */
	public CartImpl() {
		this.basePrice = BigDecimal.ZERO;
		this.youSave = BigDecimal.ZERO;
		this.yourPrice = BigDecimal.ZERO;
		this.lineItems = new ConcurrentHashMap<String, LineItem>();
	}
	
	private CartImpl(BigDecimal basePrice, BigDecimal yourPrice, BigDecimal youSave, ConcurrentMap<String, LineItem> lineItems) {
		this.basePrice = basePrice;
		this.youSave = youSave;
		this.yourPrice = yourPrice;
		this.lineItems = lineItems;
		
	}
	
	private ConcurrentMap<String, LineItem> getLineItems() {
		return lineItems;
	}

	private BigDecimal getBasePrice() {
		return basePrice;
	}

	private BigDecimal getYouSave() {
		return youSave;
	}

	private BigDecimal getYourPrice() {
		return yourPrice;
	}
	
	@Override
	public Cart products(final JsonObject sku) {
		
		final LineItem lineItemInCart = (this.lineItems != null && this.lineItems.containsKey(sku.getString(SCartConstants.SKU_ID))) ? 
				this.lineItems.get(sku.getString(SCartConstants.SKU_ID)) : null;
		
		final LineItem lineItem = lineItemInCart != null ? lineItemInCart.updateQuantity(lineItemInCart.getQuantity() + sku.getInt(SCartConstants.QTY)) : new LineItem(sku);
				
		final ConcurrentMap<String,LineItem> lineItemMap = new ConcurrentHashMap<String, LineItem>(this.lineItems);
		lineItemMap.put(sku.getString(SCartConstants.SKU_ID), lineItem);
				
		return new CartImpl(getDifferentiationValue(this.basePrice, (lineItemInCart != null ? lineItemInCart.getBasePrice() : BigDecimal.ZERO), lineItem.getBasePrice()),
				getDifferentiationValue(this.yourPrice, (lineItemInCart != null ? lineItemInCart.getYourPrice() :  BigDecimal.ZERO), lineItem.getYourPrice()),
				getDifferentiationValue(this.youSave, (lineItemInCart != null ? lineItemInCart.getDiscountAmount() :  BigDecimal.ZERO), lineItem.getDiscountAmount()),
				lineItemMap);
	}

	@Override
	public JsonObject checkOut() {
		
		return toJsonObject();
	}
	
	private JsonObject toJsonObject()
	{
		return Json.createObjectBuilder().
				add(SCartConstants.BASE_PRICE, getBasePrice()).
				add(SCartConstants.YOU_SAVE, getYouSave()).
				add(SCartConstants.YOUR_PRICE, getYourPrice()).
				add(SCartConstants.LINE_ITEMS, addToJsonArrayBuilder(getLineItems().values())).build();
	}
	
	private JsonArrayBuilder addToJsonArrayBuilder(Collection<LineItem> values) {
		
		final JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		for(LineItem lineItem : values)
			jsonArrayBuilder.add(lineItem.toJsonObject());
			
		return jsonArrayBuilder;
	}
	

	private BigDecimal getDifferentiationValue(BigDecimal cartValue, BigDecimal lineItemValue, BigDecimal toUpdate)
	{
		return ((cartValue.subtract(lineItemValue)).add(toUpdate));
	}
}
