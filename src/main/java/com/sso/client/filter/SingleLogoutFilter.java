package com.sso.client.filter;

import java.io.IOException;
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

public class SingleLogoutFilter implements Filter {

	private String serverLogoutUrl;

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		serverLogoutUrl = filterConfig.getInitParameter("serverLogoutUrl");
       
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest hsp = (HttpServletRequest) request;
		HttpServletResponse hsr = (HttpServletResponse) response;
		HttpSession session = hsp.getSession();
		session.setAttribute("assertion", null);
		System.out.println("send redirect to logout");
			hsr.sendRedirect(serverLogoutUrl+"?params="+URLEncoder.encode(hsp.getRequestURL().toString(), "utf-8"));
					
		
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

}
