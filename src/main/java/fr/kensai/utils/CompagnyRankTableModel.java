package fr.kensai.utils;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by vivian on 22/12/14.
 */
public class CompagnyRankTableModel extends AbstractTableModel {

    private List<CompagnyRank> datas;

    public CompagnyRankTableModel(List<CompagnyRank> datas) {
        this.datas = datas;
    }

    @Override
    public int getRowCount() {
        return datas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CompagnyRank row = datas.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getYear();
            case 1:
                return row.getRank();
            case 2:
                return row.getCompany();
            case 3:
                return row.getRevenue();
            case 4:
                return row.getProfit();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Year";
            case 1:
                return "Rank";
            case 2:
                return "Company";
            case 3:
                return "Revenue";
            case 4:
                return "Profit";
            default:
                return "";
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return Double.class;
            case 4:
                return Double.class;
            default:
                return String.class;
        }
    }
}

