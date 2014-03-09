<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="java.io.*" %>
<%@ page import="java.util.concurrent.TimeoutException" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URLDecoder" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
    .error {
        color: red;
    }
</style>

<%!
    private static int defaultLine = 1000;
    private static int maxLine = 10000;
    private long defaultTimeout = 15000;
    private static String[] allCommand = new String[]{
            //磁盘管理  cd ,ls, pwd
            "df", "dirs", "du", "edquota", "eject", "mcd", "mdeltree", "mdu", "mkdir", "mlabel", "mmd", "mrd", "mzip", "quota", "mount", "mmount", "rmdir", "rmt", "stat", "tree", "umount", "quotacheck", "quotaoff", "lndir", "repquota", "quotaon",
            //磁盘维护 free
            "badblocks", "cfdisk", "dd", "e2fsck", "ext2ed", "fsck", "fsck", "fsconf", "fdformat", "hdparm", "mformat", "mkbootdisk", "mkdosfs", "mke2fs",
            "mkfs.ext2", "mkfs.msdos", "mkinitrd", "mkisofts", "mkswap", "mpartition", "swapon", "symlinks", "sync", "mbadblocks", "mkfs", "fsck.ext2", "fdisk", "losetup", "mkfs", "sfdisk", "swapoff",
            //系统管理 "w", "id, who  whois  whoami ps
            "adduser", "chfn", "useradd", "date", "exit", "finger", "fwhois", "sleep", "suspend", "groupdel", "groupmod", "halt", "kill", "last", "lastb", "login", "logname", "logout",
            "nice", "procinfo", "top", "pstree", "reboot", "rlogin", "rsh", "sliplogin", "screen", "shutdown", "rwho", "sudo", "gitps", "swatch", "tload", "logrotate",
            "uname", "chsh", "userconf", "userdel", "usermod", "vlock", "newgrp", "renice", "su", "skill",
            //网络通讯 : ping ifconfig
            "apachectl", "arpwatch", "dip", "getty", "mingetty", "uux", "telnet", "uulog", "uustat", "ppp-off", "netconfig", "nc", "httpd", "minicom", "mesg", "dnsconf",
            "wall", "netstat", "pppstats", "samba", "setserial", "talk", "traceroute", "tty", "newaliases", "uuname", "netconf", "write", "statserial", "efax", " pppsetup", "tcpdump", "ytalk", "cu", "smbd", "testparm", "smbd", "smbclient", "shapecfg",
            //系统设置
            "reset", "clear", "alias", "dircolors", "aumix", "bind", "chroot", "clock", "crontab", "declare", "depmod", "dmesg",
            "enable", "eval", "export", "pwunconv", "grpconv", "rpm", "insmod", "kbdconfig", "lilo", "liloconfig", "lsmod", "minfo", "set", "modprobe", "ntsysv", "moouseconfig", "passwd", "pwconv",
            "rdate", "resize", "rmmod", "grpunconv", "modinfo", "time", "setup", "sndconfig", "setenv", "setconsole", "timeconfig", "ulimit",
            "unset", "chkconfig", "apmd", "hwclock", "mkkickstart", "fbset", "unalias", "SVGAText", "Mode",
            //设备管理
            "setleds", "loadkeys", "rdev", "dumpkeys", "MAKEDEV",
            //文件管理
            "cat", "chattr", "chgrp", "chmod", "chown", "cksum", "cmp", "diff", "diffstat", "file", "find", "git", "gitview", "indent", "cut", "ln", "less", "locate", "isattr", "mattrib", "mc", "mdel", "mdir", "mktemp", "more", "mmove", "mread", "mren",
            "mtools", "mtoolstest", "mv", "od", "paste", "patch", "rcp", "rm", "slocate", "split", "tee", "tmpwatch", "touch", "umask", "which", "cp", "in", "mcopy", "mshowfat", "rhmask", "whereis",
            //文件编辑
            "col", "colrm", "comm", "csplit", "ed", "egrep", "ex", "fgrep", "fmt", "fold", "grep", "ispell", "jed", "joe", "join", "look", "mtype", "pico", "rgrep", "sed", "sort", "spell", "tr", "expr", "uniq", "wc",
            //文件传输
            "lprm", "lpr", "lpq", "lpd", "bye", "ftp", "uuto", "uupick", "uucp", "uucico", "tftp", "ncftp", "ftpshut", "ftpwho", "ftpcount",
            //备份压缩
            "ar", "bunzip2", "bzip2", "bzip2recover", "gunzip", "unarj",
            "compress", "cpio", "dump", "uuencode", "gzexe", "gzip",
            "lha", "restore", "tar", "uudecode", "unzip", "zip",
            "zipinfo",
            //其他
            "cat", "head", "tail", "java", "javac"
    };
    private static String[] blacklist = new String[]{
            "rm", "mv", "rmdir", "eject", "mkdir", "mount", "javac", "kill", "useradd", "mkfs", "adduser", "reboot", "rlogin", "shutdown", "sudo", "userdel", "telnet", "reset", "chroot", "passwd", "chkconfig", "important.properties"
    };
    private static String[] whitelist = new String[]{
            "system", "echo", "jps", "jstat", "jmap", "jstack", "jhat", "jinfo", "df", "du", "top", "uname", "netstat", "awk", "sed", "cat", "tail", "head", "ls", "pwd", "cd", "free", "whoami", "ps", "grep", "ping", "ifconfig"

    };

    public class TimeoutThread extends Thread {
        private long timeout;
        private boolean isCanceled = false;
        private TimeoutException timeoutException;
        private Process process;
        StringBuilder outBuffer;

        public TimeoutThread(long timeout, Process process, StringBuilder outBuffer) {
            super();
            this.timeout = timeout;
            this.process = process;
            this.setDaemon(true);
            this.outBuffer = outBuffer;
        }

        public synchronized void cancel() {
            isCanceled = true;
        }

        public void run() {
            try {
                sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (!isCanceled && process != null) {
                    process.destroy();
                    outBuffer.append("<span class=\"error\">Timeout！</span><br />");
                }
            }
        }
    }

    class StreamGobbler extends Thread {
        private InputStream stream;
        private String type;
        private int line;
        private Process process;
        StringBuilder outBuffer;

        StreamGobbler(InputStream is, String type, Process process, StringBuilder outBuffer) {
            this(is, type, defaultLine, process, outBuffer);
        }

        StreamGobbler(InputStream stream, String type, int line, Process process, StringBuilder outBuffer) {
            this.stream = stream;
            this.type = type;
            this.line = line;
            this.process = process;
            this.outBuffer = outBuffer;
        }

        public void run() {
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try {
                reader = new InputStreamReader(stream);
                bufferedReader = new BufferedReader(reader);

                int readLint = 0;
                String lineStr = null;
                while (readLint < line && (lineStr = bufferedReader.readLine()) != null) {
                    if ("OUTPUT".equals(type))
                        outBuffer.append(lineStr.replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;") + "<br />");
                    else
                        outBuffer.append("<span class=\"error\">" + lineStr + "</span><br />");
                    readLint++;
                    //System.out.println(readLint + " : " + lineStr);
                }
                if ("OUTPUT".equals(type) && process != null) {
                    process.destroy();
                }
            } catch (Exception ex) {
                outBuffer.append("run " + type + ", " + Thread.currentThread().getName() + ". ");
                outBuffer.append(ex);
            } finally {
                try {
                    if (bufferedReader != null) bufferedReader.close();
                    if (reader != null) reader.close();
                    if (stream != null) stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String execute(String command, int line, long timeout) {
        StringBuilder outBuffer = new StringBuilder();
        Thread stderrThread = null;
        Thread stdoutThread = null;
        Process process = null;
        TimeoutThread timeoutThread = null;

        try {
            Runtime runtime = Runtime.getRuntime();
            process = runtime.exec(new String[]{"sh", "-c", command});
            //stderr
            stderrThread = new StreamGobbler(process.getErrorStream(), "ERROR", process, outBuffer);
            //stdout
            stdoutThread = new StreamGobbler(process.getInputStream(), "OUTPUT", line, process, outBuffer);
            //timeout
            timeoutThread = new TimeoutThread(timeout, process, outBuffer);

            stderrThread.start();
            stdoutThread.start();
            timeoutThread.start();
            int exitValue = process.waitFor();
            timeoutThread.cancel();

            stderrThread.join(timeout);
            stdoutThread.join(timeout);

            //outBuffer.append("<span class=\"error\">exitValue: " + exitValue + "</span><br />");
        } catch (Exception ex) {
            outBuffer.append(ex);
        } finally {
            if (null != stderrThread) stderrThread.interrupt();
            if (null != stdoutThread) stdoutThread.interrupt();
            if (null != timeoutThread) timeoutThread.interrupt();
            if (process != null) {
                process.destroy();
                process = null;
            }
        }
        return outBuffer.toString();
    }

    private String validate(String params) {
        if (null == params || 0 >= params.length()) {
            return "<span class=\"error\">请输入参数！</span><br />";
        }
        for (String str : blacklist) {
            if (params.indexOf(str) >= 0)
                return "<span class=\"error\">参数不能包含：" + str + "</span><br />";
        }
        for (String str : whitelist) {
            if (params.indexOf(str) == 0)
                return null;
        }

        return "<span class=\"error\">参数必须是：" + Arrays.toString(whitelist) + " 开头！</span><br />";
    }
%>

<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    String reqCode = request.getCharacterEncoding();
    request.setCharacterEncoding("UTF-8");
    String params = request.getParameter("params");
    int line = null != request.getParameter("line") ? Integer.parseInt(request.getParameter("line")) : defaultLine;
    long timeout = null != request.getParameter("timeout") ? Long.parseLong(request.getParameter("timeout")) : defaultTimeout;
    if (line > maxLine) line = maxLine;

    String result = validate(params);
    if (null == result) {
        try {
            //Properties prop = System.getProperties();
            StringBuilder sb = new StringBuilder();
            Properties properties = System.getProperties();
            String key = null;
            if (StringUtils.equals("system", params)) {
                Enumeration keys = properties.keys();
                while (keys.hasMoreElements()) {
                    key = keys.nextElement().toString();
                    sb.append("<span class=\"error\">" + key + "</span>: " + properties.getProperty(key) + "<br />");
                }
                result = sb.toString();
            } else {
                result = execute(params, line, timeout);
            }
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html");
            response.addCookie(new Cookie("params", URLEncoder.encode(params, "UTF-8").trim().replaceAll("\\+", "%20")));
            response.addCookie(new Cookie("line", String.valueOf(line)));
            response.addCookie(new Cookie("timeout", String.valueOf(timeout)));
        } catch (Exception ex) {
            if (null == result)
                result = ex.getMessage();
            else result += ex.getMessage();
        }
    }
%>

<title> Diagnose </title>
<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
    var global = {
        blacklist: '<%=Arrays.toString(blacklist)%>',
        whitelist: '<%=Arrays.toString(whitelist)%>'
    };
    var dom = {
        params: '#params',
        line: '#line',
        timeout: '#timeout'
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
        $(dom.params).focus();
        if ($(dom.params).val().length <= 0 && $.cookie('params'))
            $(dom.params).val($.cookie('params').replace(/[\^"\"$]/g, ''));
        if ($.cookie('line') && $.cookie('line').length > 0)
            $(dom.line).val($.cookie('line'));
        if ($.cookie('timeout') && $.cookie('timeout').length > 0)
            $(dom.timeout).val($.cookie('timeout'));
        $(dom.line).on('keydown', function () {
            var isOK = false;
            var e = document.all ? window.event : event;
            var key = e.keyCode
            if ((key > 95 && key < 106) ||                  //小键盘上的0到9
                    (key > 47 && key < 60) ||                   //大键盘上的0到9
                    key == 8 || key == 9 || key == 46 || key == 37 || key == 39     //不影响正常编辑键的使用(8:BackSpace;9:Tab;46:Delete;37:Left;39:Right)
                    ) {
                isOK = true;
            } else {
                if (window.event) //IE
                    e.returnValue = false;   //event.returnValue=false 效果相同.
                else //Firefox
                    e.preventDefault();
            }
            return isOK;
        });
    });
</script>
</head>
<body>
<div>
    <div class="layout-top">
        <form method="post">
            <input class="field" id="params" name="params" type="text" tabindex="1"
                   style="width: 500px;font: italic 14px arial, sans-serif;color: #A22E00;margin: 2px 6px;height: 25px;"
                   value="<%= null != request.getParameter("params") ? request.getParameter("params") : "" %>"/>
            <input class="field" id="line" name="line" type="text" tabindex="2"
                   style="width: 80px;font: italic 14px 'Comic Sans MS';color: #A22E00;margin: 2px 6px;height: 25px;"
                   value="<%= null != request.getParameter("line") ? request.getParameter("line") : defaultLine %>"/>

            <select class="field" id="timeout" name="timeout"
                    style="font: italic 14px 'Comic Sans MS';color: #A22E00;margin: 2px 6px;height: 25px;width: 80px;">
                <option value="3000"> 3s</option>
                <option value="5000"> 5s</option>
                <option value="10000">10s</option>
                <option value="15000" selected="selected">15s</option>
                <option value="30000">30s</option>
            </select>

            <input class="field" id="go" type="submit" value="执行命令！"
                   style="font: bold 14px arial, sans-serif;color: #A22E00;margin: 2px 6px;height: 25px;">
        </form>
    </div>
    <hr style="margin: 0px;padding:0px;"/>
    <div style="margin: 6px 0px; color: #A22E00;font: italic bold 12px arial, sans-serif;"><%=Calendar.getInstance().getTime()%> -
        <%=reqCode%>
    </div>
    <listing style="margin: 0px;color: #00008B;font-size: 11px;" class="layout-content"><%=result%>
    </listing>
</div>

</body>
</html>