package agh.ics.oop.EvolutionGenerator;

import agh.ics.oop.Classes.EvolutionMap;
import agh.ics.oop.GUI.App;


public class SimulationEngine implements Runnable {
    private EvolutionMap map;
    private boolean isMagic;
    private int magicUsed = 0;
    private App GUI;
    private boolean running = true;
    private boolean turnedOn = true;


    public SimulationEngine(EvolutionMap map, boolean isMagic, App GUI)
    {
        this.map = map;
        this.isMagic = isMagic;
        this.GUI = GUI;
    }

    public void toggle()
    {
        running = !running;
    }

    public void turnoff()
    {
        turnedOn = !turnedOn;
    }

    public void run()
    {
        while (turnedOn) {
            if (running) {
                map.moveAnimals();
                map.eatGrass();
                map.breedAnimals();
                map.addGrass();

                if (isMagic && map.numberOfAnimals() <= 5 && magicUsed < 3) {
                    map.createMagicAnimals();
                    magicUsed += 1;
                    GUI.magicIndicator(map, magicUsed);
                }

                GUI.positionChanged(this.map);
            }
            try {
                Thread.sleep(EntryData.breakTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
