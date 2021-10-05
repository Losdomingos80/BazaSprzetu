package com.czarnacki.bazasprzetu;

import java.awt.EventQueue;

// https://jdk.java.net/java-se-ri/16

public class Start {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Baza Sprzêtu by Dominik Czarnacki 2021");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new OknoLogin();
			}
		});
	}

}
