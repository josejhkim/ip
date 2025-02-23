import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import john.John;

/**
 * A GUI for John using FXML.
 */
public class Main extends Application {

    /**
     * Use a random string to name the file to save data
     * to avoid conflicts with other pre-existing file names.
     */
    private final String randomString = "8f534ea74181d1a6ee1acae3413fd381";

    private John john = new John("john" + randomString + ".txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setTitle("John, Your Personal Assistant Chatbot");
            stage.getIcons().add(new Image("images/DaJohn.png"));
            fxmlLoader.<MainWindow>getController().setJohn(john); // inject the John instance

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
