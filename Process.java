/* Name: Harrison Collins
 * Student Number: c3282352
 * File: Process.java
 * Description: Process object
 */
public class Process implements Comparable<Process> {
    // global variables
    protected String id;
    protected int arrivalTime;
    protected int serviceTime;
    protected int timeExecututed;
    protected int waitingTime;
    protected int turnaroundTime;
    private int timeQuantum;

    // constructors
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

    // used for FB algorithm
    public void decreaseQuantumTime() {
        if (timeQuantum > 2) {
            timeQuantum--;
        }
    }

    public void computeTurnaroundTime(int finishingTime) {
        turnaroundTime = finishingTime - arrivalTime;
    }

    // increments waiting time by 1 time unit
    public void incrementWaitingTime() {
        waitingTime++;
    }

    // returns boolean depending on if the process is finished
    public boolean isProcessFinished() {
        if (timeExecututed >= serviceTime) {
            return true;
        }
        return false;
    }

    // increment timeExecuted by 1 time unit
    public void run() {
        timeExecututed++;
    }

    // used for sorting arraylist
    @Override
    public int compareTo(Process p) {
        // sorts by comparing id's using alphabetical order
        return id.compareTo(p.getId());

    }

    @Override
    // return string describing details of each executed process
    public String toString() {
        return id + "\t" + turnaroundTime + "\t\t" + waitingTime + "\n";
    }

    // getters
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

}
