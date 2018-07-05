package main.parsers.results;

import java.io.IOException;

public interface ResultWriter {

    public void writeLine(String line) throws IOException;
    public void write(String string) throws IOException;

    public void flush() throws IOException;
    public void close() throws IOException;
}
