<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
  String wsPath = request.getServerName();
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>WebSocket Chat</title>
  <link rel="stylesheet" href="css/global.css">
  <script type='text/javascript' src='js/jquery.min.js' charset='utf-8'></script>
</head>
<body>
<script type="text/javascript">

  $.fn.serializeObject = function()
  {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
      if (o[this.name] !== undefined) {
        if (!o[this.name].push) {
          o[this.name] = [o[this.name]];
        }
        o[this.name].push(this.value || '');
      } else {
        o[this.name] = this.value || '';
      }
    });
    return o;
  };

  var socket;


  function send(cmd,message) {
    if (!window.WebSocket) {
      return;
    }
    if (socket.readyState == WebSocket.OPEN) {
      var msgJson = { "cmd": cmd, "data": message };
      socket.send(JSON.stringify(msgJson));
    } else {
      alert("连接没有开启.");
    }
  }

  $(document).ready(function(){

      if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
      }
      if (window.WebSocket) {
        socket = new WebSocket("ws://<%=wsPath %>:5888/ws");
        socket.onmessage = function(event) {
          var ta = document.getElementById('responseText');
          ta.value = ta.value + '\n' + event.data
        };
        socket.onopen = function(event) {
          var ta = document.getElementById('responseText');
          ta.value = "连接开启!";
        };
        socket.onclose = function(event) {
          var ta = document.getElementById('responseText');
          ta.value = ta.value + "连接被关闭";
        };
      } else {
        alert("你的浏览器不支持 WebSocket！");
      }

      $("#sendDataBtn").click(function(){
        if($("#senduid").val()==""){
          alert("请输入设备UID");
        }else{
          var data =$("#testForm").serializeObject();
          send("sendDataActor",data);
        }
      });
  });



</script>
<form onsubmit="return false;" id="testForm">
  <span>设备UID：</span><input type="text" name="uid"/>
  <input type="button" value="监听" onclick="send('webLoginActor',this.form.uid.value)">
  <h3>设备监听日志：</h3>
  <textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
  <h3>指令发送：</h3>
    <label>设备UID：</label><input type="text" name="senduid"  style="width: 300px" id="senduid"><p>
    <label>命令码：</label><input type="text" name="code"  style="width: 300px"><p>
    <label>数据域：</label><input type="text" name="data"  style="width: 300px"><p>
    <input type="button" value="发送数据" id="sendDataBtn">
    <input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空消息">
</form>
<br>
<br>
</body>
</html>
