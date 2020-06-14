package main.model;

import main.model.enumeration.CommandType;

public class Command {

	private CommandType commandType;

	public Command(CommandType commandType) {
		this.commandType = commandType;
	}

	public String getDescription() {
		return commandType.getDescription();
	}

	public boolean isTrackNumberRequired() {
		return commandType.isTrackNumberRequired();
	}

	@Override
	public String toString() {
		return commandType.name();
	}

}