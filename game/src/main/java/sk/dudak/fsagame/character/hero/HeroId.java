package sk.dudak.fsagame.character.hero;

import sk.dudak.fsagame.character.CharacterId;

record HeroId(Long id) implements CharacterId<Long> {

    @Override
    public Long id() {
        return 0L;
    }
}
