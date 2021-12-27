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

     public Grass() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/grass.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
        imageView.setFitWidth(450/ EntryData.width);
        imageView.setFitHeight(450/EntryData.width);
    }



    @Override
    public Vector2d getPosition() {
        return position;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
