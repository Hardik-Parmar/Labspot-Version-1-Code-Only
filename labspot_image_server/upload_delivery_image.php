<?php

	$image = $_POST["delivery_image"];
	$name = $_POST["delivery_image_name"];

	$upload_path = "delivery_profile_picture/$name";

	file_put_contents($upload_path, base64_decode($image));
	
	echo json_encode(array('delivery_image_upload_response' => 'Delivery Image Uploaded'));

?>