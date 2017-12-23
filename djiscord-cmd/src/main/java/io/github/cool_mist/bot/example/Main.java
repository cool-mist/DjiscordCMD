package io.github.cool_mist.bot.example;

import io.github.cool_mist.bot.DCMD;

public class Main {
	public static void main(String args[]) throws InterruptedException {	
		DCMD.register(MyBot.class);
		DCMD.start();
	}
}