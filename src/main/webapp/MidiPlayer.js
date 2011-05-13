function MidiPlayer(inst, num) {
	this.inPlayMode = false; 
	this.notePosition = 0; // where song is during playback
	this.instruments = inst;
	this.maxValue = num;
	this.intervalID;
	
	createTempoSlider(80);
	createPlayerSlider(0);
}

MidiPlayer.prototype.onPlayPauseClick = function (numColumns) {
	this.updatePlayback(numColumns);		// updates the max value of the playback
	var playPauseButton = document.getElementById("playpausebutton");
	
	if (!this.inPlayMode) {
		playPauseButton.src = "images/Pause-Disabled-icon.png";
		this.inPlayMode = true;
		// actually play
		applet.play();
		
		this.intervalID = setInterval("updateSongPosition()", 1000);
	} else {
		playPauseButton.src = "images/Play-Disabled-icon.png";
		this.inPlayMode = false;
		applet.pause();
		// actually pause
	}
}

MidiPlayer.prototype.onStopClick = function () {
	//
	applet.stop();
	clearInterval(this.intervalID);
	$('#playbackSlider').slider('option', 'value', 0);
	document.getElementById("playpausebutton").src = "images/Play-Disabled-icon.png";
	this.inPlayMode = false;
}


MidiPlayer.prototype.addToPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;

	applet.playNote(i, p);
	applet.addNote([i, p, column, column + note.length]);
}

MidiPlayer.prototype.removeFromPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;
	applet.removeNote([i, p, column, column + note.length]);
}


function updateSongPosition() {
	var p = midiplayer.getPosition();
	//var pos = applet.currentSongPosition();
	if (p == midiplayer.maxValue) {
		clearInterval(this.intervalID);
		$('#playbackSlider').slider('option', 'value', 0);	
	} else {
		$('#playbackSlider').slider('option', 'value', midiplayer.getPosition());	
	}
	
	
}

MidiPlayer.prototype.updatePlayback = function (numColumn) {
	this.maxValue = numColumn;
	$('#playbackSlider').slider('option', 'max', numColumn);
}

MidiPlayer.prototype.getPosition = function () {
	return this.notePosition++;
}


function createTempoSlider(initialValue) {
	$("#tempo").slider({
		// Initializing values
		animate: false,
		step: 10,
		min: 50,
		max: 200,
		value: initialValue,
		orientation: 'horizontal',
		
		
		// when value is changed
		change: function(event, ui){
			update_bpmValueOnPage();
		}
	});
}

function createPlayerSlider(initialValue) {	
	$("#playbackSlider").slider({
		// Initializing values
		animate: true,
		step: 1,
		min: 0,
		value: initialValue,
		orientation: 'horizontal',
	});
	
	$("#playbackSlider").slider("disable");
}



function update_bpmValueOnPage(){
	// get the location to update
	var bpm = document.getElementById("sliderValue");
	// get the value from the slider
	var value = $('#tempo').slider('option', 'value');
	// set the value on the page
	bpm.innerHTML = "BPM = " + value;
}
