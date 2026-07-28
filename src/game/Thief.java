package game;

public class Thief extends Character {
	private int speed;

	public Thief(String name) {
		super(name, 100, 30);
		speed = 80;
	}

	@Override
	public void info() {
		System.out.println("【" + name + "】");
		System.out.println(" 職業	:盗賊");
		System.out.println(" HP	:" + hp);
		System.out.println(" 攻撃力	:" + attack);
		System.out.println(" 素早さ	:" + speed);
	}

	@Override
	public String getJob() {
		return "盗賊";
	}

	@Override
	public void attack(Monster monster) {
		System.out.println(name + "が短剣で攻撃した！");
		monster.takeDamage(attack);
	}
}
