package agh.ics.oop.Classes;

import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement {
    private final Vector2d position;
    private final ImageView imageView;
    private final EvolutionMap map;
    private static Image grass;
    private static Image jungleGrass;

    static {
        grass = null;
        try {
            grass = new Image(new FileInputStream("src/main/resources/grass.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        jungleGrass = null;
        try {
            jungleGrass = new Image(new FileInputStream("src/main/resources/jungleGrass.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


     public Grass(Vector2d position, EvolutionMap map) {
         this.position = position;
         this.map = map;


         if (map.isJungle(position))
         {
             imageView = new ImageView(jungleGrass);
         }
         else
         {
             imageView = new ImageView(grass);
         }
         imageView.setFitWidth(Math.round((double)(450/EntryData.width)));
         imageView.setFitHeight(Math.round((double)(450/EntryData.width)));

     }



    @Override
    public Vector2d getPosition() {
        return position;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
