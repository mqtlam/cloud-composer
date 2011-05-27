
<!-- EXAMPLE! Note, the Maven JSUnit plug-in must be installed to run this test -->

<html>
    <head>
        <title>Test Page for Note</title>
        <script language="javascript" src="~/jsunit/jsUnitCore.js"></script> <!-- jsunit pkg should be installed in home dir -->
		
        <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css"/>
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js" type="text/javascript"></script>
		<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
		
		<link rel="stylesheet" type="text/css" href="../../main/webapp/Main.css" />
		<link rel="stylesheet" type="text/css" href="../../main/webapp/Grid.css" />
		<link rel="stylesheet" type="text/css" href="../../main/webapp/Instrument.css"/>
		<link rel="stylesheet" type="text/css" href="../../main/webapp/MidiPlayer.css" />
		<link rel="stylesheet" type="text/css" href="../../main/webapp/GetLink.css" />
		
		<script src="../../main/webapp/Note.js" type="text/javascript"></script>
		<script src="../../main/webapp/NoteData.js" type="text/javascript"></script>
		
		<script src="../../main/webapp/Instrument.js" type="text/javascript"></script>
		<script src="../../main/webapp/InstrumentSelector.js" type="text/javascript"></script>
		
		<script src="../../main/webapp/PageView.js" type="text/javascript"></script>
		<script src="../../main/webapp/NoteGrid.js" type="text/javascript"></script>
		
        <script src="../../main/webapp/MidiPlayer.js" type="text/javascript"></script>
        <script src="../../main/webapp/SaveSession.js" type="text/javascript"></script>
        <script src="../../main/webapp/HighlightBar.js" type="text/javascript"></script>
        <script src="../../main/webapp/GetLink.js" type="text/javascript"></script>
        <script src="../../main/webapp/Tutorial.js" type="text/javascript"></script>
        <script src="../../main/webapp/Dummy.js" type="text/javascript"></script>
	</head>
    <body>
        <!--copied from Main.html -->
		<div id="header">
			<!-- Buttons go here-->
			<div id="header_left">
				<h1>Cloud Composer</h1>
			</div>
			<div id="header_right">
				<button id="getlinkbutton">Get Link</button>
				<button id="downloadbutton">Download</button>
				<div id="tempobar">
					<div id="sliderValue">
						BPM = 80
					</div>
					<div id="tempo">
						<!-- Slider goes here-->
					</div>
				</div>
			</div>
		</div>
		
		<div id="mid">
			<div id="instrumentBar">
			<!-- Instruments bar component goes here-->
			</div>
	
			<div id="container">
				<div id="grid">
					<!-- Grid component goes here-->
				</div>
			
				<div id="midiplayer">
					<!-- Midi player goes here-->
                	<div id="playbackButtons">
                    	<img src="images/Stop-Disabled-icon.png" id="stopbutton" alt="stopbutton"/>
	                    <img src="images/Play-Disabled-icon.png" id="playpausebutton" alt="playpausebutton"/>
					</div>

					<div id="playbackSliderContainer">
						<div id="playbackSlider"></div>
					</div>
					<applet id="javaApplet" code="CloudComposerGroup/CloudComposer/CloudAppletController.class" width="1" height="1" archive="MidiPlayer.jar"></applet>
				</div>
			</div>
		</div>
		
		<div id="tutorial"></div>
		
		<div id="footer">
			<!-- Contact information goes here-->
			<p>&copy; Cloud Composer</p>
		</div>

        window.onLoad = 0;
        loadUI();

        <!-- tests -->
        <script language="javascript">
            function testsEqualsPitch() {
                var note1 = new Note(1, "piano", 1);
                var note2 = new Note(1, "piano", 2);
                assertFalse(note1.equals(note2));
            }
            function testEqualsInstrument() {
                var note1 = new Note(1, "piano", 1);
                var note2 = new Note(1, "drum", 1);
                assertFalse(note1.equals(note2));
            }
            function testEqualsLength() {
                var note1 = new Note(1, "piano", 1);
                var note2 = new Note(2, "piano", 1);
                assertFalse(note1.equals(note2));
            }
            function testChangeLength() {
                var note = new Note(1, "piano", 1);
                note.changeLength(3);
                assertTrue(note.noteLength, 3);
            }
        </script>
    </body>
</html>
