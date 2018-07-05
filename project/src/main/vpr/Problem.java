package main.vpr;

import main.tfn.TFN;

public class Problem {

    int vehicleCapacity;

    String[] clients;
    int[] demand;
    TFN[][] cost;

    public Problem(int vehicleCapacity, String[] clients, int[] demand, TFN[][] cost) {
        this.vehicleCapacity = vehicleCapacity;

        this.clients = clients;
        this.demand = demand;
        this.cost = cost;

    }

    public int getNumberOfClients() { return this.clients.length; }

    public int getVehicleCapacity() {
        return this.vehicleCapacity;
    }

    public int demand(int client) {
        return demand[client-1];
    }

    public TFN cost(int from, int to) {
        return cost[from][to];
    }
}
