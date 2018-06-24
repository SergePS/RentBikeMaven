package com.postnikov.epam.command;

import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionFactory {

	static Logger logger = LogManager.getLogger(ActionFactory.class);

	public static Optional<Command> defineCommand(String commandName) {

		Optional<Command> currentCommand = Optional.empty();

		if (commandName == null) {
			return currentCommand;
		}

		try {
			CommandType command = CommandType.valueOf(commandName.toUpperCase());
			currentCommand = Optional.of(command.getCommand());
		} catch (IllegalArgumentException e) {
			logger.log(Level.ERROR, "Illegal command");
		}

		return currentCommand;
	}

}
