package game;

public class Character {
	protected String name;
	protected int hp;
	protected int attack;

	public Character(String name, int hp, int attack) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
	}

	public void info() {
		System.out.println("【" + name + "】");
		System.out.println(" HP	:" + hp);
		System.out.println(" 攻撃力	:" + attack);
	}

	public String getJob() {
		return "冒険者";
	}

	public String getName() {
		return name;
	}

	public int getHp() {
		return hp;
	}

	public int getAttack() {
		return attack;
	}

}
