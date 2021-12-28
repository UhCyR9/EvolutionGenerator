package agh.ics.oop.GUI;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashSet;

public class AnimalTracker {
    private final EvolutionMap map;
    private final Animal animal;
    private final ArrayList<Animal> startingChildren;
    private int epochOfDeath = -1;
    private final Chart chart;
    private VBox vbox;
    private boolean isDead = false;

    public AnimalTracker(EvolutionMap map, Animal animal, Chart chart)
    {
        this.map = map;
        this.animal = animal;
        this.chart = chart;
        this.startingChildren = new ArrayList<>(animal.getChildren());

    }

    public int countDescendants(ArrayList<Animal> children, HashSet<Animal> checkForRepeat)
    {
        int tmp = 0;
        for (Animal animal : children)
        {
            if (!checkForRepeat.contains(animal))
            {
                checkForRepeat.add(animal);
                tmp += 1 + countDescendants(animal.getChildren(), checkForRepeat);
            }
        }

        return tmp;
    }

    public void updateInformation()
    {
        if (!isDead) {
            this.vbox = new VBox();
            vbox.getChildren().add(new Text(""));
            vbox.getChildren().add(new Text("GENY: " + animal.getGenes()));
            ArrayList<Animal> toCount = animal.getChildren();
            toCount.removeAll(this.startingChildren);
            vbox.getChildren().add(new Text("ILOSC DZIECI: " + toCount.size()));
            vbox.getChildren().add(new Text("ILOSC POTOMKOW: " + countDescendants(toCount, new HashSet<>())));

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
}
