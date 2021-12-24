package agh.ics.oop.Classes;

import agh.ics.oop.AbstractClasses.AbstractWorldMap;
import agh.ics.oop.EnumClasses.MapDirection;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;

import java.util.ArrayList;
import java.util.Random;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection direction;
    private final AbstractWorldMap map;
    private final Genes genes;
    private int energy;
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();


    public Animal(Vector2d position, AbstractWorldMap map) //początkowe zwierzęta
    {
        this.position = position;
        this.map = map;
        this.genes = new Genes();
        this.energy = 50; //startowa energia wprowadzana na początku #startEnergy

        // losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
    }

    public Animal(Animal parent1, Animal parent2) //dziecko
    {
        this.position = parent1.getPosition();
        this.map = parent1.getMap();
        this.genes = new Genes(parent1,parent2);

        //energia dziecka
        int par1Energy = (int) Math.round(parent1.getEnergy()*(0.25));
        int par2Energy = (int) Math.round(parent2.getEnergy()*(0.25));
        this.energy = par1Energy + par2Energy;
        parent1.setEnergy(parent1.getEnergy()-par1Energy);
        parent2.setEnergy(parent2.getEnergy()-par2Energy);

        //losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
    }


    public void move()
    {
        if (this.energy <= 0)
        {
            return;
        }

        Integer move = this.genes.randomMove();
        Vector2d tmp;
        switch (move) {
            case 0 -> {
                tmp = position;
                position = position.add(this.getDirection().tuUnitVector());
                positionChanged(tmp,position);
            }
            case 4 -> {
                tmp = position;
                position = position.add(this.getDirection().tuUnitVector().opposite());
                positionChanged(tmp,position);
            }
            default -> {
                for (int i = 0; i < move; ++i)
                {
                    direction = direction.next();
                }
            }
        }
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        for (IPositionChangeObserver observer : observers)
        {
            observer.positionChanged(oldPosition,newPosition,this);
        }
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public AbstractWorldMap getMap() {
        return map;
    }

    public ArrayList<Integer> getGenes() {
        return genes.getGenes();
    }

    public int getEnergy() {
        return energy;
    }

    public MapDirection getDirection() {
        return direction;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void addObserver(IPositionChangeObserver observer)
    {
        observers.add(observer);
    }

}
