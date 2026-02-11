package sk.dudak.fsagame.character.hero;

import sk.dudak.fsagame.ability.Ability;
import sk.dudak.fsagame.ability.AbilityCannotBeLearnedException;
import sk.dudak.fsagame.ability.AbilityFactory;
import sk.dudak.fsagame.ability.AbilityId;
import sk.dudak.fsagame.ability.AbilityNotLearnedException;
import sk.dudak.fsagame.ability.AbilityView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Logger;

// TODO: mozno nech implementuje nieco na styl domain collection
class AbilityTree {

    private static final Logger LOG = Logger.getLogger(AbilityTree.class.getName());

    Collection<Ability> learnedAbilities = new ArrayList<>();
    private final Hero owner;

    public AbilityTree(Hero owner) {
        this.owner = owner;
    }

    Collection<AbilityView> learnedAbilities() {
        return learnedAbilities.stream()
                .map(Ability::asView)
                .toList();
    }

    Optional<Ability> getAbility(AbilityId abilityId) {
        return learnedAbilities.stream()
                .filter(a -> abilityId.equals(a.abilityId))
                .max(Comparator.comparing(Ability::getLevel));
    }

    AbilityId learnAbility(AbilityId ability) throws AbilityCannotBeLearnedException {
        if (!canBeLearned(ability)) {
            throw new AbilityCannotBeLearnedException(ability);
        }

        LOG.info("ability %s learned".formatted(ability));
        learnedAbilities.add(AbilityFactory.create(ability));
        return ability;
    }

    AbilityId upgradeAbility(AbilityId abilityId) throws AbilityCannotBeLearnedException, AbilityNotLearnedException, NotEnoughXpException {
        if (owner.getXp() < 1) {
            throw new NotEnoughXpException();
        }
        Optional<Ability> ability = getAbility(abilityId);
        if (ability.isEmpty()) {
            LOG.warning("Ability %s not yet learned".formatted(abilityId));
            throw new AbilityNotLearnedException();
        }
        Optional<AbilityId> classAfterUpgrade = ability.get().upgrade();
        if (classAfterUpgrade.isEmpty()) {
            return abilityId;
        }
        if (!abilityId.equals(classAfterUpgrade.get())) {
            return learnAbility(classAfterUpgrade.get());
        }

        owner.spendXp(1);
        return abilityId;
    }

    private boolean canBeLearned(AbilityId abilityId) {
        Ability ability = AbilityFactory.create(abilityId);
        AbilityView abilityView = ability.asView();
        if (abilityView.requiredKnownAbility() != null) {
            boolean requiredAbilityLearned = this.containsAbility(abilityView.requiredKnownAbility());
            if (!requiredAbilityLearned) {
                return false;
            }
            canBeLearned(abilityView.requiredKnownAbility().abilityId());
        }
        return true;
    }

    private boolean containsAbility(AbilityView abilityView) {
        return learnedAbilities.stream()
                .anyMatch(a -> a.asView().satisfies(abilityView));
    }
}
