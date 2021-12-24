package agh.ics.oop.Classes;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;

public class WalledMap extends AbstractWorldMap {
    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }
}
