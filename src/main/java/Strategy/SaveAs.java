package Strategy;

import com.fon.p1.abstractFactory.AbstractFactory;
import com.fon.p1.abstractFactory.FactoryProducer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveAs implements Strategy {

    public AbstractFactory errorFactory = FactoryProducer.getFactory("error");
    @FXML
    private Stage controllerStage;

    @Override
    public String save(String filePath, String text) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));

        File textFile = fileChooser.showSaveDialog(controllerStage);
        if (textFile != null) {
            filePath = textFile.getAbsolutePath();
            try {
                FileWriter writer = new FileWriter(textFile);
                writer.write(text);
                writer.close();

            } catch (IOException e) {
                System.out.println("File not found");
            }
        }
        return filePath;

    }

}
