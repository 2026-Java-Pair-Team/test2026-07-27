package game;

public abstract class Character {
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

	// 攻撃
	public abstract void attack(Monster monster);

	// ダメージを受ける
	public void takeDamage(int damage) {
		hp -= damage;
		if (hp < 0) {
			hp = 0;
		}
		System.out.println(" " + name + "は" + damage + "ダメージ受けた！");
	}

	// 生存判定
	public boolean isAlive() {
		return hp > 0;
	}

}
