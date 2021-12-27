package agh.ics.oop.GUI;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class AnimalTracker {
    private EvolutionMap map;
    private Animal animal;
    private ArrayList<Integer> genes;
    private int numberOfChildren;
    private int numberOfDescendants;
    private int epochOfDeath = -1;
    private Chart chart;
    private VBox vbox;

    public AnimalTracker(EvolutionMap map, Animal animal, Chart chart)
    {
        this.map = map;
        this.animal = animal;
        this.genes = animal.getGenes();
        this.chart = chart;
        this.numberOfChildren = animal.getChildren().size();
    }

    public void updateInformation()
    {
        this.vbox = new VBox();
        vbox.getChildren().add(new Text(""));
        vbox.getChildren().add(new Text("GENY: " + animal.getGenes()));
        vbox.getChildren().add(new Text("ILOSC DZIECI: " + (animal.getChildren().size()-this.numberOfChildren)));

        if (!map.getAnimalList().contains(animal) && epochOfDeath == -1)
        {
            epochOfDeath = chart.getEpoch();
        }
        if (epochOfDeath != -1)
        {
            vbox.getChildren().add(new Text("DZIEN SMIERCI: " + epochOfDeath));
        }
    }

    public VBox getVbox()
    {
        return vbox;
    }

    public EvolutionMap getMap() {
        return map;
    }
}
