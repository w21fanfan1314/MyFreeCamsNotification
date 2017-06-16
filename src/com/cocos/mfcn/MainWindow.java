package com.cocos.mfcn;

import com.cocos.mfcn.biz.IWatchMyGrilService;
import com.cocos.mfcn.views.MainViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainWindow extends Application {
    private BorderPane rootLayout;
    private Stage primaryStage;
    public static IWatchMyGrilService grilService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MyFreeCams 模特上线通知");
        this.initRootLayout();
    }

    protected void initRootLayout()
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainViewController.class.getResource("MainWindow.fxml"));
        try {
            this.rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (null != this.rootLayout)
        {
            Scene scene = new Scene(this.rootLayout);
            this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (null != grilService)
                        grilService.stopWatch();
                }
            });
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        }
    }
}
