/* Name: Harrison Collins
 * Student Number: c3282352
 * File: FB.java
 * Description: 
 * Child of SchedulingAlgorithm
 * Implements Feedback Scheduling
 */

import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

public class FB extends SchedulingAlgorithm {
    // global variables
    // stores each queue in a linkedlist
    private LinkedList<Queue<Process>> queues = new LinkedList<>();
    private int timeQuantum = 4;
    private int indexOfQueue = 0;

    // constructor
    FB(int disp, Queue<Process> upcomingProcesses, int priorityLevel) {
        this.algorithmName = "FB (constant)";
        this.dispatchSwitches = new LinkedList<>();
        this.time = 0;
        this.disp = disp;
        this.upcomingProcesses = upcomingProcesses;
        this.executedProcesses = new LinkedList<>();
        queues = new LinkedList<>();
        for (int i = 0; i < priorityLevel; i++) {
            Queue<Process> tempQueue = new LinkedList<>();
            queues.add(tempQueue);
        }
    }

    @Override
    public void run() {
        int timeTillInterupt = timeQuantum;
        // main loop
        while (!upcomingProcesses.isEmpty() || !isQueuesEmpty() || isRunningProcess()) {
            // execute process for 1 time unit
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
                // interupt if process has been run for the given time quantam...
                // and there are other processes in the ready queue
                if (timeTillInterupt == 0 && !isQueuesEmpty()) {
                    timeTillInterupt = timeQuantum;
                    // use implemented returning of process to the next level queue
                    returnRunningProcess();
                }
            } else if (!isRunningProcess() && !isQueuesEmpty()) {
                runDispatch();
                getNextProcess();
                dispatchSwitches.add("T" + time + ": " + runningProcess.getId());
            } else {
                time++;
            }

        }
        calculateSummary();
    }

    // puts the interrupted process into lower ready queue
    public void returnRunningProcess() {
        // if interupted process is at the lowest queue
        if (indexOfQueue == queues.size() - 1) {
            queues.get(indexOfQueue).add(runningProcess);
        } else {
            queues.get(indexOfQueue + 1).add(runningProcess);
        }
        runningProcess = null;
    }

    // returns boolean depending on if there are any processes...
    // in any of the ready queues
    public boolean isQueuesEmpty() {
        for (Queue<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // checks if there are any arrived processes...
    // and adds them to the highest level queue
    @Override
    public void checkForReadyProcessess() {
        while (!upcomingProcesses.isEmpty() && upcomingProcesses.peek().getArrivalTime() <= time) {
            Process arrivedProcess = upcomingProcesses.poll();
            queues.getFirst().add(arrivedProcess);
        }
    }

    @Override
    public void getNextProcess() {
        // use FB strategy to get next process
        for (int i = 0; i < queues.size(); i++) {
            Queue<Process> queue = queues.get(i);
            if (!queue.isEmpty()) {
                runningProcess = queue.poll();
                indexOfQueue = i;
                break;
            }
        }
    }

    // overriden to account for longer algorithm name
    // creates summary in string form showing...
    // average turnaround time and waiting time @Override
    public void calculateSummary() {
        DecimalFormat df = new DecimalFormat("0.00");
        summary += algorithmName + "\t";
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

    // overriden to account for processes being in multiple ready queues
    @Override
    public void incrementWaitingTimes() {
        for (Queue<Process> queue : queues) {
            for (Process process : queue) {
                process.incrementWaitingTime();
            }
        }
    }
}
