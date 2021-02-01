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
    <form name="ZurückZuStart" action="/assess_check?user=${Sub.getBnummer()}&aid=${Sub.getAID()}" method="post">
    <div id = "rates">
        Bewertungsnote:
        <input type="radio" id="one" name="one" value="1">
        <label for="one"> 1</label>
        <input type="radio" id="two" name="two" value="2">
        <label for="two"> 2</label>
        <input type="radio" id="three" name="three" value="3">
        <label for="three"> 3</label>
        <input type="radio" id="four" name="four" value="4">
        <label for="four"> 4</label>
        <input type="radio" id="five" name="five" value="5">
        <label for="five"> 5</label>
        <input type="radio" id="six" name="six" value="6">
        <label for="six"> 6</label>
    </div>

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