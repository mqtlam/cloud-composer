
<!-- EXAMPLE! Note, the Maven JSUnit plug-in must be installed to run this test -->

<html>
    <head>
        <title>Test Page for Note Data</title>
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
            function testAddColumns() {
                nd = new NoteData(10);
                nd.addColumns(5);
                assertEquals(15, nd.data.length);
            }
            function testAddNote() {
                nd = new NoteData(10);
                note = new Note(1, "piano", 1);
                
                nd.addNote(5, note);
                assertTrue(nd.getIndex(5, note) >= 0);
            }
            function testRemoveNote() {
                nd = new NoteData(10);
                note = new Note(1, "piano", 1);
                
                nd.addNote(5, note);
                nd.removeNote(5, note);
                assertTrue(nd.getIndex(5, note) < 0);
            }
            function testChangeNoteLength() { 
                nd = new NoteData(10);
                note = new Note(1, "piano", 1);
                
                nd.addNote(5, note);
                nd.changeNoteLength(5, note, 2);
                assertTrue(nd.data[5]["piano"][nd.getIndex(5, note)].length == 2);
            }
            function testSerializeColumn() {
                nd = new NoteData(10);
                
                nd.addNote(5, new Note(1, "piano", 1));
                nd.addNote(5, new Note(1, "drum", 2));

                serializedColumn = nd.serializeColumn(5);
                assertEquals(serializedColumn, "<column id=\"5\"><piano>{1,1}</piano><drum>{1,2}</drum></column>");
            }
            function testSerializeEmptyColumn() {
                nd = new NoteData(10);

                serializedColumn = nd.serializeColumn(5);
                assertEquals(serializedColumn, "");
            }
            function testSerializeInstrument() {
                nd = new NoteData(10);
                
                nd.addNote(5, new Note(1, "piano", 1));
                
                serializedInstrument = nd.serializeInstrument(5, "piano");
                assertEquals(serializedInstrument, "<piano>{1,1}</piano>");
            }
            function testSerializeEmptyInstrument() {
                nd = new NoteData(10);
                
                nd.addNote(5, new Note(1, "piano", 1));
                
                serializedInstrument = nd.serializeInstrument(5, "drum");
                assertEquals(serializedInstrument, "");
            }
            function testHasAnyInstrumentsOnColumnWInstruments() {
                nd = new NoteData(10);
                
                nd.addNote(5, new Note(1, "piano", 1));
                assertEquals(true, nd.hasAnyInstruments(5));
            }
            function testHasAnyInstrumentsOnColumnWNoInstruments() {
                nd = new NoteData(10);
                
                assertEquals(false, nd.hasAnyInstruments(5));
            }
        </script>
    </body>
</html>
