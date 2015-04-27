/**
 * 
 */
package com.hmrc.scart.java.utill;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;

import com.hmrc.scart.checkout.CartTest;

/**
 * @author skotha
 *
 */
public class TestDataHelper {

	
	public static JsonObject getTestData(String relPath, String file) throws IOException
	{
		InputStream is = new FileInputStream(CartTest.class.getClassLoader().getResource(relPath).getPath()+file);
		JsonObject testData = Json.createReader(is).readObject();
		is.close();
		return testData;		
	}
	
}
