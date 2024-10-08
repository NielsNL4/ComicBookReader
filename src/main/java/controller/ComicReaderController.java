package controller;

import model.Comic;
import view.ComicReaderView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComicReaderController {
    private List<Comic> comics;
    private ComicReaderView view;

    public ComicReaderController() {
        this.comics = new ArrayList<>();
    }

   public void addComic(File comicFile, String title) {
        try {
            Comic comic = new Comic(comicFile, title);
            comics.add(comic);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startApplication() {
        view = new ComicReaderView(comics);
    }
}
