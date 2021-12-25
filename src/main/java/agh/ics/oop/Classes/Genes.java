package agh.ics.oop.Classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
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

    //package protected
    Genes(Animal parent1, Animal parent2)
    {
        double percentage = ((double)(parent1.getEnergy()) / (double) (parent1.getEnergy() + parent2.getEnergy()));
        int numOfGenes1 =  (int)(Math.round(GENESQUANTITY * percentage));
        int numOfGenes2 = GENESQUANTITY - numOfGenes1;

        ArrayList<Integer> moreGenes;
        ArrayList<Integer> lessGenes;
        if (parent1.getEnergy() > parent2.getEnergy()) {
            moreGenes = parent1.getGenes();
            lessGenes = parent2.getGenes();
        }
        else
        {
            moreGenes = parent2.getGenes();
            lessGenes = parent1.getGenes();
        }

        //0 lewa strona, 1 prawa strona

        Random random = new Random();
        int side = random.nextInt(2);
        ArrayList<Integer> newGenes = new ArrayList<>();

        switch (side) {
            case 0 -> {
                int middleValue = Math.max(numOfGenes1,numOfGenes2);

                for (int i = 0; i < middleValue;i++)
                {
                    newGenes.add(moreGenes.get(i));
                }
                for(int i = middleValue; i < GENESQUANTITY; i++)
                {
                    newGenes.add(lessGenes.get(i));
                }
            }
            case 1 -> {
                int middleValue = Math.min(numOfGenes1,numOfGenes2);
                for (int i = 0; i < middleValue;i++)
                {
                    newGenes.add(lessGenes.get(i));
                }
                for(int i = middleValue; i < GENESQUANTITY; i++)
                {
                    newGenes.add(moreGenes.get(i));
                }

            }
        }
        Collections.sort(newGenes);
        this.genes = newGenes;
    }

    public Integer randomMove()
    {
        Random random = new Random();
        return genes.get(random.nextInt(genes.size()));
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes);
    }
}
