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
    #header{
        text-align: center;
        color: #fff;
        background-color: #2c5b9c;
        height: 3.5em;
        padding: 1em 0em 1em 1em;

    }
    </style>
<body>
<form name ="Einschreiben" action="/check_einschreiben?ks=${my_k.kid}" method ="post">
    <div>
        <div id="header">
            <h1> ${my_k.name} </h1>
        </div>
    </div>

    <div id ="key" style="font-size: 40px; color: orangered; position: absolute ; top: 300px; left: 250px">
    Einschreibeschlüssel:   <input type="text" name="key" size="100" style="size: 100px" />
    </div>
    <div>
        <input style="margin-right: 300px;   float: right;
        color: white;
        background-color: green;
        size: 50px;
        font-size:25px;
        position: absolute;
        height: 51px;
        width : 365px;
        top :500px;
        left: 650px; " type="submit" value="Kurs Einschreiben" class = "btn btn-primary" style = "margin-bottom:10px" />
    </div>
</form>
</body>
</html>