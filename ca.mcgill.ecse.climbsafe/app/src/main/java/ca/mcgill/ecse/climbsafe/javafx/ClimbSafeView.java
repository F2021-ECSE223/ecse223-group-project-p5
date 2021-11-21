package ca.mcgill.ecse.climbsafe.javafx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClimbSafeView extends Application {
  /**
   * JavaFX Event class with a message
   * 
   * @author Michael Grieco
   */
  public static class MessageEvent extends Event {
    private String message;
    
    public MessageEvent(EventType<Event> type, String message) {
      super(type);
      this.message = message;
    }
    
    public String getMessage() {
      return this.message;
    }
  }

  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static ClimbSafeView instance;
  private List<Node> refreshableNodes = new ArrayList<>();
  private double xOffset = 0;
  private double yOffset = 0;

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      var root = (Pane) FXMLLoader.load(getClass().getResource("MainPage.fxml"));

      // handle mouse drags
      root.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          xOffset = event.getSceneX();
          yOffset = event.getSceneY();
        }
      });
      root.setOnMouseDragged(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
          primaryStage.setX(event.getScreenX() - xOffset);
          primaryStage.setY(event.getScreenY() - yOffset);
        }
      });

      var scene = new Scene(root);
      primaryStage.initStyle(StageStyle.UNDECORATED);
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(1133);
      primaryStage.setMinHeight(700);
      primaryStage.setTitle("ClimbSafe");
      primaryStage.show();
      refresh();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Register the node for receiving refresh events
  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  // Register multiple nodes for receiving refresh events
  public void registerRefreshEvent(Node... nodes) {
    for (var node : nodes) {
      refreshableNodes.add(node);
    }
  }

  // remove the node from receiving refresh events
  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  // fire the refresh event to all registered nodes
  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static ClimbSafeView getInstance() {
    return instance;
  }

}
