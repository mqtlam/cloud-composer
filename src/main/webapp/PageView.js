
////////// CONSTANTS
// These constants must be changed in CSS as well


// reference to the grid object
var grid;
var selector;
var midiplayer;
var tempobar;
var playerbar;
var applet;

// computed values

// call this onload
function setEvents() {
	document.body.addEventListener("click", mouseClick, false);
//	document.body.addEventListener("mouseover", rollOver, false);
//	document.body.addEventListener("mouseout", rollOut, false);
}

var a;
function mouseClick(event) {
	var current = event.target;
	a = current;
	if (current.className.indexOf("grid_square") == 0 || current.parentNode.className.indexOf("grid_square") == 0) { 
		var instrument = selector.currentInstrument;
		if (instrument) {
			current = current.className.indexOf("grid_square") == 0 ? current : current.parentNode;
			grid.gridClick(current, selector.currentInstrument);
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
		midiplayer.onPlayPauseClick();
	} else if (current.id == "getlinkbutton") {
		alert("HELLO WORLD");
		// TODO put this code somewhere else...
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "savesession.php");
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
			
	selector = new InstrumentSelector(instrumentsList);		
	// create Grid, multiple of 16
	grid = new NoteGrid("grid", 112, instrumentsList);		//
	midiplayer = new MidiPlayer();
	applet = document.getElementById('javaApplet');
    //tempobar = new Slider("tempo", 190, 10, "slider");
	//playerbar = new Slider("player", 800, 10, "");
	createTempoSlider(80);
	createPlayerSlider(0);
}

///////// FUNCTION CALLs
	
	
/// LOAD events
window.onload = loadUI;


	
	
