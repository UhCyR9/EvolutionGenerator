package agh.ics.oop.Interfaces;

import agh.ics.oop.Classes.Vector2d;

public interface IMapElement {
    Vector2d getPosition();

    default boolean isAt(Vector2d position) {
        return this.getPosition().equals(position);
    }

}
