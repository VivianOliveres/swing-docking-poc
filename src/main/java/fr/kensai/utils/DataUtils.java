package fr.kensai.utils;

import com.google.common.collect.Lists;
import com.googlecode.jcsv.CSVStrategy;
import com.googlecode.jcsv.annotations.internal.ValueProcessorProvider;
import com.googlecode.jcsv.reader.CSVEntryParser;
import com.googlecode.jcsv.reader.CSVReader;
import com.googlecode.jcsv.reader.CSVTokenizer;
import com.googlecode.jcsv.reader.internal.AnnotationEntryParser;
import com.googlecode.jcsv.reader.internal.CSVReaderBuilder;
import com.googlecode.jcsv.reader.internal.SimpleCSVTokenizer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vivian on 22/12/14.
 */
public final class DataUtils {

    private static final Logger log = LogManager.getLogger(DataUtils.class);
    private static final List<CompagnyRank> datas = new ArrayList<CompagnyRank>();

    private DataUtils() {
        // Singleton
    }

    public static final synchronized List<CompagnyRank> createCompagnyRankDatas() {
        if (!datas.isEmpty()) {
            return Lists.newArrayList(datas);
        }

        try (Reader reader = new FileReader("src/main/resources/fortune500.csv")) {
            CSVStrategy strategy = new CSVStrategy(';', '\"', '#', true, true);
            ValueProcessorProvider provider = new ValueProcessorProvider();
            CSVEntryParser<CompagnyRank> entryParser = new AnnotationEntryParser<CompagnyRank>(CompagnyRank.class, provider);
            CSVTokenizer tokenizer = new SimpleCSVTokenizer();
            CSVReader<CompagnyRank> csvReader = new CSVReaderBuilder<CompagnyRank>(reader)
                                                        .strategy(strategy)
                                                        .entryParser(entryParser)
                                                        .build();
            List<CompagnyRank> readDatas = csvReader.readAll();
            datas.addAll(readDatas);
            return Lists.newArrayList(datas);
        } catch (Exception e) {
            log.error("Can not read datas from fortune500.csv", e);
        }

        return Lists.newArrayList(datas);
    }
}
