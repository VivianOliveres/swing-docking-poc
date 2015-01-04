package fr.kensai.vldocking;

import com.vlsolutions.swing.docking.*;
import com.vlsolutions.swing.docking.event.DockingActionEvent;
import com.vlsolutions.swing.docking.event.DockingActionListener;
import fr.kensai.utils.TableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Created by vivian on 26/12/14.
 */
public class MainVlDocking {
    private static final Logger log = LogManager.getLogger(MainVlDocking.class);

    private JPanel contentPane = new JPanel(new BorderLayout(5, 5));
    private DockingDesktop desktop = new DockingDesktop();

    public MainVlDocking() {
        initContentPane();
        contentPane.add(desktop, BorderLayout.CENTER);
        JPanel southPane = createSouthPane();
        contentPane.add(southPane, BorderLayout.SOUTH);
    }

    private void initContentPane() {
        //TODO
        desktop.addDockingActionListener(new DockingActionListener() {
            @Override
            public boolean acceptDockingAction(DockingActionEvent event) {
                // Discard tab event during D&D
                if (event.getActionType() == DockingActionEvent.ACTION_CREATE_TAB) {
//                    log.info("DockingActionEvent[ACTION_CREATE_TAB]");
                    return false;
                }

                return true;
            }

            @Override
            public void dockingActionPerformed(DockingActionEvent event) {
                // Do nothing
            }
        });
    }

    private JPanel createSouthPane() {
        JPanel pane = new JPanel();

        JButton addTable = new JButton("New table");
        pane.add(addTable);
        addTable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doAddTable();
            }
        });

        return pane;
    }

    private void doAddTable() {
        doAddTable("Table of fortune500");
    }

    private void doAddTable(String title) {
        JScrollPane table = TableUtils.createCompagnyRankTable();
        String id = UUID.randomUUID().toString();
        log.info("Add table[{}] id[{}]", title, id);

        DockKey key = new DockKey(id, title);
        DefaultDockable dockable = new DefaultDockable(key, table);
//        desktop.addDockable(dockable, RelativeDockablePosition.BOTTOM_CENTER);
        if (desktop.getDockables() == null || desktop.getDockables().length == 0) {
            desktop.addDockable(dockable);
        } else {
            Dockable selectedDockable = desktop.getSelectedDockable() == null ? desktop.getDockables()[0].getDockable() : desktop.getSelectedDockable();
            desktop.split(selectedDockable, dockable, DockingConstants.SPLIT_BOTTOM);
        }
    }

    private JPanel getPane() {
        return contentPane;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainVlDocking controller = new MainVlDocking();
                controller.doAddTable("First");
                controller.doAddTable("Second");
                controller.doAddTable("Third");

                JFrame frame = new JFrame("Tests with VlDocking");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(controller.getPane());
                frame.setMinimumSize(new Dimension(500, 500));
                frame.setVisible(true);
            }
        });
    }
}
