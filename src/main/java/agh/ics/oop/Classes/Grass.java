package agh.ics.oop.Classes;

import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.Interfaces.IMapElement;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Grass implements IMapElement {
    private Vector2d position;
    private ImageView imageView;

     public Grass(Vector2d position) {
         this.position = position;

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/grass.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
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
