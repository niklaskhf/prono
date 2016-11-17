package com.team16.sopra.sopra16team16.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by moo on 11/17/16.
 */

public class CSVUtils {

    private static final char DEFAULT_SEPARATOR = ',';

    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    //https://tools.ietf.org/html/rfc4180
    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());
    }


    public static ArrayList<String[]> readContactFile(String path) {
        BufferedReader br = null;
        String line = "";
        String csvSplitBy = ",";
        ArrayList<String[]> strings = new ArrayList<String[]>();


        try{
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    strings.add(line.split(csvSplitBy));
                }
            }
        } catch (Exception e) {
            // handle exception
            e.printStackTrace();
        }
        return strings;
    }
}

