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
//define("WEBSITE_URL", "http://students.washington.edu/jclement/Cloud-Composer/");
define("WEBSITE_URL", "http://students.washington.edu/eui/403/");

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

/**
 * All instruments here. TODO: abstract out along with other files?
 */
$instruments = array(   "PIANO"     => "piano",
                        "VIOLIN"    => "violin",
                        "GUITAR"    => "guitar",
                        "TRUMPET"   => "trumpet",
                        "DRUM"      => "drum" );

/**
 * All pitches here. TODO: abstract out along with other files?
 */
$pitches = array(   0   => "c'",  // c4 = 60
                    1   => "d'",
                    2   => "e'",
                    3   => "a'",
                    4   => "g'",
                    5   => "c''",
                    6   => "d''",
                    7   => "e''",
                    8   => "a''",
                    9   => "g''"   );

/**
 * Column tag name.
 */
define("COL_NAME", "c");

/**
 * Index of first column in note grid.
 */
define("FIRST_COL", 0);

/**
 * How many sixteenth notes per measure, used for time signature calculation.
 * (Time signature assumes a beat is a quarter note.)
 */
define("SIXTEENTH_NOTES_PER_MEASURE", 16);

// }}}
// {{{ global variables (for maintaining state with sax parser)

$currentColumn = 0;
$currentInstrument = $instruments['PIANO'];

$newData = "";

// }}}
// {{{ xml functions
/*
function startElemHandler($parser, $name, $attribs) {
    if (strcasecmp($name, COL_NAME) == 0) {
        // <c id="num"> detected
        $currentColumn = $attribs["id"]
    }
    if (strcasecmp($name, $instruments['PIANO']) == 0) {
        // <piano> detected
        $currentInstrument = $instruments['PIANO'];
    }
}

function endElemHandler($parser, $name) {
    if (strcasecmp($name, COL_NAME) == 0) {
        // </c> detected
    }
    if (strcasecmp($name, $instruments['PIANO']) == 0) {
        // </piano> detected
    }

}

function characterData($parser, $data) {
    // text data detected
    global $newData;
    
    // TODO
    // get first {} pair
    // extract length and pitch
    $pitch = 0;
    $length = 1;
    
    // based on current column and length, determine what note type and ties if appropriate
}*/

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
    global $newData;
    global $instruments;
    global $pitches;
    
    $timeSignatureNumerator = SIXTEENTH_NOTES_PER_MEASURE / 4;
    $header = "\\new Staff\n{\n\t\\set Staff.instrumentName = #\"{$instruments["PIANO"]}\""
              . "\n\t\\clef treble\n\t\\time $timeSignatureNumerator/4\n\t";
    
    // create file
    $newData = $header;
    /*
    // new xml parser object
    $xmlParser = xml_parser_create() or die("XML SAX NOT SUPPORTED");
    xml_set_element_handler($xmlParser, "startElemHandler", "endElemHandler");
    xml_set_character_data_handler($xmlParser, "characterData");
    xml_parser_set_option($xmlParser, XML_OPTION_CASE_FOLDING, 0);
    
    // parse xml
    xml_parse($xmlParser, $data);
    
    // close xml
    xml_parser_free($xmlParser);
    */
    
    // BETA FEATURE: print out canonical scale, that's it
    // TODO: actually analyze xml file
    $newData .= "c'4 d'4 e'4 g'1";
    
    // end new data
    $newData .= "\n}";
    
    return $newData;
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