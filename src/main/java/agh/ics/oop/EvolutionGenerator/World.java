package agh.ics.oop.EvolutionGenerator;


import agh.ics.oop.Classes.EvolutionMap;

public class World {
    public static void main(String[] args)
    {
        EvolutionMap map = new EvolutionMap(false);
        for (int i = 0; i < 1000; i++)
        {
            map.moveAnimals();
            map.eatGrass();
            map.breedAnimals();
            System.out.println(map.getAnimalList().size());
        }
    }
}
