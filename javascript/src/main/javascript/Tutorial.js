function Tutorial() {
	var bubble = document.createElement("div");
	this.createBubble(bubble, "instrumentContainer", "Please select the instrument you would like to work with.", "left", 75, 100);

	this.listeningElement = bubble;
	this.elementIDList = ["instrumentContainer", "grid_square", "column_button", "playpausebutton", "getlinkbutton"];
	this.textList = ["Please select the instrument you would like to work with.",
						"Click anywhere on the grid to add a note. To remove, just click again.",
						"To hear your current song, you need to first position the highlight bar at the top to where you want to start listening from.",
						"Now you can hear the song, starting from the position of the highlight bar. Press play at the bottom.",
						"To save your work, click on GET LINK to the right, and save the URL provided for next time."];
	this.bubblePositionList = ["left", "top", "top", "top", "top"];
	this.topList = [15, 30, 100, 600, 100];
	this.leftList = [15, 500, 150, 50, 500];
}

Tutorial.prototype.hasListeningElement = function () {
	if (this.listeningElement) {
		return true;
	}
	return false;
}

Tutorial.prototype.createBubble = function (element, elementID, text, 
										bubble_position, top, left) {
	var tutorial = document.getElementById("tutorial");
	
	element.innerHTML = text;
	
	element.className = "bubble " + bubble_position;
	element.id = elementID;
	
	element.style.position = "absolute";
	element.style.padding = 15 + "px";
	
	element.style.top = top + "px";
	element.style.left = left + "px";
	
	if (tutorial) {
		tutorial.appendChild(element);
	}
}
	
Tutorial.prototype.updateTutorialView = function(elementID) {
	if (this.hasListeningElement() && (this.listeningElement.id == elementID || elementID.indexOf("bubble") >= 0)) {
		document.getElementById("tutorial").removeChild(this.listeningElement);
		
		var n = this.getIndex(elementID, this.elementIDList) + 1;
		if (n < this.elementIDList.length) {
			var bubble = document.createElement("div");
			this.createBubble(bubble, this.elementIDList[n], this.textList[n],
							this.bubblePositionList[n], this.topList[n], this.leftList[n]);
			this.listeningElement = bubble;
		} else {
			this.listeningElement = null;
		}
	}
	

}

Tutorial.prototype.getIndex = function (member, array) {
	var index = -1;
	for(var j=0;j<array.length;j++){
		if (array[j] == member){
			index = j;
			break;
		}
	}
	return index;
}


function alertTutorial() {
	var str = "<div id=\"tutorial\">"
			+ "<h2>Tutorial</h2>"
			+ "<ol>"
			+ "<li><strong>INSTRUMENT SELECTION:</strong> Choose an instrument to work with.</li>"
			+ "<li><strong>BASIC ADDING/REMOVING:</strong> Click on the grid to add/remove notes.</li>"
			+ "<li>"
            + "<strong>NOTE EXTENSION:</strong> To extend the length of a note, hover over it and you should see a white rectangle at the right edge of the note's square.<br/>"
            + "Click the white rectangle and drag to the right."
        	+ "</li>"
	        + "<li>"
            + "<strong>NOTE RETRACTION:</strong> You may also decrease the note length back to a shorter length. To do this, hover over the rightmost square of the note and you will<br/>"
            + "see the same white rectangle. Click the white rectangle and drag to the left."
        	+ "</li>"
        	+ "<li>"
            + "<strong>SCORE EXTENTION:</strong> If you run out of space, and would like to extend the score, scroll over to the rightmost column of the grid. Click the black<br/>"
            + "vertical bar to the right of the rightmost column to extend it."
        	+ "</li>"
        	+ "<li>"
            + "<strong>WHAT ARE THOSE COLORED SQUARES?</strong> You may add multiple instruments to a square. Only the icon of the selected instrument will show on a square.</br>"
            + "Any added but non-selected instruments will show up as small colored squares. The color of the square should match the background color of its icon on the left."
        	+ "</li>"
			+ "<li><strong>SELECTED START POSITION FOR PLAYBACK:</strong> Use buttons on the columns to highlight a column. The column you select corresponds to the start position of the song during playback.</li>"
			+ "<li><strong>HOW DO I HEAR MY SONG?</strong> Use the buttons at the bottom of the screen for playback.</li>"
			+ "<li><strong>HOW DO I GET SHEET MUSIC OR SAVE MY SESSION FOR NEXT TIME?</strong> Use the GetLink button to get music sheet in pdf form, or the session id for future use.</li>"
			+ "<li><strong>HOW DO I GET A MIDI FILE OF MY SONG?</strong> Use the Download button to download the composed song as midi file.</li>"
			+ "</ol>"
	
			+ "<p style=\"margin-left: 10px\">"
			+ "Here is an example of a composed music: <br />"
			+ "<a href=\"http://cloud-composer.com/?session=9916019042\">http://cloud-composer.com/?session=9916019042</a>"
			+ "</p>"
			+ "</div>";

	new DisplayBox(700, 400, "#EEEEEE", "", str);
}
