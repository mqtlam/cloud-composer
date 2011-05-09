//	Cloud Composer. Eui Jung. May 5, 2011
//	Contains the definition for Note Object


function Note(len, inst, pitch) {
	this.noteLength = len;
	this.instrument = inst;
	this.pitch = pitch;
}

// pre-condition:
// two notes can't have same instruments and pitch but different length
Note.prototype.equals = function (note) {
	return this.instrument == note.instrument
		&& this.pitch == note.pitch;
}
	
Note.prototype.changeLength = function (newLength) {
	this.noteLength = newLength;
}

