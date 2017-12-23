package io.github.cool_mist.bot;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cool_mist.bot.annotations.CommandBot;
import io.github.cool_mist.bot.impl.CommandBotImpl;


/**
 * Singleton Command Bots Library.
 * TODO: Automatic registration
 */
public enum DCMD {
	INSTANCE;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(DCMD.class);
	public static Set<CommandBotImpl> commandBots = new HashSet<>();
	
	/**
	 * Register this class with this library.
	 * @param botClass
	 */
	public static void register(Class<?> botClass) {
		
		if(botClass.isAnnotationPresent(CommandBot.class)) {
			CommandBotImpl commandAwareBot = new CommandBotImpl(botClass);
	 		commandBots.add(commandAwareBot);
		}else {
			LOGGER.warn("Class {} is not a bot !!! ", botClass);
		}
	}

	/**
	 * Will try to login and start all registered bots
	 */
	public static void start() {
		for(CommandBotImpl bot : commandBots) {
			bot.start();
		}
	}
	
	/**
	 * Will try to logout all registered bots (if they were started)
	 */
	public static void stop() {
		for(CommandBotImpl bot : commandBots) {
			bot.stop();
		}
	}
}
