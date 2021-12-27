package agh.ics.oop.EvolutionGenerator;

import agh.ics.oop.Classes.EvolutionMap;
import agh.ics.oop.GUI.App;


public class SimulationEngine implements Runnable {
    private EvolutionMap map;
    private boolean isMagic;
    private int magicUsed = 0;
    private App GUI;


    public SimulationEngine(EvolutionMap map, boolean isMagic, App GUI)
    {
        this.map = map;
        this.isMagic = isMagic;
        this.GUI = GUI;
    }

    public void run()
    {
        while (true) {
            map.moveAnimals();
            map.eatGrass();
            map.breedAnimals();
            map.addGrass();

            if (isMagic && map.numberOfAnimals() <= 5 && magicUsed < 3) {
                map.createMagicAnimals();
                magicUsed += 1;
                System.out.println("dziala");
            }

            GUI.positionChanged();
            try {
                Thread.sleep(EntryData.breakTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
