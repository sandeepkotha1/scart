/**
 * 
 */
package com.hmrc.scart.checkout;

import java.math.BigDecimal;

import javax.json.Json;
import javax.json.JsonObject;

import com.hmrc.scart.constants.SCartConstants;

/**
 * @author skotha
 *
 */
public class LineItem {

	private final String skuName;
	private final int quantity;
	private final BigDecimal unitPrice;
	private final BigDecimal basePrice;
	private final BigDecimal discountAmount;
	private final BigDecimal yourPrice;
	private final String currency;
	private final JsonObject sku;
	
	public LineItem(JsonObject sku)
	{
		this(sku, sku.getString(SCartConstants.SKU_NAME), sku.getInt(SCartConstants.QTY), 
				new BigDecimal(sku.getString(SCartConstants.UNIT_PRICE)), sku.getString(SCartConstants.CURRENCY));		
	}
	
	private LineItem(JsonObject sku, String skuName, int quantity, BigDecimal unitPrice, String currency)
	{
		this.sku = sku;
		this.skuName = skuName;		
		this.quantity = quantity < 0 ? 0 : quantity;
		this.unitPrice = unitPrice;
		this.basePrice = (this.unitPrice.multiply(new BigDecimal(this.quantity)));
		this.discountAmount = calculateDiscount(this.sku);
		this.yourPrice = this.basePrice.subtract(this.discountAmount);	
		this.currency = currency;		
	}	
	
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public String getSkuName() {
		return skuName;
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public BigDecimal getYourPrice() {
		return yourPrice;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	
	public JsonObject getSku() {
		return sku;
	}

	public LineItem updateQuantity(int quantity) {
		return new LineItem(this.sku, this.skuName, quantity, this.unitPrice, this.currency);
	}
	
	private BigDecimal calculateDiscount(JsonObject sku)
	{
		final JsonObject directive = sku.getJsonObject(SCartConstants.DIRECTIVE);
		return directive != null ? new BigDecimal(this.quantity / directive.getInt(SCartConstants.FOR_EACH)).multiply(
				new BigDecimal(directive.getString(SCartConstants.ABSOLUTE_DISCOUNT))) : 
					 BigDecimal.ZERO;
	}
	
	public JsonObject toJsonObject()
	{
		return Json.createObjectBuilder().
				add(SCartConstants.SKU_NAME, getSkuName()).
				add(SCartConstants.QTY, getQuantity()).
				add(SCartConstants.UNIT_PRICE, getUnitPrice()).
				add(SCartConstants.BASE_PRICE, getBasePrice()).
				add(SCartConstants.CURRENCY, getCurrency()).
				add(SCartConstants.YOU_SAVE, getDiscountAmount()).
				add(SCartConstants.YOUR_PRICE, getYourPrice()).build();
	}	
	
}
