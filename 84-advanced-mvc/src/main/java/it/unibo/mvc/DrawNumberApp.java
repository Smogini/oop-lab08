package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {
    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final String path, final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view: views) {
            view.setObserver(this);
            view.start();
        }
        Configuration.Builder builder = new Configuration.Builder();
        try (final BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            String[] arr;
            int val;
            while ((line = reader.readLine()) != null) {
                arr = line.split(": ");
                val = Integer.valueOf(arr[1]);
                if (arr[0].contains("max")) {
                    builder.setMax(val);
                } else if (arr[0].contains("min")) {
                    builder.setMin(val);
                } else if (arr[0].contains("attempts")) {
                    builder.setAttempts(val);
                } else {
                    displayError("Cannot understand the values in " + path);
                }
            }
            reader.close();
        } catch (final IOException e) {
            displayError(e.getMessage());
        }
        final Configuration config = builder.build();
        if (config.isConsistent()) {
           this.model = new DrawNumberImpl(config);
        } else {
            displayError("Configuration file is inconsistend. Using default settings");
            this.model = new DrawNumberImpl(new Configuration.Builder().build());
        }
    }

    private void displayError(final String message) {
        for (final var err : views) {
            err.displayError(message);
        }
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("src/main/resources/config.yml", 
                            new DrawNumberViewImpl(),
                            new DrawNumberViewImpl(),
                            new PrintStreamView(System.out));
    }
}
