# DjiscordCMD
Rapidly develop Discord bot responding to text commands in the channel using JAVA. This project internally uses [JDA](https://github.com/DV8FromTheWorld/JDA)
![Demo](https://media.giphy.com/media/l49JC3dwB4uoo73lm/giphy.gif)

## Adding dependency (Maven)

### Release version
Add the following repository definition to your `pom.xml`
```
...
  <repository>
    <id>djiscord-repo</id>
    <url>https://raw.githubusercontent.com/cool-mist/DjiscordCMD/repository/repository</url>
  </repository>
...
```

Add the following dependency
```
...
  <dependency>
    <groupId>io.github.cool-mist</groupId>
    <artifactId>djiscord-cmd</artifactId>
    <version>VERSION</version>
  </dependency>
...
```
Get the `VERSION` from releases page. (Eg : 1.0)

### Latest development version
Clone the project and do `mvn clean install`, Afterwards, add the following dependency in your pom.xml
```
...
  <dependency>
    <groupId>io.github.cool-mist</groupId>
    <artifactId>djiscord-cmd</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </dependency>
...
```



## Usage
Use the annotation `@CommandBot` on any class or interface to define a bot and use the annotation `TextCommand` on any methods with both `public` and `static` modifiers to define response for a command.

```
@CommandBot(name = "Simple", prefix = "m!", token = "src/main/resources/token")
public interface MyBot{
    @TextCommand(command = "echo")
    public static String echo(String[] message) {
        return "echo";
    }
    
     @TextCommand(command = {"roll", "random"})
    public static String echo(String[] message) {
       ...
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

