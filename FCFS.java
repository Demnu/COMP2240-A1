import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends SchedulingAlgorithm {
    FCFS(int disp, Queue<Process> upcomingProcesses) {
        this.algorithmName = "FCFS";
        this.dispatchSwitches = new LinkedList<>();
        this.time = 0;
        this.disp = disp;
        this.upcomingProcesses = upcomingProcesses;
        this.readyQueue = new LinkedList<>();
        this.executedProcesses = new LinkedList<Process>();
    }

    @Override
    public void run() {
        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {

            // check if processes have arrived, add processes to readyQueue
            checkForReadyProcessess();
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
    }

    @Override
    public void getNextProcess() {
        runningProcess = readyQueue.pollLast();
    }

}
