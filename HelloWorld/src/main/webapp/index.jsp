<html>
<body>
<h2>Hello World!</h2>
<b>
    <%
        int fontSize = 10;
        for (int counter = 1; counter <= 10; counter++) {
            String message = String.format("<p style='font-size:%s'>I will not play games during class</p>", fontSize);
            out.println(message);
        }
%>
</b>
</body>
</html>
