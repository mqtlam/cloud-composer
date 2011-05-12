function MidiPlayer() {
	this.inPlayMode = false; 
	this.notePosition = 0; // where song is during playback
}

MidiPlayer.prototype.onPlayPauseClick = function () {
	var playPauseButton = document.getElementById("playpausebutton");
	
	if (!this.inPlayMode) {
		playPauseButton.src = "images/Pause-Disabled-icon.png";
		this.inPlayMode = true;
		// actually pause
	} else {
		playPauseButton.src = "images/Play-Disabled-icon.png";
		this.inPlayMode = false;
		// actually play
	}
}

MidiPlayer.prototype.onStopClick = function () {
	//
}
