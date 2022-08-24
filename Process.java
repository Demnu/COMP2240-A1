public class Process implements Comparable<Process> {
    private String id;
    private int arrivalTime;
    private int serviceTime;
    private int timeExecututed;
    private int waitingTime;
    private int turnaroundTime;

    public Process(Process process) {
        this.id = process.id;
        this.waitingTime = 0;
        this.arrivalTime = process.arrivalTime;
        this.serviceTime = process.serviceTime;
        this.timeExecututed = 0;
    }

    public Process(String id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.waitingTime = 0;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.timeExecututed = 0;
    }

    public void computeTurnaroundTime(int finishingTime) {
        turnaroundTime = finishingTime - arrivalTime;
    }

    public void incrementWaitingTime() {
        waitingTime++;
    }

    public boolean isProcessFinished() {
        if (timeExecututed >= serviceTime) {
            return true;
        }
        return false;
    }

    public void run() {
        timeExecututed++;
    }

    public String getId() {
        return id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getTimeExecututed() {
        return timeExecututed;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    @Override
    public int compareTo(Process p) {
        return id.compareTo(p.getId());

    }

    @Override
    public String toString() {
        if (turnaroundTime < 10) {
            return id + "      " + turnaroundTime + "               " + waitingTime + "\n";
        }
        return id + "      " + turnaroundTime + "              " + waitingTime + "\n";
    }

}
