package sk.dudak.fsagame.ability;

public class AbilityCannotBeLearnedException extends Exception {
    public final AbilityId abilityId;

    public AbilityCannotBeLearnedException(AbilityId ability) {
        abilityId = ability;
        super("ability %s cannot be learned".formatted(ability));
    }
}
