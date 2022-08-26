import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Queue;

public class FB extends SchedulingAlgorithm {
    private LinkedList<Queue<Process>> queues = new LinkedList<>();

    private int timeQuantum = 4;
    private int indexOfQueue = 0;

    @Override
    public void run() {
        int timeTillInterupt = timeQuantum;

        while (!upcomingProcesses.isEmpty() || !isQueuesEmpty() || isRunningProcess()) {

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
                if (timeTillInterupt == 0 && !isQueuesEmpty()) {
                    timeTillInterupt = timeQuantum;
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

    public void returnRunningProcess() {
        if (indexOfQueue == queues.size() - 1) {
            queues.get(indexOfQueue).add(runningProcess);
        } else {
            queues.get(indexOfQueue + 1).add(runningProcess);
        }
        runningProcess = null;
    }

    public boolean isQueuesEmpty() {
        for (Queue<Process> queue : queues) {
            if (!queue.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void checkForReadyProcessess() {
        while (!upcomingProcesses.isEmpty() && upcomingProcesses.peek().getArrivalTime() <= time) {
            Process arrivedProcess = upcomingProcesses.poll();
            queues.getFirst().add(arrivedProcess);
        }
    }

    @Override
    public void getNextProcess() {
        for (int i = 0; i < queues.size(); i++) {
            Queue<Process> queue = queues.get(i);
            if (!queue.isEmpty()) {
                runningProcess = queue.poll();
                indexOfQueue = i;
                break;
            }
        }
    }

    @Override
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

    @Override
    public void incrementWaitingTimes() {
        for (Queue<Process> queue : queues) {
            for (Process process : queue) {
                process.incrementWaitingTime();
            }
        }
    }
}
