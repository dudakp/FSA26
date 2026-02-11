package sk.dudak.fsagame.character;

import sk.dudak.fsagame.ability.Ability;

import java.util.logging.Logger;

public enum CharacterState {
    ALIVE,
    DEAD;

    private static final Logger LOG = Logger.getLogger(CharacterState.class.getName());

    public CharacterState resolve(Character character) {
        Integer characterHealth = character.getHealth();
        if (characterHealth <= 0) {
            LOG.info("character is dead");
            return DEAD;
        }
        return ALIVE;
    }
}
