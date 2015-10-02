package com.sso.client.validation;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import com.sso.client.Analyzation.Analyze;
public class AssertionValidate {

	private String assertion;
	private static AssertionValidate validator = null;

	public String getAssertion() {
		return assertion;
	}

	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}

	private AssertionValidate() {
		
	}

	public static AssertionValidate getInstance() {
		if (validator == null) {
			validator = new AssertionValidate();
		}
		return validator;
	}

	public boolean checkAssertionVlidate(String key,DateTime date, HttpSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("收到的assertion:"+assertion);
		boolean flag = new Analyze().alive(assertion,key,date,session);
		return flag;
	}

	@Override
	public String toString() {
		return "AssertionValidate [assertion=" + assertion + "]";
	}

	
	
}
