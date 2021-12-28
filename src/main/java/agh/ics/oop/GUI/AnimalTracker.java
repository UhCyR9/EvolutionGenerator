package agh.ics.oop.GUI;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;

public class AnimalTracker {
    private EvolutionMap map;
    private Animal animal;
    private ArrayList<Integer> genes;
    private ArrayList<Animal> startingChildren;
    private int numberOfChildren;
    private int epochOfDeath = -1;
    private Chart chart;
    private VBox vbox;
    private boolean isDead = false;

    public AnimalTracker(EvolutionMap map, Animal animal, Chart chart)
    {
        this.map = map;
        this.animal = animal;
        this.genes = animal.getGenes();
        this.chart = chart;
        this.startingChildren = (ArrayList<Animal>) animal.getChildren().clone();
        this.numberOfChildren = animal.getChildren().size();
    }

    public int countDescendants(ArrayList<Animal> children)
    {
        int tmp = 0;
        for (Animal animal : children)
        {
            tmp += 1 + countDescendants(animal.getChildren());
        }

        return tmp;
    }

    public void updateInformation()
    {
        if (!isDead) {
            this.vbox = new VBox();
            vbox.getChildren().add(new Text(""));
            vbox.getChildren().add(new Text("GENY: " + animal.getGenes()));
            vbox.getChildren().add(new Text("ILOSC DZIECI: " + (animal.getChildren().size() - numberOfChildren)));
            ArrayList<Animal> toCount = animal.getChildren();
            toCount.removeAll(this.startingChildren);
            vbox.getChildren().add(new Text("ILOSC POTOMKOW: " + countDescendants(toCount)));

            if (!map.getAnimalList().contains(animal) && epochOfDeath == -1) {
                epochOfDeath = chart.getEpoch();
            }
            if (epochOfDeath != -1) {
                vbox.getChildren().add(new Text("DZIEN SMIERCI: " + epochOfDeath));
                this.isDead = true;
            }
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
