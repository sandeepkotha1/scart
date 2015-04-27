/**
 *
 */
package com.hmrc.scart.scala


import com.hmrc.scart.constants.SCartConstants
import org.json.JSONObject

/**
 * @author skotha
 *
 */
class LineItem(val sku : JSONObject, val skuName : String, val quantity : Int, val unitPrice : BigDecimal, val basePrice:BigDecimal,val discountAmount : BigDecimal,
    val yourPrice :BigDecimal, val currency :String) {
   
  
  def updateQuantity(qty : Int) : LineItem = {
    val quantity = LineItem.qanty(qty) 
    val basePrice = (unitPrice.*(quantity))
     val discount = LineItem.calculateDiscount(sku, quantity)
     new LineItem(sku, skuName,quantity, unitPrice, basePrice, discount, (basePrice.-(discount)), currency)};
     
  def toJsonObject : JSONObject  =
  {
     new JSONObject().put(SCartConstants.SKU_NAME, skuName). put(SCartConstants.QTY, quantity). put(SCartConstants.UNIT_PRICE, unitPrice).
        put(SCartConstants.BASE_PRICE, basePrice). put(SCartConstants.CURRENCY, currency). put(SCartConstants.YOU_SAVE, discountAmount).
        put(SCartConstants.YOUR_PRICE, yourPrice);
  } 
  
  
}

object LineItem{
  
  def apply(sku : JSONObject) : LineItem = 
  {
    val quantity : Int = qanty(sku.getInt(SCartConstants.QTY)); 
    val unitPrice : BigDecimal = BigDecimal.apply(sku.getString(SCartConstants.UNIT_PRICE));
    val basePrice : BigDecimal = unitPrice.*(quantity)
    val discount : BigDecimal = calculateDiscount(sku, quantity)

    new LineItem(sku, sku.getString(SCartConstants.SKU_NAME), quantity, unitPrice, 
         basePrice, discount, (basePrice - discount),sku.getString(SCartConstants.CURRENCY))      
  }
  
  def calculateDiscount(sku : JSONObject, quantity : Int) : BigDecimal = {
     val directive : JSONObject = if(sku.has(SCartConstants.DIRECTIVE))sku.getJSONObject(SCartConstants.DIRECTIVE) else null;
    if(directive != null)  
      (quantity / directive.getInt(SCartConstants.FOR_EACH)).*(BigDecimal.apply(directive.getString(SCartConstants.ABSOLUTE_DISCOUNT))) 
    else
           BigDecimal.apply(0);     
   
    }
  
  def qanty(qty : Int) : Int = if(qty < 0) 0 else qty; 
}  