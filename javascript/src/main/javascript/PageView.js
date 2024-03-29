/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam

	Main javascript, it loads all the necessary event handlers and UI components
*/
// Developer options:
// set Diable Java to true to disable midiplayer (for linux environment)
// During, Disable Java mode, playback does not work.

// Set Disable Tutorial to true in order to skip tutorials
var DISABLE_JAVA = false;

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


// Listens for a normal mouse click events
function mouseClick(event) {
	var current = event.target;

	var chk = grid.isSquare(current);
	if (chk) {
		var instrument = selector.currentInstrument;
		if (!instrument) {
			alert("you must select an instrument");
		}
		
	} else if (current.id == "extender") {
		grid.extendGrid(current);
	} else if (current.className == "instrumentContainer") {
		selector.setActiveInstrument(current.id);
		grid.updateMainImage(selector.currentInstrument.instrumentName);
	} else if (current.id == "playback_stop") { // 
		midiplayer.onStopClick();
	} else if (current.id == "playback_play" || current.id == "playback_pause") {
		midiplayer.onPlayPauseClick(grid.numColumns);
	} else if (current.id == "getlinkBtn") {
		getlink = new DisplayBox(600, 280, "#CCCCCC", "GetLink", "");
	} else if (current.className == "column_button") {
		var c = parseInt(current.id);
		midiplayer.setSongPosition(c);
	} else if (current.id == "displayCloseButton") {
		// if closing the browser notice, bring up the tutorial after closing it
		if (current.parentNode.children[1].children[0].id == "browserInfo") {
			alertTutorial();
		}
		// remove notice box
		document.body.removeChild(current.parentNode.parentNode);
	} else if (current.id == "getLinkButton") {
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "SaveSession.php");
	} else if (current.id == "getPdfButton") {
		var notegrid = grid.serialize();
		sendNoteGrid(notegrid, "Lilypond.php");
	} else if (current.id == "downloadBtn") {
		applet.download();
	} else if (current.id == "header_center") {
		alertTutorial();
	}
	

}

// mouse down event, start of 'a user event'
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

// mouse up event, end of 'a user event'
function mouseUp(event) {
	var current = event.target;
	var chk = grid.isSquare(current);
	if (chk) {
		grid.setEndingNote(chk, selector.currentInstrument);		
	} else {
		grid.resetNote();
	}
}

// mouse over event, intermediate event status
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

// TODO
function mousemove(event) {
	$("#header_left")[0].innerHTML = event;
}

// TODO
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

// TODO 
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

// TODO
function printNotes() {
	document.getElementById('output').textContent = notes;
}

// TODO
function sortAndPrintNotes() {
	document.getElementById('output').textContent = notes.sort();
}

// Loads the UI
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
		
	selector = new InstrumentSelector(instrumentsList);
			
	// create Grid, multiple of 16
	grid = new NoteGrid("grid", initialNumColumns, instrumentsList);
	midiplayer = new MidiPlayer(instrumentsList, initialNumColumns);
	highlightbar = new HighlightBar(0, "#CC6666");
	
	shareReferences()
	
	loadSession();
	
	// Checks the cookie, and if visited before, don't show browser info
	if (document.cookie == "") {
		if (getBrowser()[0] != "Firefox") {
			alertBrowserInfo();
		} else{
			alertTutorial();
		}
	}
	createCookie();
}

// exchanges data between different object
function shareReferences() {
	grid.java = midiplayer;
}

// cookie related functions
// creates a cookie for a month
function createCookie() {
	var date = new Date();
	date.setMonth(date.getMonth()+1);
	
	document.cookie = "cloudcomposer=comeback; expires" + date.toGMTString() + "; path=/";
}
	
	
/// LOAD events
window.onload = loadUI;

