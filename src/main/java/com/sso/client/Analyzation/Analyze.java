package com.sso.client.Analyzation;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;

import com.sso.client.validation.EncodeUrl;
import com.sso.client.validation.GenerateSAMLObject;

public class Analyze {

	public boolean alive(String element, String key, DateTime date, HttpSession session)
			throws Exception {

		
		GenerateSAMLObject object = new GenerateSAMLObject();
		Assertion a = object.StringToAssertion(element);
		String issuer = a.getIssuer().getValue();
		System.out.println("issuer:" + issuer);
		AttributeStatement attr = a.getAttributeStatements().get(0);
		List<Attribute> atribu = attr.getAttributes();
		System.out.println(atribu.size());
		Attribute ass = attr.getAttributes().get(0);
		List<XMLObject> list = ass.getAttributeValues();
		System.out.println(list.size());
		XSString username = (XSString) ass.getAttributeValues().get(0);
		System.out.println(username.getValue());
		String user = EncodeUrl.decrypt(username.getValue(), key);
		Attribute ass1 = attr.getAttributes().get(1);
		XSString md = (XSString) ass1.getAttributeValues().get(0);
		System.out.println("username:" + username.getValue());
		System.out.println("m:" + md.getValue());
		DateTime c1 = a.getConditions().getNotBefore();
		DateTime c2 = a.getConditions().getNotOnOrAfter();

		System.out.println(user + "\t" + issuer + "\t"
				+ c1.toString("yyyyMMdd-hh:mm") + "\t"
				+ c2.toString("yyyyMMdd-hh:mm"));
		String destring = EncodeUrl.encrypt(
				user + issuer + c1.toString("yyyyMMdd-hh:mm")
						+ c2.toString("yyyyMMdd-hh:mm"), key);
		System.out.println("de:" + destring);
		if (destring.equals(md.getValue())) {

			System.out.println("assertion 有效");
			System.out.println("当前时间：" + date + "\t"+c1
					+ c1.toString("yyyyMMdd-hh:mm") + "\t"
					+ c2.toString("yyyyMMdd-hh:mm") + "\t" + date.compareTo(c1)
					+ "\t" + date.compareTo(c2));
			System.out.println("aaaaa"+date.isAfter(c1.toDateTime())+"\t dgdf"+c1.isAfterNow()+"\t befor:"+c2.isBeforeNow());
//			if (DateTimeComparator.getInstance().compare(date, c1) >= -1
//					&& DateTimeComparator.getInstance().compare(date, c2) == -1)
			if((date.isAfter(c1.toDateTime())||c1.isAfterNow())&&(!c2.isBeforeNow())){
				session.setAttribute("Token", user);
				return true;
				}
		}
		System.out.println(" assertion 已过期");

		return false;
	}

	/*
	 * public boolean alive( String element, String key,DateTime date) throws
	 * Exception{ GenerateSAMLObject object = new GenerateSAMLObject();
	 * EncodeUrl encodeUrl =new EncodeUrl(); //-----
	 * System.out.println("client recieve assertion:"+element); Assertion a =
	 * object.StringToAssertion(element); String
	 * issuer=a.getIssuer().getValue(); System.out.println("issuer:"+issuer);
	 * AttributeStatement attr = a.getAttributeStatements().get(0);
	 * List<Attribute> atribu = attr.getAttributes();
	 * System.out.println(atribu.size()); Attribute ass =
	 * attr.getAttributes().get(0);
	 * 
	 * List<XMLObject> list = ass.getAttributeValues();
	 * System.out.println(list.size()); XSString username= (XSString)
	 * ass.getAttributeValues().get(0); System.out.println(username.getValue());
	 * String user = encodeUrl.decrypt(username.getValue(), key); Attribute ass1
	 * = attr.getAttributes().get(1); XSString md= (XSString)
	 * ass1.getAttributeValues().get(0);
	 * System.out.println("username:"+username.getValue());
	 * System.out.println("m:"+md.getValue()); DateTime c1 =
	 * a.getConditions().getNotBefore(); DateTime c2 =
	 * a.getConditions().getNotOnOrAfter();
	 * System.out.println(user+"\t"+issuer+"\t"
	 * +c1.toString("yyyyMMdd-hh:mm")+"\t"+c2.toString("yyyyMMdd-hh:mm"));
	 * String destring
	 * =encodeUrl.encrypt(user+issuer+c1.toString("yyyyMMdd-hh:mm"
	 * )+c2.toString("yyyyMMdd-hh:mm"),key); System.out.println("de:"+destring);
	 * if(destring.equals(md.getValue())) { System.out.println("assertion 有效");
	 * System
	 * .out.println("当前时间："+date+"\t"+c1.toString("yyyyMMdd-hh:mm")+"\t"+c2
	 * .toString
	 * ("yyyyMMdd-hh:mm")+"\t"+date.compareTo(c1)+"\t"+date.compareTo(c2));
	 * if(DateTimeComparator.getInstance().compare(date,
	 * c1)>-1&&DateTimeComparator.getInstance().compare(date, c2)==-1) return
	 * true; } System.out.println(" assertion 已过期");
	 * 
	 * return false; }
	 */

}
