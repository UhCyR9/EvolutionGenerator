package agh.ics.oop.Classes;

import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;

import java.util.*;

public class EvolutionMap implements IPositionChangeObserver {
    private int width;
    private int height;
    private boolean hasWall;
    private HashSet<Vector2d> grass = new HashSet<>();
    private Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    private HashSet<Animal> animalList = new HashSet<>();
    private static Comparator<Animal> animalComparator = new Comparator<>() {
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

    public HashSet<Animal> getAnimalList() {
        return animalList;
    }

    public EvolutionMap(boolean hasWall)
    {
        this.width = EntryData.width;
        this.height = EntryData.height;
        this.hasWall = hasWall;

        Random random = new Random();
        HashSet<Vector2d> tmp = new HashSet<>();
        Vector2d pos;
        for (int i = 0; i < EntryData.startAnimalQuantity; i ++)
        {
            pos = new Vector2d(random.nextInt(width), random.nextInt(height));
            while (tmp.contains(pos))
            {
                pos = new Vector2d(random.nextInt(width), random.nextInt(height));
            }

            new Animal(pos,this);
            tmp.add(pos);
        }
    }

    public Vector2d newMovePosition(Vector2d currentPosition, Vector2d vectorToMove)
    {
        if (!hasWall)
        {
            int newX = currentPosition.x + vectorToMove.x;
            if (newX == width)
            {
                newX = 0;
            }
            else if (newX == -1)
            {
                newX = width-1;
            }

            int newY = currentPosition.y + vectorToMove.y;
            if (newY == height)
            {
                newY = 0;
            }
            else if (newY == -1)
            {
                newY = height-1;
            }

            return new Vector2d(newX,newY);
        }
        else
        {
            return currentPosition.add(vectorToMove);
        }
    }

    public void moveAnimals()
    {
        // funkcja rusza każdym zwierzęciem i usuwa przy okazji martwe zwierzęta
        ArrayList<Animal> toRemove = new ArrayList<>();
        for (Animal animal : animalList) {
            if (animal.getEnergy() <= 0) {
                toRemove.add(animal);
            } else {
                animal.setEnergy(animal.getEnergy() - EntryData.moveEnergy);
                animal.move();
            }
        }
        for (Animal animal : toRemove)
        {
            animals.get(animal.getPosition()).remove(animal);
            animalList.remove(animal);
        }

        for (Animal animal : animalList) {
            animal.addAge();
        }
    }

    public void breedAnimals()
    {
        for (TreeSet<Animal> tset : animals.values())
        {
            ArrayList<Animal> toBreed = new ArrayList<>();
            int i = 0;
            for (Animal animal : tset)
            {
                if (i == 2)
                {
                    break;
                }
                toBreed.add(animal);
                i++;
            }
            if (toBreed.size() < 2)
            {
                break;
            }
            Animal parent1 = toBreed.get(0);
            Animal parent2 = toBreed.get(1);
            if (parent1.getEnergy() < EntryData.breedEnergy() || parent2.getEnergy() < EntryData.breedEnergy())
            {
                break;
            }
            new Animal(parent1, parent2);
            tset.remove(parent1);
            tset.remove(parent2);
            parent1.addChild();
            parent2.addChild();
            tset.add(parent1);
            tset.add(parent2);
        }
    }

    public void eatGrass()
    {
        for (Vector2d grassPos : grass)
        {
            ArrayList<Animal> tmp = new ArrayList<>();
            int currEnergy = 0;
            if (animals.containsKey(grassPos))
            {
                for (Animal animal : animals.get(grassPos)) //znajdź zwierzęta z taką samą największą ilością energii
                {
                    if (currEnergy <= animal.getEnergy())
                    {
                        currEnergy = animal.getEnergy();
                        tmp.add(animal);
                    }
                    else
                    {
                        break;
                    }
                }

                int energyForAll = EntryData.plantEnergy / tmp.size(); //równy podział energii

                for (Animal animal : tmp)   //rozdanie energii i aktualizacja listy zwierząt
                {
                    animal.setEnergy(animal.getEnergy()+energyForAll);
                    animals.get(grassPos).remove(animal);
                    animals.get(grassPos).add(animal);
                }
            }
        }
    }

    public void addGrass(){

    }

    public boolean canMoveTo(Vector2d position) {
        return !hasWall || position.precedes(new Vector2d(width - 1, height - 1)) && position.follows(new Vector2d(0, 0));
    }

    public boolean place(Animal animal)
    {
        if (animals.containsKey(animal.getPosition()))
        {
            animals.get(animal.getPosition()).add(animal);
        }
        else
        {
            TreeSet<Animal> tmp = new TreeSet<>(animalComparator);
            tmp.add(animal);
            animals.put(animal.getPosition(),tmp);
        }
        animalList.add(animal);
        animal.addObserver(this);
        return true;
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement element) {
        animals.get(oldPosition).remove((Animal)element);
        if (animals.containsKey(newPosition))
        {
            animals.get(newPosition).add((Animal) element);
        }
        else
        {
            TreeSet<Animal> tmp = new TreeSet<>(animalComparator);
            tmp.add((Animal) element);
            animals.put(newPosition,tmp);
        }
    }
}
