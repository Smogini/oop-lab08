package it.unibo.mvc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A very simple program using a graphical interface.
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();
    private static final int PROPORTION = 2;

    /**
     * Set up the whole view.
     * @param controller
     */
    public SimpleGUI(final Controller controller) {
        /* Create the panel and add the text field and text area. */
        final JPanel canvas = new JPanel();
        canvas.setLayout(new BorderLayout());
        final JTextField upperTextField = new JTextField();
        canvas.add(upperTextField, BorderLayout.NORTH);
        final JTextArea centerTextArea = new JTextArea();
        centerTextArea.setEditable(false);
        canvas.add(centerTextArea, BorderLayout.CENTER);
        /* create a new lower panel with box layout and add the two buttons */
        final JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new BoxLayout(lowerPanel, BoxLayout.X_AXIS));
        canvas.add(lowerPanel, BorderLayout.SOUTH); // add the lower panel to the first one
        final JButton print = new JButton("Print");
        lowerPanel.add(print);
        final JButton show = new JButton("Show");
        lowerPanel.add(show);
        frame.setContentPane(canvas);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Handlers */
        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                controller.setNextString(upperTextField.getText());
                controller.printCurrentString();
                upperTextField.setText("");
            }
        });
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent arg0) {
                final StringBuilder text = new StringBuilder();
                final List<String> history = controller.getPrintedStrings();
                for (final var element : history) {
                    text.append(element);
                    text.append('\n');
                }
                centerTextArea.setText(text.toString());
            }
        });
    }

    /**
     * Starts the graphical application.
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
     * 
     * @param args
     */
    public static void main(final String[] args) {
        final SimpleGUI gui = new SimpleGUI(new SimpleController());
        gui.display();
    }
}
