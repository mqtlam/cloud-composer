// Contains functions related to the grid

function NoteGrid(id, cols, instr, midiplayer) {
	// initiated values
	this.numColumns = 0;								// current number of columns
	this.notes = new NoteData(this.numColumns);			// array that contains all the notes
	this.instruments = instr;
	this.java = midiplayer;
	
	this.dragNote;
	
	// constants for the grid	
	// must change is css
	this.raw_size = 40;					// Size of a square
	this.margin = 1;					// Margin for the square
	this.border = 1;					// border of a square
	this.measureSeparation = 4;			// gap between measures in pixels	

	// just constants
	this.maxLength = 4;
	this.pitches = 5;					// number of pitches (squares) in a column
	this.octaves = 2;					// number of octaves
	this.octaveMargin = 4;
	this.measureWidth = 16;				// number of squares in a measure
	this.extenderWidth = 24;			// width of the extender bar
	this.appendBy = 2;					// number of measures to append at a time
	this.instrumentOffset = 2;			// stack distancei
	this.scrollbarOffsets = 20;			//
	this.selectorHeight = 14;			//	

	//computed values
	this.size = this.raw_size + 2*this.border; 			// actual size including borders
	this.h = this.size*this.pitches*this.octaves
			+ this.margin*this.pitches*this.octaves
			+ this.selectorHeight; 	// height of a column
	this.w = (this.margin+this.size)*20; 				// the visible section of the grid
	


////////////////// create the grid and then add it to the child
/// adjust columns to become multiple of 16
	var grid = document.getElementById(id);
	grid.style.height = this.scrollbarOffsets + this.h + "px";
	grid.appendChild(this.createGrid(cols));	// must be multiple of 16
}

NoteGrid.prototype.createSquare = function (loc_y) {
	var sq = document.createElement("div");
	
	sq.className = "grid_square";
	sq.id = "square" + (this.reversePitch(loc_y));
	
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
	var columnbutton = document.createElement("button");
	columnbutton.className = "column_button";
	columnbutton.id = loc_x;
	column.appendChild(columnbutton);

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
	
	this.updateColumnNumber(this.appendBy*this.measureWidth);
	
	// bug fix, onmouseout never gets detected when appending columns:
	bar.style.backgroundColor = this.extenderColor;
}


NoteGrid.prototype.updateColumnNumber = function (addition) {
	this.numColumns = this.numColumns + addition;
	this.notes.addColumns(addition);
	// update the Notedata
}

NoteGrid.prototype.updateDisplay = function() {	// column, pitch
	var column = this.dragNote[2];
	var pitch = this.dragNote[1];
	
	var instrumentsToDisplay = this.getInstruments(this.notes.getInstruments(column, pitch));
	var square = this.getSquare(column, pitch);
	
	this.updateIndicator(square, instrumentsToDisplay);
}

NoteGrid.prototype.updateIndicator = function(square, instrumentsList) {
	square.innerHTML = "";	// removes all children
	square.appendChild(this.createInstrumentIndicator(instrumentsList));
}

NoteGrid.prototype.updateMainImage = function(instrumentName) {
	for (var j=0; j<this.instruments.length; j++) {
		var notes = document.getElementsByClassName(this.instruments[j].instrumentName);
		for (var i=0; i<notes.length; i++) {
			notes[i].style.backgroundImage = "none";
		}
	}
	var notes = document.getElementsByClassName(instrumentName);
	for (var i=0; i<notes.length; i++) {
		notes[i].style.backgroundImage = "url('images/thumbnail_"+instrumentName+".png')";
	}
}


NoteGrid.prototype.createInstrumentIndicator = function (instrs) {
	var len = this.instruments.length;
	var side = Math.floor((this.raw_size-3)/len) - 1;
	var holder = document.createElement("div");
	holder.className = "instrumentDisplayBox";
	holder.style.width = (this.raw_size - 4) + "px";
	holder.style.height = side + "px";
	for (var i=0; i<instrs.length; i++) {
		var span = document.createElement("span");
		span.className = "instrumentIndicator";
		span.style.width = side + "px";
		span.style.height = side + "px";
		span.style.backgroundColor = instrs[i].color;
		span.style.position = "absolute";
		span.style.marginLeft = (1+((side+1)*this.instruments.indexOf(instrs[i]))) + "px";
		holder.appendChild(span);
	}
	return holder;
}

NoteGrid.prototype.createInstrumentImg = function (instrumnet) {
	var len = this.instruments.length;
	var side = Math.floor((this.raw_size-3)/len) - 1;
	var holder = document.createElement("div");
	holder.className = "instrumentDisplayBox";
	holder.style.width = (this.raw_size - 4) + "px";
	holder.style.height = (this.raw_size - 6 - side) + "px";
	holder.className = "instrumentImage";

	return holder;
}

NoteGrid.prototype.gridClick = function (evt, instrument) {
/*
	var square = evt;
	
	var pitch = parseInt(this.getInt(square.id));
	var column = parseInt(this.getInt(square.parentNode.parentNode.id));
	
	var note = new Note(1, instrument, pitch);
	var index = this.notes.getIndex(column, note);

	if (applet.player.earlySetString == "") {
		// should add the note if not already there, otherwise remove
		if (index == -1) {
			this.notes.addNote(column, note);
			this.java.addToPlayer(column, note);
			square.className = square.className + " " + instrument.instrumentName;
			square.style.backgroundImage = "url('images/thumbnail_"+instrument.instrumentName+".png')";
		} else {
			this.notes.removeNote(column, note);
			this.java.removeFromPlayer(column, note);
			square.style.backgroundImage = "none";
			square.className = square.className.replace(" " + instrument.instrumentName, "");
		}
		
		this.updateDisplay(column, pitch);
	} else {
		alert("I am sorry, but your platform does not support Midi Playback.\n"
			+ "For Possible Resolution visit the following link:\n"
			+"http://publicstaticdroid.com/cloudcomposer/Issues.html");
	}
*/
}

/////////////////////// Note Length
NoteGrid.prototype.setStartingNote = function(evt, instrument) {
	var square = evt;
	
	var pitch = parseInt(this.getInt(square.id));
	var column = parseInt(this.getInt(square.parentNode.parentNode.id));
	
	var note = new Note(1, instrument, pitch);
	var index = this.notes.getIndex(column, note);

	if (applet.player.earlySetString == "") {
		// should add the note if not already there, otherwise remove
		if (index == -1) {
			this.dragNote = [instrument, pitch, column, column];
			square.style.backgroundImage = "url('images/thumbnail_gray_"+instrument.instrumentName+".png')";
		} else {
			this.dragNote = [instrument, pitch, column, -1];
		}
		
	} else {
		alert("I am sorry, but your platform does not support Midi Playback.\n"
			+ "For Possible Resolution visit the following link:\n"
			+"http://publicstaticdroid.com/cloudcomposer/Issues.html");
	}
}

NoteGrid.prototype.setIntermediateNote = function(evt, instrument) {
	if (this.dragNote) {
		var square = evt;
	
		var pitch = parseInt(this.getInt(square.id));
		var column = parseInt(this.getInt(square.parentNode.parentNode.id));
		// check if it's a valid intermediate note
		
		if (pitch == this.dragNote[1]) {
			var diff = column - this.dragNote[3];
			if (diff == 1 && this.dragNote[3]-this.dragNote[2] + 1 < this.maxLength) {
				// add
				this.dragNote[3] += 1;
				square.style.backgroundImage = "url('images/thumbnail_gray_"+instrument.instrumentName+".png')";
			} else if (diff == -1 && column >= this.dragNote[2]) {
				// remove
				this.dragNote[3] -= 1;
				var oldSquare = this.getSquare(column+1, pitch);
				
				oldSquare.style.backgroundImage = "none";
			}
		}
	}
}



NoteGrid.prototype.setEndingNote = function(evt, instrument) {
	if (this.dragNote) {
		var square = evt;
		var pitch = parseInt(this.getInt(square.id));
		var column = parseInt(this.getInt(square.parentNode.parentNode.id));

		var note = new Note(this.dragNote[3]-this.dragNote[2] + 1, this.dragNote[0], this.dragNote[1]);	
		
		if (this.dragNote[3] == -1) {
			if (this.dragNote[2] == column) {	
				var removedNote = this.notes.removeNote(column, note);
				this.java.removeFromPlayer(column, removedNote);
				
				this.dragNote[3] = column + removedNote.noteLength;
				this.clearBackImage();
				square.className = square.className.replace(" " + instrument.instrumentName, "");
			}
			
		} else {	
			// has same pitch & has acceptable length
			// move always updates this.dragNote[3] before up event
			// So just checking if the column is the same as this.dragNote[3] is sufficient for checking the length
			if (this.dragNote[1] == pitch && this.dragNote[3] == column) {
				//this.notes.changeNoteLength(column, new Note(1, instrument, pitch), len);
				// must visually update as well

				this.notes.addNote(this.dragNote[2], note);
				this.java.addToPlayer(this.dragNote[2], note);
				
				var mainSquare = this.getSquare(this.dragNote[2], this.dragNote[1]);
				mainSquare.className = mainSquare.className + " " + this.dragNote[0].instrumentName;
				mainSquare.style.backgroundImage = "url('images/thumbnail_"+this.dragNote[0].instrumentName+".png')";
				
				var endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
//				endSquare.className = 
				
			} else {
				// removes all the background
				this.clearBackImage();	
			}
	
			// consume the event
		}
		
		this.updateDisplay();
		this.dragNote = undefined;
	}
}





NoteGrid.prototype.resetNote = function() {
	if (this.dragNote) {
		// reset background Image and then remove dragNote
		this.clearBackImage();
		this.dragNote = undefined;
	}
}

NoteGrid.prototype.clearBackImage = function() {
	if (this.dragNote) {
		// reset background Image and then remove dragNote
		for (var i=this.dragNote[2]; i<=this.dragNote[3]; i++) {
			var square = this.getSquare(i, this.dragNote[1]);
			square.style.backgroundImage = "none";
		}
	}
}

NoteGrid.prototype.addBackImage = function() {
	if (this.dragNote) {
		// reset background Image and then remove dragNote
		for (var i=this.dragNote[2]; i<=this.dragNote[3]; i++) {
			var square = this.getSquare(i, this.dragNote[1]);
			square.style.backgroundImage = "none";
		}
	}
}

// assume there is some integer in the string
NoteGrid.prototype.getInt = function (str) {
	return str.match(/\d+/)[0];
}

NoteGrid.prototype.lookUpInstrumentIndex = function (instrumentName) {
	for (var i=0; i<this.instruments.length; i++) {
		if (this.instruments[i].instrumentName == instrumentName) {
			return i;
		}
	}
	return -1;
}

NoteGrid.prototype.getInstruments = function (instrumentNameList) {
	var newList = [];
	for (var i=0; i<this.instruments.length; i++) {
		for (var j=0; j<instrumentNameList.length; j++) {
			if (this.instruments[i].instrumentName == instrumentNameList[j]) {
				newList.push(this.instruments[i]);
			}	
		}
	}
	return newList;
}

NoteGrid.prototype.reversePitch = function (i) {
	return (this.pitches*this.octaves - i - 1);
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

NoteGrid.prototype.getSquare = function(column, pitch) {
	var col = document.getElementById("column"+column);
	var square = col.getElementsByClassName("grid_square")[this.reversePitch(pitch)];
	
	return square;
}

NoteGrid.prototype.isSquare = function(current) {
	if (current.className.indexOf("grid_square") == 0) {
		return current;
	} else if (current.parentNode.className.indexOf("grid_square") == 0) {
		return current.parentNode;
	} else {
		return false;
	}
}

/* returns a string version of the current grid (used for submitting form to PHP) */
NoteGrid.prototype.serialize = function () {
	var notegridstring = "<?xml version=\"1.0\"?>";
	notegridstring += "<noteData>";
	for (var i = 0; i < this.numColumns; i++) {
		notegridstring += this.notes.serializeColumn(i); // returns "<col><><>...</col>"
	}
	notegridstring += "</noteData>";
	return notegridstring;
}

