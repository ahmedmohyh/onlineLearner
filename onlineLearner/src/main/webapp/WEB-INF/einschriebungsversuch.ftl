<html>

<head><title>New Course Created</title></head>
<style type="text/css">
* { margin:0;
		padding:0;
	}
  
  body{
        text-align:center;
        background: #efe4bf none repeat scroll 0 0;
    }
	
	#message{
  		font-size: 20px;
  		color: blue;
    }
</style>
	
<body>
	<div id="message">
		<p> ${error} </p>
	</div>
	<form action="/main_page" method="POST">
		<input type="submit" value="Zurück zur Startseite"/>
	</form>
</body>

</html>