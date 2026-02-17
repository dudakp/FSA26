package sk.dudak.fsagame.ability;

public class AbilityNotLearnedException extends Exception {
    public final AbilityId abilityId;

    public AbilityNotLearnedException(AbilityId abilityId) {
        this.abilityId = abilityId;
    }
}
