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
		min: 50,
		max: 200,
		value: initialValue,
		orientation: 'horizontal',
		
		// when value is changed
//		change: function(event, ui){
//			update_bpmValueOnPage();
//		}
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
