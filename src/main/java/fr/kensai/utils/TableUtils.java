package fr.kensai.utils;

import javax.swing.*;
import java.util.List;

/**
 * Created by vivian on 22/12/14.
 */
public final class TableUtils {

    private TableUtils(){
        // Singleton
    }

    public static JScrollPane createCompagnyRankTable() {
        List<CompagnyRank> datas = DataUtils.createCompagnyRankDatas();
        CompagnyRankTableModel model = new CompagnyRankTableModel(datas);
        JTable table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        return pane;
    }
}
