/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam

	contains the note data object

//	Basically 3-D array:
	ArrayList<Map<Integer, ArrayList<Note>>> data;
		[column1 column2 column 3 ... column x]
					|		
		[Inst1	Inst2	... Inst y]
		 Note1	Note1		Note1
		 Note2	Note2		 ...
		  ...	 ...
		
//  int tempo;

*/

// constructor
function NoteData(col) {
	this.data = [];
	for (var i=0; i<col; i++) {
		this.data.push([]);			// notes storage
	}
	
	this.tempo = 80;			// tempo
}

// adds extra columns at the end
NoteData.prototype.addColumns = function (col) {
	for (var i=0; i<col; i++) {
		this.data.push([]);
	}
}


// adds the note to the data structure
NoteData.prototype.addNote = function (column, note) {
	var noteArray = this.data[column][note.instrument.instrumentName];
	var index = this.getIndex(column, note);
	if (index == -1) {
		// okay to add
		if (!noteArray) {
			this.data[column][note.instrument.instrumentName] = [];
		}
		this.data[column][note.instrument.instrumentName].push(note);
		return true;

	} else {
		// can't add.
		return false;
	}
}


// removes the note, returns true on success.
NoteData.prototype.removeNote = function (column, note) {
	var noteArray = this.data[column][note.instrument.instrumentName];
	var index = this.getIndex(column, note);

	if (index == -1) {
		// note is not found
		return false;
		
	} else {
		// remove the note
		var temp = this.data[column][note.instrument.instrumentName][index];
		this.data[column][note.instrument.instrumentName].splice(index, 1);
		return temp;
	}
}

// changes the note length at given place
NoteData.prototype.changeNoteLength = function (column, note, len) {
	var index = this.getIndex(column, note);
	this.data[column][note.instrument.instrumentName][index].changeLength(len);
}


// searches the data to see if the note exist.
// if exist, return the index, otherwise -1
NoteData.prototype.getIndex = function (column, note) {
//	alert(this.data.length + " trying to access: 	" + column);					///////////////
	var noteArray = this.data[column][note.instrument.instrumentName];
	if (noteArray) {
		for (var i=0; i<noteArray.length; i++) {
			if (noteArray[i].equals(note)) {
				return i;
			}
		}
	}
	
	return -1;
}

// get all the instruments that exist in the specified column and pitch.
NoteData.prototype.getInstruments = function (column, pitch) {
	var list = [];
	for (var instr in this.data[column]) {
		for (var i=0; i<this.data[column][instr].length; i++) {
//			alert(this.data[column][instr][i].pitch+" ?? "+pitch);
			if (this.data[column][instr][i].pitch == pitch) {
				list.push(instr);
				break;
			}
		}
	}
	return list;
}

// prints the note for debugging
NoteData.prototype.print = function () {
	var str = "";
	for (var i=0; i<this.data.length; i++) {
		var column = this.data[i];
		
		var temp = "";
		for (var instr in column) {
			temp += "\t" + instr + ":\n";
			var notes = column[instr];			
			for (var n = 0; n<notes.length; n++) {
				temp += "\t\t pitch:" + notes[n].pitch + " length:" + notes[n].noteLength + "\n";
			}
			temp += "\n";
		}
		if (temp != "") {
			str += "column " + i + "\n";
			str += temp;
			str += "\n";
		}
	}
	
	return str;
}

// output the notes into data
NoteData.prototype.output = function () {
//	for (var i=0; i<this.data.length; i++) {
//		this.data[i];
//	}
}

// returns a string version of the current column of the grid
NoteData.prototype.serializeColumn = function (column) {		
	var columnstring = "";
	
	if (this.hasAnyInstruments(column)) {
		columnstring += "<column" + " id=\"" + column + "\">";	
		for (var instr in this.data[column]) {
			columnstring += this.serializeInstrument(column, instr);
		}
		columnstring += "</column>";
	}
	
	return columnstring;
}

// returns a string version of the current instrument's notes in the given column of the grid
NoteData.prototype.serializeInstrument = function (column, instr) {
	var instrumentstring = "";
	
	if (this.data[column][instr].length > 0) {
		instrumentstring += "<instrument name=\"" + instr + "\">";
		for (var i = 0; i < this.data[column][instr].length; i++) {
			var noteLength = this.data[column][instr][i].noteLength;
			var pitch = this.data[column][instr][i].pitch;
			
			var notestring = "{" + noteLength + "," + pitch + "}";
			instrumentstring += "<length>" + noteLength + "</length>";
			instrumentstring += "<pitch>" + pitch + "</pitch>";
		}
		instrumentstring += "</instrument>";
	}
	
	return instrumentstring;
}

// checks if a column has any instruments
NoteData.prototype.hasAnyInstruments = function(column) {
	for (var instr in this.data[column]) {
		if (this.data[column][instr] && this.data[column][instr].length > 0) {
			return true;
		}
	}

	return false;
}
