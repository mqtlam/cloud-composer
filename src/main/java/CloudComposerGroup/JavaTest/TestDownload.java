package JavaTest;

import java.io.File;
import java.io.IOException;

import javax.sound.midi.*;

import CloudComposer.*;
import CloudComposer.CloudMidiPlayer.SequenceInst;

public class TestDownload {
	
	public static void testDownload () throws InvalidMidiDataException, IOException {
		CloudAppletController app = new CloudAppletController();
		app.init();
		for (SequenceInst inst : CloudMidiPlayer.SequenceInst.values()) {
			for (int pitch = 0; pitch < CloudMidiPlayer.SCALE.length * CloudMidiPlayer.OCTAVES; pitch++) {
				int noteStart = inst.value * pitch;
				int[] noteData = {inst.value, pitch, noteStart, noteStart+1};
				app.addNote(noteData);
			}
		}
		Sequence song = app.getSongSequence();
		app.download("testDownload.midi");
		File f = new File("testDownload.midi");
		Sequence readSong = MidiSystem.getSequence(f);
		boolean failure = false;
		failure = song.getResolution() != readSong.getResolution();
		failure = song.getTickLength() != readSong.getTickLength();
		failure = song.getDivisionType() != readSong.getDivisionType();
		Track[] songTracks = song.getTracks();
		Track[] readTracks = readSong.getTracks();
		failure = songTracks.length != readTracks.length;
		for (int i = 0; i < songTracks.length; i++) {
			Track songT = songTracks[i];
			Track readT = readTracks[i];
			failure = songT.size() != readT.size();
			for (int j = 0; j < songT.size(); j++) {
				MidiEvent songEvent = songT.get(j);
				MidiEvent readEvent = readT.get(j);
				failure = songEvent.getTick() != readEvent.getTick();
				MidiMessage songMess = songEvent.getMessage();
				MidiMessage readMess = readEvent.getMessage();
				byte[] songByte = songMess.getMessage();
				byte[] readByte = readMess.getMessage();
				failure = songByte.length != readByte.length;
				for (int k = 0; k < songByte.length; k++) {
					failure = songByte[k] != readByte[k];
				}
			}
		}
		
		
	}
}
