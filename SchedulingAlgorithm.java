import java.util.ArrayList;

public abstract class SchedulingAlgorithm {
    private int disp;
    private ArrayList<Process> upcomingProcesses;
    private ArrayList<Process> readyQueue;

    public void setDisp(int disp) {
        this.disp = disp;
    }

    public void setReadyQueue(ArrayList<Process> readyQueue) {
        this.readyQueue = readyQueue;
    }

    public void setUpcomingProcesses(ArrayList<Process> upcomingProcesses) {
        this.upcomingProcesses = upcomingProcesses;
    }

}
