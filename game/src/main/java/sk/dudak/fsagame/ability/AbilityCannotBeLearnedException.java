package sk.dudak.fsagame.ability;

public class AbilityCannotBeLearnedException extends Exception {
    public AbilityCannotBeLearnedException(AbilityId ability) {
        super("ability %s cannot be learned".formatted(ability));
    }
}
