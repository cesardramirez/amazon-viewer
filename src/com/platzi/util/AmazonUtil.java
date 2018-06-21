package com.platzi.util;

import java.util.Scanner;

public class AmazonUtil {
	public static int validateUserResponseOptionMenu(int oMin, int oMax, String message) {
		Scanner sc = new Scanner(System.in);
		int responseOption;
		
		do {
			System.out.print(message);
			while (!sc.hasNextInt()) {
				System.out.println("No es un número!");
				System.out.print(message);
				sc.next(); // this is important!
			}
			responseOption = sc.nextInt();
			if (!(responseOption >= oMin && responseOption <= oMax)) System.out.println("Opción inválida !!");
		} while (responseOption < oMin || responseOption > oMax);
		
		return responseOption;
	}
}