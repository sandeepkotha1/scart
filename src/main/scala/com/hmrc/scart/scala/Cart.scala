/**
 *
 */
package com.hmrc.scart.scala

import org.json.JSONObject

/**
 * @author skotha
 *
 */
trait Cart {

  def products(sku: JSONObject): Cart

  def checkOut(): JSONObject
}