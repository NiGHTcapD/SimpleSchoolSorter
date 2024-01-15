<!DOCTYPE html>
<html>
<head>
<title>Create Teacher</title>
</head>
<body>

<h1>Input teacher name here</h1>
<p>please.</p>

<form action="/action_page.php">
  <label for="fname">First name:</label><br>
  <input type="text" id="fname" name="fname" value="John"><br>
  <label for="lname">Last name:</label><br>
  <input type="text" id="lname" name="lname" value="Doe"><br><br>

  <label for="schedule">Hour 1:</label>

<select name="schedule" id="schedule">
  <option value="null">Prep</option>
  <option value="empty">empty</option>
</select>
  <input type="submit" value="Submit">
</form> 



</body>
</html>