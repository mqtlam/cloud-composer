// Developer options:
// set Diable Java to true to disable midiplayer (for linux environment)
// During, Disable Java mode, playback does not work.

// Set Disable Tutorial to true in order to skip tutorials
var DISABLE_JAVA = false;
var DISABLE_TUTORIAL = true;

// reference to the grid object
var grid;
var selector;
var midiplayer;
var tempobar;
var playerbar;
var highlightbar;
var tutorial;
var applet;
var getlink;

// computed values

/// disables Firefox's dragging
$(document).bind("dragstart", function() {
     return false;
});


// call this onload
function setEvents() {
	document.addEventListener("click", mouseClick, false);
	document.addEventListener("mousedown", mouseDown, false);
	document.addEventListener("mouseup", mouseUp, true);
	document.addEventListener("mouseover", mouseOver, false);
//	document.addEventListener("mouseover", mousemove, false);
//	document.addEventListener("mouseout", mouseOut, false);
}

function mouseClick(event) {
	var current = event.target;
	var tutorialChecker = current.className == "" ? current.id : current.className;
	tutorial.updateTutorialView(tutorialChecker);

	var chk = grid.isSquare(current);
	if (chk) {
		var instrument = selector.currentInstrument;
		if (instrument) {
			grid.gridClick(chk, selector.currentInstrument);
		} else {
			alert("you must select an instrument");
		}
		
	} else if (current.id == "extender") {
		grid.extendGrid(current);
	} else if (current.className == "instrumentContainer") {
		selector.setActiveInstrument(current.id);
		grid.updateMainImage(selector.currentInstrument.instrumentName);
	} else if (current.id == "stopbutton") { // 
		midiplayer.onStopClick();
	} else if (current.id == "playpausebutton") {
		midiplayer.onPlayPauseClick(grid.numColumns);
	} else if (current.id == "getlinkbutton") {
		getlink = new GetLink(600, 280, "#CCCCCC");		
	} else if (current.className == "column_button") {
		var c = parseInt(current.id);
		midiplayer.setSongPosition(c);
	} else if (current.id == "getLinkCloseButton") {
		// remove the GetLink Box
		document.body.removeChild(getlink.back);
	} else if (current.id == "getLinkButton") {
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "SaveSession.php");
	} else if (current.id == "getPdfButton") {
		var notegrid = grid.serializeForLilypond();
		sendNoteGrid(notegrid, "Lilypond.php");
	}
	

}

// Setting note length
function mouseDown(event) {
	if (selector.currentInstrument) {		
		var current = event.target;
		var chk = grid.isSquare(current);
		if (chk) {
			event.preventDefault();
			if (current.className == "adjuster") {
				// change the note length
				grid.changeNoteLength(chk, selector.currentInstrument);
			} else {
				grid.setStartingNote(chk, selector.currentInstrument);
			}
		}
	}
}

function mouseUp(event) {
	var current = event.target;
	var chk = grid.isSquare(current);
	if (chk) {
		grid.setEndingNote(chk, selector.currentInstrument);		
	} else {
		grid.resetNote();
	}
}

function mouseOver(event) {
	if (selector.currentInstrument) {
		var current = event.target;
		var chk = grid.isSquare(current);
		if (chk) {
			grid.setIntermediateNote(chk, selector.currentInstrument);
		}
	
		// let grid take care of the boundaries
		grid.setAdjuster(current, selector.currentInstrument.instrumentName);
	}
}

var a;
function mousemove(event) {
	a = event;
	$("#header_left")[0].innerHTML = event;
}

function rollOver(event) {
	var current = event.target;
	if (current.className == "grid_square") {  
		var square = event.target;
		var column = square.parentNode;
	
		square.style.backgroundColor = noteColor;
	} else if (current.id == "extender") {
		current.style.backgroundColor = extenderOverColor;
	}
}

function rollOut(event) { 
	var current = event.target;
	if (current.className == "grid_square") { 
		var square = event.target;
		var column = square.parentNode;

		if (checkNote(column.id, square.id) == -1) {
			square.style.backgroundColor = "transparent";
		}
	} else if (current.id == "extender") {
		current.style.backgroundColor = extenderColor;
	}
}

function printNotes() {
	document.getElementById('output').textContent = notes;
}

function sortAndPrintNotes() {
	document.getElementById('output').textContent = notes.sort();
}

function loadUI() {
	setEvents();
	var instrumentsList = [
			new Instrument("piano", "images/piano.png", "red"),
			new Instrument("guitar", "images/guitar.png", "orange"),
			new Instrument("drum", "images/drum.png", "green"),
			new Instrument("trumpet", "images/trumpet.png", "yellow"),
			new Instrument("violin", "images/violin.png","purple")];
	
	var initialNumColumns = 112;
	
	applet = DISABLE_JAVA ? new Dummy() : document.getElementById('javaApplet');
	tutorial = DISABLE_TUTORIAL ? new Dummy() : new Tutorial();
		
	selector = new InstrumentSelector(instrumentsList);		
	midiplayer = new MidiPlayer(instrumentsList, initialNumColumns);
	// create Grid, multiple of 16
	grid = new NoteGrid("grid", initialNumColumns, instrumentsList, midiplayer);
	highlightbar = new HighlightBar(0, "#CC6666");
	
	
	loadSession();
	
}

///////// FUNCTION CALLs
	
	
/// LOAD events
window.onload = loadUI;


	
	
