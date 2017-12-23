package io.github.cool_mist.bot.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotate a type with {@link CommandBot} so that its methods will be registered as commands with the library
 */

@Retention(RUNTIME)
@Target(TYPE)
public @interface CommandBot {
	
	/**
	 * Name of this bot
	 */
	String name()   default "DJiscordCMD";
	
	/**
	 * Description of this bot 
	 */
	String help()   default "Command Manager Bot";
	
	/**
	 * The prefix defining the commands this bot will serve 
	 */
	String prefix() default "!";
	
	/**
	 * The complete location of the token file for this bot 
	 */
	String token();
}
