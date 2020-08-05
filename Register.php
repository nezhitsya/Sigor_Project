<?php
  $con = mysqli_connect("localhost", "angela22388", "wellgoodA!!", "angela22388");

  $email = $_POST["email"];
  $password = $_POST["password"];
  $nickname = $_POST["password"];
  $username = $_POST["username"];

  $statement = mysqli_prepare($con, "INSERT INTO user VALUES (?, ?, ?, ?)");
  mysqli_stmt_bind_param($statement, "ssss", $email, $username, $nickname, $password);
  mysqli_stmt_execute($statement);

  $response = array();
  $response["success"] = true;

  echo json_encode($response);

?>
