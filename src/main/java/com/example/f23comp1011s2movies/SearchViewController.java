package com.example.f23comp1011s2movies;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class SearchViewController {

    @FXML
    private ListView<Movie> listView;

    @FXML
    private Label msgLabel;

    @FXML
    private ImageView posterImageView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private HBox resultsBox;

    @FXML
    private TextField searchTextField;

    @FXML
    private VBox selectedVBox;

    @FXML
    private void initialize()
    {
        progressBar.setVisible(false);
        selectedVBox.setVisible(false);
        selectedVBox.setVisible(false);

        //configure the listview with a listener so that when a movie is selected, it will
        //display the poster art
        listView.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, movieSelected) -> {
                    if (movieSelected != null)
                    {
                        selectedVBox.setVisible(true);
                        try {
                            posterImageView.setImage(new Image(movieSelected.getPoster()));
                        } catch (IllegalArgumentException e) {
                            posterImageView.setImage(new Image(Main.class
                                    .getResourceAsStream("images/default_poster.png")));
                        }
                    }
                    else
                    {
                        selectedVBox.setVisible(false);
                    }
                });
    }

    @FXML
    void searchOMDB(ActionEvent event) throws IOException, InterruptedException {
        String movieName = searchTextField.getText();
        msgLabel.setVisible(false);
        if(!movieName.trim().isEmpty())
        {
            APIResponse apiResponse = APIUtility.searchMovies(movieName.trim());
            listView.getItems().clear();
            listView.getItems().addAll(apiResponse.getMovies());
        }
        else
        {
            msgLabel.setVisible(true);
            msgLabel.setText("Enter a movie title in the search field");
        }
    }

}
