public class NRR_Process extends Process {
    private int timeQuantum;

    NRR_Process(Process process, int timeQuantum) {
        this.timeQuantum = timeQuantum;
        this.id = process.id;
        this.waitingTime = 0;
        this.arrivalTime = process.arrivalTime;
        this.serviceTime = process.serviceTime;
        this.timeExecututed = 0;
    }

}
