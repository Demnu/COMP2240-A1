import java.util.LinkedList;
import java.util.Queue;

public class RR extends SchedulingAlgorithm {
    private int timeQuantum = 4;

    @Override
    public void run() {
        int timeTillInterupt = timeQuantum;

        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {

            // check if processes have arrived, add processes to readyQueue
            checkForReadyProcessess();
            if (isRunningProcess()) {
                runningProcess.run();
                time++;
                timeTillInterupt--;
                incrementWaitingTimes();

                if (runningProcess.isProcessFinished()) {
                    timeTillInterupt = timeQuantum;
                    runningProcess.computeTurnaroundTime(time);
                    executedProcesses.add(runningProcess);
                    runningProcess = null;
                }
                if (timeTillInterupt == 0 && !readyQueue.isEmpty()) {
                    timeTillInterupt = timeQuantum;
                    readyQueue.push(runningProcess);
                    runningProcess = null;
                }
            } else if (!isRunningProcess() && !readyQueue.isEmpty()) {
                runDispatch();
                getNextProcess();
                dispatchSwitches.add("T" + time + ": " + runningProcess.getId());
            } else {
                time++;
            }

        }
    }

    RR(int disp, Queue<Process> upcomingProcesses) {
        this.algorithmName = "RR";
        this.dispatchSwitches = new LinkedList<>();
        this.time = 0;
        this.disp = disp;
        this.upcomingProcesses = upcomingProcesses;
        this.readyQueue = new LinkedList<>();
        this.executedProcesses = new LinkedList<>();
    }

    @Override
    public void getNextProcess() {
        runningProcess = readyQueue.pollLast();
    }
}
