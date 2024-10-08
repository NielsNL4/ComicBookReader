import controller.ComicReaderController;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        ComicReaderController controller = new ComicReaderController();
        controller.addComic(new File("C:\\Users\\n" + //
                        "iels\\Documents\\comicreaderJava\\src\\main\\java\\comics\\Invincible.pdf"), "Invincible");

        controller.addComic(new File("C:\\Users\\n" + //
                        "iels\\Documents\\comicreaderJava\\src\\main\\java\\comics\\Batman.pdf"), "Batman");
        controller.startApplication();
    }
}
