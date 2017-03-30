package risk.client.ui.gui.file;

import risk.common.network.Server;

import javax.swing.*;
import java.awt.*;

public class FilePanel extends JPanel {
    private Server server;
    private JButton playButton;
    private JButton exitButton;
    private JButton loadButton;
    private JButton saveButton;

    public FilePanel(Server server)  {
        this.server = server;
        this.server = server;

        //setPreferredSize(new Dimension(50, 150)); // TODO
        //setMinimumSize(new Dimension(50, 100)); // TODO
        //setBackground(Color.WHITE); // TODO
        setLayout(new GridLayout(2, 2)); // TODO

        playButton = createButton("risk-client/assets/play.png");
        exitButton = createButton("risk-client/assets/quit.png");
        loadButton = createButton("risk-client/assets/load.png");
        saveButton = createButton("risk-client/assets/save.png");
    }

    private JButton createButton(String imagePath) {
        ImageIcon exitIcon = new ImageIcon(imagePath);
        exitIcon.setImage(exitIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH));
        JButton button = new JButton(exitIcon);
        button.setContentAreaFilled(false);
        //button.setBorder(BorderFactory.createEmptyBorder());
        add(button);
        return button;
    }

    /**
     * @return the button to start the game
     */
    public JButton getPlayButton() {
        return playButton;
    }

    /**
     * @return the button to exit the game
     */
    public JButton getExitButton() {
        return exitButton;
    }

    /**
     * @return the button to load a game
     */
    public JButton getLoadButton() {
        return loadButton;
    }

    /**
     * @return the button to save the game
     */
    public JButton getSaveButton() {
        return saveButton;
    }
}