/* Name: Harrison Collins
 * Student Number: c3282352
 * File: RR.java
 * Description: 
 * Child of SchedulingAlgorithm
 * Implements First Come First Serve Scheduling
 */
import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends SchedulingAlgorithm {
    // constructor
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
        // main loop
        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {
            checkForReadyProcessess();
            if (isRunningProcess()) {
                // execute process for 1 time unit
                runningProcess.run();
                if (runningProcess.isProcessFinished()) {
                    runningProcess.computeTurnaroundTime(time + 1);
                    executedProcesses.add(runningProcess);
                    runningProcess = null;
                }
                time++;
                incrementWaitingTimes();

            }
            // get next process if there is no running process...
            // and there is a process ready to be executed
            else if (!isRunningProcess() && !readyQueue.isEmpty()) {
                runDispatch();
                getNextProcess();
                dispatchSwitches.add("T" + time + ": " + runningProcess.getId());
            } else {
                time++;
            }
        }
        calculateSummary();
    }

    @Override
    public void getNextProcess() {
        // fcfs gets the next process by retrieving the next process in the ready queue
        runningProcess = readyQueue.pollLast();
    }

}
