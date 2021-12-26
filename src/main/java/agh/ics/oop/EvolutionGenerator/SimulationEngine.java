package agh.ics.oop.EvolutionGenerator;

import agh.ics.oop.Classes.EvolutionMap;


public class SimulationEngine {
    private EvolutionMap map;
    private boolean isMagic;
    private int magicUsed = 0;


    public SimulationEngine(EvolutionMap map, boolean isMagic)
    {
        this.map = map;
        this.isMagic = isMagic;
    }

    public void run()
    {
        int i = 0;
        while (i < 100) {
            map.moveAnimals();
            map.eatGrass();
            map.breedAnimals();
            map.addGrass();

            if (isMagic && map.numberOfAnimals() <= 5 && magicUsed < 3) {
                map.createMagicAnimals();
                magicUsed += 1;
            }
            i++;
        }
    }
}
