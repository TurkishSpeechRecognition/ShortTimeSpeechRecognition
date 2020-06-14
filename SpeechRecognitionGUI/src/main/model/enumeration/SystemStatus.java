package main.model.enumeration;

public enum SystemStatus {

	// "IDLE", "LISTENING", "EXECUTING", "ERROR"
	IDLE("Idle"),
	LISTENING("Listening"),
	EXECUTING("Executing..."),
	FAIL("ERROR");

	private String description;

	private SystemStatus(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
