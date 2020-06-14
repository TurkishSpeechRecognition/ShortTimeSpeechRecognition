package main.view;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import main.MainApp;
import main.model.Execution;
import main.model.enumeration.CommandType;
import main.model.enumeration.MicrophoneStatus;
import main.model.enumeration.SystemStatus;
import main.util.SoundRecorder;

public class MainScreenController implements Initializable {

	private SoundRecorder recorder = SoundRecorder.getInstance();
	private Thread thread;
	private boolean isRecording = false;

	@FXML
	private ListView<CommandType> commandListView;

	@FXML
	private TextArea commandDetailArea;

	@FXML
	private ListView<Execution> executionListView;

	@FXML
	private Label ctrlLabel;

	@FXML
	private Label microphoneStatusLabel;

	@FXML
	private Slider microphoneVolSlider;

	@FXML
	private ImageView soundWaveView;

	@FXML
	private Button lastCommandButton;

	@FXML
	private Label statusLabel;

	@FXML
	private Label listeningStatus;

	@FXML
	private Label executingStatus;

	@FXML
	private Label execResultStatus;

	private MainApp mainApp;

	@FXML
	void onActionLastCommandButton(ActionEvent event) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("./wavfiles/command.wav")));
			clip.start();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}

	@FXML
	void onCommandSelected(MouseEvent event) {
		displaySelectedCommandInfo(commandListView.getSelectionModel().getSelectedItem());
	}

	@FXML
	void onKeyPressed(KeyEvent event) {
		System.out.println(event.getCode());
		if (event.getCode() == KeyCode.CONTROL) {
			System.out.println("Ctrl is pressed!");
			if (isRecording) {
				System.out.println("Thread is alive. Finishing job...");
				recorder.finish();
				displayListeningStatus(0);
				isRecording = false;
				return;
			}
			thread = getThread();
			thread.start();
			recorder.start();
			isRecording = true;
			displayCurrentStatus(SystemStatus.LISTENING);
			displaySoundWave(true);
		} else if (event.getCode() == KeyCode.ESCAPE) {
			displaySelectedCommandInfo(null);
		}
	}

	@FXML
	private void onKeyPressedCommandList(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			displaySelectedCommandInfo(null);
		}
	}

	@FXML
	void onKeyReleased(KeyEvent event) {
		if (event.getCode() == KeyCode.CONTROL) {
			System.out.println("Ctrl is released!");
			if (thread != null && thread.isAlive()) {
				recorder.finish();
				displayCurrentStatus(SystemStatus.IDLE);
				displayListeningStatus(0);
				displaySoundWave(false);
				isRecording = false;
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initilazing!");

		ObservableList<CommandType> commandList = CommandType.asObservableArrayList();
		commandListView.setItems(commandList);
		commandListView.getSelectionModel().clearSelection();

		displayCurrentStatus(SystemStatus.IDLE);
		int micVol = SoundRecorder.getInstance().getMicrophoneVolume();
		displayMicVol(micVol, true);
		microphoneVolSlider.valueProperty().addListener(listener -> {
			SoundRecorder.getInstance().setMicrophoneVolume((int) microphoneVolSlider.getValue());
			displayMicVol((int) microphoneVolSlider.getValue());
		});
	}

	private Thread getThread() {
		return new Thread(new Runnable() {

			@Override
			public void run() {
				int seconds = (int) (SoundRecorder.RECORD_TIME / 1000);
				for (int i = seconds; i > 0; i--) {
					try {
						System.out.println("Remaining time : " + i + " secs");
						displayListeningStatus(i);
						Thread.sleep(1000);
					} catch (InterruptedException ex) {
						ex.printStackTrace();
					}
					if (!isRecording)
						break;
				}
				if (isRecording) {
					recorder.finish();
					displayListeningStatus(0);
					displaySoundWave(false);
				} else {
					System.out.println("Already stopped Recording!");
				}
			}
		});
	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		executionListView.setItems(mainApp.getExecutionList());
		executionListView.getSelectionModel().clearSelection();
	}

	// Display methods
	private void displayMicVol(int micVol) {
		displayMicVol(micVol, false);
	}

	private void displayMicVol(int micVol, boolean atInit) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (atInit)
						microphoneVolSlider.setValue(Math.min(100, Math.max(micVol, 0)));
					microphoneStatusLabel.setText(MicrophoneStatus.getByValue(micVol).getDescription());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void displaySoundWave(boolean isRunning) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					soundWaveView.setImage(new Image(
							getClass().getResourceAsStream(isRunning ? "img/soundwave3.gif" : "img/soundwave3.png")));
				} catch (Exception e) {
					e.printStackTrace();
					soundWaveView.setImage(new Image(getClass().getResourceAsStream("img/soundwave3.png")));
				}
			}
		});
	}

	private void displayCurrentStatus(SystemStatus systemStatus) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					statusLabel.setText(systemStatus.getDescription());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void displayListeningStatus(int seconds) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				try {
					if (seconds == 0) {
						listeningStatus.setText("6-5-4-3-2-1-0");
						listeningStatus.setFont(new Font(12));
					} else {
						listeningStatus.setText(seconds + " sec remaining!");
						listeningStatus.setFont(new Font(15));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void displaySelectedCommandInfo(CommandType command) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (command == null) {
					commandListView.getSelectionModel().clearSelection();
					commandDetailArea.clear();
				} else {
					commandDetailArea.setText(command.getDescription());	
				}
			}
		});
	}
}