/* Name: Harrison Collins
 * Student Number: c3282352
 * File: RR.java
 * Description: 
 * Child of SchedulingAlgorithm
 * Implements Round Robin Scheduling
 */

import java.util.LinkedList;
import java.util.Queue;

public class RR extends SchedulingAlgorithm {
    // global variables
    private int timeQuantum = 4;

    // constructor
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
    public void run() {
        int timeTillInterupt = timeQuantum;
        // main loop
        while (!upcomingProcesses.isEmpty() || !readyQueue.isEmpty() || isRunningProcess()) {
            checkForReadyProcessess();
            // execute process for 1 time unit
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
                // interupt if process has been run for the given time quantam...
                // and there are other processes in the ready queue
                if (timeTillInterupt == 0 && !readyQueue.isEmpty()) {
                    timeTillInterupt = timeQuantum;
                    readyQueue.push(runningProcess);
                    runningProcess = null;
                }
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
        // use FCFS strategy to get next process
        runningProcess = readyQueue.pollLast();
    }
}
