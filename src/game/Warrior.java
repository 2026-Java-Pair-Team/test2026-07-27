package game;

public class Warrior extends Character {
	public Warrior(String name) {
		super(name, 150, 40);
	}

	@Override
	public void info() {
		System.out.println("【" + name + "】");
		System.out.println(" 職業	:戦士");
		System.out.println(" HP	:" + hp);
		System.out.println(" 攻撃力	:" + attack);
	}

	@Override
	public String getJob() {
		return "戦士";
	}

	@Override
	public void attack(Monster monster) {
		System.out.println(name + "が剣で攻撃した！");
		monster.takeDamage(attack);
	}

}
