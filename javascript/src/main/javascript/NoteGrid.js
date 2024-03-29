/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	contains functions for creating the grid and
	Event handlers for mouse clicking for adding/removing/changing note
*/

// constructor, contains many private variables that can be adjusted
function NoteGrid(id, cols, instr) {
	// initiated values
	this.numColumns = 0;								// current number of columns
	this.notes = new NoteData(this.numColumns);			// array that contains all the notes
	this.instruments = instr;
	this.java;
	
	// beyond this column, note is not present
	this.lastColumn = 0;
	
	this.dragNote;
	this.tempNote;
	this.adjuster;
	this.movedOut;
	
	
	// constants for the grid	
	// must change is css
	this.raw_size = 40;					// Size of a square
	this.margin = 1;					// Margin for the square
	this.border = 1;					// border of a square
	this.measureSeparation = 4;			// gap between measures in pixels	

	// just constants
	this.maxLength = 100000;
	this.pitches = 5;					// number of pitches (squares) in a column
	this.octaves = 2;					// number of octaves
	this.octaveMargin = 4;
	this.measureWidth = 4;				// number of squares in a measure
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
	if (grid && grid.style) {
		grid.style.height = this.scrollbarOffsets + this.h + "px";	
		grid.appendChild(this.createGrid(cols));	// must be multiple of 16
	}
}

// creates a single square, with class name of grid_square
NoteGrid.prototype.createSquare = function (loc_y) {
	var sq = document.createElement("div");
	
	sq.className = "grid_square";
	sq.id = "square" + (this.reversePitch(loc_y));
	
	return sq;

}

// creates an octave. Our Octave contains 5 pitches (pentatonic scale) 
NoteGrid.prototype.createOctave = function (octaveID) {
	var octave = document.createElement("div");
	octave.style.overflow = "hidden";
	
	for (var i=octaveID*this.pitches; i<octaveID*this.pitches + this.pitches; i++) {
		octave.appendChild(this.createSquare(i));
	}

	return octave;
}

// 	Column contains 2 octaves in our program.
//	column includes a selector for highlight bar at the top
NoteGrid.prototype.createColumn = function (loc_x) {
	var column = document.createElement("div");
	column.id = "column" + loc_x;

	column.style.width = this.size + "px";
	column.style.height = this.octaveMargin + this.h + "px";
	
	// create selector bar
	var columnbutton = document.createElement("button");
	columnbutton.className = "column_button";
	columnbutton.innerHTML = "<span class=\"columnNumber\">" + (loc_x+1) + "</span>";
	columnbutton.id = loc_x;
	column.appendChild(columnbutton);

	for (var i=0; i<this.octaves; i++) {
		column.appendChild(this.createOctave(i));
	}

	return column;
}


//	Measure divides squares horizontally.
//	Technically, measure should be 16, but we are using 4 for easy viewing.
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

//	Makes the bar that will allow users to generate more squares at the end
NoteGrid.prototype.createExtender = function () {
	var btn = document.createElement("div");
	btn.id = "extender";

	btn.style.width = this.extenderWidth + "px";
	btn.style.height = (this.h-1) + "px";
	
	return btn;
}

//	Creates the container for the overall grid inside of existing "grid" div
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

//	Creates more squares at the end.
NoteGrid.prototype.extendGrid = function (bar) {
	// APPend columns, make sure to check if you are at the limit later
	// MUST APPEND ALL COLUMNS
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

//	updates the total number of columns
NoteGrid.prototype.updateColumnNumber = function (addition) {
	this.numColumns = this.numColumns + addition;
	this.notes.addColumns(addition);
}

//	Creates indicator images that tells you what instruments occupy the square 
//	Fresh updates the indicator image.
NoteGrid.prototype.updateDisplay = function(column, pitch) {	// column, pitch
	var instrumentsToDisplay = this.getInstruments(this.notes.getInstruments(column, pitch));
	var square = this.getSquare(column, pitch);

	square.innerHTML = "";	// removes all children	
	square.appendChild(this.createInstrumentIndicator(instrumentsToDisplay));
}

//	TODO: never gets called
NoteGrid.prototype.updateIndicator = function(square, instrumentsList) {
	square.appendChild(this.createInstrumentIndicator(instrumentsList));
}

//	actually creates the instruments indicator.
NoteGrid.prototype.createInstrumentIndicator = function (instrs) {
	var len = this.instruments.length;
	var side = Math.floor((this.raw_size-3)/len) - 1;
	
	// creates the holder
	var holder = document.createElement("div");
	holder.className = "instrumentDisplayBox";
	holder.style.width = (this.raw_size - 4) + "px";
	holder.style.height = side + "px";
	
	// place the indicator image for each instrument
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

//	updates the note images on the grid
NoteGrid.prototype.updateMainImage = function(instrumentName) {
	for (var j=0; j<this.instruments.length; j++) {
		// removes the image on the main square
		var notes = document.getElementsByClassName(this.instruments[j].instrumentName+"Main");
		for (var i=0; i<notes.length; i++) {
			notes[i].style.backgroundImage = "none";
		}
		// removes the image on the children square
		notes = document.getElementsByClassName(this.instruments[j].instrumentName+"LengthyNote");
		for (var i=0; i<notes.length; i++) {
			notes[i].style.backgroundImage = "none";
		}
	}
	
	// place the main image
	var notes = document.getElementsByClassName(instrumentName+"Main");
	for (var i=0; i<notes.length; i++) {
			notes[i].style.backgroundImage = "url('images/thumbnail_"+instrumentName+".png')";
	}
	
	// place the children image
	notes = document.getElementsByClassName(instrumentName+"LengthyNote");
	for (var i=0; i<notes.length; i++) {
			notes[i].style.backgroundImage = "url('images/thumbnail_gray_"+instrumentName+".png')";
	}
}

//	creates the adjuster that lets user change the length of a note
NoteGrid.prototype.createAdjuster = function() {
	var btn = document.createElement("div");
	btn.className = "adjuster";
	
	return btn;
}

/*	TODO: never called?? */
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

//	To load an existing session, simulate adding of notes
NoteGrid.prototype.manuallyAddNote = function(instrument, pitch, column, noteLength) {
	this.setStartingNote(this.getSquare(column, pitch), instrument);
	
	for (var i=column+1; i<column+noteLength; i++) {
		this.setIntermediateNote(this.getSquare(i, pitch), instrument);
	}
	
	this.setEndingNote(this.getSquare(column+noteLength-1, pitch), instrument);
	
	// remove all the adjuster
	this.adjuster.parentNode.removeChild(this.adjuster);
}


//	Note Length 
//	When clicked on the adjuster, this method gets triggered
//	it simulates the state where note is just created and dragged until the position of the event 
NoteGrid.prototype.changeNoteLength = function(square, instrument) {
	// find the main note
	// fake create dragNote and also store it in tempNote
	// remove the existing note
	// pretend as if you just put down a note
	// if need to revert, do so if tempNote exist in reset
	
	if (!this.dragNote) {
		square.className = square.className.replace(" "+instrument.instrumentName+"Extendable", "");

		var pitch = parseInt(this.getInt(square.id));
		var column = parseInt(this.getInt(square.parentNode.parentNode.id));

		var mainSquare = this.findMainSquare(column, pitch, instrument.instrumentName);
		var mainColumn = parseInt(this.getInt(mainSquare.parentNode.parentNode.id));
		mainSquare.className = mainSquare.className.replace(" "+instrument.instrumentName+"Main", "");
		
		// tempNote exists for reverting; if changing note length fails, the note should go back to original state
		this.dragNote = [instrument, pitch, mainColumn, column];
		this.tempNote = [instrument, pitch, mainColumn, column];
		
		// remove the note from the data
		var note = new Note(column-mainColumn+1, instrument, pitch);
		var removedNote = this.notes.removeNote(mainColumn, note);
		this.java.removeFromPlayer(mainColumn, removedNote);
		
		this.lastColumn = this.getLastColumn() + 1;
	}
}

//	Gets called when mouse is down on the grid square
NoteGrid.prototype.setStartingNote = function(evt, instrument) {
	var square = evt;
	
	var pitch = parseInt(this.getInt(square.id));
	var column = parseInt(this.getInt(square.parentNode.parentNode.id));
	
	var note = new Note(1, instrument, pitch);
	var index = this.notes.getIndex(column, note);

	if (applet.player && applet.player.earlySetString == "") {

		// can't place a note if it's a part of the note is there.
		if (square.className.indexOf(instrument.instrumentName+"LengthyNote") < 0) {
		
			// if the note doesn't exist, start adding
			if (index == -1) {
				this.dragNote = [instrument, pitch, column, column];
				square.style.backgroundImage = "url('images/thumbnail_gray_"+instrument.instrumentName+".png')";
				
			// if the note exist on the place, remove the note.
			} else {
				this.dragNote = [instrument, pitch, column, -1, pitch, column];
				/////
				
				var removedNote = this.notes.removeNote(column, note);		// <- noteLength maybe wrong, but doesn't matter
				this.java.removeFromPlayer(column, removedNote);
				
				this.lastColumn = this.getLastColumn() + 1;
				this.dragNote[3] = column + removedNote.noteLength - 1;
				
				
			}
		}
		
	} else {
		// switch to the popupbox
		alert("I am sorry, but your platform does not support Midi Playback.\n"
			+ "For Possible Resolution visit the following link:\n"
			+"http://cloud-composer.com/Issues.html");
	}
}

// Gets called while mouse is moving. only does something if mouse is on down
NoteGrid.prototype.setIntermediateNote = function(evt, instrument) {
	if (this.dragNote) {
		var square = evt;
	
		var pitch = parseInt(this.getInt(square.id));
		var column = parseInt(this.getInt(square.parentNode.parentNode.id));
		// check if it's a valid intermediate note
		
		// not moving; extending note while adding
		if (pitch == this.dragNote[1] && this.dragNote[4] == undefined) {
			var diff = column - this.dragNote[3];
			if (diff == 1 && this.dragNote[3]-this.dragNote[2] + 1 < this.maxLength && square.className.indexOf(instrument.instrumentName) < 0) {
				// add
				this.dragNote[3] += 1;
				square.style.backgroundImage = "url('images/thumbnail_gray_"+instrument.instrumentName+".png')";
				square.className = square.className + " " + instrument.instrumentName + "LengthyNote";
			} else if (diff == -1 && column >= this.dragNote[2]) {
				// remove
				this.dragNote[3] -= 1;
				var oldSquare = this.getSquare(column+1, pitch);
				
				oldSquare.style.backgroundImage = "none";
				oldSquare.className = oldSquare.className.replace(" "+instrument.instrumentName + "LengthyNote", "");
			}
			
			
		// moving the note.
		} else if (this.dragNote[4] != undefined) {
			this.movedOut = true;

			// Erase the old square image
			var endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
			endSquare.className = endSquare.className.replace(" "+this.dragNote[0].instrumentName+"Extendable", "");
			
			// erase images from the old squares
			this.clearBackImage();
			
			// need to remove Adjuster image if exist.
			// temporary undefine this.dragNote[4]
			var temp_drag_note_4 = this.dragNote[4];
			this.dragNote[4] = undefined;
			this.setAdjuster(endSquare, this.dragNote[0].instrumentName);
			this.dragNote[4] = temp_drag_note_4;
			
			// update indicator, (gets removed here while maintaining other instruments' indicators)
			this.updateDisplay(this.dragNote[2], this.dragNote[1]);
			
			
			// update dragNote
			this.dragNote[1] = pitch;
			this.dragNote[3] = this.dragNote[3]-this.dragNote[2]+column;
			this.dragNote[2] = column;
			
			// create new backImage
			this.setIntermediateNotes();
			
			// set appropriate classNames
			mainSquare = this.getSquare(this.dragNote[2], this.dragNote[1]);
			mainSquare.className = mainSquare.className + " " + this.dragNote[0].instrumentName+"Main";
			
			endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
			endSquare.className = endSquare.className + " "+this.dragNote[0].instrumentName+"Extendable";
		}
	}
}

// gets called on mouse up, ends a user event by either adding a note or reverting a note
NoteGrid.prototype.setEndingNote = function(evt, instrument) {
	if (this.dragNote) {
		var endSquare;
	
		var square = evt;
		var pitch = parseInt(this.getInt(square.id));
		var column = parseInt(this.getInt(square.parentNode.parentNode.id));

		var note = new Note(this.dragNote[3]-this.dragNote[2] + 1, this.dragNote[0], this.dragNote[1]);	
		
		// moving and moved out
		if (this.dragNote[4] != undefined && this.movedOut) {
			
			// if there is a conflict
			if (this.noteConflict()) {
				// revert all image, not remove
				this.revertNote();
			}
			
			// if moved, create new note at the beginning column
			note = new Note(this.dragNote[3]-this.dragNote[2] + 1, this.dragNote[0], this.dragNote[1]);	
			
			this.notes.addNote(this.dragNote[2], note);
			this.java.addToPlayer(this.dragNote[2], note);
			this.lastColumn = Math.max(this.lastColumn, this.dragNote[3]+2);


			
			endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
			this.dragNote[4] = undefined;
			this.dragNote[5] = undefined;
			
			this.movedOut = false;
			
		// if moved back to the same place
		} else if (this.dragNote[4] != undefined) {
			var endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
			endSquare.className = endSquare.className.replace(" "+this.dragNote[0].instrumentName+"Extendable", "");
			endSquare = undefined;
			
			this.clearBackImage();
			
		// ADDING A NOTE including initial Drag.
		} else {
			// has same pitch & has acceptable length
			// move always updates this.dragNote[3] before up event
			// So just checking if the column is the same as this.dragNote[3] is sufficient for checking the length
			if (this.dragNote[1] == pitch && this.dragNote[3] == column) {
				this.notes.addNote(this.dragNote[2], note);
				this.java.addToPlayer(this.dragNote[2], note);
				this.lastColumn = Math.max(this.lastColumn, this.dragNote[3]+2);
				
				// set the main square
				var mainSquare = this.getSquare(this.dragNote[2], this.dragNote[1]);
				mainSquare.className = mainSquare.className + " " + this.dragNote[0].instrumentName+"Main";
				mainSquare.style.backgroundImage = "url('images/thumbnail_"+this.dragNote[0].instrumentName+".png')";
				
				// set the ending square
				endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
				endSquare.className = endSquare.className + " "+this.dragNote[0].instrumentName+"Extendable";
			} else {
				// removes all the background
				this.clearBackImage();	
			}
	
			// consume the event
		}
		
		this.updateDisplay(this.dragNote[2], this.dragNote[1]);
		if (endSquare) {
			this.setAdjuster(endSquare, this.dragNote[0].instrumentName);
		}
		this.dragNote = undefined;
		this.tempNote = undefined;
	}
}

// sets the tempo
NoteGrid.prototype.setTempo = function (t) {
	this.notes.tempo = t;
	$('#tempo').slider('option', 'value', t);
}

// helper method, sets the images of the lengthy notes
NoteGrid.prototype.setIntermediateNotes = function() {
	if (this.dragNote) {
		var square = this.getSquare(this.dragNote[2], this.dragNote[1]);
		square.style.backgroundImage = "url('images/thumbnail_"+this.dragNote[0].instrumentName+".png')";
		
		for (var i=this.dragNote[2]+1; i<= this.dragNote[3]; i++) {
			square = this.getSquare(i, this.dragNote[1]);
			square.style.backgroundImage = "url('images/thumbnail_gray_"+this.dragNote[0].instrumentName+".png')";
			square.className = square.className + " " + this.dragNote[0].instrumentName + "LengthyNote";
		}
	}
}

// show note length adjusting bar
NoteGrid.prototype.setAdjuster = function(obj, instrumentName) {
	if (!this.dragNote || this.dragNote[4] == undefined) {
		if (obj.className.indexOf(instrumentName+"Extendable") >= 0) {
			if (obj.getElementsByClassName("adjuster").length <= 0) {
			
				// make sure to remove the previous one before making new one
				if (this.adjuster && this.adjuster.parentNote) {
					this.adjuster.parentNode.removeChild(this.adjuster);
				}
				
				this.adjuster = this.createAdjuster();
				obj.appendChild(this.adjuster);
			}
		} else if (obj.className != "adjuster" && this.adjuster) {
			if (this.adjuster.parentNode) {
				this.adjuster.parentNode.removeChild(this.adjuster);
			}
			this.adjuster = undefined;
		}
	}
}

// TODO: never called?
NoteGrid.prototype.resetNote = function() {
	if (this.dragNote) {
	
		// released in an invalid area while moving
		if (this.movedOut) {
			this.revertNote();
			this.setEndingNote(this.getSquare(this.dragNote[3], this.dragNote[1]), this.dragNote[0]);
			
		// released in an invalid area while changing the length
		} else {
			// reset background Image and then remove dragNote
			this.clearBackImage();
			this.dragNote = undefined;
		}
	}
}

// revert the note
NoteGrid.prototype.revertNote = function() {
	if (this.dragNote) {
		var endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
		endSquare.className = endSquare.className.replace(" "+this.dragNote[0].instrumentName+"Extendable", "");
		endSquare = undefined;
		
		this.clearBackImage();
	
		// move back to original spot
		this.dragNote[3] = this.dragNote[3] - this.dragNote[2] + this.dragNote[5];
		this.dragNote[2] = this.dragNote[5];
		this.dragNote[1] = this.dragNote[4];
		
		this.setIntermediateNotes();
		mainSquare = this.getSquare(this.dragNote[2], this.dragNote[1]);
		mainSquare.className = mainSquare.className + " " + this.dragNote[0].instrumentName+"Main";
		
		endSquare = this.getSquare(this.dragNote[3], this.dragNote[1]);
		endSquare.className = endSquare.className + " "+this.dragNote[0].instrumentName+"Extendable";
	}
}


// checks for conflicting note when changing length of a note
NoteGrid.prototype.noteConflict = function() {
	if (this.dragNote) {
		for (var i=this.dragNote[2]; i<=this.dragNote[3]; i++) {
			var classNames = this.getSquare(i, this.dragNote[1]).classList;
			var checker = 0;
			for (var c=0; c<classNames.length; c++) {
				if (classNames[c].indexOf(this.dragNote[0].instrumentName+"Main") >= 0 || classNames[c].indexOf(this.dragNote[0].instrumentName+"Lengthy") >= 0) {
					checker++;
				}
			}
			
			if (checker >= 2) {
				return true;
			}
		}
		
		return false;
	} else {
		//IllegalStateException
	}
}

// clear instruments images if necessary
NoteGrid.prototype.clearBackImage = function() {
	if (this.dragNote) {
		// reset background Image and then remove dragNote
		var square = this.getSquare(this.dragNote[2], this.dragNote[1]);
		square.className = square.className.replace(" "+this.dragNote[0].instrumentName+"Main", "");
		if (square.className.indexOf(this.dragNote[0].instrumentName+"LengthyNote") != -1) {
			square.style.backgroundImage = "url('images/thumbnail_gray_"+this.dragNote[0].instrumentName+".png')";
		} else if (square.className.indexOf(this.dragNote[0].instrumentName) != -1) {
			square.style.backgroundImage = "url('images/thumbnail_"+this.dragNote[0].instrumentName+".png')";
		} else {
			square.style.backgroundImage = "none";
		}
			
			
		for (var i=this.dragNote[2]+1; i<=this.dragNote[3]; i++) {
			square = this.getSquare(i, this.dragNote[1]);
			square.className = square.className.replace(" "+this.dragNote[0].instrumentName+"LengthyNote", "");
			
			if (square.className.indexOf(this.dragNote[0].instrumentName+"LengthyNote") != -1) {
				square.style.backgroundImage = "url('images/thumbnail_gray_"+this.dragNote[0].instrumentName+".png')";
			} else if (square.className.indexOf(this.dragNote[0].instrumentName) != -1) {
				square.style.backgroundImage = "url('images/thumbnail_"+this.dragNote[0].instrumentName+".png')";
			} else {
				square.style.backgroundImage = "none";
			}
		}
	}
	
	if (this.tempNote) {
		this.dragNote = this.tempNote;
		this.setIntermediateNotes();
		this.setEndingNote(this.getSquare(this.dragNote[3], this.dragNote[1]), this.dragNote[0]); //fake register
		this.tempNote = undefined;
	}
}

// add back images to the current note
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

// look up the index of the instrument from its list
NoteGrid.prototype.lookUpInstrumentIndex = function (instrumentName) {
	for (var i=0; i<this.instruments.length; i++) {
		if (this.instruments[i].instrumentName == instrumentName) {
			return i;
		}
	}
	return -1;
}

// get the instrument objects list from instrument names
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

// reverse the pitch for array index
NoteGrid.prototype.reversePitch = function (i) {
	return (this.pitches*this.octaves - i - 1);
}

// checks if a note is already placed
NoteGrid.prototype.checkNote = function (a, b) {
	var string = a+":"+b;
	for (var i = 0; i < this.notes.length; i++) {
		if (this.notes[i] == string) {
			return i;
		}
	}
	return -1;
}

// gets the square object at given column and pitch
NoteGrid.prototype.getSquare = function(column, pitch) {
	var col = document.getElementById("column"+column);
	var square = col.getElementsByClassName("grid_square")[this.reversePitch(pitch)];
	
	return square;
}

// find the main square that corresponds to the selected column, pitch and instrument
NoteGrid.prototype.findMainSquare = function(column, pitch, instrumentName) {
	var square = this.getSquare(column, pitch);
	while (square.className.indexOf(instrumentName+"LengthyNote") >= 0) {
		square = this.getSquare(--column, pitch);
	}
	
	return square;
}

// checks if given is a square
NoteGrid.prototype.isSquare = function(current) {
	// if move out of browser, className is undefined
	if (current && current.className && current.className.indexOf("grid_square") == 0) {
		return current;
		
	// if move out of <html> tag, there's no parentNode's className
	} else if (current.parentNode && current.parentNode.className && current.parentNode.className.indexOf("grid_square") == 0) {
		return current.parentNode;
	} else {
		return false;
	}
}

// compute the last column that is occupied
NoteGrid.prototype.getLastColumn = function() {
	var grid = document.getElementById("inner_grid");
	
	for (var i=grid.children.length-2; i>=0; i--) {
		var measure = grid.children[i];
		for (var m=this.measureWidth-1; m>=0; m--) {
			var col = measure.children[m];
			if (col == undefined) { alert("what");a = measure;}
			for (var o=this.octaves; o>=1; o--) {
				var oct = col.children[o];
				for (var p=this.pitches-1; p>=0; p--) {
					var str = oct.children[p].className;
					if (str.indexOf("Lengthy")>=0 || str.indexOf("Main")>=0) {
						return i*this.measureWidth + m + 1;
					}
				}
			}
		}
	}
	return 0;
}

// returns a string version of the current grid (used for submitting form to PHP)
NoteGrid.prototype.serialize = function () {
	var notegridstring = "<?xml version=\"1.0\"?>";
	notegridstring += "<noteData tempo=\"" + this.notes.tempo + "\">";
	for (var i = 0; i < this.numColumns; i++) {
		notegridstring += this.notes.serializeColumn(i); // returns "<col><><>...</col>"
	}
	notegridstring += "</noteData>";
	return notegridstring;
}

