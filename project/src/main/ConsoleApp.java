package main;

import main.ga.GeneticAlgorithm;
import main.parsers.configuration.CSVConfigurationParser;
import main.parsers.configuration.ConfigurationParser;
import main.parsers.configuration.XMLConfigurationParser;
import main.parsers.instances.InstanceReader;
import main.vpr.Problem;

import java.io.IOException;

public class ConsoleApp {

    // args[0] = -o -> console writing // -g -> graph
    // args[1] = instance file
    // args[2] = configuration file
    // args[3] = configuration number(s) if csv
    static public void main(String[] args) {
        int i = 0;
        String options = null;
        boolean consoleOutput = false;
        boolean displayCharts = false;

        if(args[i].charAt(0) == '-') {
            options = args[i].substring(1);
            i++;
        }

        GeneticAlgorithm ga;
        if(options != null) {
            if(options.contains("o")) {
                consoleOutput = true;
            }

            if(options.contains("g")) {
                displayCharts = true;
            }
            ga = new GeneticAlgorithm(consoleOutput, displayCharts);
        } else {
            ga = new GeneticAlgorithm();
        }

        // arg[1] -> instanceFile -> src/main.files/instances/kelly08
        String instanceName = args[i];
        InstanceReader instanceReader = new InstanceReader(instanceName);
        Problem problem = instanceReader.readInstance();

        i++;
        // arg[2] -> configurationFile
        // args[3] -> config number
        ConfigurationParser configurationParser;
        int configuration = 0;
        String configFile = args[i];
        if(configFile.contains(".xml")) {
            configurationParser = new XMLConfigurationParser(configFile);
        } else if(configFile.contains(".csv")) {
            configurationParser = new CSVConfigurationParser(configFile);
            i++;
        } else {
            throw new IllegalArgumentException("Not a valid argument: "+args[i]);
        }

        configurationParser.parse();


        try {
            if(configFile.contains(".csv")) {
                while(i < args.length) {
                    configuration = Integer.valueOf(args[i]);
                    ((CSVConfigurationParser) configurationParser).readConfig(configuration);
                    ga.configureAlgorithm(configurationParser, problem, instanceName);
                    ga.runAlgorithm();
                    i++;
                }
            } else {
                ga.configureAlgorithm(configurationParser, problem, instanceName);
                ga.runAlgorithm();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}