package agh.ics.oop.GUI;


import com.opencsv.CSVWriter;

import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;


import java.util.ArrayList;

public class CSVCreator {
    public static void writeDataLineByLine(ArrayList<String[]> historyData)
    {
        try {
            Writer writer = Files.newBufferedWriter(Paths.get("./result.csv"));

            CSVWriter csvWriter = new CSVWriter(writer,
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            String[] header = { "Epoka", "Ilosc zwierzat", "Ilosc trawy","Srednia energia","Srednia dlugosc zycia", "Srednia ilosc dzieci" };
            csvWriter.writeNext(header);

            csvWriter.writeAll(historyData);

            String[] average = {"SREDNIA:","","","","",""};
            for (int i = 1; i < 6;i++)
            {
                int avg = Integer.parseInt(historyData.get(0)[i]);
                for (int j = 1; j < historyData.size(); j++)
                {
                    avg = (avg + Integer.parseInt(historyData.get(j)[i]))/2;
                }
                average[i] = String.valueOf(avg);
            }

            csvWriter.writeNext(average);

            csvWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
