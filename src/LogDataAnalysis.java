import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LogDataAnalysis {

    /**
     * Create a lookUp Map to map dstport and protocol combination with tag
     *
     * @param lookUpTablePath
     * @return
     * @throws IOException
     */
    public static Map<String, String> createLookUpMap(String lookUpTablePath) throws IOException {
        Map<String, String> lookUpMap = new HashMap<>();
        try (Reader reader = new FileReader(lookUpTablePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader("dstport", "protocol", "tag").parse(reader);
            for (CSVRecord record : records) {
                String key = record.get("dstport") + "-" + record.get("protocol").toLowerCase();
                lookUpMap.put(key, record.get("tag"));
            }
        }
        return lookUpMap;
    }

    /**
     * Parse the flow log data file to tag each log message.
     *
     * @param flowLogDataPath
     * @param lookUpTablePath
     * @param outputFilePath
     * @throws IOException
     */
    public static void parseFlowLogData(String flowLogDataPath, String lookUpTablePath, String outputFilePath) throws IOException {
        Map<String, String> lookUpMap = createLookUpMap(lookUpTablePath);
        Map<String, Integer> tagCounts = new HashMap<>();
        tagCounts.put("Untagged", 0);
        Map<String, Integer> portProtocolCounts = new HashMap<>();

        // Reading the log file and storing the counts of each tag and dstport,protocol combination
        try (Reader reader = new FileReader(flowLogDataPath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(' ').parse(reader);

            for (CSVRecord record : records) {

                if(record.size() > 7){
                    String dstport = record.get(6);
                    String protocol = IpProtocol.getProtocolName(Integer.parseInt(record.get(7))).toLowerCase();
                    String key = dstport + "-" + protocol;

                    String tag = lookUpMap.getOrDefault(key, "Untagged");

                    tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);

                    portProtocolCounts.put(key, portProtocolCounts.getOrDefault(key, 0) + 1);
                }

            }
        }

        // Writing the output into a file.
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write("Tag Counts:\nTag,Count\n");

            for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
            writer.write("\nPort/Protocol Combination Counts:\nPort,Protocol,Count\n");

            for (Map.Entry<String, Integer> entry : portProtocolCounts.entrySet()) {

                String[] key = entry.getKey().split("-");
                writer.write(key[0] + "," + key[1] + "," + entry.getValue() + "\n");

            }
        }
    }

    public static void main(String[] args) {
        String defaultFlowLogDataPath = "resources/logflow.txt"; // Update path to your flow log file
        String defaultLookUpTablePath = "resources/lookupTable.csv";  // Update path to your lookup table file
        String defaultOutputFilePath = "resources/outputCounts.csv";  // Output file path

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the path of the flow log file or press enter to continue with default file path:");
        String userInput = scanner.nextLine();
        String flowLogDataPath =  userInput.isEmpty() ? defaultFlowLogDataPath : userInput;

        System.out.print("Please enter the path of the lookUp table or press enter to continue with default file path:");
        userInput = scanner.nextLine();
        String lookUpTablePath =  userInput.isEmpty() ? defaultLookUpTablePath : userInput;

        System.out.print("Please enter the path of the output file or press enter to continue with default file path:");
        userInput = scanner.nextLine();
        String outputFilePath =  userInput.isEmpty() ? defaultOutputFilePath : userInput;

        try {
            parseFlowLogData(flowLogDataPath, lookUpTablePath, outputFilePath);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
