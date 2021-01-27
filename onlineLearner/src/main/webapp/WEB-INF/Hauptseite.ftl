<html>
<head><title>Haupt seite</title>
</head>
<style type="text/css">
    * {
        margin:0;
        padding:0;
    }

    body{
        text-align:center;
        background: #efe4bf none repeat scroll 0 0;
    }
    #title{
        width:960px;
        margin:0 auto;
        text-align:center;
        background-color: #fff;
        border-radius: 0 0 10px 10px;
        padding: 20px;
        box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
    }
    #sec{
    width:500px;
    margin:10px ;
    text-align:left;
    background-color: white;
    border-radius: 0 0 10px 10px;
    padding: 10px;
    box-shadow: 1px -2px 14px rgba(0, 0, 0, 0.4);
    }

    #my_block {

        display: inline-block;
        border-style: solid;
        border-color: black;
        width:300px;
        height:110px ;
        margin: 5px;
        color : black;
        font-size: 25px;
      }



</style>
<body>
<div id="title">
   <p style="color: blue; font-size: 40px"  >
       Online Learner
   </p>
</div>
<div id="sec">
    <p style="color:red; font-size: 25px ; font-weight: bold">Meine Kurse</p>
</div>
<div>
    <#list meine as my_list >
        <div id="my_block" >
            <form name="view_course" action ="/view_course?ks=${my_list.kid}" method="post">
            <div>
                ${my_list.name}
            </div>
            <div>
                Ersteller: ${my_list.ersteller_name}
            </div>
            <div>
                Freie plätze: ${my_list.freiePlaetze}
            </div>
                <input type="submit" value="view" style="margin-right: 20px ;color: white; background-color: green; size: 25px; font-size: 18px; float: right;" />
            </form>
        </div>
    </#list>
</div>
<br>
<div id="sec">
    <p style="color:green; font-size: 25px ; font-weight: bold">Verfügbare Kurse</p>

</div>
<div>
    <#list avail as my_list >
        <div id="my_block" >
        <form name="view_course" action ="/view_course?ks=${my_list.kid}" method="post">
            <div>
                ${my_list.name}
            </div>
            <div>
               Ersteller: ${my_list.ersteller_name}
            </div>
            <div>
                Freie plätze: ${my_list.freiePlaetze}
            </div>

        <input type="submit" value="view" style="margin-right: 20px ;color: white; background-color: green; size: 25px; font-size: 18px; float: right;" />
        </form>
        </div>
    </#list>
</div>
<br>
<div>
    <form name="newcourse" action ="/new_course" method="get">
        <input style="margin-right: 300px;   float: right;
        color: white;
        background-color: blue;
        size: 50px;
        font-size:25px;

        height: 51px;
        width : 365px;
       float: right;
        left: 1100px; " type="submit" value="Kurs Erstellen" class = "btn btn-primary" style = "margin-bottom:10px" />
    </form>
</div>
</body>

</html>