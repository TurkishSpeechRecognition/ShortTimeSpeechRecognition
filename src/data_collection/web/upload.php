<?php 
/*
php.ini içinde : file_uploads = On 
*/

if(isset($_FILES['fileToUpload']) ){

  if(!isset($_POST['appcode']) || "qfnDq5U*.boBV0.yezabM6zsi" != $_POST['appcode'] ){
	echo "Sorry, your file can not be uploaded:0 \n";
  }else if(!isset($_POST['phoneid']) || "" == $_POST['phoneid'] ){
	echo "Sorry, your file can not be uploaded:1 \n";
  }else if(!isset($_POST['timestamp']) || "" == $_POST['timestamp'] ){
	echo "Sorry, your file can not be uploaded:2 \n";
  }else{

     //mkdir -p /var/www/html/soundfiles
     //chown -Rf www-data /var/www/html/soundfiles
     //chown -Rf apache /var/www/html/soundfiles

     $target_dir = "/var/www/html/soundfiles/data/".$_POST['phoneid']."/".$_POST['timestamp']."/";
     $target_file = $target_dir . basename($_FILES["fileToUpload"]["name"]);
     $uploadOk = 1;
     $mp3FileType = pathinfo($target_file,PATHINFO_EXTENSION);

     // Check if file already exists
     if (file_exists($target_file)) {
	echo "Sorry, your file can not be uploaded:4\n";
	$uploadOk = 0;
     }
     // Check file size
     if ($_FILES["fileToUpload"]["size"] > 100000) {
	echo "Sorry, your file can not be uploaded:5\n";
	$uploadOk = 0;
     }
     // Allow certain file formats
     if(strtolower($mp3FileType) != "m3a" && strtolower($mp3FileType) != "mp3") {
	echo "Sorry, your file can not be uploaded:6\n";
	$uploadOk = 0;
     }
     // Check if $uploadOk is set to 0 by an error
     if ($uploadOk == 0) {
	echo "Sorry, your file can not be uploaded.";
     } else {
	if (!file_exists($target_dir)) {
               mkdir($target_dir, 0755, true);
        }
	if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
		echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
	} else {
		echo "Sorry, there was an error uploading your file.";
	}
     }
   }

}else{
?>
<!DOCTYPE html>
<html>
<body>
<form action="" method="post" enctype="multipart/form-data">
    <input type="text" name="appcode" id="appcode"/>
    <input type="text" name="phoneid" id="phoneid"/>
    <input type="text" name="timestamp" id="timestamp"/>
    <input type="file" name="fileToUpload" id="fileToUpload"/>
    <input type="submit" value="Upload Mp3" name="submit"/>
</form>
</body>
</html> 
<?php 
}
?>
