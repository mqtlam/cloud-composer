<?php

/**
 * This script gets the composition data from the front end and saves
 * it to a file on the server, then returns the link of the file.
 *
 * USAGE:   Pass the composition data to this php file
 *          using the POST variable 'data'.
 *          
 *          Saves to songs/filename.xml
 *          
 *          Returns the link on success or displays an error message:
 *              CANNOT OPEN FILE: file cannot be open to write
 * 
 * PHP version 5
 *
 * @author     Michael Lam <mqtlam@cs.washington.edu>
 * @version    1.0
 * @link       savesession.php
 */

// {{{ constants

/**
 * URL of website for displaying to user
 */
//define("WEBSITE_URL", "http://students.washington.edu/jclement/Cloud-Composer/");
define("WEBSITE_URL", "http://publicstaticdroid.com/cloudcomposer/test/");
/**
 * Directory to save new file (and look up old files)
 */
define("SAVE_DIRECTORY", "songs/");

/**
 * File extension type
 */
define("FILE_EXTENSION", ".xml");

/**
 * POST parameter to pass data to this php file.
 */
define("DATA_PARAM", "data");

// }}}
// {{{ functions

/**
 * Returns a randomly generated file name, 
 * which does not already exist on the server.
 * 
 * @return file name
 */
function generateFileName() {
    // {{{ get existing file names
    
    $existingFileNames = array();
    $handler = opendir(SAVE_DIRECTORY);
    
    while ($file = readdir($handler)) {
        if ($file != "." && $file != "..") {
            $existingFileNames[] = $file;
        }
    }
    
    closedir($handler);
    
    // }}}
    // {{{ generate new file name
    
    $filename = "";
    
    while (empty($filename)) // note: could go on forever theoretically
    {
        $filename = rand(1000000000, 9999999999);
        
        if (array_search($filename . FILE_EXTENSION,
            $existingFileNames)) {
            $filename = "";
        }
    }
    
    // }}}
    
    return $filename;
}

/**
 * Saves the composition data to a file on the server.
 *
 * @param data      Composition data
 * @param filename  File name to store on server
 */
function saveSession($data, $filename)
{
    // write data
    $fileHandler = fopen(SAVE_DIRECTORY . $filename . FILE_EXTENSION, 'w')
        or die("CANNOT OPEN FILE");
    $dataToWrite = stripslashes($data);
    fwrite($fileHandler, $dataToWrite);
    fclose($fileHandler);
}

/**
 * Displays the permalink.
 */
function displayLink($filename)
{
//   echo WEBSITE_URL . SAVE_DIRECTORY . $filename .FILE_EXTENSION;
	echo WEBSITE_URL . "Main.html?session=" . $filename;
}

// }}}
// {{{ SAVE SESSION AND DISPLAY LINK

$data = stripslashes($_POST['data']);
$filename = generateFileName();
saveSession($data, $filename);
displayLink($filename);

// }}}

?>