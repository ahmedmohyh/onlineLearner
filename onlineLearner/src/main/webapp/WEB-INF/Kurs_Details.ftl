<html>
<head><title>Einschreibung Seite </title>
	<style>
		* {
			margin:0;
			padding:0;
		}

		body{
			text-align:center;
			background: #efe4bf none repeat scroll 0 0;
		}
		#header1{
			text-align: center;
			color: White;
			background-color:RED;
			height: 3.5em;
			padding: 1em 0em 1em 1em;
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
<div id="header1">
	<h1> Informationen </h1>
</div>
<form name ="Einschreiben"action="main_page" method="post" >
	<div>
		<div id="header">
			<h1> ${my_k.name} </h1>
		</div>
	</div>
	<div>
		<h1> ${my_k.ersteller_name} </h1>
	</div>
	<div style="font-size: 40px; color: green">
		${my_k.beschreibungstext}
	</div>
	<div style="font-size: 40px; color: blueviolet">
		Anz. freier Plätze : ${my_k.freiePlaetze}
	</div>

	<div>
		<input style="margin-right: 300px;   float: right;
        color: white;
        background-color: RED;
        size: 50px;
        font-size:25px;
        position: absolute;
        height: 51px;
        width : 250px;
        top :200px;
        left: 1150px; " type="submit" value="Kurs Löschen" class = "btn btn-primary" style = "margin-bottom:10px" />
	</div>
</form>
<di>
	<hr size="3" width="100%" color="black" >
</di>
<div>
	<table class="datatable" >
		<tr>
			<th style="font-size: 25px; color: brown ;margin: 40px"> Aufgabe </th> <th style="font-size: 25px; color: brown ;margin: 20px; padding:30px"> Meine Abgabe </th>
		</tr>
		<#list my_auf_list as mylist>
			<tr style="margin: auto">
				<td style="font-size: 20px; color: blueviolet"> ${mylist.name}</td>
				<td style="font-size: 20px; color: blueviolet"> ${mylist.abgabetext} </td>
			</tr>
		</#list>
	</table>

</div>

</body>
</html>