function Slider(id, w, h) {
	var holder = document.createElement("div");
	holder.style.width = w + "px";
	holder.style.height = h + "px";
	holder.style.position = "absolute";
	holder.style.bottom = "2px";
	holder.className = "slider";

	document.getElementById(id).appendChild(holder);
	
	$(".slider").slider({
		// Initializing values
		animate: true,
		step: 10,
		min: 50,
		max: 200,
		value: 80,
		orientation: 'horizontal',
		
		// when value is changed
		change: function(event, ui){
			update_bpmValue();
		}
	});
}

// Update tempo 
function update_bpmValue(){
	// get the location to update
	var bpm = document.getElementById("sliderValue");
	// get the value from the slider
	var value = $('.slider').slider('option', 'value');
	// set the value on the page
	bpm.innerHTML = "BPM = " + value;
	
	// update tempo in NoteGrid
	this.grid.notes.tempo = value;
}
