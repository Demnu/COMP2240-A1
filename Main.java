import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Main {

    public static void main(String[] args) {
        // read file get disp value then save processes into arraylist
        int disp;
        ArrayList<Process> upcomingProcesses = new ArrayList<>();
        try {
            File myObj = new File("data.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                if (line.contains("DISP")) {
                    String dispStr = line.replace("DISP: ", "");
                    disp = Integer.parseInt(dispStr);
                }
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
        System.out.println();
        FCFS fcfs = new FCFS();
    }

}