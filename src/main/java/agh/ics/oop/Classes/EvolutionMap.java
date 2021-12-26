package agh.ics.oop.Classes;

import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import agh.ics.oop.Interfaces.IPositionChangeObserver;

import java.util.*;

public class EvolutionMap implements IPositionChangeObserver {
    private final int width;
    private final int height;
    private final int jungleWidth;
    private final int jungleHeight;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final boolean hasWall;
    private HashMap<Vector2d, Grass> grass = new HashMap<>();
    private Map<Vector2d, HashSet<Animal>> animals = new HashMap<>();
    private HashSet<Animal> animalList = new HashSet<>();

    public HashSet<Animal> getAnimalList() {
        return animalList;
    }

    public EvolutionMap(boolean hasWall)
    {
        this.width = EntryData.width;
        this.height = EntryData.height;
        this.jungleWidth = EntryData.jungleWidth;
        this.jungleHeight = EntryData.jungleHeight;
        this.jungleLowerLeft = new Vector2d((this.width/2)-(this.jungleWidth/2),(this.height/2)-(this.jungleHeight/2));
        this.jungleUpperRight = new Vector2d((this.width/2)+(this.jungleWidth/2),(this.height/2)+(this.jungleHeight/2));
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
        for (Animal animal : animalList)
        {
            if (animal.getEnergy() <= 0)
            {
                toRemove.add(animal);
            }
            else
            {
                animal.setEnergy(animal.getEnergy() - EntryData.moveEnergy);
                animal.move();
            }
            animal.addAge();
        }
        for (Animal animal : toRemove)
        {
            animals.get(animal.getPosition()).remove(animal);
            animalList.remove(animal);
        }

    }

    public void breedAnimals()
    {
        for (HashSet<Animal> tset : animals.values())
        {
            if (tset.size() < 2)
            {
                return;
            }
            Animal parent1 = Collections.max(tset, Comparator.comparingInt(Animal::getEnergy));
            tset.remove(parent1);
            Animal parent2 = Collections.max(tset, Comparator.comparingInt(Animal::getEnergy));
            tset.remove(parent2);

            if (parent1.getEnergy() < EntryData.breedEnergy() || parent2.getEnergy() < EntryData.breedEnergy())
            {
                tset.add(parent1);
                tset.add(parent2);
                break;
            }

            new Animal(parent1, parent2);
            parent1.addChild();
            parent2.addChild();
            tset.add(parent1);
            tset.add(parent2);
        }
    }

    public void eatGrass()
    {
        int sum = 0;
        for (HashSet<Animal> tset : animals.values())
        {
            sum += tset.size();
        }
        System.out.println(sum + " " + this.numberOfAnimals());

        for (Vector2d grassPos : grass.keySet())
        {
            ArrayList<Animal> tmp = new ArrayList<>();
            if (animals.containsKey(grassPos))
            {
                if(animals.get(grassPos).size() < 1)
                {
                    return;
                }

                Animal animal1 = Collections.max(animals.get(grassPos), Comparator.comparingInt(Animal::getEnergy));
                tmp.add(animal1);

                for (Animal animal : animals.get(grassPos))
                {
                    if (animal.getEnergy() == animal1.getEnergy())
                    {
                        tmp.add(animal);
                    }
                }


                int energyForAll = EntryData.plantEnergy / tmp.size(); //równy podział energii

                for (Animal animal : tmp)   //rozdanie energii i aktualizacja listy zwierząt
                {
                    animal.setEnergy(animal.getEnergy()+energyForAll);
                }
            }
        }
    }

    public void addGrass(){
        Random random = new Random();
        ArrayList<Vector2d> jungle = new ArrayList<>();
        ArrayList<Vector2d> nonJungle = new ArrayList<>();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Vector2d tmp = new Vector2d(i,j);
                if (grass.containsKey(tmp) || (animals.containsKey(tmp) && animals.get(tmp).size() > 0))
                {
                    continue;
                }

                if (tmp.follows(jungleLowerLeft) && tmp.precedes(jungleUpperRight))
                {
                    jungle.add(tmp);
                }
                else
                {
                    nonJungle.add(tmp);
                }
            }
        }
        if (jungle.size() > 0)
        {
            int i = random.nextInt(jungle.size());
            grass.put(jungle.get(i), new Grass());
        }

        if (nonJungle.size() > 0)
        {
            int i = random.nextInt(nonJungle.size());
            grass.put(nonJungle.get(i), new Grass());
        }
    }

    public boolean canMoveTo(Vector2d position) {
        return !hasWall || position.precedes(new Vector2d(width - 1, height - 1)) && position.follows(new Vector2d(0, 0));
    }

    public void place(Animal animal)
    {
        if (animals.containsKey(animal.getPosition()))
        {
            animals.get(animal.getPosition()).add(animal);
        }
        else
        {
            HashSet<Animal> tmp = new HashSet<>();
            tmp.add(animal);
            animals.put(animal.getPosition(),tmp);
        }
        animalList.add(animal);
        animal.addObserver(this);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, IMapElement element) {

        if (!(animals.get(oldPosition).remove((Animal)element)))
        {
            System.out.println(animals.get(oldPosition));
            System.out.println(element);
        }
        if (animals.containsKey(newPosition))
        {
            animals.get(newPosition).add((Animal) element);
        }
        else
        {
            HashSet<Animal> tmp = new HashSet<>();
            tmp.add((Animal) element);
            animals.put(newPosition,tmp);
        }
    }

    public int numberOfAnimals() {
        return animalList.size();
    }

    public void createMagicAnimals() {

    }
}
