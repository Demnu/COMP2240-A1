public class Process implements Comparable<Process> {
    protected String id;
    protected int arrivalTime;
    protected int serviceTime;
    protected int timeExecututed;
    protected int waitingTime;
    protected int turnaroundTime;
    private int timeQuantum;

    public Process() {
    }

    public Process(Process process) {
        this.id = process.id;
        this.waitingTime = 0;
        this.arrivalTime = process.arrivalTime;
        this.serviceTime = process.serviceTime;
        this.timeExecututed = 0;
        this.timeQuantum = 4;
    }

    public Process(String id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.waitingTime = 0;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.timeExecututed = 0;
        this.timeQuantum = 4;

    }

    public void decreaseQuantumTime() {
        if (timeQuantum > 2) {
            timeQuantum--;
        }
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

    public int getTimeQuantum() {
        return timeQuantum;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public int compareTo(Process p) {
        return id.compareTo(p.getId());

    }

    @Override
    public String toString() {
        return id + "\t" + turnaroundTime + "\t\t" + waitingTime + "\n";
    }

}
