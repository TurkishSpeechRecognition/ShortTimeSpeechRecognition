package main.model.enumeration;

public enum MicrophoneStatus {

	HEALTHY("Healthy", 25),
	TOO_LOW("Too Low", 0),
	NO_MIC("No Microphone", -1);

	private String description;
	private int minValue;

	private MicrophoneStatus(String description, int minValue) {
		this.description = description;
		this.minValue = minValue;
	}

	public String getDescription() {
		return description;
	}

	public int getMinValue() {
		return minValue;
	}

	public static MicrophoneStatus getByValue(int value) {
		if (value == -1)
			return NO_MIC;
		else if (value <= 25)
			return TOO_LOW;

		return HEALTHY;
	}
}