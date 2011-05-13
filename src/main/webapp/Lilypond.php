<?php

/**
 * This script gets the composition data from the front end,
 * generates a file containing Lilypond data, and returns
 * the link of the file.
 *
 * USAGE:   Pass the composition data to this php file
 *          using the POST variable 'data'.
 *          
 *          Saves to songs/filename.ly
 *          
 *          Returns the link on success or displays an error message:
 *              CANNOT OPEN FILE: file cannot be open to write
 * 
 * PHP version 5
 *
 * @author     Michael Lam <mqtlam@cs.washington.edu>
 * @version    1.0
 * @link       lilypond.php
 */

// {{{ constants

/**
 * URL of website for displaying to user
 */
define("WEBSITE_URL", "http://students.washington.edu/jclement/Cloud-Composer/");

/**
 * Directory to save new file (and look up old files)
 */
define("SAVE_DIRECTORY", "songs/");

/**
 * File extension type
 */
define("FILE_EXTENSION", ".ly");

/**
 * POST parameter to pass data to this php file.
 */
define("DATA_PARAM", "data");

// }}}
// {{{ variables/functions for instruments

$instruments = array(   "PIANO"     => "piano",
                        "VIOLIN"    => "violin",
                        "GUITAR"    => "guitar",
                        "TRUMPET"   => "trumpet",
                        "DRUM"      => "drum" );

define("COL_NAME", "c");

define("FIRST_COL", 0);

define("SIXTEENTH_NOTES_PER_MEASURE", 16);

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
 * Converts the composition data from the front end
 * to the lilypond format.
 *
 * @param data      Composition data
 * @return Lily data format
 */
function interpretData($data)
{
    // TODO: do actual interpretation
    // current generates a blank template
    
    $newdata = "";
    
    $newdata .= "{\n\t\\time 4/4\n\t\\clef treble";
    $newdata .= "\n}";
    
    return $newdata;
}

/**
 * Saves the data to a file on the server.
 *
 * @param data      Composition data
 * @param filename  File name to store on server
 */
function saveFile($data, $filename)
{
    // write data
    $fileHandler = fopen(SAVE_DIRECTORY . $filename . FILE_EXTENSION, 'w')
        or die("CANNOT OPEN FILE");
    $dataToWrite = $data;
    fwrite($fileHandler, $dataToWrite);
    fclose($fileHandler);
}

/**
 * Displays the permalink.
 */
function displayLink($filename)
{
    echo WEBSITE_URL . $filename;
}

// }}}
// {{{ SAVE SESSION AND DISPLAY LINK

$data = $_POST[DATA_PARAM];
$lilydata = interpretData($data);

$filename = generateFileName();
saveFile($lilydata, $filename);

displayLink($filename);

// }}}

?>