package game;

import java.util.Random;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();
		int partySize = 0;

		System.out.println("============================");
		System.out.println("	冒険をはじめる");
		System.out.println("============================");
		System.out.println("[仲間の人数を入力してください]");

		while (true) {
			try {
				System.out.print("▶ ︎");
				partySize = scanner.nextInt();
				if (partySize > 0) {
					break;
				}
				System.out.println(">> 1以上を入力してください\n");
			} catch (Exception e) {
				System.out.println(">> 数字を入力してください\n");
				scanner.nextLine();
			}
		}

		Character[] party = new Character[partySize];
		for (int i = 0; i < partySize; i++) {
			scanner.nextLine();
			System.out.println("\n【" + (i + 1) + "人目】");
			System.out.print(" 名前 ▶ ");

			String name = scanner.nextLine();
			int job = 0;
			while (true) {
				try {
					System.out.println("[職業を選択してください]");
					System.out.println(" 1. 戦士");
					System.out.println(" 2. 魔法使い");
					System.out.println(" 3. 盗賊");
					System.out.print("▶ ");

					job = scanner.nextInt();

					if (job >= 1 && job <= 3) {
						break;
					}

					System.out.println(">> 1〜3を入力してください\n");
				} catch (Exception e) {
					System.out.println(">> 数字を入力してください\n");
					scanner.nextLine();
				}
			}
			switch (job) {
			case 1:
				party[i] = new Warrior(name);
				break;
			case 2:
				party[i] = new Wizard(name);
				break;
			case 3:
				party[i] = new Thief(name);
				break;
			}
		}

		System.out.println("\n============================");
		System.out.println("	  パーティー");
		System.out.println("============================");

		for (Character member : party) {
			member.info();
			System.out.println();
		}

		//		System.out.println("============================");
		//		System.out.println("      出撃メンバーを選択");
		//		System.out.println("============================");
		System.out.println("[出撃メンバーを選択してください]");

		int select = 0;
		while (true) {
			try {
				for (int i = 0; i < party.length; i++) {
					System.out.println(" " + (i + 1) + ". " + party[i].getName() + "【" + party[i].getJob() + "】");
				}
				System.out.print("▶ ︎");
				select = scanner.nextInt();
				if (select >= 1 && select <= party.length) {
					break;
				}
				System.out.println(">> 正しい番号を入力してください\n");
			} catch (Exception e) {
				System.out.println(">> 数字を入力してください\n");
				scanner.nextLine();
			}
		}
		Character player = party[select - 1];

		System.out.println("\n============================");
		System.out.println("	 出撃メンバー");
		System.out.println("============================");

		player.info();

		// モンスター名前追加したいのいたらどうぞ
		int playerMaxHp = player.getHp();
		String[] monsterNames = new String[] { "スライム", "ゴブリン", "オーク", "ドラゴン", "エルフ" };
		Monster monster = new Monster(monsterNames[random.nextInt(monsterNames.length)], random.nextInt(100) + 50,
				random.nextInt(30) + 10);

		int monsterMaxHp = monster.getHp();

		System.out.println("\n============================");
		System.out.println("      モンスターが現れた！");
		System.out.println("============================");
		monster.info();

		System.out.println("\n============================");
		System.out.println("	 戦闘開始");
		System.out.println("============================");

		while (player.isAlive() && monster.isAlive()) {
			System.out.println("\n[行動を選択してください]");
			System.out.println(" 1. 攻撃");
			System.out.println(" 2. 逃げる");
			System.out.print("▶ ︎");

			int action = 0;
			while (true) {
				try {
					action = scanner.nextInt();
					if (action == 1 || action == 2) {
						break;
					}
					System.out.println(">> 正しい番号を入力してください\n");
				} catch (Exception e) {
					System.out.println(">> 数字を入力してください\n");
					scanner.nextLine();
				}
			}

			if (action == 1) {
				System.out.println("\n【" + player.getName() + "のターン】");
				player.attack(monster);
				if (monster.isAlive()) {
					System.out.println("\n【" + monster.getName() + "のターン】");
					monster.attack(player);

					//					System.out.println(" " + player.getName() + "の残りHP：" + player.getHp());
					System.out.println("\n-------------------------");
					showHpGauge(player.getName(), player.getHp(), playerMaxHp);
					showHpGauge(monster.getName(), monster.getHp(), monsterMaxHp);
					System.out.println("-------------------------");
				} else {
					System.out.println("\n " + monster.getName() + "を倒した！");
					System.out.println("\n============================");
					System.out.println("	  戦闘勝利");
					System.out.println("============================");
				}
			} else if (action == 2) {
				System.out.println("\n" + player.getName() + "は逃げ出した...");
				break;
			}

		}

		if (!player.isAlive()) {
			System.out.println("\n" + player.getName() + "は倒れた...");
		}

		scanner.close();
	}

	private static void showHpGauge(String name, int hp, int maxHp) {
    int gaugeLength = 10;

    int filledLength = hp * gaugeLength / maxHp;

    // 0～10の範囲から外れないようにする
    filledLength = Math.max(0, Math.min(gaugeLength, filledLength));

    StringBuilder gauge = new StringBuilder();

    for (int i = 0; i < gaugeLength; i++) {
        if (i < filledLength) {
            gauge.append("■");
        } else {
            gauge.append("□");
        }
    }

    System.out.println(
        name + " HP [" + gauge + "] " + hp + "/" + maxHp
    );
}
}
