/* Name: Harrison Collins
 * Student Number: c3282352
 * File: SchedulingAlgorithm.java
 * Description: Scheduling Algorithm abstract class
 */
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.text.DecimalFormat;

public abstract class SchedulingAlgorithm {
    // global variables
    protected String algorithmName;
    protected int time;
    protected int disp;
    protected Process runningProcess;
    protected Queue<Process> upcomingProcesses;
    protected LinkedList<Process> readyQueue;
    protected LinkedList<Process> executedProcesses;
    protected LinkedList<String> dispatchSwitches;
    protected String summary = "";

    // constructors
    SchedulingAlgorithm() {

    }

    SchedulingAlgorithm(int disp, Queue<Process> upcomingProcesses) {
        this.time = 0;
        this.disp = disp;
        this.upcomingProcesses = upcomingProcesses;
    }

    // execute implemented algorithm
    public abstract void run();

    // check for incoming processess
    // add process to ready queue
    public void checkForReadyProcessess() {
        while (!upcomingProcesses.isEmpty() && upcomingProcesses.peek().getArrivalTime() <= time) {
            readyQueue.push(new Process(upcomingProcesses.poll()));
        }
    }

    // increment 1 unit of waiting time to each process in the ready queue
    public void incrementWaitingTimes() {
        for (Process process : readyQueue) {
            process.incrementWaitingTime();

        }
    }

    // execute implemented getNextProcess
    public abstract void getNextProcess();

    // imitate dispatch
    public void runDispatch() {
        // increment disp amount of waiting time to each process in the ready queue
        for (int i = 0; i < disp; i++) {
            incrementWaitingTimes();
        }
        time += disp;
    }

    // returns boolean that checks if there is a running process
    public boolean isRunningProcess() {
        if (runningProcess == null) {
            return false;
        }
        return true;
    }

    // creates summary in string form showing...
    // average turnaround time and waiting time
    public void calculateSummary() {
        DecimalFormat df = new DecimalFormat("0.00");

        summary += algorithmName + "\t\t";
        double averageTT = 0;
        double averageWT = 0;
        for (Process process : executedProcesses) {
            averageTT += process.getTurnaroundTime();
            averageWT += process.getWaitingTime();
        }
        averageTT = averageTT / executedProcesses.size();
        averageWT = averageWT / executedProcesses.size();
        summary += df.format(averageTT) + "\t\t\t  " + df.format(averageWT);

    }

    // return string describing details of each executed process
    @Override
    public String toString() {
        // sort executedProcesses arraylist by alphabetical order
        Collections.sort(executedProcesses);

        // begin string construction
        String str = algorithmName + ":\n";
        for (String dispatchSwitch : dispatchSwitches) {
            str += dispatchSwitch + "\n";
        }
        str += "\n";
        str += "Process\tTurnaround Time\tWaiting Time\n";

        for (Process process : executedProcesses) {
            str += process.toString();
        }
        return str;
    }

    // setters
    public void setDisp(int disp) {
        this.disp = disp;
    }

    public void setReadyQueue(LinkedList<Process> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setUpcomingProcesses(Queue<Process> upcomingProcesses) {
        this.upcomingProcesses = upcomingProcesses;
    }

    // getters
    public String getSummary() {
        return summary;
    }

}
