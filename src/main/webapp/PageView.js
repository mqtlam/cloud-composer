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

// computed values

/// disables Firefox's dragging
$(document).bind("dragstart", function() {
     return false;
});


// call this onload
function setEvents() {
	document.body.addEventListener("click", mouseClick, false);
	document.body.addEventListener("mousedown", mouseDown, false);
	document.body.addEventListener("mouseup", mouseUp, true);
	document.body.addEventListener("mouseover", mouseOver, false);
//	document.body.addEventListener("mouseover", d, false);
	document.body.addEventListener("mouseout", mouseOut, false);
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
		// TODO put this code somewhere else...
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "SaveSession.php");
	} else if (current.className == "column_button") {
		var c = parseInt(current.id);
		highlightbar.move(c);
		midiplayer.setSongPosition(c);
	}

}


// Setting note length
function mouseDown(event) {
	event.preventDefault();
	if (selector.currentInstrument) {		
		var current = event.target;
		var chk = grid.isSquare(current);
		if (chk) {
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

function mouseOut(event) {
	var current = event.target;
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
	
	selector = new InstrumentSelector(instrumentsList);		
	midiplayer = new MidiPlayer(instrumentsList, initialNumColumns);
	// create Grid, multiple of 16
	grid = new NoteGrid("grid", initialNumColumns, instrumentsList, midiplayer);
	highlightbar = new HighlightBar(0, "#CC6666");
	
	applet = DISABLE_JAVA ? new Dummy() : document.getElementById('javaApplet');
	tutorial = DISABLE_TUTORIAL ? new Dummy() : new Tutorial();
    //tempobar = new Slider("tempo", 190, 10, "slider");
	//playerbar = new Slider("player", 800, 10, "");
}

///////// FUNCTION CALLs
	
	
/// LOAD events
window.onload = loadUI;


	
	
