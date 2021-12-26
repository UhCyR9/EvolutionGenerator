package agh.ics.oop.EvolutionGenerator;


import agh.ics.oop.Classes.EvolutionMap;

public class World {
    public static void main(String[] args)
    {
        EvolutionMap map = new EvolutionMap(false);
        SimulationEngine engine = new SimulationEngine(map,true);
        engine.run();
    }
}
