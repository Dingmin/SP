package com.sso.client.validation;

import org.joda.time.DateTime;
import org.opensaml.Configuration;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.common.impl.SecureRandomIdentifierGenerator;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.saml2.core.AttributeStatement;
import org.opensaml.saml2.core.AttributeValue;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.XMLObjectBuilder;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.schema.XSString;


public class Utils {
	
	static{
		 try {
			DefaultBootstrap.bootstrap();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId() throws Exception{
		return new SecureRandomIdentifierGenerator().generateIdentifier();
	}

	public Issuer getIssuer(String issuer2) {
		// TODO Auto-generated method stub
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
		@SuppressWarnings("rawtypes")
		SAMLObjectBuilder builder = (SAMLObjectBuilder) builderFactory.getBuilder(Issuer.DEFAULT_ELEMENT_NAME);
		Issuer issuer = (Issuer) builder.buildObject();
		issuer.setValue(issuer2);
		return issuer;
	}

	

	public Conditions getConditions(DateTime time, DateTime date2) {
		System.out.println("========="+time.toString("yyyyMMdd-hh:mm")+"\t"+date2.toString("yyyyMMdd-hh:mm"));
		// TODO Auto-generated method stub
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
		@SuppressWarnings("rawtypes")
		SAMLObjectBuilder builder = (SAMLObjectBuilder) builderFactory.getBuilder(Conditions.DEFAULT_ELEMENT_NAME);
		Conditions conditions = (Conditions) builder.buildObject();
		conditions.setNotBefore(time.minusHours(4));
		conditions.setNotOnOrAfter(date2.minusHours(4));
		return conditions;
	}
	
	public AttributeStatement getAttri(String username, String md_string) {
		// TODO Auto-generated method stub
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
		 @SuppressWarnings("rawtypes")
		SAMLObjectBuilder attrStatementBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(AttributeStatement.DEFAULT_ELEMENT_NAME);
         AttributeStatement attrStatement = (AttributeStatement) attrStatementBuilder.buildObject();
         attrStatement.getAttributes().add(getAttrb("sys",username));
         attrStatement.getAttributes().add(getAttrb("m",md_string));
		return attrStatement;
	}

	private Attribute getAttrb(String key,String username) {
		// TODO Auto-generated method stub
		XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
		@SuppressWarnings("rawtypes")
		SAMLObjectBuilder attrBuilder = (SAMLObjectBuilder) builderFactory.getBuilder(Attribute.DEFAULT_ELEMENT_NAME);
        Attribute attrGroups = (Attribute) attrBuilder.buildObject();
        attrGroups.setName(key);
        @SuppressWarnings("rawtypes")
		XMLObjectBuilder stringBuilder = builderFactory.getBuilder(XSString.TYPE_NAME);
        XSString attrNewValue = (XSString) stringBuilder.buildObject(AttributeValue.DEFAULT_ELEMENT_NAME, XSString.TYPE_NAME);
        attrNewValue.setValue(username);
        attrGroups.getAttributeValues().add(attrNewValue);
		return attrGroups;
	}
	
}
