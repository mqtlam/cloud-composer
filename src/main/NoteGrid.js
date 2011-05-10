// Contains functions related to the grid

function NoteGrid(id, cols) {
	// initiated values
	this.numColumns = 0;					// current number of columns
	this.notes = new NoteData(this.numColumns);			// array that contains all the notes
	
	
	// constants for the grid	
	// must change is css
	this.raw_size = 40;					// Size of a square
	this.margin = 1;						// Margin for the square
	this.border = 1;						// border of a square
	this.measureSeparation = 4;			// gap between measures in pixels	

	// just constants
	this.pitches = 5;					// number of pitches (squares) in a column
	this.octaves = 2;					// number of octaves
	this.octaveMargin = 4;
	this.measureWidth = 16;				// number of squares in a measure
	this.extenderWidth = 24;			// width of the extender bar
	this.appendBy = 2;					// number of measures to append at a time
	this.instrumentOffset = 2;			// stack distancei
	
	//computed values
	this.size = this.raw_size + 2*this.border; 			// actual size including borders
	this.h = this.size*this.pitches*this.octaves
			+ this.margin*this.pitches*this.octaves
			+ this.selectorHeight; 	// height of a column
	this.w = (this.margin+this.size)*20; 				// the visible section of the grid
	
	
	// temporary variables... need to get rid of this eventually?
	this.noteColor = "#CCCCCC";			// 
	this.extenderOverColor = "#EEEEEE";	// 
	this.extenderColor = "#444444";		// 
	this.scrollbarOffsets = 20;			//
	this.selectorHeight = 14;			//


////////////////// create the grid and then add it to the child
/// adjust columns to become multiple of 16
	grid = document.getElementById(id);
	grid.style.height = this.scrollbarOffsets + this.h + "px";
	grid.appendChild(this.createGrid(cols));	// must be multiple of 16
}


NoteGrid.prototype.createSquare = function (loc_y) {
	var sq = document.createElement("div");
	
	sq.className = "grid_square";
	sq.id = "square" + loc_y;
	
	return sq;

}

NoteGrid.prototype.createOctave = function (octaveID) {
	var octave = document.createElement("div");
	octave.style.overflow = "hidden";
	
	for (var i=octaveID*this.pitches; i<octaveID*this.pitches + this.pitches; i++) {
		octave.appendChild(this.createSquare(i));
	}

	return octave;
}

/* column includes a selector at the top */
NoteGrid.prototype.createColumn = function (loc_x) {
	var column = document.createElement("div");
	column.id = "column" + loc_x;

	column.style.width = this.size + "px";
	column.style.height = this.octaveMargin + this.h + "px";
	
	// create selector bar
	column.appendChild(document.createElement("button"));

	for (var i=0; i<this.octaves; i++) {
		column.appendChild(this.createOctave(i));
	}

	return column;
}

NoteGrid.prototype.createMeasure = function (numMeasure) {
	var measure = document.createElement("div");

	measure.style.width = ((this.size+this.margin)*this.measureWidth) + "px";
	measure.style.height = this.h + "px";
	
	for (var i=this.measureWidth*numMeasure;
			i<this.measureWidth*numMeasure + this.measureWidth; i++) {
		measure.appendChild(this.createColumn(i));
	}

	return measure;
}

NoteGrid.prototype.createExtender = function () {
	var btn = document.createElement("div");
	btn.id = "extender";

	btn.style.width = this.extenderWidth + "px";
	btn.style.height = (this.h-1) + "px";
	
	return btn;
}

NoteGrid.prototype.createGrid = function (cols) {
	var innerGrid = document.createElement("div"); 	
	var tempW = cols*(this.size + this.margin) 
			+ this.measureSeparation*cols/this.measureWidth
			+ this.extenderWidth + 4;	//need to also calculate extra margin

	innerGrid.id = "inner_grid";	
	innerGrid.style.width = tempW + "px";
	innerGrid.style.height = this.h + "px";


	for (var j=0; j<cols/this.measureWidth; j++) {
		innerGrid.appendChild(this.createMeasure(j));
	}
	
	// button
	innerGrid.appendChild(this.createExtender());
	this.updateColumnNumber(cols);
	
	return innerGrid;
}


// creates an image of an instrument to place in the grid
NoteGrid.prototype.createInstrumentSquare = function (instrumentID) {
	var listenForEvent = instrumentID == 0 ? true : false;
	var innerSquare = document.createElement("span");
	
	return innerSquare;
}

NoteGrid.prototype.extendGrid = function (bar) {
	/// APPend columns, make sure to check if you are at the limit later
	/// MUST APPEND ALL COLUMNS
	var innerGrid = document.getElementById('inner_grid');
	innerGrid.removeChild(bar);			//remove extender bar
	
	for (var i=this.numColumns/this.measureWidth;
		i<this.numColumns/this.measureWidth+this.appendBy; i++) {
		innerGrid.appendChild(this.createMeasure(i));
	}
	
	innerGrid.appendChild(bar);			// add back extender bar
	innerGrid.style.width = (innerGrid.offsetWidth 
		+ this.appendBy*((this.measureWidth*(this.size+this.margin))
		+ this.measureSeparation)) + "px";	// reset width of innergrid
	
	updateColumnNumber(this.appendBy*this.measureWidth);
	
	// bug fix, onmouseout never gets detected when appending columns:
	bar.style.backgroundColor = this.extenderColor;
}


NoteGrid.prototype.updateColumnNumber = function (addition) {
	this.numColumns = this.numColumns + addition;
	this.notes.addColumns(addition);
	// update the Notedata
}

NoteGrid.prototype.gridClick = function (evt, instrument) {
	var square = evt;
	
	var pitch = this.getInt(square.id);
	var column = this.getInt(square.parentNode.parentNode.id);
	
	var note = new Note(1, instrument, pitch);			/// construct note from input
	var index = this.notes.getIndex(column, note);

	// should add the note if not already there, otherwise remove
	if (index == -1) {
		square.style.backgroundColor = this.noteColor;
		this.notes.addNote(column, note);
		
	} else {
		square.style.backgroundColor = "transparent";
		this.notes.removeNote(column, note);
	}
}

// assume there is some integer in the string
NoteGrid.prototype.getInt = function (str) {
	return str.match(/\d+/)[0];
}


/* checks if a note is already placed */
NoteGrid.prototype.checkNote = function (a, b) {
	var string = a+":"+b;
	for (var i = 0; i < this.notes.length; i++) {
		if (this.notes[i] == string) {
			return i;
		}
	}
	return -1;
}

