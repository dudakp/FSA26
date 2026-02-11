package sk.dudak.fsagame.character;

public interface CharacterIdGenerator<T extends Number> {

    CharacterId<T> getNext();

}
