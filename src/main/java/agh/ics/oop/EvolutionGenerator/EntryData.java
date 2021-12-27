package agh.ics.oop.EvolutionGenerator;

import java.util.ArrayList;

public class EntryData {
    public static int width = 10;
    public static int height = 10;
    public static int startEnergy = 100;
    public static int moveEnergy = 1;
    public static int plantEnergy = 20;
    public static int jungleWidth = 5;
    public static int jungleHeight = 5;
    public static int startAnimalQuantity = 10;
    public static int breakTime = 300;


    public static int breedEnergy()
    {
        return startEnergy/2;
    }

    public static void setInitialValues(ArrayList<Integer> values)
    {
        width = values.get(0);
        height = values.get(1);
        startEnergy = values.get(2);
        moveEnergy = values.get(3);
        plantEnergy = values.get(4);
        jungleWidth = values.get(5);
        jungleHeight = values.get(6);
        startAnimalQuantity = values.get(7);
        breakTime = values.get(8);
    }
}
