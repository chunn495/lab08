package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() {
    }

    private static final String[] VIEWS_TYPES = { "Swing", "StandardOutput" };

    /**
     * Runs the application.
     *
     * @throws ClassNotFoundException    if the fetches class does not exist
     * @throws NoSuchMethodException     if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException    if the constructor throws exceptions
     * @throws IllegalAccessException    in case of reflection issues
     * @throws IllegalArgumentException  in case of reflection issues
     */
    public static void main(final String[] args) throws ClassNotFoundException,
            NoSuchMethodException, InvocationTargetException, InstantiationException,
            IllegalAccessException, IllegalArgumentException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        for (final String viewType : VIEWS_TYPES) {
            final String classViewName = "it.unibo.mvc.view.DrawNumber" + viewType + "View";
            final Class<?> classView = Class.forName(classViewName);
            final Constructor<?> constructorView = classView.getConstructor();
            for (int i = 0; i < 3; i++) {
                app.addView((DrawNumberView) constructorView.newInstance());
            }
        }
    }
}
