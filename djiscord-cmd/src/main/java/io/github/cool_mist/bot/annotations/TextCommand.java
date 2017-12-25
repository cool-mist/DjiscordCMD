package io.github.cool_mist.bot.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Annotate a method with {@link TextCommand} so that these methods can be registered as commands.
 * Note, the method should be public and static
 */

@Retention(RUNTIME)
@Target(METHOD)
public @interface TextCommand {

	/**
	 * The first string which will be used to match this command.
	 * Eg: if command is 'echo', commands matching 'echo' will receive callback to the annotated method
	 */
	String[] command();
	
	
	/**
	 * The description of this command. Include usage details.
	 */
	String help() default "";
}
