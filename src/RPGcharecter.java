import java.util.Random;
public class RPGcharecter {
    String name;
    int level;
    int maxHP;
    int currentHP;
    int maxMana;
    int currentMana;
    int runSpeed;
    int attack;
    int defense;
    Sword equippedSword;
    Shield equippedShield;


    public RPGcharecter(String name, int level, int maxHP, int maxMana, int runSpeed, int attack, int defense) {
        this.name = name;
        this.level = level;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.runSpeed = runSpeed;
        this.attack = attack;
        this.defense = defense;
    }

    public static RPGcharecter createRandomCharacter(String name, int level) {
        Random rand = new Random();
        int randommaxHP = rand.nextInt(50) + 100; // Random HP between 50 and 100
        int randommaxMana = rand.nextInt(20) + 40; // Random mana between 20 and 50
        int randomSpeed = rand.nextInt(5) + 10; // Random speed between 5 and 10
        int attack = 0;
        int defense = 0;
        return new RPGcharecter(name, level, randommaxHP, randommaxMana, randomSpeed, attack, defense);
    }

    public void levelUp() {
        level++;
        maxHP += 10;
        currentHP = maxHP;
        maxMana += 5;
        currentMana = maxMana;
        runSpeed += 1;

        System.out.println(name + " leveled up to level " + level + "!");
        updateRunSpeed();

    }

    public void equipSword(Sword sword) {
        equippedSword = sword;
        attack += sword.damage;
        updateRunSpeed();
        System.out.println(name + " have a sword equipped.");
    }

    public void equipShield(Shield shield) {
        equippedShield = shield;
        defense += shield.reducedamage;
        updateRunSpeed();
        System.out.println(name + " have a shield equipped.");
    }

    public void levelupSword() {
        if (equippedSword != null) {
            equippedSword.Levelup();
            attack += equippedSword.getLevel();
            System.out.println(name + "'s sword has leveled up to level " + equippedSword.getLevel() + "!");
            updateRunSpeed();
        } else {
            System.out.println(name + " does not have a sword equipped.");
        }
    }

    public void levelUpShield() {
        if (equippedShield != null) {
            equippedShield.Levelup();
            defense += equippedShield.getLevel();
            System.out.println(name + "'s shield has leveled up to level " + equippedShield.getLevel() + "!");
            updateRunSpeed();
        } else {
            System.out.println(name + " does not have a shield equipped.");
        }
    }

    public void attack(RPGcharecter target) {
        if (equippedSword != null) {
            System.out.println(name + " is attacking " + target.getName() + " with " + equippedSword.getName() + "!");
            int damage = calculateDamage();
            target.takeDamage(damage);
            //System.out.println(target.getName() + " took " + damage + " damage.");
        } else {
            System.out.println(name + " does not have a sword equipped. Cannot attack.");
        }
    }

    private int calculateDamage() {
        int baseDamage = attack;
        if (equippedSword != null) {
            baseDamage += equippedSword.getLevel(); // Consider the sword's level for additional damage
        }
        return baseDamage;
    }

    public void takeDamage(int damage) {
        int effectiveDefense = defense;
        if (equippedShield != null) {
            effectiveDefense += equippedShield.getLevel(); // Consider the shield's level for additional defense
        }

        int actualDamage = Math.max(0, damage - effectiveDefense);
        currentHP -= actualDamage;

        if (currentHP < 0) {
            currentHP = 0;
        }

        System.out.println(name + " took " + actualDamage + " damage. Current HP: " + currentHP);
    }

    public void updateRunSpeed() {
        int swordRunSpeedEffect = (equippedSword != null) ? equippedSword.getRunSpeedEffect() : 0;
        int shieldRunSpeedEffect = (equippedShield != null) ? equippedShield.getRunSpeedEffect() : 0;
        runSpeed = Math.max(0, 10 - swordRunSpeedEffect - shieldRunSpeedEffect);
    }

    public void showStats() {
        System.out.println("Character Stats for " + name + ":");
        System.out.println("Level: " + level);
        System.out.println("HP: " + currentHP + "/" + maxHP);
        System.out.println("Mana: " + currentMana + "/" + maxMana);
        System.out.println("Attack: " + attack);
        System.out.println("Defense: " + defense);
        System.out.println("Run Speed: " + runSpeed);
        System.out.println("Equipped Sword: " + ((equippedSword != null) ? equippedSword.getName() : "None"));
        System.out.println("Equipped Shield: " + ((equippedShield != null) ? equippedShield.getName() : "None"));
        System.out.println("-----------------------------");
    }
    public String getName(){
        return  name ;
    }

    public static class RPGmain {
        public static void main(String[] args) {
            Sword playerSword = new Sword("Great Sword", 1, 12,1);
            Shield playerShield = new Shield("Steel Shield", 1, 5,3);

            //create player
            RPGcharecter randomCharacter = RPGcharecter.createRandomCharacter("RandomPlayer", 1);

            //create enemy
            RPGcharecter enemy = new RPGcharecter("Enemy1", 1, 80, 40, 10, 15, 10);

            //show stats player and enemy
            randomCharacter.showStats();
            enemy.showStats();

            //equip sword and shield
            randomCharacter.equipSword(playerSword);
            randomCharacter.equipShield(playerShield);

            //show stats player and enemy
            randomCharacter.showStats();
            enemy.showStats();

            //action attack
            randomCharacter.attack(enemy);
            enemy.attack(randomCharacter) ;

            //chatacter levelup
            randomCharacter.levelUp();

            //show stats player and enemy
            randomCharacter.showStats();
            enemy.showStats();

            //action attack
            randomCharacter.attack(enemy);
            enemy.equipSword(playerSword);
            enemy.attack(randomCharacter);

            //upgate sword and shield
            enemy.levelupSword();
            randomCharacter.levelUpShield();

            //action attack
            randomCharacter.attack(enemy);
            enemy.attack(randomCharacter) ;


            randomCharacter.showStats();
            enemy.showStats();



        }

    }
}
class Sword {
    String name;
    int level;
    int runSpeedEffect;
    int damage ;
    public Sword(String name, int level,int damage, int runSpeedEffect) {
        this.name = name;
        this.level = level;
        this.damage = damage ;
        this.runSpeedEffect = runSpeedEffect;
    }

    public int Levelup() {
        level++;
        damage += 2 ;
        //System.out.println(  " Sword leveled up to level " + level + "!");
        return level ;
    }
    public int getLevel() {
        return level;
    }
    public int getRunSpeedEffect() {
        return runSpeedEffect;
    }
    public  String getName(){
        return name ;
    }
}

class Shield {
    String name;
    int level;
    int runSpeedEffect;
    int reducedamage ;

    public Shield(String name, int level,int reducedamage, int runSpeedEffect) {
        this.name = name;
        this.level = level;
        this.reducedamage = reducedamage ;
        this.runSpeedEffect = runSpeedEffect;
    }
    public int Levelup() {
        level++;
        reducedamage += 2 ;
        //System.out.println(  " Shield leveled up to level " + level + "!");
        return level ;
    }
    public int getLevel() {
        return level;
    }

    public int getRunSpeedEffect() {
        return runSpeedEffect;
    }
    public  String getName(){
        return name ;
    }
}
