/*
	Cloud Composer. Eui Jung. May 5, 2011
		Contains the definition for Note Object

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
function NoteData(col) {
	this.data = [];
	for (var i=0; i<col; i++) {
		this.data.push([]);			// notes storage
	}
	
	this.tempo = 0;			// tempo
}

NoteData.prototype.addColumns = function (col) {
	for (var i=0; i<col; i++) {
		this.data.push([]);
	}
}


// adds the note
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
		this.data[column][note.instrument.instrumentName].splice(index, 1);
		return true;
	}
}

NoteData.prototype.changeNoteLength = function (column, note, len) {
	var index = getIndex(column, note);
	this.data[column][note.instrument.instrumentName][index].changeLength(len);
}


// searches the data to see if the note exist.
// if exist, return the index, otherwise -1
NoteData.prototype.getIndex = function (column, note) {
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
		
		str += "column " + i + "\n";
		for (var instr in column) {
			str += "\t" + instr + ":\n";
			var notes = column[instr];			
			for (var n = 0; n<notes.length; n++) {
				str += "\t\t" + notes[n].pitch + "\n";
			}
			str += "\n";
		}
		str += "\n";
	}
	
	return str;
}

// output the notes into data
NoteData.prototype.output = function () {
//	for (var i=0; i<this.data.length; i++) {
//		this.data[i];
//	}
}
