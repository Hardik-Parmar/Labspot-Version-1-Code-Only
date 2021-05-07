<?php

	$image = $_POST["lab_logo"];
	$name = $_POST["lab_logo_name"];

	$upload_path = "lab_logo/$name";

	file_put_contents($upload_path, base64_decode($image));
	
	echo json_encode(array('lab_image_upload_response' => 'Lab Image Uploaded'));

?>