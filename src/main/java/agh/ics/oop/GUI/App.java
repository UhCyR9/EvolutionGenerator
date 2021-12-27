package agh.ics.oop.GUI;

import agh.ics.oop.Classes.Animal;
import agh.ics.oop.Classes.EvolutionMap;
import agh.ics.oop.Classes.Grass;
import agh.ics.oop.Classes.Vector2d;
import agh.ics.oop.EvolutionGenerator.EntryData;
import agh.ics.oop.EvolutionGenerator.SimulationEngine;
import javafx.application.Application;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class App extends Application {
    private Scene mainScene;
    private EvolutionMap wrappedMap;
    private EvolutionMap walledMap;
    private GridPane wrappedGrid;
    private GridPane walledGrid;
    private Chart wrappedChart;
    private Chart walledChart;
    private TextField wrappedDominant = new TextField();
    private TextField walledDominant = new TextField();
    private VBox wrappedWrapper;
    private VBox walledWrapper;
    private SimulationEngine wrappedSimulation;
    private SimulationEngine walledSimulation;

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createStartScene();
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void createStartScene()
    {
        TextField widthField = new TextField();
        widthField.setText(String.valueOf(EntryData.width));

        TextField heightField = new TextField();
        heightField.setText(String.valueOf(EntryData.height));

        TextField startEnergyField = new TextField();
        startEnergyField.setText(String.valueOf(EntryData.startEnergy));

        TextField moveEnergyField = new TextField();
        moveEnergyField.setText(String.valueOf(EntryData.moveEnergy));

        TextField plantEnergyField = new TextField();
        plantEnergyField.setText(String.valueOf(EntryData.plantEnergy));

        TextField jungleWidthField = new TextField();
        jungleWidthField.setText(String.valueOf(EntryData.jungleWidth));

        TextField jungleHeightField = new TextField();
        jungleHeightField.setText(String.valueOf(EntryData.jungleHeight));

        TextField startAnimalQuantityField = new TextField();
        startAnimalQuantityField.setText(String.valueOf(EntryData.startAnimalQuantity));

        TextField breakTimeField = new TextField();
        breakTimeField.setText(String.valueOf(EntryData.breakTime));

        Button startBtn = new Button("START");


        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("SZEROKOSC: "),0,0);
        grid.add(widthField,1,0);

        grid.add(new Label("WYSOKOSC: "),0,1);
        grid.add(heightField,1,1);

        grid.add(new Label("POCZATKOWA ENERGIA: "),0,2);
        grid.add(startEnergyField,1,2);

        grid.add(new Label("DZIENNE ZUZYCIE: "),0,3);
        grid.add(moveEnergyField,1,3);

        grid.add(new Label("ENERGIA Z TRAWY: "),0,4);
        grid.add(plantEnergyField,1,4);

        grid.add(new Label("SZEROKOSC JUNGLI: "),0,5);
        grid.add(jungleWidthField,1,5);

        grid.add(new Label("WYSOKOSC JUNGLI: "),0,6);
        grid.add(jungleHeightField,1,6);

        grid.add(new Label("ILOSC ZWIERZAT: "),0,7);
        grid.add(startAnimalQuantityField,1,7);

        grid.add(new Label("CZAS POMIEDZY DNIAMI: "),0,8);
        grid.add(breakTimeField,1,8);

        ChoiceBox<String> wrappedMapChoice = new ChoiceBox<>();
        wrappedMapChoice.getItems().addAll("ZWYKLA", "MAGICZNA");
        wrappedMapChoice.getSelectionModel().selectFirst();

        ChoiceBox<String> walledMapChoice = new ChoiceBox<>();
        walledMapChoice.getItems().addAll("ZWYKLA", "MAGICZNA");
        walledMapChoice.getSelectionModel().selectFirst();


        grid.add(new Label("ZAWIJANA MAPA"), 2,3);
        grid.add(wrappedMapChoice,2,4);

        grid.add(new Label("MAPA Z MUREM"), 3,3);
        grid.add(walledMapChoice,3,4);

        grid.add(startBtn,0,9,2,1);
        GridPane.setHalignment(startBtn, HPos.CENTER);
        GridPane.setValignment(startBtn, VPos.CENTER);

        startBtn.setOnAction(event ->
        {
            ArrayList<Integer> values = new ArrayList<>();
            for(Node node : grid.getChildren())
            {
                if (node instanceof TextField)
                {
                    values.add(Integer.valueOf(((TextField) node).getText()));
                }
            }
            EntryData.setInitialValues(values);
            drawMaps();
            startSimulation(wrappedMapChoice.getValue(),walledMapChoice.getValue());
            setUpButtons(wrappedWrapper);
            setUpButtons(walledWrapper);
        });

        mainScene = new Scene(grid,1600,1000);
    }

    private void drawMaps()
    {
        wrappedMap = new EvolutionMap(false);
        walledMap = new EvolutionMap(true);
        wrappedGrid = new GridPane();
        walledGrid = new GridPane();

        drawObjects(wrappedMap, wrappedGrid);
        drawObjects(walledMap, walledGrid);

        Button wrappedStopButton = new Button("STOP");
        Button walledStopButton = new Button("STOP");
        Button wrappedGenotype = new Button("DOMINUJACY GENOM");
        Button walledGenotype = new Button("DOMINUJACY GENOM");
        Button wrappedSave = new Button("SAVE");
        Button walledSave = new Button("SAVE");

        TextField wrappedMagicCounter = new TextField("ILOSC MAGII: 0");
        TextField walledMagicCounter = new TextField("ILOSC MAGII: 0");
        wrappedMagicCounter.setEditable(false);
        walledMagicCounter.setEditable(false);

        wrappedWrapper = new VBox(10,wrappedGrid,wrappedDominant,wrappedStopButton,wrappedGenotype,wrappedSave, wrappedMagicCounter);
        walledWrapper = new VBox(10,walledGrid,walledDominant,walledStopButton,walledGenotype,walledSave, walledMagicCounter);

        wrappedDominant.setEditable(false);
        walledDominant.setEditable(false);

        this.wrappedChart = new Chart(wrappedMap);
        this.walledChart = new Chart(walledMap);

        HBox wrapper = new HBox(30,wrappedChart.getLineChart(), wrappedWrapper,walledChart.getLineChart(), walledWrapper);
        mainScene.setRoot(wrapper);
    }

    public void magicIndicator(EvolutionMap map, int count)
    {
        Platform.runLater(() -> {
            if (map == wrappedMap)
            {
                TextField text = (TextField) wrappedWrapper.getChildren().get(5);
                text.setText("ILOSC MAGII: " + count);

            }
            else
            {
                TextField text = (TextField) walledWrapper.getChildren().get(5);
                text.setText("ILOSC MAGII: " + count);
            }
        });

    }

    private void setUpButtons(VBox box)
    {
        Button stop = (Button) box.getChildren().get(2);
        stop.setOnAction(event -> {
            if (box.getChildren().get(0) == wrappedGrid)
            {
                wrappedSimulation.toggle();
            }
            else
            {
                walledSimulation.toggle();
            }
        });

        Button showDominant = (Button) box.getChildren().get(3);
        showDominant.setOnAction(event -> {
            if (box.getChildren().get(0) == wrappedGrid)
            {
                ColorAdjust changeColor = new ColorAdjust();
                changeColor.setBrightness(0.5);
                for (Animal animal : wrappedMap.getAnimalList())
                {
                    if (wrappedMap.getDominant().equals(animal.getGenes()))
                    {
                        animal.getImageView().setEffect(changeColor);
                    }
                }
            }
            else
            {
                ColorAdjust changeColor = new ColorAdjust();
                changeColor.setBrightness(0.5);
                for (Animal animal : walledMap.getAnimalList())
                {
                    if (wrappedMap.getDominant().equals(animal.getGenes()))
                    {
                        animal.getImageView().setEffect(changeColor);
                    }
                }
            }
        });
    }

    private void drawObjects(EvolutionMap evolutionMap, GridPane grid)
    {
        grid.getChildren().clear();

        Image image = null;
        try {
            image = new Image(new FileInputStream("src/main/resources/dirt.jpg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < EntryData.width; i++)
        {
            for (int j = 0; j < EntryData.height; j++)
            {
                ImageView dirt = new ImageView(image);
                dirt.setFitWidth((int)(450/EntryData.width));
                dirt.setFitHeight((int)(450/EntryData.width));
                grid.add(dirt,i,j);
            }
        }

        HashMap<Vector2d,Grass> grassList = (HashMap<Vector2d, Grass>) evolutionMap.getGrass().clone();
        HashMap<Vector2d,HashSet<Animal>> animals = (HashMap<Vector2d, HashSet<Animal>>) evolutionMap.getAnimals().clone();
        for (Vector2d grassPos : grassList.keySet())
        {
            grid.add(grassList.get(grassPos).getImageView(),grassPos.x,grassPos.y);
        }

        for (Vector2d animalPos : animals.keySet())
        {
            for (Animal animal : animals.get(animalPos))
            {
                if (animal != null)
                {
                    grid.add(animal.getImageView(),animalPos.x,animalPos.y);
                }

            }
        }
    }

    public void positionChanged(EvolutionMap map)
    {
        if (map.equals(wrappedMap)) {
            Platform.runLater(() -> {
                wrappedChart.updateChart();
                drawObjects(wrappedMap, wrappedGrid);
                wrappedDominant.setText(String.valueOf(wrappedMap.getDominant()));
            });
        }
        else {
            Platform.runLater(() -> {
                walledChart.updateChart();
                drawObjects(walledMap, walledGrid);
                walledDominant.setText(String.valueOf(walledMap.getDominant()));
            });
        }
    }

    private void startSimulation(String wrappedInfo, String walledInfo)
    {
        boolean wrappedMagic = !wrappedInfo.equals("ZWYKLA");
        boolean walledMagic = !walledInfo.equals("ZWYKLA");

        wrappedSimulation = new SimulationEngine(wrappedMap,wrappedMagic, this);
        walledSimulation = new SimulationEngine(walledMap,walledMagic,this);
        Thread wrappedThread = new Thread(wrappedSimulation);
        Thread walledThread = new Thread(walledSimulation);
        wrappedThread.start();
        walledThread.start();
    }
}
