/*
describe("NoteGrid", function() {
	it("serializes empty grid to submit to PHP", function() {
		
		var instrumentsList = [
				new Instrument("piano", "images/piano.png", "red"),
				new Instrument("guitar", "images/guitar.png", "orange"),
				new Instrument("drum", "images/drum.png", "green"),
				new Instrument("trumpet", "images/trumpet.png", "yellow"),
				new Instrument("violin", "images/violin.png","purple")];
		
		var initialNumColumns = 112;
		
		grid = new NoteGrid("grid", initialNumColumns, instrumentsList);
		var notegridstring = grid.serialize();
		expect(notegridstring).toBe("<?xml version=\"1.0\"?><noteData tempo=\"80\"></noteData>");
		
	});
	
	it ("serializes empty grid to submit to Lilypond", function() {	
		var instrumentsList = [
				new Instrument("piano", "images/piano.png", "red"),
				new Instrument("guitar", "images/guitar.png", "orange"),
				new Instrument("drum", "images/drum.png", "green"),
				new Instrument("trumpet", "images/trumpet.png", "yellow"),
				new Instrument("violin", "images/violin.png","purple")];
		
		var initialNumColumns = 112;
		
		grid = new NoteGrid("grid", initialNumColumns, instrumentsList);
		var notegridstring = grid.serializeForLilypond();
		expect(notegridstring).toBe("<?xml version=\"1.0\"?><noteData></noteData>");
		
	});
});*/