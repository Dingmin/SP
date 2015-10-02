package com.sso.client.validation;

import java.net.URLDecoder;

public class DecodeString {

	
	public String getElement(String decodedString) throws Exception{
		String result =  URLDecoder.decode(decodedString, "utf-8");
		result = EncodeUrl.decrypt(result);
		return result;
	}
}
