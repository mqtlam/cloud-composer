/**
 * SimpleFTPClient.java
 * This is a file our group found on the Internet that allows the CloudMidiPlayer
 * to connect to the FTP server to write the MIDI file so that
 * the client can then download the file to his or her computer.
 * It has been modified to write Sequences instead of uploading files.
 */
package CloudComposerGroup.CloudComposer;

import java.net.*;
import java.io.*;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;

public class SimpleFTPClient
{

	/** The URL connection object */
	private URLConnection m_client;

	/** The FTP host/server to be connected */
	private String host;

	/** The FTP user */
	private String user;

	/** The FTP userÕs password */
	private String password;

	/** The remote file that needs to be uploaded or downloaded */
	private String remoteFile;

	/** The previous error message triggered after a method is called */
	private String erMesg;

	/** The previous success message after any method is called */
	private String succMesg;

	public SimpleFTPClient(){}

	/** Setter method for the FTP host/server */
	public void setHost (String host)
	{
		this.host = host;
	}

	/** Setter method for the FTP user */
	public void setUser (String user)
	{
		this.user = user;
	}

	/** Setter method for the FTP userÕs password */
	public void setPassword (String p)
	{
		this.password = p;
	}

	/** Setter method for the remote file, this must include the sub-directory path relative
   to the userÕs home directory, e.g youÕe going to download a file that is within a sub directory
   called ÒsdirÓ, and the file is named Òd.txtÓ, so you shall include the path as Òsdir/d.txtÓ
	 */
	public void setRemoteFile (String d)
	{
		this.remoteFile = d;
	}

	/** The method that returns the last message of success of any method call */
	public synchronized String getLastSuccessMessage()
	{
		if (succMesg==null ) return ""; return succMesg;
	}

	/** The method that returns the last message of error resulted from any exception of any method call */
	public synchronized String getLastErrorMessage()
	{
		if (erMesg==null ) return ""; return erMesg;
	}

	/**
	 * Uploads the provided sequence to the file provided.
	 * @param seq, the Sequence data of the song
	 * @param location, the file name to be written
	 * @return A value to indicate success
	 */
	public synchronized boolean uploadSequence (Sequence seq, String location)
	{
		try{
			OutputStream os =m_client.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			MidiSystem.write(seq, 0, bos);

			bos.close();

			this.succMesg = "Uploaded!";

			os.close();

			return true;
		}
		catch(Exception ex)
		{
			StringWriter sw0= new StringWriter ();
			PrintWriter p0= new PrintWriter ( sw0, true );
			ex.printStackTrace ( p0 );
			erMesg = sw0.getBuffer().toString ();

			return false;
		}
	}

	/** The method to download a file and save it onto the local drive of the client in the specified absolut path
   @param localfilename Ð the local absolute file name that the file needs to be saved as */
	public synchronized boolean downloadFile (String localfilename)
	{
		try{
			InputStream is = m_client.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			OutputStream os = new FileOutputStream(localfilename);
			BufferedOutputStream bos = new BufferedOutputStream(os);

			byte[] buffer = new byte[1024];
			int readCount;

			while( (readCount = bis.read(buffer)) > 0)
			{
				bos.write(buffer, 0, readCount);
			}
			bos.close();
			is.close (); // close the FTP inputstream
			this.succMesg = "Downloaded!";

			return true;
		}catch(Exception ex)
		{
			StringWriter sw0= new StringWriter ();
			PrintWriter p0= new PrintWriter ( sw0, true );
			ex.printStackTrace ( p0 );
			erMesg = sw0.getBuffer().toString ();

			return false;
		}
	}


	/** The method that connects to the remote FTP server */
	public synchronized boolean connect()
	{
		try{

			URL url = new URL("ftp://"+user+":"+password+"@"+host+":21/"+remoteFile+";type=i");
			m_client = url.openConnection();

			return true;

		}
		catch(Exception ex)
		{
			StringWriter sw0= new StringWriter ();
			PrintWriter p0= new PrintWriter ( sw0, true );
			ex.printStackTrace ( p0 );
			erMesg = sw0.getBuffer().toString ();
			return false;
		}
	}


}