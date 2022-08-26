/* Name: Harrison Collins
 * Student Number: c3282352
 * File: RR.java
 * Description: Entry point
 */
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.LinkedList;
import java.util.Queue;

public class Main {

    public static void main(String[] args) {
        int disp = 0;
        Queue<Process> upcomingProcesses = new LinkedList<>();

        // read file
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                // get DISP
                if (line.contains("DISP")) {
                    String dispStr = line.replace("DISP: ", "");
                    disp = Integer.parseInt(dispStr);
                }
                // get Process
                if (line.contains("ID")) {
                    String id = line.replace("ID: ", "");
                    line = myReader.nextLine();
                    String arriveTimeStr = line.replace("Arrive: ", "");
                    int arrivalTime = Integer.parseInt(arriveTimeStr);
                    line = myReader.nextLine();
                    String serviceTimeStr = line.replace("ExecSize: ", "");
                    int serviceTime = Integer.parseInt(serviceTimeStr);
                    upcomingProcesses.add(new Process(id, arrivalTime, serviceTime));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // instantiate algorithms
        FCFS fcfs = new FCFS(disp, new LinkedList<Process>(upcomingProcesses));
        RR rr = new RR(disp, new LinkedList<Process>(upcomingProcesses));
        NRR nrr = new NRR(disp, new LinkedList<Process>(upcomingProcesses));
        int priorityLevel = 6;
        FB fb = new FB(disp, new LinkedList<Process>(upcomingProcesses), priorityLevel);

        // run algorithms
        fcfs.run();
        rr.run();
        nrr.run();
        fb.run();

        // output algorithms to terminal
        System.out.println(fcfs);
        System.out.println(rr);
        System.out.println(nrr);
        System.out.println(fb);

        // output summary to terminal
        System.out.println("Summary");
        System.out.println("Algorithm\tAverage Turnaround Time\t Average Waiting Time");
        System.out.println(fcfs.getSummary());
        System.out.println(rr.getSummary());
        System.out.println(nrr.getSummary());
        System.out.println(fb.getSummary());
        System.out.println();
    }
}