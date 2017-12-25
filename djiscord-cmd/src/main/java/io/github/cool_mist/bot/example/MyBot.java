package io.github.cool_mist.bot.example;

import java.util.Random;

import io.github.cool_mist.bot.annotations.CommandBot;
import io.github.cool_mist.bot.annotations.TextCommand;

/**
 * Example bot using DJiscordCMD
 */

@CommandBot(name = "Simple", prefix = "m!", token = "src/main/resources/token")
public interface MyBot{
	
	/**
	 * Echo command
	 */
	@TextCommand(command = "echo")
	public static String echo(String[] message) {
		return "echo";
	}
	
	
	/**
	 * Rolls a random number between 1 and provided number (or 100 by default)
	 */
	@TextCommand(command = { "roll", "random" })
	public static String roll(String[] message){
		int max = 100;
		if(message.length > 1) {
			try {
				max = Integer.parseInt(message[1]);
			} catch(Exception e) {
				max = 100;
			}
		}
		StringBuilder builder = new StringBuilder("Rolling 1-").append(max).append(" : ");
		int randNum = new Random().nextInt(max) + 1;
		builder.append(randNum);
		
		return builder.toString();
	}
}
