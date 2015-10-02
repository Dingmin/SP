<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>welcome to sso_test1 </title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<head>
  		<style>
  			body {
  				background: #FFFFCC;
  			}
  		</style>
  	</head>
  	<center>
 <h1>${Token}</h1>
  <br>
  <h2>Welcome..  This is sso_test1.</h2> <br>
    <a href="logout" style="text-decoration: none;"><h3>退出</h3></a>
    <br>
    
    <a href="http://localhost:8080/sso_test2"><h2>进入sso_test2系统</h2></a>
    </center>
   
  </body>
</html>
