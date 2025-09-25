import javafx.application.Application;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane; 
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.Button;

// 👉 Import các UI riêng cho từng thể loại
import javafxapplication3.VBoxUI;
import javafxapplication3.RockUI;
import javafxapplication3.RapUI;
import javafxapplication3.USUKUI;
import javafxapplication3.OtherUI;






public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // ===== Block 1 =====
        VBox option_menu = new VBox();
        option_menu.setPrefSize(300, 400); 
        Label label_1 = new Label("MusicPlayer");
        label_1.setStyle("-fx-font-size: 20px; -fx-text-fill: white;-fx-font-weight: bold;");
        label_1.setTranslateX(30);
        option_menu.getChildren().add(label_1);
        option_menu.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");

        // 👉 Đổi Pane thành StackPane
        StackPane mainDisplay = new StackPane();
        mainDisplay.setPrefSize(900, 400);
        mainDisplay.setStyle("-fx-background-color: #010101;");

        HBox block_1 = new HBox(10);
        block_1.getChildren().addAll(option_menu, mainDisplay);
        block_1.setAlignment(Pos.CENTER_LEFT);

        // ===== Block 2 =====
        VBox top_your_genres = new VBox(5); 
        top_your_genres.setPrefSize(350, 200);
        top_your_genres.setPadding(new Insets(10));
        top_your_genres.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");

        Label genreTitle = new Label("Your Genres");
        genreTitle.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");

        VBox genreContainer = new VBox(3);

        // Hàng 1
        HBox genreRow_1 = new HBox(3);
        Button genreChoice_1 = new Button("VPop");
        genreChoice_1.setPrefSize(160,50);
        genreChoice_1.setStyle(
            "-fx-background-color: #C60DD3;" +
            "-fx-background-radius: 5px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 600;" +
            "-fx-cursor: hand;"
        );
        // 👉 Xử lý click: VPop
        genreChoice_1.setOnAction(e -> {
            mainDisplay.getChildren().clear();
            VBoxUI vboxUI = new VBoxUI();
            vboxUI.prefWidthProperty().bind(mainDisplay.widthProperty());
            vboxUI.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().add(vboxUI);
        });

        Button genreChoice_2 = new Button("Rock");
        genreChoice_2.setPrefSize(160,50);
        genreChoice_2.setStyle(
            "-fx-background-color: #C11E1F;" +
            "-fx-background-radius: 5px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 600;" +
            "-fx-cursor: hand;"
        );
        // 👉 Xử lý click: Rock
        genreChoice_2.setOnAction(e -> {
            mainDisplay.getChildren().clear();
            RockUI rockUI = new RockUI();
            rockUI.prefWidthProperty().bind(mainDisplay.widthProperty());
            rockUI.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().add(rockUI);
        });
        genreRow_1.getChildren().addAll(genreChoice_1, genreChoice_2);

        // Hàng 2
        HBox genreRow_2 = new HBox(3);
        Button genreChoice_3 = new Button("Rap");
        genreChoice_3.setPrefSize(160,50);
        genreChoice_3.setStyle(
            "-fx-background-color: #227E28;" +
            "-fx-background-radius: 5px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 600;" +
            "-fx-cursor: hand;"
        );
        // 👉 Xử lý click: Rap
        genreChoice_3.setOnAction(e -> {
            mainDisplay.getChildren().clear();
            RapUI rapUI = new RapUI();
            rapUI.prefWidthProperty().bind(mainDisplay.widthProperty());
            rapUI.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().add(rapUI);
        });

        Button genreChoice_4 = new Button("US-UK");
        genreChoice_4.setPrefSize(160,50);
        genreChoice_4.setStyle(
            "-fx-background-color: #7E28E2;" +  
            "-fx-background-radius: 5px;" +    
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 600;" +
            "-fx-cursor: hand;"
        );
        // 👉 Xử lý click: US-UK
        genreChoice_4.setOnAction(e -> {
            mainDisplay.getChildren().clear();
            USUKUI usukUI = new USUKUI();
            usukUI.prefWidthProperty().bind(mainDisplay.widthProperty());
            usukUI.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().add(usukUI);
        });
        genreRow_2.getChildren().addAll(genreChoice_3, genreChoice_4);

        // Hàng 3
        HBox genreRow_3 = new HBox(3);
        Button genreChoice_5 = new Button("Other...");
        genreChoice_5.setPrefSize(180,50);
        genreChoice_5.setStyle(
            "-fx-background-color: #37817A;" +
            "-fx-background-radius: 5px;" +
            "-fx-text-fill: #FEFEFE;" +
            "-fx-font-size: 20px;" +
            "-fx-font-weight: 600;" +
            "-fx-cursor: hand;"
        );
        // 👉 Xử lý click: Other
        genreChoice_5.setOnAction(e -> {
            mainDisplay.getChildren().clear();
            OtherUI otherUI = new OtherUI();
            otherUI.prefWidthProperty().bind(mainDisplay.widthProperty());
            otherUI.prefHeightProperty().bind(mainDisplay.heightProperty());
            mainDisplay.getChildren().add(otherUI);
        });
        genreRow_3.getChildren().add(genreChoice_5);

        genreContainer.getChildren().addAll(genreRow_1, genreRow_2, genreRow_3);
        top_your_genres.getChildren().addAll(genreTitle, genreContainer);

        // News section
        VBox news = new VBox();
        news.setPrefSize(425, 200);
        Label label_3 = new Label("New Music");
        label_3.setStyle("-fx-font-size: 20;-fx-text-fill: white;-fx-font-weight :bold");
        label_3.setTranslateX(150);
        news.getChildren().add(label_3);
        news.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");

        // Music list section
        Pane musicList = new Pane();
        musicList.setPrefSize(425, 200);
        musicList.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");

        HBox block_2 = new HBox(5);
        block_2.getChildren().addAll(top_your_genres, news, musicList);

        // ===== Block 3 =====
        Pane musicDisplay = new Pane();
        musicDisplay.setPrefSize(1200, 70);
        musicDisplay.setMinHeight(70);
        musicDisplay.setMaxHeight(70);
        musicDisplay.setStyle("-fx-background-color: #4A4A4A;-fx-background-radius: 10;");

        HBox block_3 = new HBox();
        block_3.getChildren().addAll(musicDisplay);

        // ===== Full layout =====
        VBox FullDisplay = new VBox(10);
        FullDisplay.getChildren().addAll(block_1, block_2, block_3);
        FullDisplay.setAlignment(Pos.CENTER);
        FullDisplay.setPadding(new Insets(5, 5, 5, 5));
        FullDisplay.setStyle("-fx-background-color: #010101;");

        // ===== Scene =====
        Scene scene = new Scene(FullDisplay, 1250, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Music UI Example");

        // ===== Resize logic =====
        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            double width = newVal.doubleValue();

            double optionMenuWidth = (primaryStage.isMaximized() || primaryStage.isFullScreen()) ? 400 : 300;
            option_menu.setPrefWidth(optionMenuWidth);

            mainDisplay.setPrefWidth(width - optionMenuWidth - 40);

            double block2Width = width - 20;
            double eachWidth = block2Width / 3.0;
            top_your_genres.setPrefWidth(eachWidth);
            news.setPrefWidth(eachWidth);
            musicList.setPrefWidth(eachWidth);

            musicDisplay.setPrefWidth(block2Width);
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            double height = newVal.doubleValue();

            double optionMenuHeight = height * 0.6;
            option_menu.setPrefHeight(optionMenuHeight);

            double remainingHeight = height - optionMenuHeight - 100;
            double eachHeight = remainingHeight / 3.0;

            top_your_genres.setPrefHeight(eachHeight);
            news.setPrefHeight(eachHeight);
            musicList.setPrefHeight(eachHeight);

            musicDisplay.setPrefHeight(70);
        });

        primaryStage.show();
    }
}
