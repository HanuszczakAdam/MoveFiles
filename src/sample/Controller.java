package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Controller {

    private String imageFolderUri;
    private String destinationFolderUri;
    private String fileUri;
    private boolean imageFolderCheck;
    private boolean destinationFolderCheck;
    private boolean fileCheck;

    //BUTTON SECTION
    @FXML
    private Button imageFolderButton;

    @FXML
    private Button destinationFolderButton;

    @FXML
    private Button moveFilesButton;

    //LABEL SECTION
    @FXML
    private Label imageFolderLabel;

    @FXML
    private Label destinationFolderLabel;

    @FXML
    private Label outputFileLabel;

    @FXML
    private Label informationLabel;

    //IMAGE SECTION
    @FXML
    private ImageView imageView;

    @FXML
    void initialize() {
        loadOkImage();
        actionsOnClickButton();
    }

    @FXML
    public void chooseImageFolder() {
        DirectoryChooser imageDirectoryChooser = new DirectoryChooser();
        File selectedImageDirectory = imageDirectoryChooser.showDialog(new Stage());
        if (selectedImageDirectory == null)
            imageFolderLabel.setText("No Directory selected");
        else {
            imageFolderLabel.setText(selectedImageDirectory.getAbsolutePath());
            imageFolderUri = selectedImageDirectory.getAbsolutePath();
            imageFolderCheck = true;
            actionsOnClickButton();
        }
    }

    @FXML
    public void chooseDestinationFolder() {
        DirectoryChooser imageDirectoryChooser = new DirectoryChooser();
        File selectedImageDirectory = imageDirectoryChooser.showDialog(new Stage());
        if (selectedImageDirectory == null)
            destinationFolderLabel.setText("No Directory selected");
        else {
            destinationFolderLabel.setText(selectedImageDirectory.getAbsolutePath());
            destinationFolderUri = selectedImageDirectory.getAbsolutePath();
            destinationFolderCheck = true;
            actionsOnClickButton();
        }
    }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(new Stage());
        if (file == null)
            outputFileLabel.setText("No File selected");
        else {
            outputFileLabel.setText(file.getAbsolutePath());
            fileUri = file.getAbsolutePath();
            fileCheck = true;
            actionsOnClickButton();
        }
    }

    @FXML
    public void moveFiles() {
        readLine();
    }

    private void activateMoveFilesButton() {
        if (fileCheck && imageFolderCheck && destinationFolderCheck) moveFilesButton.setDisable(false);
    }

    private void readLine() {
        try
        {
            File file = new File(fileUri);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null)
            {
                String fileName = splitLine(line);
                try {
                    File singleImageFile = new File(imageFolderUri + "/" + fileName);
                    if (singleImageFile.exists()) {
                        informationLabel.setText("Moving file: " + imageFolderUri + "/" + fileName);
                        Files.move(Paths.get(imageFolderUri + "/" + fileName), Paths.get(destinationFolderUri + "/" + fileName), StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                clearInformationLabel();
            }
            fr.close();
            imageView.setVisible(true);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private String splitLine(String line) {
        String[] splitLine = line.split(",");
        return splitLine[0];
    }

    private void clearInformationLabel() {
        informationLabel.setText("");
    }

    private void actionsOnClickButton() {
        imageView.setVisible(false);
        activateMoveFilesButton();
    }

    private void loadOkImage() {
        try {
            Image image = new Image(Controller.class.getResourceAsStream("/resources/ok.png"));
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
