import java.util.LinkedList;
import java.util.Queue;

public class RR extends SchedulingAlgorithm {
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
