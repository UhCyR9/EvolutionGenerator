package agh.ics.oop.Classes;

import agh.ics.oop.Interfaces.IMapElement;

public class Grass implements IMapElement {
    private Vector2d position;


    @Override
    public Vector2d getPosition() {
        return position;
    }
}
