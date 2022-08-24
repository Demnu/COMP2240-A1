import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public abstract class SchedulingAlgorithm {
    protected String algorithmName;
    protected int time;
    protected int disp;
    protected Process runningProcess;
    protected Queue<Process> upcomingProcesses;
    protected LinkedList<Process> readyQueue;
    protected LinkedList<Process> executedProcesses;
    protected LinkedList<String> dispatchSwitches;

    public abstract void run();

    public void checkForReadyProcessess() {
        while (!upcomingProcesses.isEmpty() && upcomingProcesses.peek().getArrivalTime() <= time) {
            readyQueue.push(new Process(upcomingProcesses.poll()));
        }
    }

    public void incrementWaitingTimes() {
        for (Process process : readyQueue) {
            process.incrementWaitingTime();

        }
    }

    SchedulingAlgorithm() {

    }

    public abstract void getNextProcess();

    public void runDispatch() {
        for (int i = 0; i < disp; i++) {
            incrementWaitingTimes();
        }
        time += disp;
    }

    SchedulingAlgorithm(int disp, Queue<Process> upcomingProcesses) {
        this.time = 0;
        this.disp = disp;
        this.upcomingProcesses = upcomingProcesses;
    }

    public void setDisp(int disp) {
        this.disp = disp;
    }

    public void setReadyQueue(LinkedList<Process> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setUpcomingProcesses(Queue<Process> upcomingProcesses) {
        this.upcomingProcesses = upcomingProcesses;
    }

    public boolean isRunningProcess() {
        if (runningProcess == null) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        Collections.sort(executedProcesses);
        String str = algorithmName + ":\n";
        for (String dispatchSwitch : dispatchSwitches) {
            str += dispatchSwitch + "\n";
        }
        str += "\n";
        str += "Process " + "Turnaround Time " + "Waiting Time\n";

        for (Process process : executedProcesses) {
            str += process.toString();
        }
        return str;
    }

}
