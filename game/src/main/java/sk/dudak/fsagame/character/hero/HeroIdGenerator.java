package sk.dudak.fsagame.character.hero;

import sk.dudak.fsagame.character.CharacterIdGenerator;

enum HeroIdGenerator implements CharacterIdGenerator<Long> {
    INSTANCE;

    public HeroId getNext() {
        return new HeroId(1L);
    }

}
