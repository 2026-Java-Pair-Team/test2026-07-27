package game;

public class Wizard extends Character {
	private int magic;

	public Wizard(String name) {
		super(name, 80, 20);
		magic = 70;
	}

	@Override
	public void info() {
		System.out.println("【" + name + "】");
		System.out.println(" 職業	:魔法使い");
		System.out.println(" HP	:" + hp);
		System.out.println(" 攻撃力	:" + attack);
		System.out.println(" 魔力	:" + magic);
	}

	@Override
	public String getJob() {
		return "魔法使い";
	}

}
