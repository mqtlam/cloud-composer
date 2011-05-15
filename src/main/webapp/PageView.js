
////////// CONSTANTS
// These constants must be changed in CSS as well


// reference to the grid object
var grid;
var selector;
var midiplayer;
var tempobar;
var playerbar;
var highlightbar;
var tutorial;

// computed values

// call this onload
function setEvents() {
	document.body.addEventListener("click", mouseClick, false);
//	document.body.addEventListener("mouseover", rollOver, false);
//	document.body.addEventListener("mouseout", rollOut, false);
}

function mouseClick(event) {
	var current = event.target;
	if (current.className.indexOf("grid_square") == 0 || current.parentNode.className.indexOf("grid_square") == 0) { 
		var instrument = selector.currentInstrument;
		if (instrument) {
			tutorial.updateTutorialView("addRemoveNotes");
			current = current.className.indexOf("grid_square") == 0 ? current : current.parentNode;
			grid.gridClick(current, selector.currentInstrument);
		} else {
			alert("you must select an instrument");
		}
		
	} else if (current.id == "extender") {
		grid.extendGrid(current);
	} else if (current.className == "instrumentContainer") {
		tutorial.updateTutorialView("selectInstrument");
		selector.setActiveInstrument(current.id);
		grid.updateMainImage(selector.currentInstrument.instrumentName);
	} else if (current.id == "stopbutton") { // 
		midiplayer.onStopClick();
	} else if (current.id == "playpausebutton") {
		tutorial.updateTutorialView("hearPlayback");
		midiplayer.onPlayPauseClick(grid.numColumns);
	} else if (current.id == "getlinkbutton") {
		tutorial.updateTutorialView("getLink");
		// TODO put this code somewhere else...
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "SaveSession.php");
	} else if (current.className == "column_button") {
		tutorial.updateTutorialView("setHighlightBar");
		var c = parseInt(current.id);
		highlightbar.move(c);
		midiplayer.setSongPosition(c);
	}

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
	
	applet = document.getElementById('javaApplet');
	tutorial = new Tutorial();
    //tempobar = new Slider("tempo", 190, 10, "slider");
	//playerbar = new Slider("player", 800, 10, "");
}

///////// FUNCTION CALLs
	
	
/// LOAD events
window.onload = loadUI;


	
	
