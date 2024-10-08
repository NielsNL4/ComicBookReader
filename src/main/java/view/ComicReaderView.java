package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import model.Comic;
import java.util.List;
import java.util.ArrayList;

public class ComicReaderView extends JFrame {
    private JPanel coverPanel;

    // Maximum height for each comic cover panel (fixed)
    private static final int PANEL_MAX_HEIGHT = 300; 

    public ComicReaderView(List<Comic> comics) {
        this.setTitle("Comic Reader");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);

        // Get the screen width
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        // Calculate panel width as 10% of the screen width
        int panelMaxWidth = (int) (screenWidth * 0.10); // 10% of the screen width

        coverPanel = new JPanel();
        coverPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Adjust spacing between buttons

        for (Comic comic : comics) {
            try {
                // Load the cover image from the file path
                BufferedImage coverImage = ImageIO.read(new File(comic.getCoverImagePath()));

                // Resize the cover image to fit the panel
                Image resizedImage = coverImage.getScaledInstance(panelMaxWidth, PANEL_MAX_HEIGHT, Image.SCALE_SMOOTH);

                // Add the resized image to a JButton
                JButton comicButton = new JButton(new ImageIcon(resizedImage));
                comicButton.addActionListener(e -> openComicReader(comic)); // Open comic reader when clicked

                // Set the button's size to match the image size
                Dimension buttonSize = new Dimension(panelMaxWidth, PANEL_MAX_HEIGHT);
                comicButton.setPreferredSize(buttonSize);
                comicButton.setMaximumSize(buttonSize);
                comicButton.setMinimumSize(buttonSize);

                coverPanel.add(comicButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JScrollPane scrollPane = new JScrollPane(coverPanel);
        scrollPane.setPreferredSize(new Dimension(800, 600)); // Adjust to your preferred window size

        this.add(scrollPane, BorderLayout.CENTER);
        this.setVisible(true);
    }

    // Method to open the comic reader panel
    private void openComicReader(Comic comic) {
    try {
        JPanel comicPanel = new ComicViewer(comic, coverPanel.getWidth(), coverPanel.getHeight()).getComicPanel();
        this.getContentPane().removeAll(); // Clear the existing content
        this.add(comicPanel, BorderLayout.CENTER); // Add the comic panel
        this.revalidate(); // Refresh the frame
        this.repaint(); // Repaint the frame
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
