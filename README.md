# DjiscordCMD
APIs to rapidly develop Discord bot responding to text commands in the channel

## Adding dependency
WIP
### Maven
WIP

## Usage
Use the annotation `@CommandBot` on any class or interface to define a bot and use the annotation `TextCommand` on any methods with both `public` and `static` modifiers to define response for a command.

```
@CommandBot(name = "Simple", prefix = "m!", token = "src/main/resources/token")
public interface MyBot{
    @TextCommand(command = "echo")
    public static String echo(String[] message) {
        return "echo";
    }
}
```

Use the BotManager `DCMD` to register bots and start
```
public static void main(String args[]) throws InterruptedException {	
    DCMD.register(MyBot.class);
    DCMD.start();
}
```
 

## Examples
Checkout [examples](https://github.com/cool-mist/DjiscordCMD/tree/master/djiscord-cmd/src/main/java/io/github/cool_mist/bot/example) 

