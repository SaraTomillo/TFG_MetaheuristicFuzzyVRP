package main.parsers.instances;

import main.tfn.TFN;
import java.io.*;
import java.util.Random;

public class InstanceFuzzifier {

    int numberOfClients;
    double[][] coordinates;
    double[][] stocasticCost;
    TFN[][] tfnCost;
    String filename;

    public InstanceFuzzifier(String filename){ this.filename = filename; }

    public TFN[][] fuzzify(double[][] coordinates) {
        this.coordinates = coordinates;
        this.numberOfClients = coordinates.length-1;
        calculateCost();
        return convertToFuzzy();
    }


    // distance (x,y) to (a,b) = sqrt((x - a)^2 + (y - b)^2)
    // (x,y) = client1
    // (a,b) = client2
    private void calculateCost() {
        stocasticCost = new double[numberOfClients+1][numberOfClients+1];
        double cost = 0;
        double x, y, a, b;
        for(int i = 0; i < stocasticCost.length; i++){
            for(int j = 0; j < stocasticCost[i].length; j++) {
                x = coordinates[i][0];
                y = coordinates[i][1];
                a = coordinates[j][0];
                b = coordinates[j][1];

                cost = Math.abs(Math.sqrt(Math.pow((x - a), 2) + Math.pow((y -b),2)));
                stocasticCost[i][j] = cost;
            }
        }
    }

    private TFN[][] convertToFuzzy() {
        tfnCost = new TFN[numberOfClients+1][numberOfClients+1];
        TFN tfn;
        double cost, alfa;
        Random random = new Random();
        for(int i = 0; i < stocasticCost.length; i++) {
            for(int j = 0; j < stocasticCost[i].length; j++) {
                cost = stocasticCost[i][j];
                // fuzzificar el coste
                // c -> (c1, c2, c3)
                // c2 = c;
                // c1 = c - alfa; c3 = c + alfa;
                //alfa = cost*0.85 + (cost - 0.85*cost) * random.nextDouble();
                alfa = (0.15) * random.nextDouble();
                tfn = new TFN(cost - alfa, cost, cost + alfa);
                tfnCost[i][j] = tfn;
                //tfnCost[i][j] = new TFN(cost, cost, cost);
            }
        }

        return tfnCost;
    }

    // NEW INSTANCE STRUCTURE
    // n Q
    // id q
    //
    // (c1,c2,c3) (c1,c2,c3) ...
    // (c1,c2,c3) (c1,c2,c3) ...
    // ...
    public void writeNewInstance(int vehicleCapacity, int[] demand) {
        BufferedWriter writer = null;
        try {
            File file = new File(filename+"-fuzzy.txt");
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(numberOfClients + " " + vehicleCapacity);
            writer.write("\n");
            for(int i = 0; i < demand.length; i++) {
                writer.write(i+1 + "\t" + demand[i]);
                writer.write("\n");
            }
            writer.write("\n");
            double[] tfn;
            for(int i = 0; i < tfnCost.length; i++) {
                for(int j = 0; j < tfnCost[0].length; j++) {
                    tfn = tfnCost[i][j].getT();
                    writer.write("(" + tfn[0] + "," + tfn[1] + "," + tfn[2] + ")\t");
                }
                writer.write("\n");
            }

            writer.flush();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
