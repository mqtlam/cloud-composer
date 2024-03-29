<?php

/**
 * This script gets the composition data from the front end,
 * generates a file containing Lilypond data, and returns
 * the link of the file.
 *
 * NOTE:    Due to limitations of lilypond, not all composition data
 *          may be converted correctly. This does not support
 *          complex polyphony. The $_exactTranscription flag will
 *          become false once this parser encounters data that cannot
 *          be converted into sheet music. Nonetheless, 
 *          this parser will try its best to notate what it can.
 *
 * NOTE:    THIS SCRIPT PARSES THE NEW XML FORMAT, WHICH CONTAINS
 *          <length> and <pitch> tags, instead of the {length,pitch} pairs!
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
 * Path on server pointing to the location of the Lilypond binary executable.
 */
define("LILYPOND_BINARY_PATH", "~/lilypond/usr/bin/lilypond");

/**
 * URL of website for displaying to user
 */
define("WEBSITE_URL", "http://" . $_SERVER['SERVER_NAME'] . substr($_SERVER['PHP_SELF'], 0, strrpos($_SERVER['PHP_SELF'], "/")) . "/");

/**
 * Directory to save new file (and look up old files)
 */
define("SAVE_DIRECTORY", "songs/");

/**
 * Message to display in pdf for compositions that cannot be transcribed note for note.
 */
define("SIMPLIFIED_TRANSCRIPTION_MESSAGE", "NOTE: This is a simplified transcription of the composition.");

/**
 * POST parameter to pass data to this php file.
 */
define("DATA_PARAM", "data");

/**
 * GET parameter. Value is the test XML file to load.
 * It is also the output filename.
 */
define("TEST_PARAM", "testfile");

/**
 * Lilypond file extension type
 */
define("LILY_FILE_EXTENSION", ".ly");

/**
 * PDF file extension type
 */
define("PDF_FILE_EXTENSION", ".pdf");

/**
 * All instruments here.
 */
$INSTRUMENTS = array(   "PIANO"     => "piano",
                        "VIOLIN"    => "violin",
                        "GUITAR"    => "guitar",
                        "TRUMPET"   => "trumpet",
                        "DRUM"      => "drum" );

/**
 * All pitches here.
 */
$PITCHES = array(   0   => "c'",  // c4 = 60
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
 * Note data tag name.
 */
define("NOTE_DATA_TAG", "noteData");

/**
 * Note data tempo attribute.
 */
define("NOTE_DATA_TEMPO_ATTR", "tempo");

/**
 * Column tag name.
 */
define("COLUMN_TAG", "column");

/**
 * Length tag name.
 */
define("LENGTH_TAG", "length");

/**
 * Pitch tag name.
 */
define("PITCH_TAG", "pitch");

/**
 * Instrument tag name.
 */
define("INSTRUMENT_TAG", "instrument");

/**
 * Name attribute for instrument tag.
 */
define("INSTRUMENT_NAME_ATTR", "name");

/**
 * Column id attribute.
 */
define("COLUMN_ID_ATTR", "id");

/**
 * Index of first column in note grid.
 */
define("FIRST_COLUMN", 0);

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
$_exactTranscription = true;

/**
 * Stores the tempo of the song. If 0, then no tempo marking will be recorded.
 */
$_tempo = 0;

/**
 * Stores the current instrument as a state from the XML parser.
 */
$_currentInstrument = $INSTRUMENTS['PIANO'];

/**
 * Stores the entire transcription as a string to be written.
 */
$_newData = "";

/**
 * Stores the transcription for each instrument part.
 */
$_newDataPerInstrument = array();
foreach ($INSTRUMENTS as $instrument => $name)
{
  $_newDataPerInstrument[$instrument] = "";
}

/**
 * Stores the current position state, used to compute rests.
 */
$_currentCol = FIRST_COLUMN; // single for parsing
$_currentColumn = array();  // per instrument
foreach ($INSTRUMENTS as $instrument => $name)
{
  $_currentColumn[$instrument] = FIRST_COLUMN;
}

/**
 * Stores the total amount of rhythms processed,
 * used to compute rests.
 */
$_rhythmBuffer = array();
foreach ($INSTRUMENTS as $instrument => $name)
{
  $_rhythmBuffer[$instrument] = FIRST_COLUMN;
}

/**
 * Stores a buffer of {length,pitch}'s per instrument per column.
 * This exists because the xml file format changed
 * since the feature complete release.
 */
$_notesBuffer = "";
$_withinLengthTag = false;
$_withinPitchTag = false;

// }}}
// {{{ xml functions

/**
 * Called whenever SAX parser encounters a start tag,
 * e.g. <start>. $name stores the tag name and 
 * $attribs is an array of attributes.
 */
function startElemHandler($parser, $name, $attribs)
{
    global $INSTRUMENTS;
    global $_currentCol;
    global $_currentColumn;
    global $_currentInstrument;
    global $_tempo;
    global $_notesBuffer;
    global $_withinLengthTag;
    global $_withinPitchTag;

    if (strcasecmp($name, NOTE_DATA_TAG) == 0)
    {
        // <noteData tempo="num"> detected
        $_tempo = $attribs[NOTE_DATA_TEMPO_ATTR];
    }
    else if (strcasecmp($name, COLUMN_TAG) == 0)
    {
        // <column id="num"> detected
        $_currentCol = $attribs[COLUMN_ID_ATTR];
    }
    else if (strcasecmp($name, LENGTH_TAG) == 0)
    {
        // <length> detected
        $_withinLengthTag = true;
    }
    else if (strcasecmp($name, PITCH_TAG) == 0)
    {
        // <pitch> detected
        $_withinPitchTag = true;
    }
    else if (strcasecmp($name, INSTRUMENT_TAG) == 0)
    {
        foreach ($INSTRUMENTS as $instr => $instrName)
        {
            if (strcasecmp($attribs[INSTRUMENT_NAME_ATTR], $instrName) == 0)
            {
              // <name> detected
              $_currentInstrument = $instr;
              $_currentColumn[$_currentInstrument] = $_currentCol;
              
              // reset notesBuffer
              $_notesBuffer = "";
            }
        }
    }
}

/**
 * Called whenever SAX parser encounters an end tag,
 * e.g. </start>. $name stores the tag name.
 */
function endElemHandler($parser, $name)
{
    global $INSTRUMENTS;
    global $_currentCol;
    global $_currentColumn;
    global $_currentInstrument;
    global $_tempo;
    global $_notesBuffer;
    global $_withinLengthTag;
    global $_withinPitchTag;
    
    if (strcasecmp($name, LENGTH_TAG) == 0)
    {
        // </length> detected
        $_withinLengthTag = false;
    }
    else if (strcasecmp($name, PITCH_TAG) == 0)
    {
        // </pitch> detected
        $_withinPitchTag = false;
    }
    else if (strcasecmp($name, INSTRUMENT_TAG) == 0)
    {
        // </instrument> detected          
        parseNotes();
        
        // reset notesBuffer for sanity
        $_notesBuffer = "";
    }
}

/**
 * Called whenever SAX parser encounters text data between tags,
 * e.g. <start>DATA</end>, where DATA would be stored in $data.
 */
function characterData($parser, $data)
{
    // text data detected
    global $_withinLengthTag;
    global $_withinPitchTag;
    global $_notesBuffer;
    
    if ($_withinLengthTag)
    {
      $_notesBuffer .= "{{$data},";
    }
    else if ($_withinPitchTag)
    {
      $_notesBuffer .= "{$data}}";
    }
}

/**
 * Parses the current notesBuffer into lilypond format.
 * This is processed per instrument per column.
 */
function parseNotes()
{
    global $PITCHES;
    global $_currentColumn;
    global $_rhythmBuffer;
    global $_currentInstrument;
    global $_newDataPerInstrument;
    global $_exactTranscription;
    global $_notesBuffer;
    
    $data = $_notesBuffer; // "backwards compatibility with previous code"

    // {{{ add rests if necessary
    
    // This describes a state where there is a start to polyphony.
    // We ignore it for now, i.e. don't trascribe it.
    if ($_rhythmBuffer[$_currentInstrument] > $_currentColumn[$_currentInstrument])
    {
      $_exactTranscription = false;
      return;
    }
    
    // This describes a state that we need to fill in rests before proceeding.
    if ($_rhythmBuffer[$_currentInstrument] < $_currentColumn[$_currentInstrument])
    {
      $nextBar = $_rhythmBuffer[$_currentInstrument];
      
      if ($_rhythmBuffer[$_currentInstrument] % SIXTEENTH_NOTES_PER_MEASURE != 0)
      {
        $nextBar = $_rhythmBuffer[$_currentInstrument] + (SIXTEENTH_NOTES_PER_MEASURE - $_rhythmBuffer[$_currentInstrument] % SIXTEENTH_NOTES_PER_MEASURE);
      }
      
      // fills in rest up to the measure if one exists
      if ($_currentColumn[$_currentInstrument] > $nextBar)
      {
        $restDuration = $nextBar - $_rhythmBuffer[$_currentInstrument];
        durationHelper($restDuration, "r");
        
        $restDuration = $_currentColumn[$_currentInstrument] - $nextBar;
        durationHelper($restDuration, "r");
      }
      else
      {
        $restDuration = $_currentColumn[$_currentInstrument] - $_rhythmBuffer[$_currentInstrument];
        durationHelper($restDuration, "r");
      }
      
      $_rhythmBuffer[$_currentInstrument] = $_currentColumn[$_currentInstrument];
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
    foreach ($rows as $row)
    {
      list($length, $pitch) = explode(",", $row);
      
      // if two different durations detected in one column
      if ($duration > 0 && $duration != $length)
      {
        $_exactTranscription = false;
      }
      
      $duration = max($duration, $length);
    }
    
    // pass 2: only record notes with the max duration among col
    foreach ($rows as $row)
    {
      list($length, $pitch) = explode(",", $row);
      
      if ($length == $duration)
      {
        $chord .= "{$PITCHES[$pitch]} ";
      }
    }

    $chord .= ">";

    // }}}
    // {{{ transcribe rhythm

    // handles durations longer than whole note
    durationHelper($duration, $chord);

    // }}}
}

/**
 * Appends notes or rests to the composition for the given duration.
 * $chord is a chord in lilypond notation, or "r" if a rest.
 */
function durationHelper($duration, $chord)
{
    global $PITCHES;
    global $_currentColumn;
    global $_rhythmBuffer;
    global $_currentInstrument;
    global $_newDataPerInstrument;
    global $_exactTranscription;
    
    // flag if we're inserting notes
    $usingNotes = strcasecmp($chord, "r") != 0;
    
    $inserted = $chord;
    $remainingDuration = $duration;
    while ($remainingDuration > 0)
    {

      // consider the current duration as whole note tops
      $currentDuration = min(SIXTEENTH_NOTES_PER_MEASURE, $remainingDuration);
      
      if ($usingNotes)
      {
        $_rhythmBuffer[$_currentInstrument] += $currentDuration;
      }

      // if any remaining duration left after the (possible) whole note,
      // use it up in the next iteration of the loop
      $remainingDuration -= $currentDuration;

      $numQuarterNotes = (int) ($currentDuration / 4); // cast necessary in PHP!
      $remainingSixteenthNotes = $currentDuration % 4;

      // {{{ for very specific notation cases
      if ($numQuarterNotes == 1 && $remainingSixteenthNotes == 2)
      {
        // write out the dotted quarter note
        $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}4. ";
      }
      else
      {
        // }}}
        // {{{ for general notation case
        
        switch($numQuarterNotes)
        {
          case 1:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}4 ";
            break;
          case 2:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}2 ";
            break;
          case 3:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}2. ";
            break;
          case 4:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}1 ";
            break;
          default:
        }

        if ($remainingSixteenthNotes > 0 && $numQuarterNotes > 0)
        {
          if ($usingNotes)
          {
            $_newDataPerInstrument[$_currentInstrument] .= " ~ ";
          }
          else
          {
            $_newDataPerInstrument[$_currentInstrument] .= " ";
          }
        }
        
        switch ($remainingSixteenthNotes)
        {
          case 1:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}16 ";
            break;
          case 2:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}8 ";
            break;
          case 3:
            $_newDataPerInstrument[$_currentInstrument] .= " {$inserted}8. ";
            break;
          default:
        }

        // }}}
      }

      if ($usingNotes && $remainingDuration > 0)
      {
        $_newDataPerInstrument[$_currentInstrument] .= " ~ ";
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

    while ($file = readdir($handler))
    {
        if ($file != "." && $file != "..")
        {
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

        if (array_search($filename . LILY_FILE_EXTENSION, $existingFileNames))
        {
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
    global $INSTRUMENTS;
    global $PITCHES;
    global $_newData;
    global $_newDataPerInstrument;
    global $_exactTranscription;
    global $_tempo;

    $timeSignatureNumerator = SIXTEENTH_NOTES_PER_MEASURE / 4;

    // {{{ parse XML

    // new xml parser object
    $xmlParser = xml_parser_create()
      or die("XML SAX NOT SUPPORTED");
    xml_set_element_handler($xmlParser, "startElemHandler", "endElemHandler");
    xml_set_character_data_handler($xmlParser, "characterData");
    xml_parser_set_option($xmlParser, XML_OPTION_CASE_FOLDING, 0);

    // parse xml
    xml_parse($xmlParser, $data);

    // close xml
    xml_parser_free($xmlParser);

    // }}}
    // {{{ create file

    $_newData = "";

    // add data for each instrument
    foreach ($_newDataPerInstrument as $instr => $instrumentData)
    {
      $_newData .= "\\new Staff\n{\n\t\\set Staff.instrumentName = #\"{$INSTRUMENTS[$instr]}\""
              . "\n\t\\clef treble\n\t\\time $timeSignatureNumerator/4\n\t";
      
      if ($_tempo > 0)
      {
        $_newData .= "\\tempo 4 = $_tempo\n\t";
      }
      
      $_newData .= $instrumentData . " \\bar \"|.\"" . "\n}\n\n";
    }
    
    if (!$_exactTranscription)
    {
      $_newData .= "\\markup {\n\t" . SIMPLIFIED_TRANSCRIPTION_MESSAGE . "\n}\n";
    }

    // }}}

    return $_newData;
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
    $fileHandler = fopen(SAVE_DIRECTORY . $filename . LILY_FILE_EXTENSION, 'w');
    $dataToWrite = $data;
    fwrite($fileHandler, $dataToWrite);
    fclose($fileHandler);
}

/**
 * Generates the PDF using Lilypond.
 * Assumes the lily file is already created.
 */
function generatePdf($filename)
{
  $output = shell_exec(LILYPOND_BINARY_PATH . ' ' . SAVE_DIRECTORY . $filename . LILY_FILE_EXTENSION);
	shell_exec('mv *.pdf songs/');
	shell_exec('mv *.ps songs/');
}

/**
 * Displays the permalink.
 */
function displayLink($filename)
{
    echo WEBSITE_URL . SAVE_DIRECTORY . $filename . PDF_FILE_EXTENSION;
}

// }}}
// {{{ *** THE SCRIPT BELOW: SAVE SESSION AND DISPLAY LINK ***

// This is for easy debugging purposes.
$data = $_POST[DATA_PARAM];
if (isset($_GET[TEST_PARAM]))
{
  $myFile = $_GET[TEST_PARAM];
  $fh = fopen($myFile, 'r')
    or die("CANNOT LOAD TEST FILE");
  $data = fread($fh, 10000000);
  fclose($fh);
}

// Interpret Data
$lilydata = interpretData(stripslashes($data));

// Generate Filename
$filename = generateFileName();
if (isset($_GET[TEST_PARAM]))
  $filename = $_GET[TEST_PARAM];

// Save .ly file
saveFile($lilydata, $filename);

// Generate pdf file from .ly file
generatePdf($filename);

// Display the link
displayLink($filename);

// }}}

?>
