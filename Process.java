public class Process {
    private String id;
    private int arrivalTime;
    private int serviceTime;
    private int timeExecututed;
    private int waitingTime;
    private int turnaroundTime;

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
        if (timeExecututed == serviceTime) {
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
    public String toString() {
        if (turnaroundTime < 10) {
            return id + "      " + turnaroundTime + "               " + waitingTime + "\n";

        }
        // TODO Auto-generated method stub
        return id + "      " + turnaroundTime + "              " + waitingTime + "\n";
    }

}
