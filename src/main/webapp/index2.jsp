<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: wenwen
  Date: 2017/4/11
  Time: 22:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  // 设置请求的编码为UTF-8;
  request.setCharacterEncoding("UTF-8");
  // 设置响应的编码为UTF-8
  response.setCharacterEncoding("UTF-8");

  // 帐号密码数据准备
  Map<String, String> accountMap = new HashMap<>();
  accountMap.put("ftpuser", "password");

  // 获取参数
  String userName = request.getParameter("userName");
  System.err.println(userName);
  String result;
  if (userName == null || "".equals(userName.trim())) {
    result = "{\"code\":101,\"msg\":\"参数为空\"}";
  } else {
    String password = accountMap.get(userName);
    if (password == null || "".equals(password)) {
      result = "{\"code\":102,\"msg\":\"结果为空。\"}";
    } else {
      char[] array = password.toCharArray();
      for (int i = 0; i < array.length; i++) {
        array[i] = (char) (array[i] ^ 20160831);
      }
      System.out.println("结果如下：");
      System.out.println(new String(array));//输出加密或者解密结果
      result = "{\"code\":100,\"msg\":'成功',\"data\":\"" + new String(array) + "\"}";
    }
  }

  // 将处理后的结果返回给客户端
  response.getWriter().write("" + result);
%>