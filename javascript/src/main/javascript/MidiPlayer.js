/*
	CSE 403 Cloud Composer Group (https://code.google.com/p/cloud-composer/wiki/CloudComposer)
	Eui Min Jung, Hannah Hemmaplardh, James Vaughan, Jared Clement, Junebae Kye, Jungryul Choi, Michael Lam
	
	contains the functions that performs midi playback
*/

// constructor, accepts possible instruments in an array, and the total number of columns
function MidiPlayer(inst, num) {
	this.inPlayMode = false; 
	this.notePosition = 0; // where song is during playback
	this.instruments = inst;
	this.maxValue = num-1;
	this.intervalID = 0;
	this.intervalSpeed = 200;
	this.positionTracker = 1;
	this.basePosition = 0;
	
	createTempoSlider(120, this);
	createPlayerSlider(0);
}

// event handler for play or pause button, either plays or pause the playback
MidiPlayer.prototype.onPlayPauseClick = function (numColumns) {
	this.updatePlayback(numColumns);		// updates the max value of the playback
	
	if (grid.lastColumn != 0 ) {
		if (!this.inPlayMode) {
			document.getElementById("playback_play").id = "playback_pause";
			this.inPlayMode = true;
			// actually play
			applet.play();
				
			//var g = document.getElementById('grid');
			//var b = document.getElementsByClassName('highlightbar')[0];
			//g.scrollLeft = b.offsetLeft;
			this.intervalID = setInterval("updateSongPosition()", this.intervalSpeed);
		} else {
			document.getElementById("playback_pause").id = "playback_play";
			this.inPlayMode = false;
			applet.pause();
			clearInterval(this.intervalID);
			// actually pause
		}
	}
}

// event handler for stop button, returns the focus of the grid to the origin
MidiPlayer.prototype.onStopClick = function () {
	var g = document.getElementById('grid');
	g.scrollLeft = 0;
	applet.stop();
	this.reset(0);
}

// set the current position to 'to'
MidiPlayer.prototype.reset = function (to) {
	$('#playbackSlider').slider('option', 'value', to);
	highlightbar.move(to);
	clearInterval(this.intervalID);
	if (document.getElementById("playback_pause")) {
		document.getElementById("playback_pause").id = "playback_play";
	}
	this.inPlayMode = false;
	this.setSongPosition(to);
}

// add a given note to Java, at given column 
MidiPlayer.prototype.addToPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;

	applet.playNote(i, p);
	applet.addNote([i, p, column, column + note.noteLength - 1]);
}

// remove a given note from the given column
MidiPlayer.prototype.removeFromPlayer = function (column, note) {
	var i = this.instruments.indexOf(note.instrument);
	var p = note.pitch;
	applet.removeNote([i, p, column, column + note.noteLength - 1]);
}

// set current song position
MidiPlayer.prototype.setSongPosition = function(pos) {
	this.notePosition = pos;
	this.basePosition = pos;
	applet.setSongPosition(pos);
	
	$('#playbackSlider').slider('option', 'value', pos);	
	highlightbar.move(pos);
	
	var column = document.getElementById('column'+pos);
	var g = document.getElementById('grid');
	this.positionTracker = Math.floor(column.offsetLeft/g.offsetWidth)+1;
}

// updates the song position, (changing location of highlight bar and playback bar)
// stops when end of the song is reached
function updateSongPosition() {
	var g = document.getElementById('grid');
	var b = document.getElementsByClassName('highlightbar')[0];
	var p = applet.currentSongPosition();
	
	if (midiplayer.notePosition != midiplayer.basePosition && (p == midiplayer.maxValue || p >= grid.lastColumn)) {
		applet.pause();
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

// update the max number of columns
MidiPlayer.prototype.updatePlayback = function (numColumn) {
	this.maxValue = numColumn;
	$('#playbackSlider').slider('option', 'max', numColumn);
}

// creates the tempo slider
function createTempoSlider(initialValue, player) {
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
			update_bpmValueOnPage(player);
		}
	});
	
	update_bpmValueOnPage(player);
}

// creates the playback slider
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

// updates the tempo
function update_bpmValueOnPage(player){
	// get the location to update
	var bpm = document.getElementById("sliderValue");
	// get the value from the slider
	var value = $('#tempo').slider('option', 'value');
	// set the value on the page
	if (bpm) {
		bpm.innerHTML = "BPM = " + value;
	}
	// update bpm of midiplayer
	if (applet.player && applet.player.earlySetString == "") { 
		applet.setTempo(value);
	}
	if (grid && grid.notes) {
		grid.notes.tempo = value;
	}
	
	// updates the interval speed
	player.intervalSpeed = Math.floor(15000/parseInt(value));
}
