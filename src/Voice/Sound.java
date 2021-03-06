package Voice;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.darkprograms.speech.synthesiser.SynthesiserV2;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 * This is where all begins .
 * 
 * @author GOXR3PLUS
 *
 */
public class Sound {
	
	//Create a Synthesizer instance
	private SynthesiserV2 synthesizer = new SynthesiserV2("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
	private boolean b = true;
	private JFrame parentFrame;
	
	public Sound(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}
	/**
	 * Calls the MaryTTS to say the given text
	 * 
	 * @param text
	 */
	public boolean speak(String text, String languageCode) {
		synthesizer.setLanguage(languageCode);
		//Create a new Thread because JLayer is running on the current Thread and will make the application to lag
		Thread thread = new Thread(() -> {
			try {
				//Create a JLayer instance
				AdvancedPlayer player = new AdvancedPlayer(synthesizer.getMP3Data(text));
				player.play();
			} catch (IOException | JavaLayerException e) {
				e.printStackTrace(); //Print the exception ( we want to know , not hide below our finger , like many developers do...)
				JOptionPane.showMessageDialog(parentFrame, "An Error has occured !", "Error !",
						JOptionPane.ERROR_MESSAGE);
				b = false;
			}

		});
		
		//We don't want the application to terminate before this Thread terminates
		thread.setDaemon(false);
		
		//Start the Thread
		thread.start();
		return b;
	}	
}
