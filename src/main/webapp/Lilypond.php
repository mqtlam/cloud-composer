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
 *              XML SAX NOT SUPPORTED: XML SAX extension not enabled on server
 *              PDF GENERATION FAILED: shell execution for lilypond failed
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
                    3   => "g'",
                    4   => "a'",
                    5   => "c''",
                    6   => "d''",
                    7   => "e''",
                    8   => "g''",
                    9   => "a''"   );

/**
 * Column tag name.
 */
define("COL_NAME", "column");

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
$remainingRhythm = 0;

$newData = "";

$newDataPerInstrument = array();
foreach ($instruments as $instrument => $name)
  $newDataPerInstrument[$instrument] = "";

// }}}
// {{{ xml functions

/**
 * Called whenever SAX parser encounters a start tag,
 * e.g. <start>. $name stores the tag name and 
 * $attribs is an array of attributes.
 */
function startElemHandler($parser, $name, $attribs) {
    global $instruments;
    global $currentColumn;
    global $currentInstrument;

    if (strcasecmp($name, COL_NAME) == 0) {
        // <c id="num"> detected
        $currentColumn = $attribs["id"];
    }

    foreach ($instruments as $instr => $instrName)
    {
        if (strcasecmp($name, $instrName) == 0) {
          // <name> detected
          $currentInstrument = $instr;
        }
    }
}

/**
 * Called whenever SAX parser encounters an end tag,
 * e.g. </start>. $name stores the tag name.
 */
function endElemHandler($parser, $name) {
    if (strcasecmp($name, COL_NAME) == 0) {
        // </c> detected
    }
    foreach ($instruments as $instr => $instrName)
    {
        if (strcasecmp($name, $instrName) == 0) {
          // <name> detected
        }
    }
}

/**
 * Called whenever SAX parser encounters text data between tags,
 * e.g. <start>DATA</end>, where DATA would be stored in $data.
 */
function characterData($parser, $data) {
    // text data detected
    global $pitches;
    global $currentColumn;
    global $currentInstrument;
    global $newDataPerInstrument;

    if (strpos($data, "{") === false)
      return;

    // {{{ collect simultaneous pitches in a column into a chord
    
    // put all {} pairs into an array
    $processed = $data;
    $processed = preg_replace(array("/^\s*{/", "/}\s*$/", "/\s+/"), array("","",""), $processed);
    $columns = explode("}{", $processed);
    
    // construct the chord at this column
    $chord = "< ";
    $duration = 0;

    foreach ($columns as $col) {
      list($length, $pitch) = explode(",", $col);
      $duration = intval($length);

      $chord .= "{$pitches[$pitch]} ";
    }

    $chord .= ">";

    // }}}
    // {{{ transcribe rhythm

    // handles durations longer than whole note
    $remainingDuration = $duration;
    while ($remainingDuration > 0)
    {

      // consider the current duration as whole note tops
      $currentDuration = min(SIXTEENTH_NOTES_PER_MEASURE, $remainingDuration);

      // if any remaining duration left after the (possible) whole note,
      // use it up in the next iteration of the loop
      $remainingDuration = $remainingDuration - $currentDuration;

      $numQuarterNotes = (int) ($currentDuration / 4);
      $remainingSixteenthNotes = $currentDuration % 4;

      // {{{ for very specific notation cases
      if ($numQuarterNotes == 1 && $remainingSixteenthNotes == 2)
      {
        // write out the dotted quarter note
        $newDataPerInstrument[$currentInstrument] .= "{$chord}4.";
      }
      else
      {
        // }}}
        // {{{ for general notation case

        if ($numQuarterNotes == 1)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}4";
        else if ($numQuarterNotes == 2)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}2";
        else if ($numQuarterNotes == 3)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}2.";
        else if ($numQuarterNotes == 4)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}1";
        //else if ($numQuarterNotes > 4)
        //  $newData .= "{$chord}1 ~";

        if ($remainingSixteenthNotes > 0 && $numQuarterNotes > 0)
          $newDataPerInstrument[$currentInstrument] .= " ~ ";

        if ($remainingSixteenthNotes == 1)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}16";
        else if ($remainingSixteenthNotes == 2)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}8";
        else if ($remainingSixteenthNotes == 3)
          $newDataPerInstrument[$currentInstrument] .= "{$chord}8.";

        // }}}
      }

      if ($remainingDuration > 0)
        $newDataPerInstrument[$currentInstrument] .= " ~ ";

    }

    // }}}
}

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
    global $newDataPerInstrument;
    global $instruments;
    global $pitches;

    $timeSignatureNumerator = SIXTEENTH_NOTES_PER_MEASURE / 4;

    // {{{ parse XML

    // new xml parser object
    $xmlParser = xml_parser_create() or die("XML SAX NOT SUPPORTED");
    xml_set_element_handler($xmlParser, "startElemHandler", "endElemHandler");
    xml_set_character_data_handler($xmlParser, "characterData");
    xml_parser_set_option($xmlParser, XML_OPTION_CASE_FOLDING, 0);

    // parse xml
    xml_parse($xmlParser, $data);

    // close xml
    xml_parser_free($xmlParser);

    // }}}
    // {{{ create file

    $newData = "";

    // add data for each instrument
    foreach ($newDataPerInstrument as $instr => $instrumentData)
    {
      $newData .= "\\new Staff\n{\n\t\\set Staff.instrumentName = #\"{$instruments[$instr]}\""
              . "\n\t\\clef treble\n\t\\time $timeSignatureNumerator/4\n\t";
      $newData .= $instrumentData . "\n}\n\n";
    }

    // }}}

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
 * Generates the PDF using Lilypond.
 * Assumes the lily file is already created.
 */
function generatePDF($filename)
{
  $output = shell_exec('lilypond ' + $filename)
    or die("PDF GENERATION FAILED");

  //echo '<pre>'.$output.'</pre>';
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

// for debug
/*$myFile = "lilytest.xml";
$fh = fopen($myFile, 'r');
$data = fread($fh, 10000000);
fclose($fh);*/
// end debug

$data = $_POST[DATA_PARAM];
$lilydata = interpretData($data);

$filename = generateFileName();
saveFile($lilydata, $filename);

//generatePDF($filename);
displayLink($filename);

// }}}

?>