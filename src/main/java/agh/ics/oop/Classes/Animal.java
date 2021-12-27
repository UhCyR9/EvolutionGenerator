package agh.ics.oop.Classes;

import agh.ics.oop.EnumClasses.MapDirection;
import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Animal implements IMapElement {
    private static int numberOfAnimals = 0;
    private Vector2d position;
    private MapDirection direction;
    private final EvolutionMap map;
    private final Genes genes;
    private int energy;
    private int age=0;
    private ArrayList<Animal> children= new ArrayList<>();
    private ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private int toHashCode;
    private ImageView imageView;


    public Animal(Vector2d position, EvolutionMap map) //początkowe zwierzęta
    {
        this.position = position;
        this.map = map;
        this.genes = new Genes();
        this.energy = EntryData.startEnergy;
        toHashCode= numberOfAnimals;
        numberOfAnimals += 1;

        // losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
        this.map.place(this);

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/animal.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }


    public Animal(Animal toCopy, Vector2d position) //kopiowanie z pełną energią
    {
        this.position = position;
        this.map = toCopy.getMap();
        this.genes = toCopy.getGenes2();
        this.energy = EntryData.startEnergy;
        toHashCode= numberOfAnimals;
        numberOfAnimals += 1;

        // losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
        this.map.place(this);

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/animal.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }

    public Animal(Animal parent1, Animal parent2) //dziecko
    {
        this.position = parent1.getPosition();
        this.map = parent1.getMap();
        this.genes = new Genes(parent1,parent2);
        this.toHashCode= numberOfAnimals;
        numberOfAnimals += 1;

        //energia dziecka
        int par1Energy = (int) Math.round(parent1.getEnergy()*(0.25));
        int par2Energy = (int) Math.round(parent2.getEnergy()*(0.25));
        this.energy = par1Energy + par2Energy;
        parent1.setEnergy(parent1.getEnergy()-par1Energy);
        parent2.setEnergy(parent2.getEnergy()-par2Energy);

        //losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
        this.map.place(this);

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/animal.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
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
                if (map.canMoveTo(position.add(this.getDirection().tuUnitVector()))) {
                    tmp = position;
                    position = map.newMovePosition(tmp, this.getDirection().tuUnitVector());
                    positionChanged(tmp, position);
                }
            }
            case 4 -> {
                if (map.canMoveTo(position.add(this.getDirection().tuUnitVector().opposite()))) {
                    tmp = position;
                    position = map.newMovePosition(tmp, this.getDirection().tuUnitVector().opposite());
                    positionChanged(tmp, position);
                }
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


    public EvolutionMap getMap() {
        return map;
    }

    public ArrayList<IPositionChangeObserver> getObservers() {
        return observers;
    }

    public ArrayList<Integer> getGenes() {
        return genes.getGenes();
    }

    public Genes getGenes2()
    {
        return genes;
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

    public int getAge() {
        return age;
    }

    public ArrayList<Animal> getChildren() {
        return children;
    }

    public void addAge() {
        this.age += 1;
    }

    public void addChild(Animal animal) {
        this.children.add(animal);
    }

    public void addObserver(IPositionChangeObserver observer)
    {
        observers.add(observer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(toHashCode);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
