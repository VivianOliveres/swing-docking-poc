package fr.kensai.infonode;

import net.infonode.docking.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by vivian on 28/12/14.
 */
public class DynamicView extends View {
    private final int id;

    /**
     * Constructor.
     *
     * @param title     the view title
     * @param icon      the view icon
     * @param component the view component
     * @param id        the view id
     */
    public DynamicView(String title, Icon icon, Component component, int id) {
        super(title, icon, component);
        this.id = id;
    }

    /**
     * Returns the view id.
     *
     * @return the view id
     */
    public int getId() {
        return id;
    }
}