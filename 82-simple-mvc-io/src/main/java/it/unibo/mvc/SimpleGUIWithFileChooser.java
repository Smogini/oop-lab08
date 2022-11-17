package it.unibo.mvc;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;

//import javax.swing.JFrame;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private static final String PATH = System.getProperty("user.home")
                                 + System.getProperty("file.separator");
    private final JFrame frame = new JFrame("My second Java graphical interface");
    private static final int PROPORTION = 5;

    /**
     * Set the layout.
     * @param controller
     */
    public SimpleGUIWithFileChooser(final Controller controller) {
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        canvas.add(textArea, BorderLayout.CENTER);
        final JButton save = new JButton("Save");
        canvas.add(save, BorderLayout.SOUTH);
        final JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
        final JTextField textField = new JTextField(controller.getPath());
        textField.setEditable(false);
        upperPanel.add(textField);
        final JButton browse = new JButton("Browse...");
        upperPanel.add(browse);
        canvas.add(upperPanel, BorderLayout.NORTH);
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Handlers */
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                try {
                    controller.saveString(textArea.getText());
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(frame, "Cannot save the text", "Alert", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        browse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                final JFileChooser fileChooser = new JFileChooser(PATH);
                final int returnVal = fileChooser.showSaveDialog(frame);
                switch (returnVal) {
                    case JFileChooser.APPROVE_OPTION: 
                        controller.setFile(fileChooser.getSelectedFile());
                        textField.setText(fileChooser.getSelectedFile().getAbsolutePath()); break;
                    case JFileChooser.CANCEL_OPTION: break;
                    default: JOptionPane.showMessageDialog(frame, "An error has occurred", "Alert", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    /**
     * Display the application on screen.
     */
    private void display() {
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / PROPORTION, sh / PROPORTION);
        frame.setLocationByPlatform(true);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Start the application.
     * @param args
     */
    public static void main(final String[] args) {
        final SimpleGUIWithFileChooser simpleGUI = new SimpleGUIWithFileChooser(new Controller());
        simpleGUI.display();
    }
}
