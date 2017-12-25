package io.github.cool_mist.bot.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.cool_mist.bot.annotations.CommandBot;
import io.github.cool_mist.bot.annotations.TextCommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

// TODO : Refactor for readability

public class CommandBotImpl{

	public static final Logger LOGGER = LoggerFactory.getLogger(CommandBotImpl.class);

	Class<?>    botClass;
	CommandBot  bot;
	String      name;
	String      token;
	String      help;
	String      prefix;
	JDA         jda;
	JDABuilder  botBuilder;
	Map<String, Method> registeredCommands;
	
	
	public CommandBotImpl(Class<?> botClass ) {
		
		this.botClass = botClass;
		this.bot      = botClass.getAnnotationsByType(CommandBot.class)[0];
		this.name     = bot.name();
		this.help     = bot.help();
 		this.token    = bot.token();
 		this.prefix   = bot.prefix();
 		
 		this.botBuilder = new JDABuilder(AccountType.BOT);
 		initializeListeners();
 		initializeToken();
 		
 		LOGGER.info("Registered a command bot with name {} and command prefix {}", name, prefix);
	}

	public void start() {

		if(registeredCommands.isEmpty()) {
			LOGGER.warn("No commands are registered");
			return;
		}		
		try {
			jda = botBuilder.buildBlocking();
		} catch (Exception e) {
			LOGGER.error("Failed to start bot {}, reason {}", name, e);
		}
	}

	public void stop() {
		if(jda != null) {
			jda.shutdown();
		}
	}

	private void initializeToken() {
		try {
			String tokenFromFile = getTokenFromFile(token);
			botBuilder.setToken(tokenFromFile);
		} catch(IOException ioe) {
			LOGGER.error("Bot {} failed to start, could not read token from classpath:{}",name, token );
		}
	}
	
	private String getTokenFromFile(String token) throws IOException {
		return IOUtils.toString(new FileInputStream(token), "UTF-8");
	}
	
	private void initializeListeners() {
		
		registeredCommands = new HashMap<>();
		
		Method[] methods = botClass.getDeclaredMethods();
		for(Method m : methods) {
			if(m.isAnnotationPresent(TextCommand.class)) {
				if(Modifier.isPublic(m.getModifiers()) && Modifier.isStatic(m.getModifiers())) {
					String[] commandsToRespond = m.getAnnotation(TextCommand.class).command();
					for(String command: commandsToRespond) {
						registeredCommands.put(command,m);
						LOGGER.info("Registered text command {}, for bot {}", command, name);
					}
				}else {
					LOGGER.warn("TextCommand {} not registered, make sure method is public and static");
				}
			}
		}
		
		botBuilder.addEventListener(new ListenerAdapter() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				String message = event.getMessage().getContentRaw();
				if(message == null || !message.startsWith(prefix)) {
					return;
				}
				message = StringUtils.substringAfter(message, prefix);
				
				final String[] commandArgs = StringUtils.split(message, " ");
				// Match a command.
				for(String command : registeredCommands.keySet()) {
					if(StringUtils.equalsAnyIgnoreCase(command, commandArgs[0])) {
						LOGGER.debug("Matched command : {} for message {}",command, message);
						// execute with String[] args and get response
						try {
							Object response = registeredCommands.get(command).invoke(null, new Object[] {commandArgs});
							
							// queue the response
							if(response instanceof String) {
								event.getChannel().sendMessage((String) response).queue((t) -> LOGGER.debug("Sent message : {}", t));	}
						} catch (Exception e) {
							LOGGER.error("Exception calling command {} : {}", command, e);
						}
					}
				}
			}
		});
	}
}
