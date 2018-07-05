package main.parsers.instances;

import main.tfn.TFN;
import main.vpr.Problem;

import java.io.*;

public class InstanceReader {

    private final String filename;
    int numberOfClients;
    int vehicleCapacity;
    double[][] coordinates;
    double[][] stocasticCost;
    int[] demand;
    TFN[][] tfnCost;
    String[] clients;

    InstanceFuzzifier instanceFuzzifier;

    public InstanceReader(String filename) {
        this.filename = filename;
        this.instanceFuzzifier = new InstanceFuzzifier(filename);
    }

    public Problem readInstance() {
        readOriginalInstance();
        this.tfnCost = instanceFuzzifier.fuzzify(coordinates);
        instanceFuzzifier.writeNewInstance(vehicleCapacity, demand);

        clients = new String[numberOfClients];
        for(int i = 0; i < demand.length; i++) {
            clients[i] = "" + i+1;
        }

        return new Problem(vehicleCapacity, clients, demand, tfnCost);
    }

    // ORIGINAL STRUCTURE
    // n Q D d B
    // depot coordinates
    // x y q
    public void readOriginalInstance() {
        BufferedReader buffer = null;
        try {
            buffer = new BufferedReader(new FileReader(filename +".txt"));

            String line;

            // FIRST LINE -> problem configuration
            if((line = buffer.readLine()) != null) {
                String[] config = line.split(" ");

                this.numberOfClients = Integer.valueOf(config[0]);
                this.vehicleCapacity = Integer.valueOf(config[1]);
            }

            coordinates = new double[numberOfClients+1][2];

            // SECOND LINE -> depot coordinates
            double x = 0;
            double y = 0;
            if((line = buffer.readLine()) != null) {
                String[] depot = line.split(" +");
                x = Double.valueOf(depot[1]);
                y = Double.valueOf(depot[2]);
                coordinates[0] = new double[]{x, y};
            }

            demand = new int[numberOfClients];
            double a, b;
            int i = 0;
            while((line = buffer.readLine()) != null) {
                String[] client = line.split(" +");
                // fix for a problem with the input main.files
                if(client.length == 4){
                    for(int j = 1; j < 4; j++) {
                        client[j-1] = client[j];
                    }
                }
                a = Double.valueOf(client[0]);
                b = Double.valueOf(client[1]);
                coordinates[i+1] = new double[]{a, b};
                demand[i] = Integer.valueOf(client[2]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
            } catch (Exception e) {
            }
        }
    }




}
