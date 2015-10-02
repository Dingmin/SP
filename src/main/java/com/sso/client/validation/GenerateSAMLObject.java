package com.sso.client.validation;


import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.common.SAMLObjectBuilder;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.impl.AssertionMarshaller;
import org.opensaml.saml2.core.impl.AssertionUnmarshaller;
import org.opensaml.xml.Configuration;
import org.opensaml.xml.XMLObjectBuilderFactory;
import org.opensaml.xml.parse.BasicParserPool;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GenerateSAMLObject {

	private String username;
	public GenerateSAMLObject(String username) {
		super();
		this.setUsername(username);
	}
	
public GenerateSAMLObject() {
	// TODO Auto-generated constructor stub
}
	public Assertion generate(String username,String issuer,DateTime date, DateTime date2, String md_string) throws Exception{
		Utils utils = new Utils();
			XMLObjectBuilderFactory builderFactory = Configuration.getBuilderFactory();
			 
			// Get the assertion builder based on the assertion element name
			@SuppressWarnings("rawtypes")
			SAMLObjectBuilder builder = (SAMLObjectBuilder) builderFactory.getBuilder(Assertion.DEFAULT_ELEMENT_NAME);
//			System.out.println(builderFactory);
//			System.out.println(builder);
			Assertion assertion = (Assertion) builder.buildObject();
			assertion.setID(utils.getId());
			assertion.setIssueInstant(date);
			assertion.setIssuer(utils.getIssuer(issuer));
			assertion.setConditions(utils.getConditions(date,date2));
			assertion.getAttributeStatements().add(utils.getAttri(username,md_string));
			return assertion;
	}
	
	public String generateToString(Assertion assertion) throws Exception{
		 DefaultBootstrap.bootstrap();
		 AssertionMarshaller marshaller = new AssertionMarshaller();
         Element element = marshaller.marshall(assertion);
         TransformerFactory  tf  =  TransformerFactory.newInstance();  
  		Transformer t = tf.newTransformer();  
  		t.setOutputProperty("encoding","UTF-8");//解决中文问题，试过用GBK不行  
  		ByteArrayOutputStream  bos  =  new  ByteArrayOutputStream(); 
  		t.transform(new DOMSource(element), new StreamResult(bos));  
  		String xmlString =  bos.toString(); 
  		return xmlString;
	}
	
	public Assertion StringToAssertion(String xmlString) throws Exception{
		Document d =new BasicParserPool().parse(new StringReader(xmlString));
 		Element e = d.getDocumentElement();
 		AssertionUnmarshaller unmarshaller = new AssertionUnmarshaller();
 		Assertion assertion =(Assertion) unmarshaller.unmarshall(e);
 		return assertion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
