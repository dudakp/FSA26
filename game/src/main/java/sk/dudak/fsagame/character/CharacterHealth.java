package sk.dudak.fsagame.character;

public class CharacterHealth {
    private int health;

    public CharacterHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return this.health;
    }

    public CharacterState dealDamage(int damage, Character character) {
        this.health -= damage;
        return character.getState().resolve(character);
    }
}
