<?php

/**
 * This script gets the composition data from the front end,
 * generates a file containing Lilypond data, and returns
 * the link of the file.
 *
 * NOTE:    Due to limitations of lilypond, not all composition data
 *          may be converted correctly. This does not support
 *          complex polyphony. The $exactTranscription flag will
 *          become false once this parser encounters data that cannot
 *          be converted into sheet music. Nonetheless, 
 *          this parser will try its best to notate what it can.
 *
 * USAGE:   Pass the composition data to this php file
 *          using the POST variable 'data'.
 *
 *          Alternatively, for testing, pass the location of the 
 *          XML test file using the GET variable 'testfile'.
 *
 *          Saves to songs/filename.ly
 *          Publishes the PDF to songs/filename.pdf
 *
 *          Returns the link on success or displays an error message:
 *              CANNOT OPEN FILE: file cannot be open to write
 *              XML SAX NOT SUPPORTED: XML SAX extension not enabled on server
 *              PDF GENERATION FAILED: shell execution for lilypond failed
 *              CANNOT LOAD TEST FILE: unable to open test xml file
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
 * Lilypond file extension type
 */
define("LILY_FILE_EXTENSION", ".ly");

/**
 * PDF file extension type
 */
define("PDF_FILE_EXTENSION", ".pdf");

/**
 * POST parameter to pass data to this php file.
 */
define("DATA_PARAM", "data");

/**
 * GET parameter. Value is the test XML file to load.
 * It is also the output filename.
 */
define("DATA_PARAM", "testfile");

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

/**
 * If false, then the song sheet generated is not an exact transcription
 * of the data due to Lilypond limitations. If true, then 
 * the sheet generated is an exact transcription.
 */
$exactTranscription = true;

/**
 * Stores the current instrument as a state from the XML parser.
 */
$currentInstrument = $instruments['PIANO'];

/**
 * Stores the entire transcription as a string to be written.
 */
$newData = "";

/**
 * Stores the transcription for each instrument part.
 */
$newDataPerInstrument = array();
foreach ($instruments as $instrument => $name)
  $newDataPerInstrument[$instrument] = "";

/**
 * Stores the current position state, used to compute rests.
 */
$currentCol = FIRST_COL;
$currentColumn = array();
foreach ($instruments as $instrument => $name)
  $currentColumn[$instrument] = FIRST_COL;

/**
 * Stores the total amount of rhythms processed,
 * used to compute rests.
 */
$rhythmBuffer = array();
foreach ($instruments as $instrument => $name)
  $rhythmBuffer[$instrument] = FIRST_COL;

// }}}
// {{{ xml functions

/**
 * Called whenever SAX parser encounters a start tag,
 * e.g. <start>. $name stores the tag name and 
 * $attribs is an array of attributes.
 */
function startElemHandler($parser, $name, $attribs) {
    global $instruments;
    global $currentCol;
    global $currentColumn;
    global $currentInstrument;

    if (strcasecmp($name, COL_NAME) == 0) {
        // <column id="num"> detected
        $currentCol = $attribs["id"];
    }

    foreach ($instruments as $instr => $instrName)
    {
        if (strcasecmp($name, $instrName) == 0) {
          // <name> detected
          $currentInstrument = $instr;
          $currentColumn[$currentInstrument] = $currentCol;
        }
    }
}

/**
 * Called whenever SAX parser encounters an end tag,
 * e.g. </start>. $name stores the tag name.
 */
function endElemHandler($parser, $name) {
    if (strcasecmp($name, COL_NAME) == 0) {
        // </column> detected
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
    global $rhythmBuffer;
    global $currentInstrument;
    global $newDataPerInstrument;
    global $exactTranscription;

    if (strpos($data, "{") === false)
      return;

    // {{{ add rests if necessary
    
    // This describes a state where there is a start to polyphony.
    // We ignore it for now, i.e. don't trascribe it.
    if ($rhythmBuffer[$currentInstrument] > $currentColumn[$currentInstrument]) {
      $exactTranscription = false;
      return;
    }
    
    // This describes a state that we need to fill in rests before proceeding.
    if ($rhythmBuffer[$currentInstrument] < $currentColumn[$currentInstrument])
    {
      $nextBar = $rhythmBuffer[$currentInstrument];
      
      if ($rhythmBuffer[$currentInstrument] % SIXTEENTH_NOTES_PER_MEASURE != 0)
        $nextBar = $rhythmBuffer[$currentInstrument] + (SIXTEENTH_NOTES_PER_MEASURE - $rhythmBuffer[$currentInstrument] % SIXTEENTH_NOTES_PER_MEASURE);
      
      // fills in rest up to the measure if one exists
      if ($currentColumn[$currentInstrument] > $nextBar) {
        $restDuration = $nextBar - $rhythmBuffer[$currentInstrument];
        restHelper($restDuration);
        
        $restDuration = $currentColumn[$currentInstrument] - $nextBar;
        restHelper($restDuration);
      } else {
        $restDuration = $currentColumn[$currentInstrument] - $rhythmBuffer[$currentInstrument];
        restHelper($restDuration);
      }
      
      $rhythmBuffer[$currentInstrument] = $currentColumn[$currentInstrument];
    }
    
    // }}
    // {{{ collect simultaneous pitches in a column into a chord
    
    // put all {} pairs into an array
    $processed = $data;
    $processed = preg_replace(array("/^\s*{/", "/}\s*$/", "/\s+/"), array("","",""), $processed);
    $rows = explode("}{", $processed);
    
    // construct the chord at this column
    $chord = " < ";
    $duration = 0;

    // pass 1: find max duration among col
    foreach ($rows as $row) {
      list($length, $pitch) = explode(",", $row);
      
      // if two different durations detected in one column
      if ($duration > 0 && $duration != $length)
        $exactTranscription = false;
      
      $duration = max($duration, $length);
    }
    
    // pass 2: only record notes with the max duration among col
    foreach ($rows as $row) {
      list($length, $pitch) = explode(",", $row);
      
      if ($length == $duration)
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
      $rhythmBuffer[$currentInstrument] += $currentDuration;

      // if any remaining duration left after the (possible) whole note,
      // use it up in the next iteration of the loop
      $remainingDuration -= $currentDuration;

      $numQuarterNotes = (int) ($currentDuration / 4); // cast necessary in PHP!
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

/**
 * Appends rests to the composition for the given duration.
 */
function restHelper($restDuration)
{
    global $newDataPerInstrument;
    global $currentInstrument;

    // handles durations longer than whole note
    $remainingDuration = $restDuration;
    while ($remainingDuration > 0)
    {

      // consider the current duration as whole note tops
      $currentDuration = min(SIXTEENTH_NOTES_PER_MEASURE, $remainingDuration);

      // if any remaining duration left after the (possible) whole note,
      // use it up in the next iteration of the loop
      $remainingDuration -= $currentDuration;

      $numQuarterNotes = (int) ($currentDuration / 4);
      $remainingSixteenthNotes = $currentDuration % 4;

      // {{{ for very specific notation cases
      if ($numQuarterNotes == 1 && $remainingSixteenthNotes == 2)
      {
        // write out the dotted quarter note
        $newDataPerInstrument[$currentInstrument] .= " r4. ";
      }
      else
      {
        // }}}
        // {{{ for general notation case

        if ($numQuarterNotes == 1)
          $newDataPerInstrument[$currentInstrument] .= " r4 ";
        else if ($numQuarterNotes == 2)
          $newDataPerInstrument[$currentInstrument] .= " r2 ";
        else if ($numQuarterNotes == 3)
          $newDataPerInstrument[$currentInstrument] .= " r2. ";
        else if ($numQuarterNotes == 4)
          $newDataPerInstrument[$currentInstrument] .= " r1 ";

        if ($remainingSixteenthNotes > 0 && $numQuarterNotes > 0)
          $newDataPerInstrument[$currentInstrument] .= " ";

        if ($remainingSixteenthNotes == 1)
          $newDataPerInstrument[$currentInstrument] .= " r16 ";
        else if ($remainingSixteenthNotes == 2)
          $newDataPerInstrument[$currentInstrument] .= " r8 ";
        else if ($remainingSixteenthNotes == 3)
          $newDataPerInstrument[$currentInstrument] .= " r8. ";

        // }}}
      }
    }
}

// }}}
// {{{ functions

/**
 * Returns a randomly generated file name,
 * which does not already exist on the server.
 *
 * @return file name
 */
function generateFileName()
{
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

        if (array_search($filename . LILY_FILE_EXTENSION,
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
    global $exactTranscription;

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
      $newData .= $instrumentData . " \\bar \"|.\"" . "\n}\n\n";
    }
    
    if (!$exactTranscription)
      $newData .= "\\markup {\n\tNOTE: This is a simplified transcription of the composition.\n}\n";

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
    $fileHandler = fopen(SAVE_DIRECTORY . $filename . LILY_FILE_EXTENSION, 'w')
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

  // check if generated file exists
  return file_exists(SAVE_DIRECTORY . $filename . PDF_FILE_EXTENSION);
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
if (isset($_GET[TEST_PARAM])) {
  $myFile = $_GET[TEST_PARAM];
  $fh = fopen($myFile, 'r')
    or die("CANNOT LOAD TEST FILE");
  $data = fread($fh, 10000000);
  fclose($fh);
}

// Interpret Data
$lilydata = interpretData($data);

// Generate Filename
$filename = generateFileName();
if (isset($_GET[TEST_PARAM]))
  $filename = $_GET[TEST_PARAM];

// Save .ly file
saveFile($lilydata, $filename);

// Generate PDF file from .ly file
//generatePDF($filename);

// Display the link
displayLink($filename);

// }}}

?>