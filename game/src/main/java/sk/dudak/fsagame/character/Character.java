package sk.dudak.fsagame.character;

public interface Character extends CanDie, CanUseAbility {

    Integer getHealth();

    CharacterId<Long> getId();

    CharacterState getState();

    void dealDamage(int damage);
}
