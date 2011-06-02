/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam

	Note Object
		note length
		Instrument Object
		pitch		
*/

// constructor
function Note(len, inst, pitch) {
	this.noteLength = len;
	this.instrument = inst;
	this.pitch = pitch;
}

// pre-condition:
// two notes can't have same instruments and pitch but different length
Note.prototype.equals = function (note) {
	return this.instrument.equals(note.instrument)
		&& this.pitch == note.pitch;
}
	
// method for changing note length
Note.prototype.changeLength = function (newLength) {
	this.noteLength = newLength;
}

