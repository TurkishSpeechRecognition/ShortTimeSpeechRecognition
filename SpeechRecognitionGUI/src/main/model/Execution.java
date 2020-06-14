package main.model;

import java.time.LocalDate;

import main.model.enumeration.CommandType;
import main.model.enumeration.ExecutionResult;

public class Execution {

	private LocalDate date;
	private CommandType commandType;
	private ExecutionResult executionResult;

	private boolean executed;

	public Execution(CommandType commandType) {
		this.date = LocalDate.now();
		this.commandType = commandType;
		this.executionResult = ExecutionResult.NOT_STARTED;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public CommandType getCommandType() {
		return commandType;
	}

	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}

	public boolean isExecuted() {
		return executed;
	}

	public void setExecuted(boolean executed) {
		this.executed = executed;
	}

	public ExecutionResult getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(ExecutionResult executionResult) {
		this.executionResult = executionResult;
	}

	@Override
	public String toString() {
		return commandType + " " + date.toString() + " Result : " + executionResult.toString();
	}

	
}