package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import model.Comic;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class ComicViewer {
    private int currentPage = 0;
    private int totalPages = 0;
    private JLabel comicPageLabel;
    private PDDocument document;
    private PDFRenderer pdfRenderer;
    private final int panelWidth;
    private final int panelHeight;

    public ComicViewer(Comic comic, int panelWidth, int panelHeight) {
        this.panelWidth = panelWidth;
        this.panelHeight = panelHeight;
        try {
            // Load the PDF document
            document = PDDocument.load(comic.getFile()); // Use the getFilePath method
            pdfRenderer = new PDFRenderer(document);
            totalPages = document.getNumberOfPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Create a JPanel to display the comic page and navigation buttons
    public JPanel getComicPanel() {
        JPanel comicPanel = new JPanel();
        comicPanel.setLayout(new BorderLayout());

        comicPageLabel = new JLabel();
        loadPage(currentPage); // Load the first page
        comicPanel.add(comicPageLabel, BorderLayout.CENTER);

        // Invisible buttons for left and right navigation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));

        // Left (previous) button
        JButton leftButton = new JButton("Previous");
        leftButton.addActionListener(e -> navigatePage(-1)); // Navigate to the previous page
        buttonPanel.add(leftButton);

        // Right (next) button
        JButton rightButton = new JButton("Next");
        rightButton.addActionListener(e -> navigatePage(1)); // Navigate to the next page
        buttonPanel.add(rightButton);

        comicPanel.add(buttonPanel, BorderLayout.SOUTH);
        return comicPanel; // Return the comic panel
    }

    // Load and display the specified page
    private void loadPage(int pageIndex) {
        try {
            if (pageIndex >= 0 && pageIndex < totalPages) {
                BufferedImage pageImage = pdfRenderer.renderImageWithDPI(pageIndex, 150); // Render page at 150 DPI

                // Scale image to fit the panel while maintaining aspect ratio
                Image scaledImage = scaleImageToFit(pageImage);
                ImageIcon pageIcon = new ImageIcon(scaledImage);
                comicPageLabel.setIcon(pageIcon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Scale the image while maintaining aspect ratio
    private Image scaleImageToFit(BufferedImage image) {
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        double aspectRatio = (double) imageWidth / (double) imageHeight;
        int newWidth = panelWidth;
        int newHeight = (int) (panelWidth / aspectRatio);

        if (newHeight > panelHeight) {
            newHeight = panelHeight;
            newWidth = (int) (panelHeight * aspectRatio);
        }

        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    // Navigate between pages
    private void navigatePage(int direction) {
        int newPageIndex = currentPage + direction;
        if (newPageIndex >= 0 && newPageIndex < totalPages) {
            currentPage = newPageIndex;
            loadPage(currentPage);
        }
    }

    @Override
    public void finalize() {
        try {
            if (document != null) {
                document.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
