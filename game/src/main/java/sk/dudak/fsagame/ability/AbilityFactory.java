package sk.dudak.fsagame.ability;

public class AbilityFactory {

    public static Ability create(AbilityId ability) {
        return switch (ability) {
            case FIREBALL -> new Fireball();
            case FIREBLAST -> new Fireblast();
            case LESSER_HEAL -> new LesserHeal();
        };
    }

}
