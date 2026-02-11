package sk.dudak.fsagame.character;

import java.util.logging.Logger;

import static sk.dudak.fsagame.character.CharacterState.DEAD;

public abstract class Character implements CanDie, CanUseAbility {

    private static final Logger LOG = Logger.getLogger(Character.class.getName());

    private final CharacterId<Long> id;

    protected int health = 20;

    private CharacterState state = CharacterState.ALIVE;

    public Character(CharacterIdGenerator<Long> idGenerator, int health) {
        this.id = idGenerator.getNext();
        this.health = health;
    }

    public void applyDamage(int damage) {
        health -= damage;
        if (damage < 0) {
            LOG.info("healed for %s to character %s, health now at: %d".formatted(-damage, id, health));
        } else {
            LOG.info("applied %s damage to character %s, health now at: %d".formatted(damage, id, health));
        }
        state = state.resolve(this);
        if (DEAD.equals(state)) {
            onCharacterDied();
        }
    }

    public void applyDoT(int damage, int repetitions) {
        for (int i = 0; i < repetitions; i++) {
            applyDamage(damage);
        }
    }

    public Integer getHealth() {
        return health;
    }

    public CharacterId<Long> getId() {
        return id;
    }

    public CharacterState getState() {
        return state;
    }
}
