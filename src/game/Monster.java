package game;

public class Monster {
	protected String name;
	protected int hp;
	protected int attack;

	public Monster(String name, int hp, int attack) {
		this.name = name;
		this.hp = hp;
		this.attack = attack;
	}

	public void info() {
		System.out.println("【" + name + "】");
		System.out.println(" HP	:" + hp);
		System.out.println(" 攻撃力	:" + attack);
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

    public void takeDamage(int damage){
        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }

        System.out.println(name + "は" + damage + "ダメージ受けた！");
    }

    public void attack(Character target){
        System.out.println(name + "の攻撃!");
        target.takeDamage(attack);
    }
    public boolean isAlive(){
        return hp > 0;
    }
}
