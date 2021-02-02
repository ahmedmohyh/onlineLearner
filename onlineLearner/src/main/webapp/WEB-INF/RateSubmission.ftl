<html>
<head><title>Rate Submission</title></head>

<style type="text/css">
    * {
        margin: 0;
        padding: 0;
    }

    body {
        text-align: center;
        background: #efe4bf none repeat scroll 0 0;
        font-size: 20px;
    }

    #description {
        font-size: 20px;
        color: black;
        size: 50px;
    }

</style>

<body>

    <div style="margin: 10px; color: darkred" >
        Aufgabe: ${Sub.getName()}
    </div>
    <div  style="margin: 10px; color: darkred">
        Beschreibung:  ${Sub.getBeschreibung()}
    </div>
    <div  style="margin: 10px; color: darkred">
        Abgabetext:  ${Sub.getAbgabetext()}
    </div>
    <form name="Bewertung" action="/assess_check?user=1&aid=${Sub.getAID()}" method="post">
    <fieldset id = "rates">
        Bewertungsnote:
        <input type="radio" name="rates" value="1">
        1
        <input type="radio" name="rates" value="2">
        2
        <input type="radio" name="rates" value="3">
        3
        <input type="radio" name="rates" value="4">
        4
        <input type="radio" name="rates" value="5">
        5
    </fieldset>

    <div>
            Kommentar
            <textarea id="comment" name="comment" rows="10" cols="50"></textarea>
    </div>


        <div>
            <input style="margin-right: 300px;   float: right;
        color: white;
        background-color: BLUE;
        size: 50px;
        font-size:25px;
        " type="submit" value="Bewerten" class="btn btn-primary" style="margin-bottom:10px"/>
        </div>
    </form>

</body>


</html>