package main.model.enumeration;

public enum ExecutionResult {

	NOT_STARTED("Not Started"),
	EXECUTING("Executing"),
	SUCCESS("Success"),
	FAIL("Failure");

	private String description;

	private ExecutionResult(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

}
