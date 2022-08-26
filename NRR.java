import java.util.LinkedList;
import java.util.Queue;

public class NRR extends SchedulingAlgorithm {

    @Override
    public void run() {
        int timeQuantum = 0;
        int timeTillInterupt = 0;

        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {

            // check if processes have arrived, add processes to readyQueue
            checkForReadyProcessess();
            if (isRunningProcess()) {
                runningProcess.run();
                time++;
                timeTillInterupt--;
                incrementWaitingTimes();

                if (runningProcess.isProcessFinished()) {
                    runningProcess.computeTurnaroundTime(time);
                    executedProcesses.add(runningProcess);
                    runningProcess = null;
                }
                if (timeTillInterupt == 0 && !readyQueue.isEmpty()) {
                    readyQueue.push(runningProcess);
                    runningProcess = null;
                }
            } else if (!isRunningProcess() && !readyQueue.isEmpty()) {
                runDispatch();
                getNextProcess();
                timeQuantum = runningProcess.getTimeQuantum();
                timeTillInterupt = timeQuantum;
                runningProcess.decreaseQuantumTime();
                dispatchSwitches.add("T" + time + ": " + runningProcess.getId());
            } else {
                time++;
            }
        }
        calculateSummary();
    }

    NRR(int disp, Queue<Process> upcomingProcesses) {
        this.algorithmName = "NRR";
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
