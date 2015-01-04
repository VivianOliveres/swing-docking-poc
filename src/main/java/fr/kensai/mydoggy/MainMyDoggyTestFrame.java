package fr.kensai.mydoggy;

import fr.kensai.utils.TableUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.noos.xing.mydoggy.*;
import org.noos.xing.mydoggy.event.ContentManagerUIEvent;
import org.noos.xing.mydoggy.plaf.MyDoggyToolWindowManager;
import org.noos.xing.mydoggy.plaf.ui.content.MyDoggyMultiSplitContentManagerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

/**
 * Created by vivian on 24/12/14.
 */
public class MainMyDoggyTestFrame {
    private static final Logger log = LogManager.getLogger(MainMyDoggyTestFrame.class);

    private JPanel contentPane = new JPanel(new BorderLayout(5 ,5));
    private MyDoggyToolWindowManager toolWindowManager = new MyDoggyToolWindowManager();

    public MainMyDoggyTestFrame() {
        initContentPane();
        contentPane.add(toolWindowManager, BorderLayout.CENTER);
        JPanel southPane = createSouthPane();
        contentPane.add(southPane, BorderLayout.SOUTH);
    }

    private void initContentPane() {
        MultiSplitContentManagerUI contentManagerUI = new MyDoggyMultiSplitContentManagerUI();
        contentManagerUI.setMinimizable(false);
        contentManagerUI.setDetachable(false);
        contentManagerUI.setCloseable(true);
        contentManagerUI.setMaximizable(true);

        ContentManager contentManager = toolWindowManager.getContentManager();
        contentManager.setContentManagerUI(contentManagerUI);

        contentManagerUI.addContentManagerUIListener(new ContentManagerUIListener() {
            public boolean contentUIRemoving(ContentManagerUIEvent event) {
                log.info("removing - [{}]", event.getId());
                return true;
            }

            public void contentUIDetached(ContentManagerUIEvent event) {
                log.info("detached - [{}]", event.getId());
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
        JScrollPane table = TableUtils.createCompagnyRankTable();
        String id = UUID.randomUUID().toString();
        String title = "Table of fortune500";
        log.info("Add table[{}] id[{}]", title, id);
        ContentManager contentManager = toolWindowManager.getContentManager();
        Content content = contentManager.addContent(id, title, null, table, "", new MultiSplitConstraint(AggregationPosition.BOTTOM));

        ContentManagerUI contentManagerUI = contentManager.getContentManagerUI();

        ContentUI contentUI = contentManagerUI.getContentUI(content);
        contentUI.setCloseable(true);
        contentUI.setDetachable(false);
        contentUI.setMinimizable(false);
        contentUI.setTransparentMode(true);
        contentUI.setTransparentRatio(0.7f);
        contentUI.setTransparentDelay(1000);
    }

    private JPanel getPane() {
        return contentPane;
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMyDoggyTestFrame controller = new MainMyDoggyTestFrame();
                controller.doAddTable();
                controller.doAddTable();
                controller.doAddTable();

                JFrame frame = new JFrame("Tests with MyDoggy");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setContentPane(controller.getPane());
                frame.setMinimumSize(new Dimension(500, 500));
                frame.setVisible(true);
            }
        });
    }
}
