<?php

	$image = $_POST["user_image"];
	$name = $_POST["user_image_name"];

	$upload_path = "user_profile_picture/$name";

	file_put_contents($upload_path, base64_decode($image));
	
	echo json_encode(array('user_image_upload_response' => 'User Image Uploaded'));

?>