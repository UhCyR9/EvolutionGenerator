package agh.ics.oop.GUI;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;

import java.util.ArrayList;

public class AnimalTracker {
    private EvolutionMap map;
    private Animal animal;
    private ArrayList<Integer> genes;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int epochOfDeath;

    public AnimalTracker(EvolutionMap map, Animal animal)
    {
        this.map = map;
        this.animal = animal;
        this.genes = animal.getGenes();
    }

    public void updateInformation()
    {

    }
}
