package fr.kensai.infonode;

import fr.kensai.utils.TableUtils;
import net.infonode.docking.*;
import net.infonode.docking.drag.DockingWindowDragSource;
import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drag.DockingWindowDraggerProvider;
import net.infonode.docking.drop.DropFilter;
import net.infonode.docking.drop.DropInfo;
import net.infonode.docking.mouse.DockingWindowActionMouseButtonListener;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.PropertiesUtil;
import net.infonode.docking.util.ViewMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.UUID;

/**
 * Created by vivian on 28/12/14.
 */
public class MainInfoNode {
    private static final Logger log = LogManager.getLogger(MainInfoNode.class);

    private JPanel contentPane = new JPanel(new BorderLayout(5, 5));
    private RootWindow rootWindow = DockingUtil.createRootWindow(new ViewMap(), true);

    private static volatile int idView = 0;

    public MainInfoNode() {
        initContentPane();
        contentPane.add(rootWindow, BorderLayout.CENTER);
        JPanel southPane = createSouthPane();
        contentPane.add(southPane, BorderLayout.SOUTH);
    }

    private void initContentPane() {
        //TODO

        // Use default title bar
        RootWindowProperties properties = new RootWindowProperties();
        RootWindowProperties titleBarStyleProperties = PropertiesUtil.createTitleBarStyleRootWindowProperties();
        properties.addSuperObject(titleBarStyleProperties);

        // Add a Drop filter for tabs
        DropFilter filter = new DropFilter() {
            @Override
            public boolean acceptDrop(DropInfo dropInfo) {
                return false;
            }
        };
        properties.getDockingWindowProperties().getDropFilterProperties().setInteriorDropFilter(filter);

        // Set properties to rootWindows
        rootWindow.getRootWindowProperties().addSuperObject(properties);

        // Middle button close the view
        rootWindow.addTabMouseButtonListener(DockingWindowActionMouseButtonListener.MIDDLE_BUTTON_CLOSE_LISTENER);

        // Update delete
        rootWindow.addListener(new DockingWindowAdapter() {
            @Override
            public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
                //Nothing to do
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

        JButton dragButton = new JButton("Drag & Drop");
        pane.add(dragButton);
        new DockingWindowDragSource(dragButton, new DockingWindowDraggerProvider() {
            public DockingWindowDragger getDragger(MouseEvent mouseEvent) {
                String title = "Drag&Drop";
                DynamicView view = createTab(title);
                return view.startDrag(rootWindow);
            }
        });

        return pane;
    }

    private DynamicView createTab(String title) {
        JScrollPane table = TableUtils.createCompagnyRankTable();
        String id = UUID.randomUUID().toString();
        log.info("Add table[{}] id[{}]", title, id);

        DynamicView view = new DynamicView(title, null, table, idView++);
        return view;
    }

    private void doAddTable() {
        doAddTable("Table of fortune500");
    }

    private void doAddTable(String title) {
        DynamicView view = createTab(title);

        // Manage layout programatically
        DockingWindow myLayout = null;
        DockingWindow window = rootWindow.getWindow();
        if (window instanceof TabWindow && ((TabWindow)window).getChildWindowCount() == 0) {
            // Remove first empty TabWindow
            myLayout = view;
        }else {
            int dynamicViews = getDynamicViewCount(window) + 1;
            double rate = 1 - (1.0 / dynamicViews);
            myLayout = new SplitWindow(false, (float)rate, window, view);
        }

        rootWindow.setWindow(myLayout);
    }

    private int getDynamicViewCount(DockingWindow window){
        if (window.getChildWindowCount() == 0) {
            if (window instanceof DynamicView){
                return 1;
            }else{
                return 0;
            }
        }

        int count = 0;
        for (int i = 0; i < window.getChildWindowCount(); i++) {
            count += getDynamicViewCount(window.getChildWindow(i));
        }
        return count;
    }

    private JPanel getPane() {
        return contentPane;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainInfoNode controller = new MainInfoNode();
                controller.doAddTable("First");
                controller.doAddTable("Second");
                controller.doAddTable("Third");

                JFrame frame = new JFrame("Tests with InfoNode");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(controller.getPane());
                frame.setMinimumSize(new Dimension(500, 500));
                frame.setVisible(true);

//                log.info(DeveloperUtil.getWindowLayoutAsString(controller.rootWindow));
            }
        });
    }
}
