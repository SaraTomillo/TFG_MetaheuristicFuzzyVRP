package main.parsers.results;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CSVResultWriter implements ResultWriter {

    char separator = ';';
    String filename;
    Writer writer;

    public CSVResultWriter(String filename) throws IOException {
        File directory = new File("results");

        if(!directory.exists()) {
            try {
                directory.mkdir();
            } catch (SecurityException se) {
                //handle it
            }
        }

        this.filename = filename + ".csv";
        this.writer = new FileWriter(this.filename);
    }


    @Override
    public void writeLine(String line) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        sb.append(separator);
        sb.append("\n");
        writer.append(sb.toString());
    }

    @Override
    public void write(String line) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(line);
        sb.append(separator);
        writer.append(sb.toString());
    }

    @Override
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }


}
