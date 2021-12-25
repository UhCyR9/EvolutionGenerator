package agh.ics.oop.EvolutionGenerator;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;


import java.util.HashSet;

public class SimulationEngine {
    private EvolutionMap map;


    public SimulationEngine(EvolutionMap map)
    {

    }

    public void run()
    {
        map.moveAnimals();
        map.eatGrass();
        map.breedAnimals();
        map.addGrass();
    }
}
