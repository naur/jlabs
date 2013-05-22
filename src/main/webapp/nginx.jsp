<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@ page import="java.text.MessageFormat" %>
<html>
<head>
    <style type="text/css">
        .error {
            color: red;
        }
    </style>

    <%!
        private static String htmlFormat = "<span>{0}</span>{1}<br />";

        String format(String... msg) {ta
            //return String.format(htmlFormat, msg);
            if (msg.length < 2)
                return MessageFormat.format(htmlFormat, msg[0], "");
            else
                return MessageFormat.format(htmlFormat, msg);
        }
    %>

    <%
        StringBuilder result = new StringBuilder();
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {
            result.append(format(ex.toString()));
        }

        Object key = null;
        //request header
        result.append(format("____________ request header _______________"));
        Enumeration heads = request.getHeaderNames();
        while (heads.hasMoreElements()) {
            key = heads.nextElement();
            if (!"cookie".equals(key.toString()))
                result.append(format(key.toString(), ": " + request.getHeader(key.toString())));
        }
        //cookie
        result.append(format("____________ cookie _______________"));
        if (request.getCookies() != null)
            for (Cookie cookie : request.getCookies()) {
                result.append(format(cookie.getName(), ": " + "-" + cookie.getPath() + "-" + cookie.getValue()));
            }

        result.append(format("<hr />"));
        result.append(format("OK."));
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        request.setAttribute("content", result.toString());
    %>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title> Nginx Test </title>
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.9.0.min.js"></script>
    <script type="text/javascript">
        var global = {
        };
        var dom = {
        };
        $.cookie = function (name, value, options) {
            if (typeof value != 'undefined') {
                options = options || {};
                if (value === null) {
                    value = '';
                    options.expires = -1;
                }
                var expires = '';
                if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
                    var date;
                    if (typeof options.expires == 'number') {
                        date = new Date();
                        date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
                    } else {
                        date = options.expires;
                    }
                    expires = '; expires=' + date.toUTCString();
                }
                var path = options.path ? '; path=' + (options.path) : '';
                var domain = options.domain ? '; domain=' + (options.domain) : '';
                var secure = options.secure ? '; secure' : '';
                document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
            } else {
                var cookieValue = null;
                if (document.cookie && document.cookie != '') {
                    var cookies = document.cookie.split(';');
                    for (var i = 0; i < cookies.length; i++) {
                        var cookie = jQuery.trim(cookies[i]);
                        if (cookie.substring(0, name.length + 1) == (name + '=')) {
                            cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                            break;
                        }
                    }
                }
                return cookieValue;
            }
        }
        $(function () {
        });
    </script>
</head>
<body>
<div>
    <div style="margin: 6px 0px; color: #A22E00;font: italic bold 12px arial, sans-serif;">
        <%=Calendar.getInstance().getTime()%>
        16
    </div>
    <hr style="margin: 0px;padding:0px;"/>
    <listing style="margin: 0px;color: #00008B;font-size: 11px;"
             class="layout-content"><%=request.getAttribute("content")%>
    </listing>

    <ul style="margin:0px;padding: 0px;">
        <li><a target="_blank" href="http://91.360buy.com:60080/nginx.jsp">91.360buy.com/nginx.jsp</a> <br/>
        </li>
        <li><a target="_blank" href="http://91.360buy.com:60080/diagnose.jsp">91.360buy.com/diagnose.jsp</a>
        </li>
    </ul>

</div>

</body>
</html>
