package agh.ics.oop.Classes;

import agh.ics.oop.EnumClasses.MapDirection;
import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


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
    private final ArrayList<Animal> children= new ArrayList<>();
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();
    private final ImageView imageView;
    private static Image image;


    static {
        image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/animal.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Animal(Vector2d position, EvolutionMap map) //początkowe zwierzęta
    {
        this.position = position;
        this.map = map;
        this.genes = new Genes();
        this.energy = EntryData.startEnergy;
        numberOfAnimals += 1;

        // losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
        this.map.place(this);

        imageView = new ImageView(image);
        imageView.setFitWidth(Math.round((double)(450/EntryData.width)));
        imageView.setFitHeight(Math.round((double)(450/EntryData.width)));
    }


    public Animal(Animal toCopy, Vector2d position) //kopiowanie z pełną energią
    {
        this.position = position;
        this.map = toCopy.getMap();
        this.genes = toCopy.getGenes2();
        this.energy = EntryData.startEnergy;
        numberOfAnimals += 1;

        // losowy kierunek
        Random random = new Random();
        this.direction = MapDirection.values()[random.nextInt(MapDirection.values().length)];
        this.map.place(this);

        imageView = new ImageView(image);
        imageView.setFitWidth(Math.round((double)(450/EntryData.width)));
        imageView.setFitHeight(Math.round((double)(450/EntryData.width)));
    }

    public Animal(Animal parent1, Animal parent2) //dziecko
    {
        this.position = parent1.getPosition();
        this.map = parent1.getMap();
        this.genes = new Genes(parent1,parent2);
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

        imageView = new ImageView(image);
        imageView.setFitWidth(Math.round((double)(450/EntryData.width)));
        imageView.setFitHeight(Math.round((double)(450/EntryData.width)));
        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            for (Animal animal : map.getAnimalList())
            {
                if (animal.getImageView().equals(event.getTarget()))
                {

                    break;
                }
            }
            event.consume();
        });
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

    public ImageView getImageView() {
        ColorAdjust changeColor = new ColorAdjust();
        changeColor.setBrightness(0);

        this.imageView.setEffect(changeColor);

        if (this.energy < EntryData.startEnergy/2)
        {
            changeColor.setBrightness(-0.5);
            this.imageView.setEffect(changeColor);
        }

        if (this.energy < EntryData.startEnergy/4)
        {
            changeColor.setBrightness(-0.8);
            this.imageView.setEffect(changeColor);
        }

        return imageView;
    }
}
