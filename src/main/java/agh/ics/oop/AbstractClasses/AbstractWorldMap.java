package agh.ics.oop.AbstractClasses;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.Grass;
import agh.ics.oop.Classes.Vector2d;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;

import java.util.*;

public abstract class AbstractWorldMap implements IPositionChangeObserver {
    protected int width;
    protected int height;
    protected Map<Vector2d, Grass> grass = new HashMap<>();
    protected Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    protected static Comparator<Animal> animalComparator = new Comparator<>() {
        @Override
        public int compare(Animal o1, Animal o2) {
            if (o1.getEnergy() <= o2.getEnergy())
            {
                return 1;
            }
            else
            {
                return -1;
            }
        }
    };



    public abstract boolean canMoveTo(Vector2d position);

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement element) {

    }

}
