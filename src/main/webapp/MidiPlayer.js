function MidiPlayer(inst, num) {
	this.inPlayMode = false; 
	this.notePosition = 0; // where song is during playback
	this.instruments = inst;
	this.maxValue = num-1;
	this.intervalID = 0;
	this.intervalSpeend = 150;
	this.positionTracker = 1;
	this.basePosition = 0;
	
	createTempoSlider(120);
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
			
		//var g = document.getElementById('grid');
		//var b = document.getElementsByClassName('highlightbar')[0];
		//g.scrollLeft = b.offsetLeft;
		this.intervalID = setInterval("updateSongPosition()", this.intervalSpeend);
	} else {
		playPauseButton.src = "images/Play-Disabled-icon.png";
		this.inPlayMode = false;
		applet.pause();
		clearInterval(this.intervalID);
		// actually pause
	}
}

MidiPlayer.prototype.onStopClick = function () {
	var g = document.getElementById('grid');
	g.scrollLeft = 0;
	applet.stop();
	this.reset(0);
}

MidiPlayer.prototype.reset = function (to) {
	$('#playbackSlider').slider('option', 'value', to);
	highlightbar.move(to);
	clearInterval(this.intervalID);
	document.getElementById("playpausebutton").src = "images/Play-Disabled-icon.png";
	this.inPlayMode = false;
	this.setSongPosition(to);
}

MidiPlayer.prototype.addToPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;

	applet.playNote(i, p);
	applet.addNote([i, p, column, column + note.noteLength - 1]);
}

MidiPlayer.prototype.removeFromPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;
	applet.removeNote([i, p, column, column + note.noteLength - 1]);
}

MidiPlayer.prototype.setSongPosition = function(pos) {
	this.notePosition = pos-1;
	var column = document.getElementById('column'+pos);
	var g = document.getElementById('grid');
	this.positionTracker = Math.floor(column.offsetLeft/g.offsetWidth)+1;
	applet.setSongPosition(pos);
	this.basePosition = pos;
}

function updateSongPosition() {
	var g = document.getElementById('grid');
	var b = document.getElementsByClassName('highlightbar')[0];
	var p = applet.currentSongPosition();
	if (p == midiplayer.maxValue || p == midiplayer.notePosition) {
		midiplayer.reset(midiplayer.basePosition);
		g.scrollLeft = midiplayer.baseScroll;
		
	} else {
		$('#playbackSlider').slider('option', 'value', p);	
		highlightbar.move(p);
		if (midiplayer.positionTracker == Math.floor(b.offsetLeft/g.offsetWidth)) {
			g.scrollLeft = b.offsetLeft;
			midiplayer.positionTracker++;
		}
		midiplayer.notePosition = p;
	}
}

MidiPlayer.prototype.updatePlayback = function (numColumn) {
	this.maxValue = numColumn;
	$('#playbackSlider').slider('option', 'max', numColumn);
}

function createTempoSlider(initialValue) {
	$("#tempo").slider({
		// Initializing values
		animate: false,
		step: 1,
		min: 50,
		max: 200,
		value: initialValue,
		orientation: 'horizontal',
		
		// when value is changed
		change: function(event, ui){
			update_bpmValueOnPage();
		}
	});
	
	update_bpmValueOnPage();
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
	// update bpm of midiplayer
	applet.setTempo(value);
}
