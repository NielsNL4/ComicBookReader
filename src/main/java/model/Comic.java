package model;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Comic {
    private File file;
    private String title;
    private String coverImagePath;
    private int currentPage;
    private PDDocument document;

    public Comic(File file, String title) throws IOException {
        this.file = file;
        this.title = title;
        this.document = PDDocument.load(file);
        this.currentPage = 0;
        this.coverImagePath = extractCoverImage();
    }

    public File getFile() { return file; }

    public String getTitle() { return title; }

    public String getCoverImagePath() { return coverImagePath; }

    public int getCurrentPage() { return currentPage; }

    public void setCurrentPage(int currentPage) { this.currentPage = currentPage; }

    private String extractCoverImage() throws IOException {
        // Create PDFRenderer for the document
        PDFRenderer pdfRenderer = new PDFRenderer(document);

        // Render the first page (index 0) as a BufferedImage
        BufferedImage firstPageImage = pdfRenderer.renderImage(0);

        // Save the BufferedImage as a JPEG file
        String coverImagePath = "covers/" + title.replaceAll("\\s+", "_") + "_cover.jpg";
        File coverImageFile = new File(coverImagePath);

        // Create directories if not exist
        coverImageFile.getParentFile().mkdirs();

        // Save the image
        ImageIO.write(firstPageImage, "jpg", coverImageFile);

        return coverImagePath;
    }

}


