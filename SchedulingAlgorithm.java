import java.util.LinkedList;
import java.util.Queue;

public abstract class SchedulingAlgorithm {
    protected String algorithmName;
    protected int time;
    protected int disp;
    protected Process runningProcess;
    protected Queue<Process> upcomingProcesses;
    protected LinkedList<Process> readyQueue;
    protected Queue<Process> executedProcesses;
    protected LinkedList<String> dispatchSwitches;

    public void run() {
        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {
            // check if processes have arrived, add processes to readyQueue
            while (!upcomingProcesses.isEmpty() && upcomingProcesses.peek().getArrivalTime() <= time) {
                readyQueue.push(new Process(upcomingProcesses.poll()));
            }
            if (isRunningProcess()) {
                runningProcess.run();
                if (runningProcess.isProcessFinished()) {
                    runningProcess.computeTurnaroundTime(time + 1);
                    executedProcesses.add(runningProcess);
                    runningProcess = null;
                }
                time++;
                incrementWaitingTimes();
            } else if (!isRunningProcess() && !readyQueue.isEmpty()) {
                runDispatch();
                getNextProcess();
                dispatchSwitches.add("T" + time + ": " + runningProcess.getId());
            } else {
                time++;
            }

        }
    };

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
        String str = algorithmName + ":\n";
        for (String dispatchSwitch : dispatchSwitches) {
            str += dispatchSwitch + "\n";
        }
        str += "Process " + "Turnaround Time " + "Waiting Time\n";
        for (Process process : executedProcesses) {
            str += process.toString();
        }
        return str;
    }
}
