<html>

<head><title>New Assignment</title></head>

<style type="text/css">
	* { margin:0;
		padding:0;
	}
	
	body{
			text-align:center;
			background: #efe4bf none repeat scroll 0 0;
			font-size: 20px;	
		}

	#header{
			text-align: center;
			color: #fff;
			background-color: #2c5b9c;
			height: 3.5em;
			padding: 1em 0em 1em 1em;
		}
</style>

<body>
	<form action="/new_assignment_check?user=1&kid=${kid}&aNum=${aNum}" method="POST">
		<div id="header">
			<h1>Kurs: ${kName}</h1>
		</div>
		<div>
			Aufgabe: ${aufName}
		</div>
		<div>
			Beschreibung: ${aufText}
		</div>
		<div>
			Abgabetext:
			<textarea id="abText" name="abText" rows="10" cols="50"></textarea>
		</div>
		<div>
			<input type="submit" value="Einreichen" />
		</div>
	</form>
</body>

</html>