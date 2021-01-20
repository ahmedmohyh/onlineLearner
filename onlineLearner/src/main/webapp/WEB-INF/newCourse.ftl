<html>
<head><title>New Course</title></head>
<style type="text/css">
	* { margin:0;
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
  
  #name {
  		font-size: 20px;
  		color: blue;
  }
  
  #key {
  		font-size: 20px;
  		color: blue;
  }
  
  #limit  {
  		font-size: 20px;
  		color: blue;
  }
  
  #description  {
  		font-size: 20px;
  		color: blue;
  }
 </style>
  <body>
  <form action="/new_course_created?user=1" method="POST"> 
    <div id="title">
      <p style="color: blue; font-size: 40px">
        	Kurs erstellen
      </p>
    </div>

    <div id="title">
      Name
      <input id="title_field" name="title" type="text" value="" minlength="1" maxlength="50"/>
    </div>
    
    <div id="key">
      Einschreibeschlüssel
      <input id="key_field" name="key" type="text" value="" maxlength="50"/>
    </div>
    
    <div id="limit">
      Anz. freier Plätze
      <input id="limit_field" name="limit" type="number" value="" min="0" max="100" size="5"/>
    </div>
    
    <div id="description">
      Beschreibung
      <textarea id="description_field" name="description"></textarea>
    </div>
    
    <input type="submit" value="Erstellen"/>
  </form>  
  </body>
</html>