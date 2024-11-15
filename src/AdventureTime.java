import java.io.*;
import java.util.*;

public class AdventureTime {

    public static void main(String[] args) {
        // Test each challenge and store the results
        int challengeOneAnswer = challengeOne("inputOneTwo.txt");
        int challengeTwoAnswer = challengeTwo("inputOneTwo.txt");
        int challengeThreeAnswer = challengeThree("inputThreeFour.txt");
        int challengeFourAnswer = challengeFour("inputThreeFour.txt");

        // Write all answers to a file
        writeAnswersToFile("AdventureTime.txt", challengeOneAnswer, challengeTwoAnswer, challengeThreeAnswer, challengeFourAnswer);
    }

    public static int challengeOne(String fileName) {
        List<Integer> depths = readFile(fileName);
        return countIncreases(depths);
    }

    public static int challengeTwo(String fileName) {
        List<Integer> depths = readFile(fileName);
        return countSlidingWindowIncreases(depths, 3);
    }

    public static int challengeThree(String fileName) {
        return calculatePosition(fileName, false);
    }

    public static int challengeFour(String fileName) {
        return calculatePosition(fileName, true);
    }

    private static int calculatePosition(String fileName, boolean useAim) {
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (String line : readLines(fileName)) {
            String[] parts = line.split(" ");
            String direction = parts[0];
            int value = Integer.parseInt(parts[1]);

            if (direction.equals("forward")) {
                horizontalPosition += value;
                if (useAim) {
                    depth += aim * value;
                }
            } else if (direction.equals("down")) {
                if (useAim) {
                    aim += value;
                } else {
                    depth += value;
                }
            } else if (direction.equals("up")) {
                if (useAim) {
                    aim -= value;
                } else {
                    depth -= value;
                }
            }
        }

        return horizontalPosition * depth;
    }

    private static List<Integer> readFile(String fileName) {
        List<Integer> values = new ArrayList<>();
        for (String line : readLines(fileName)) {
            values.add(Integer.parseInt(line));
        }
        return values;
    }

    private static List<String> readLines(String fileName) {
        List<String> lines = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    // Handle the IOException silently
                }
            }
        }
        return lines;
    }

    private static int countIncreases(List<Integer> values) {
        int count = 0;
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) > values.get(i - 1)) count++;
        }
        return count;
    }

    private static int countSlidingWindowIncreases(List<Integer> values, int windowSize) {
        int count = 0;
        for (int i = windowSize; i < values.size(); i++) {
            int firstWindowSum = sum(values, i - windowSize, i - 1);
            int secondWindowSum = sum(values, i - windowSize + 1, i);
            if (secondWindowSum > firstWindowSum) count++;
        }
        return count;
    }

    private static int sum(List<Integer> values, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += values.get(i);
        }
        return sum;
    }

    private static void writeAnswersToFile(String fileName, int... answers) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < answers.length; i++) {
                writer.write("Challenge " + (i + 1) + " Answer: " + answers[i]);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // Handle the IOException silently
                }
            }
        }
    }
}