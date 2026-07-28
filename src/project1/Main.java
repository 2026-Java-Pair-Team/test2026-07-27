package project1;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		NumberGame numGame = new NumberGame();
		Scanner scanner = new Scanner(System.in);
		System.out.println("偶数奇数当てマンです。");
		
		num = scanner.nextInt();
		numGame.judge(num);
	}
}
