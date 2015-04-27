/**
 *
 */
package com.hmrc.scart.scala

import org.json.JSONObject
import org.json.JSONArray
import scala.collection._
import com.hmrc.scart.constants.SCartConstants;
import scala.math.BigDecimal

/**
 * @author skotha
 *
 */
class ShoppingCart(lineItems: Map[String, LineItem], basePrice : BigDecimal, youSave : BigDecimal, yourPrice : BigDecimal) extends Cart {

 
 def products(sku: JSONObject): Cart = {
    
   val lineItemInCart : LineItem = if ((this.lineItems != null && this.lineItems.contains(sku.getString(SCartConstants.SKU_ID)))) 
      lineItems.get(sku.getString(SCartConstants.SKU_ID)).get else null
      
    val lineItem : LineItem  = if (lineItemInCart != null) lineItemInCart.updateQuantity(lineItemInCart.quantity + sku.getInt(SCartConstants.QTY)) else LineItem.apply(sku)
    
    val lineItemMap : Map[String, LineItem] = Map[String, LineItem]() ++ lineItems + (sku.getString(SCartConstants.SKU_ID) -> lineItem) 
    
   
    new ShoppingCart(lineItemMap,
        getDifferentiationValue(this.basePrice, (if (lineItemInCart != null) lineItemInCart.basePrice else BigDecimal.apply(0)), lineItem.basePrice),
        getDifferentiationValue(this.youSave, (if (lineItemInCart != null) lineItemInCart.discountAmount else BigDecimal.apply(0)),lineItem.discountAmount),
        getDifferentiationValue(this.yourPrice, (if (lineItemInCart != null) lineItemInCart.yourPrice else BigDecimal.apply(0)), lineItem.yourPrice))
  }
  
  def checkOut(): JSONObject = toJsonObject()
 
  def  getDifferentiationValue(cartValue : BigDecimal , lineItemValue : BigDecimal, toUpdate : BigDecimal) : BigDecimal = ((cartValue.-(lineItemValue)).+(toUpdate));
  
  def toJsonObject() : JSONObject =
  {
    new JSONObject(). put(SCartConstants.BASE_PRICE, basePrice). put(SCartConstants.YOU_SAVE, youSave).
        put(SCartConstants.YOUR_PRICE, yourPrice). put(SCartConstants.LINE_ITEMS, addToJsonArrayBuilder(lineItems.values));
  }
  
  def addToJsonArrayBuilder(values :Iterable[LineItem]) :  JSONArray = {    
  values.foldLeft(new JSONArray()){case (jsonArray : JSONArray, lineItem : LineItem) => jsonArray.put(lineItem.toJsonObject)}};
   
}

object ShoppingCart  {
  
  def apply : ShoppingCart = { new ShoppingCart(Map[String, LineItem](), BigDecimal.apply(0),  BigDecimal.apply(0),  BigDecimal.apply(0))}
  
} 