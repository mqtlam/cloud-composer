
////////// CONSTANTS
// These constants must be changed in CSS as well
var instrumentSize = 16;			// size of a single image

// Normal constants



var instrument = "piano";

var grid;


// computed values

// call this onload
function setEvents() {
	document.body.addEventListener("click", mouseClick, false);
//	document.body.addEventListener("mouseover", rollOver, false);
//	document.body.addEventListener("mouseout", rollOut, false);
}


function mouseClick(event) {
	var current = event.target;
	if (current.className == "grid_square") { 
		grid.gridClick(current, instrument);
		
	} else if (current.id == "extender") {
		grid.extendGrid(current);
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
	
	// create Grid
	grid = new NoteGrid("grid", 112);
	
	loadInstruments();
	
}


///////// FUNCTION CALLs
	
	
/// LOAD events
window.onload = loadUI;


	
	
