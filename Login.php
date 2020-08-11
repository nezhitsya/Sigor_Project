<?php
  $con = mysqli_connect("localhost", "angela22388", "wellgoodA!!", "angela22388");

  $email = $_POST["email"];
  $password = $_POST["password"];

  $statement = mysqli_prepare($con, "SELECT * FROM user WHERE email = ? AND password = ?");
  mysqli_stmt_bind_param($statement, "ss", $email, $password);
  mysqli_stmt_execute($statement);

  mysqli_stmt_store_result($statement);
  mysqli_stmt_bind_result($statement, $email, $password, $username, $nickname);

  $response = array();
  $response["success"] = false;

  while(mysqli_stmt_fetch($statement)) {
    $response["success"] = true;
    $response["email"] = $email;
    $response["password"] = $password;
    $response["username"] = $username;
    $response["nickname"] = $nickname;
  }

  echo json_encode($response);
  
?>
