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
<form action="/assess?user=1" method="POST">
    <div>
        Aufgabe:
    </div>
    <div>
        Beschreibung:
    </div>
    <div>
        Abgabetext:
    </div>

    <form>
        Bewertungsnote:
        <input type="radio" id="one" name="note" value="1">
        <label for="one"> 1</label>
        <input type="radio" id="two" name="note" value="2">
        <label for="two"> 2</label>
        <input type="radio" id="three" name="note" value="3">
        <label for="three"> 3</label>
        <input type="radio" id="four" name="note" value="4">
        <label for="four"> 4</label>
        <input type="radio" id="five" name="note" value="5">
        <label for="five"> 5</label>
        <input type="radio" id="six" name="note" value="6">
        <label for="six"> 6</label>
    </form>

    <div id="description">
        Kommentar
        <textarea id="description_field" name="comment" rows="10" cols="50" ></textarea>
    </div>
    <form name="ZurückZuStart" action="/assess_check" method="post">
        <div>
            <input style="margin-right: 300px;   float: right;
        color: white;
        background-color: BLUE;
        size: 50px;
        font-size:25px;
        " type="submit" value="Bewerten" class="btn btn-primary" style="margin-bottom:10px"/>
        </div>
    </form>
</form>
</body>


</html>