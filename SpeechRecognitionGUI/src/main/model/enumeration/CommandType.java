package main.model.enumeration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public enum CommandType {

	Track_Info("Shows the information about Track with expressed ID.", true),
	Track_Filter("Filters Tracks according to environment or identity(Hostile, Friend etc.) information.", true),
	Coverage_Area("Displays the warfare zone of wepaons and sensors of ownship.", false);

	private String description;
	private boolean trackNumberRequired;

	private CommandType(String desc) {
		this(desc, false);
	}

	private CommandType(String description, boolean needTrackNumber) {
		this.description = description;
		this.trackNumberRequired = needTrackNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public boolean isTrackNumberRequired() {
		return this.trackNumberRequired;
	}

	public static ObservableList<CommandType> asObservableArrayList() {
		return FXCollections.observableArrayList(Track_Filter, Track_Info, Coverage_Area);
	}

	@Override
	public String toString() {
		return name().replace('_', ' ');
	}

	
}