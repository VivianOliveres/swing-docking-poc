package fr.kensai.raven;

import com.kitfox.docking.DockingContent;
import com.kitfox.docking.DockingRegionRoot;
import fr.kensai.utils.TableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Created by vivian on 22/12/14.
 */
public class DockingRavenTestFrame {
    private static final Logger log = LogManager.getLogger(DockingRavenTestFrame.class);

    private JPanel contentPane = new JPanel(new BorderLayout(5 ,5));
    private DockingRegionRoot dockPanel = new DockingRegionRoot();

    public DockingRavenTestFrame() {
        contentPane.add(dockPanel, BorderLayout.CENTER);
        JPanel southPane = createSouthPane();
        contentPane.add(southPane, BorderLayout.SOUTH);
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
        JScrollPane table = TableUtils.createCompagnyRankTable();
        String id = UUID.randomUUID().toString();
        String title = "Table of fortune500";
        log.info("Add table[{}] id[{}]", title, id);
        DockingContent cont = new DockingContent(id, title, table);
        dockPanel.getDockingRoot().addDockContent(cont);
    }


    private JPanel getPane() {
        return contentPane;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DockingRavenTestFrame controller = new DockingRavenTestFrame();
                controller.doAddTable();
                controller.doAddTable();
                controller.doAddTable();

                JFrame frame = new JFrame("Tests with RavenDocking");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(controller.getPane());
                frame.setMinimumSize(new Dimension(500, 500));
                frame.setVisible(true);
            }
        });
    }

}
