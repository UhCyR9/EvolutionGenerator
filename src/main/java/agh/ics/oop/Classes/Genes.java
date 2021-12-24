package agh.ics.oop.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Genes {
    private static final int GENESQUANTITY = 32;
    private ArrayList<Integer> genes = new ArrayList<>();

    //package protected
    Genes()
    {
        Random random = new Random();
        for (int i = 0; i < GENESQUANTITY; i++)
        {
            genes.add(random.nextInt(8));
        }
        Collections.sort(genes);
    }

    Genes(Animal parent1, Animal parent2)
    {
        float percentage = (float)(parent1.getEnergy() / (parent1.getEnergy() + parent2.getEnergy()));
        int numOfGenes1 =  Math.round(GENESQUANTITY * percentage);
        int numOfGenes2 = GENESQUANTITY - numOfGenes1;

        //0 lewa strona, 1 prawa strona

    }

    public Integer randomMove()
    {
        Random random = new Random();
        return genes.get(random.nextInt(genes.size()));
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }
}
