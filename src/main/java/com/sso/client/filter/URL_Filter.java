package com.sso.client.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URLEncoder;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.opensaml.DefaultBootstrap;
import org.opensaml.xml.ConfigurationException;

import com.sso.client.validation.AssertionValidate;
import com.sso.client.validation.EncodeUrl;

public class URL_Filter implements Filter {

	private String serverLoginUrl;
	private AssertionValidate validator;
	private boolean flag ;
	private String sysUrl;
	private String params;
	private String key;
	private ObjectInputStream stream1;

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		serverLoginUrl = filterConfig.getInitParameter("serverLoginUrl");
		validator = AssertionValidate.getInstance();
		flag=false;
		try {
			stream1 = new ObjectInputStream(
					new FileInputStream(new File("../webapps/sso_conf/share.key")));
			try {
				key = (String) stream1.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("没有 privateKey类");
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("找不到密钥");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("读取异常");
			e.printStackTrace();
		}
       try {
		DefaultBootstrap.bootstrap();
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		System.out.println("opeansaml 加载出现问题，请查看相关包是否已导入");
		e.printStackTrace();
	}
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		String assertion = null;
		HttpServletRequest hsp = (HttpServletRequest) request;
		HttpServletResponse hsr = (HttpServletResponse) response;
		if(!flag) {
			flag=true;
			sysUrl = hsp.getRequestURL().toString();
			if(sysUrl.endsWith("/")) sysUrl = sysUrl.substring(0, sysUrl.length()-1);
		System.out.println("系统应用访问路径："+sysUrl);
		
		}
		System.out.println("url::" + hsp.getRequestURL());
		HttpSession session = hsp.getSession();
		assertion = (String)session.getAttribute("assertion");
		if (assertion == null) {// 检查session
														// 是否有assertion
			assertion = hsp.getParameter("assert");
			if (assertion == null || assertion.trim() == "") {// 检查request param
																// 是否有assertion
				System.out.println("session and request 都没有 assertion");
				new EncodeUrl();
				params = EncodeUrl.encrypt(hsp.getRequestURL()+";"+sysUrl);
				hsr.sendRedirect(serverLoginUrl + "?target=" + URLEncoder.encode(params,"utf-8"));
				
				return ;
			}
			
				
		}	
			validator.setAssertion(assertion);
		System.out.println(assertion.length());
		/*
		 * 检查assertion是否有效
		 * 
		 * 有效：assertion
		 * 无效：重定向验证身份
		 */
		try {
			if (!validator.checkAssertionVlidate(key,new DateTime(),session)) {//无效
				session.setAttribute("assertion", null);
				System.out.println("assertion:"+assertion);
				System.out.println("发现assertion"+validator.toString()+"但无效");
				new EncodeUrl();
				params = EncodeUrl.encrypt(hsp.getRequestURL()+";"+sysUrl);
				hsr.sendRedirect(serverLoginUrl + "?target=" + URLEncoder.encode(params,"utf-8"));
				return;
			}else{//
				//有效则判断是否要存assertion进session
				if(session.getAttribute("assertion")==null){
					session.setAttribute("assertion", assertion);
					
				}
				if( hsp.getRequestURL().indexOf(sysUrl)<0){
					request.setAttribute("assert", assertion);

				}
				System.out.println("pass and could request the resources");
				System.out.println("the user want to request:"+((HttpServletRequest)request).getRequestURL());
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
