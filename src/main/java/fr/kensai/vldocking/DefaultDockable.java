package fr.kensai.vldocking;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;

import java.awt.*;

/**
 * Created by vivian on 26/12/14.
 */
public class DefaultDockable implements Dockable {

    private final DockKey key;
    private final Component component;

    public DefaultDockable(DockKey key, Component component) {
        this.key = key;
        this.component = component;
    }

    @Override
    public DockKey getDockKey() {
        return key;
    }

    @Override
    public Component getComponent() {
        return component;
    }
}
