package com.cocos.mfcn.views;


import com.cocos.mfcn.MainWindow;
import com.cocos.mfcn.biz.IWatchMyGrilService;
import com.cocos.mfcn.biz.impls.WatchMyGrilService;
import com.cocos.mfcn.models.Gril;
import com.cocos.mfcn.models.GrilState;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javax.management.Notification;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainViewController implements IWatchMyGrilService.WatchListener{
    // 全部
    @FXML
    private ListView<Gril> allGrilsListView;
    // 头像
    @FXML
    private ImageView grilImageView;
    // 名字
    @FXML
    private Label grilNameView;
    // 状态
    @FXML
    private RadioButton grilStateView;
    // 搜索
    @FXML
    private TextField searchInputField;
    // 信息面板
    @FXML
    private VBox infoPanel;

    private String currentSearchGrilName;
    private IWatchMyGrilService grilService;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    private void initialize()
    {
        MainWindow.grilService = this.grilService = new WatchMyGrilService();
        this.grilService.startWatch(this);

        this.initListView();
        refreshListData();
        this.infoPanel.setVisible(false);
    }

    @FXML
    private void onSearch(ActionEvent event)
    {
        currentSearchGrilName = this.searchInputField.getText();
        if (null == currentSearchGrilName || currentSearchGrilName.isEmpty()) {
            currentSearchGrilName = "Saria_K";
        }
        final Button button = (Button) event.getTarget();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                MainViewController.this.grilService.addWatch(new Gril(currentSearchGrilName));
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        button.setText("搜索");
                        button.setDisable(false);
                        refreshListData();
                    }
                });
            }
        });
        button.setText("搜索中...");
        button.setDisable(true);
    }

    @FXML
    private void onEnterGrilPorfile()
    {
        Gril gril = this.allGrilsListView.getSelectionModel().getSelectedItem();
        if (null == gril)
            return;
        try {
            Desktop.getDesktop().browse(new URI("http://www.myfreecams.com/#" + gril.getName()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onDeleteWatch(ActionEvent event)
    {
        Gril gril = this.allGrilsListView.getSelectionModel().getSelectedItem();
        if (null != gril) {
            this.grilService.removeWatch(gril);
            this.infoPanel.setVisible(false);
            refreshListData();
        }
    }

    @FXML
    private void onDeleteAllWatch(ActionEvent event)
    {
        this.grilService.removeAllWatch();
        this.infoPanel.setVisible(false);
        refreshListData();
    }

    @FXML
    private void onRefresh()
    {
        refreshListData();
    }

    private void refreshListData()
    {
        this.allGrilsListView.setItems(grilService.listMyGrils());
    }

    private void showGril(final Gril gril)
    {
        if (null != gril)
        {
            if (null != gril.getImageUrl())
                this.grilImageView.setImage(new Image(gril.getImageUrl(), true));
            else
                this.grilImageView.setImage(null);
            this.grilNameView.setText(gril.getName());
            boolean isOnline = (gril.getState() == GrilState.ONLINE);
            this.grilStateView.setSelected(isOnline);
            this.grilStateView.setText(gril.getState().toString());
            this.infoPanel.setVisible(true);
        }
    }

    private void initListView()
    {
        this.allGrilsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        this.allGrilsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Gril>() {
            @Override
            public void changed(ObservableValue<? extends Gril> observable, Gril oldValue, Gril newValue) {
                showGril(newValue);
            }
        });

        Image image = new Image("assets/list_placeholder.jpg",true);

        ImageView placeHolderView = new ImageView(image);
        placeHolderView.setFitWidth(190);
        placeHolderView.setFitHeight(210);

        this.allGrilsListView.setPlaceholder(placeHolderView);
    }

    @Override
    public void someGrilsOnline(List<Gril> grils) {
        StringBuffer info = new StringBuffer();
        for (Gril gril : grils)
        {
            info.append(gril.getName()).append("\r\n");
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("通知");
        alert.setContentText(info.toString());
        alert.setHeaderText("上线啦！！！");
        alert.show();
        this.refreshListData();
    }
}
