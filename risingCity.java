import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class risingCity {

    private int GlobalTime = 0;//Global time
    private int fiveDayCounter = 0;//Counter to 5 days
    private int expectedCompleteTime = 0;//Time at which the current building being executed will end if continued indefinitely
    private RBTree tree = new RBTree();//Tree intialize
    private MinHeap heap = new MinHeap();//Heap intialize
    private FileWriter fileWriter;//File output
    private HNode currentBuilding = null;//Current Building pointer
    private static String INPUT_FILE;
    private static final String OUT_FILE = "output_file.txt";//Output File name

    //Main accepts the input as args
    public static void main(String[] args) {

        INPUT_FILE = args[0];
        risingCity first = new risingCity();
        first.run();

    }

    //run the city one day at a time
    //Operations like print and insert are given preference over completion of the building
    //operation only performed when the global timer is equal to the command time
    private void run() {
        BufferedReader br = null;
        FileReader fr = null;
        try {
            URL url = ClassLoader.getSystemResource(INPUT_FILE);
            fr = new FileReader(new File(url.toURI()));
            br = new BufferedReader(fr);

            fileWriter = new FileWriter(OUT_FILE);

            String presentLine;
            String[] operation;

            while ((presentLine = br.readLine()) != null) {

                Pattern p = Pattern.compile("(^\\d+): ([a-zA-Z]+)\\((.+)\\)");
                Matcher m = p.matcher(presentLine);

                if (m.find()) {
                    operation = m.group(3).split(",");
                    int commandtime = Integer.parseInt(m.group(1));
                    //case when they are not equal
                    while (commandtime != GlobalTime) {
                        builder();
                        GlobalTime++;
                        if (currentBuilding != null) {
                            currentBuilding.executedTime = currentBuilding.executedTime + 1;
                        }

                    }

                    switch (m.group(2)) {
                        case "Insert": {
                            insertBuilding(operation);
                            break;
                        }
                        case "PrintBuilding": {
                            printBuilding(operation);
                            break;
                        }
                    }
                }
                builder();
                GlobalTime++;
                if (currentBuilding != null) {
                    currentBuilding.executedTime = currentBuilding.executedTime + 1;
                }
            }
            while (currentBuilding != null) {//Reader from file is empty but we process the remaining commands
                builder();
                GlobalTime++;
                if (currentBuilding != null) {
                    currentBuilding.executedTime = currentBuilding.executedTime + 1;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void builder() throws IOException {
        if (currentBuilding == null) {
            if (!heap.isEmpty()) {
                currentBuilding = heap.remove();
                fiveDayCounter = GlobalTime + 5;
                expectedCompleteTime = GlobalTime + currentBuilding.rbNode.totalTime - currentBuilding.executedTime;
            }
        } else {
            if (expectedCompleteTime <= fiveDayCounter && GlobalTime == expectedCompleteTime) {
                fileWriter.write("(" + currentBuilding.rbNode.Building_ID + "," + expectedCompleteTime + ")\n");
                tree.delete(currentBuilding.rbNode);
                currentBuilding = null;
                fiveDayCounter = 0;
                expectedCompleteTime = 0;
                builder();
            } else if (GlobalTime == fiveDayCounter) {
                heap.add(currentBuilding);
                currentBuilding = null;
                fiveDayCounter = 0;
                expectedCompleteTime = 0;
                builder();
            }
        }
    }


    private void printBuilding(String[] values) throws IOException {

        if (values.length == 1) {
            int build_ID = Integer.parseInt(values[0]);
            RBNode rbNode = tree.search(build_ID);
            if (rbNode == null) {
                fileWriter.write("(0,0,0)\n");
            } else {
                if (rbNode.Building_ID == currentBuilding.rbNode.Building_ID) {
                    fileWriter.write("(" + rbNode.Building_ID + "," + currentBuilding.executedTime + "," + rbNode.totalTime + ")\n");
                } else {
                    fileWriter.write("(" + rbNode.Building_ID + "," + rbNode.heapNode.executedTime + "," + rbNode.totalTime + ")\n");
                }

            }
        } else {
            int Build1 = Integer.parseInt(values[0]);
            int Build2 = Integer.parseInt(values[1]);
            List<RBNode> list = tree.searchInRange(Build1, Build2);

            if (!list.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (RBNode node : list) {
                    sb.append("(" + node.Building_ID + "," + node.heapNode.executedTime + "," + node.totalTime + "),");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
                fileWriter.write(sb.toString());
            } else {
                fileWriter.write("(0,0,0)\n");
            }
        }
    }


    //Insert operation
    //Building is inserted into the city
    //Added into the MinHeap and the RedBlackTree
    private void insertBuilding(String[] op) throws IOException {
        int id = Integer.parseInt(op[0]);
        int Building_TOT = Integer.parseInt(op[1]);
        RBNode rbNode = new RBNode(id);
        rbNode.totalTime = Building_TOT;
        HNode heapNode = new HNode(0);
        rbNode.heapNode = heapNode;
        heapNode.rbNode = rbNode;
        tree.insertNode(rbNode);//added to RB Tree
        heap.add(heapNode);//adde to the MinHeap
    }
}

