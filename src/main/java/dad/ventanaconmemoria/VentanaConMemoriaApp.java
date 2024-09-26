package dad.ventanaconmemoria;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class VentanaConMemoriaApp extends Application {

    // model

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private DoubleProperty height = new SimpleDoubleProperty();

    private IntegerProperty red = new SimpleIntegerProperty();
    private IntegerProperty green = new SimpleIntegerProperty();
    private IntegerProperty blue = new SimpleIntegerProperty();

    @Override
    public void init() throws Exception {
        super.init();
        System.out.println("iniciando");

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder, ".VentanaConMemoria");
        File configFile = new File(configFolder, "config.properties");

        if (configFile.exists()) {
            // lo cargamos
            FileInputStream fis = new FileInputStream(configFile);

            Properties props = new Properties();
            props.load(fis);

            width.set(Double.parseDouble(props.getProperty("size.width")));
            height.set(Double.parseDouble(props.getProperty("size.height")));
            x.set(Double.parseDouble(props.getProperty("location.x")));
            y.set(Double.parseDouble(props.getProperty("location.y")));
            red.set(Integer.parseInt((props.getProperty("background.red"))));
            green.set(Integer.parseInt((props.getProperty("background.green"))));
            blue.set(Integer.parseInt((props.getProperty("background.blue"))));

        } else {

            width.set(320);
            height.set(200);
            x.set(0);
            y.set(0);
            red.set(255);
            green.set(255);
            blue.set(255);

        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Slider redSlider = new Slider();
        redSlider.setMin(0);
        redSlider.setMax(255);
        redSlider.setShowTickMarks(true);
        redSlider.setShowTickLabels(true);
        redSlider.setMajorTickUnit(255);
        redSlider.setMinorTickCount(5);

        Slider greenSlider = new Slider();
        greenSlider.setMin(0);
        greenSlider.setMax(255);
        greenSlider.setShowTickMarks(true);
        greenSlider.setShowTickLabels(true);
        greenSlider.setMajorTickUnit(255);
        greenSlider.setMinorTickCount(5);

        Slider blueSlider = new Slider();
        blueSlider.setMin(0);
        blueSlider.setMax(255);
        blueSlider.setShowTickMarks(true);
        blueSlider.setShowTickLabels(true);
        blueSlider.setMajorTickUnit(255);
        blueSlider.setMinorTickCount(5);

        VBox root = new VBox();
        root.setFillWidth(false);
        Color colorInicial = Color.rgb(red.get(), green.get(), blue.get());
        root.setBackground(Background.fill(colorInicial));
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(redSlider, greenSlider, blueSlider);

        Scene scene = new Scene(root, width.get(), height.get());

        primaryStage.setX(x.get());
        primaryStage.setY(y.get());
        primaryStage.setTitle("Ventana con memoria");
        primaryStage.setScene(scene);
        primaryStage.show();

        // bindings

        x.bind(primaryStage.xProperty());
        y.bind(primaryStage.yProperty());
        width.bind(primaryStage.widthProperty());
        height.bind(primaryStage.heightProperty());

        redSlider.valueProperty().bindBidirectional(red);
        greenSlider.valueProperty().bindBidirectional(green);
        blueSlider.valueProperty().bindBidirectional(blue);

        red.addListener((o, ov, nv) -> {
            Color c = Color.rgb(red.get(), green.get(), blue.get());
            root.setBackground(Background.fill(c));
        });

        green.addListener((o, ov, nv) -> {
            Color c = Color.rgb(red.get(), green.get(), blue.get());
            root.setBackground(Background.fill(c));
        });

        blue.addListener((o, ov, nv) -> {
            Color c = Color.rgb(red.get(), green.get(), blue.get());
            root.setBackground(Background.fill(c));
        });
    }

    @Override
    public void stop() throws Exception {

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder, ".VentanaConMemoria");
        File configFile = new File(configFolder, "config.properties");

        if (!configFolder.exists()) {
            configFolder.mkdir();
        }

        System.out.println("Saving config: " + configFile);

        FileOutputStream fos = new FileOutputStream(configFile);

        Properties props = new Properties();
        props.setProperty("size.width", "" + width.getValue());
        props.setProperty("size.height", "" + height.getValue());
        props.setProperty("location.x", "" + x.getValue());
        props.setProperty("location.y", "" + y.getValue());
        props.setProperty("background.red", "" + red.getValue());
        props.setProperty("background.green", "" + green.getValue());
        props.setProperty("background.blue", "" + blue.getValue());
        props.store(fos, "Estado de la ventana");

    }
}
