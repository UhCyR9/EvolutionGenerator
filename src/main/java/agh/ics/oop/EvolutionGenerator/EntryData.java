package agh.ics.oop.EvolutionGenerator;

public class EntryData {
    public static int width = 5;
    public static int height = 5;
    public static int startEnergy = 10000;
    public static int moveEnergy = 10;
    public static int plantEnergy = 30;
    public static int jungleRatio = 4;
    public static int startAnimalQuantity = 10;

    public static int breedEnergy()
    {
        return startEnergy/2;
    }
}
