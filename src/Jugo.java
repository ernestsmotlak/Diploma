import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.*;


public class Jugo {
    public Jugo() {
        //String inputString = "Kako je lepo kada su ulice pune ljudi smeha gužve i života nadam se da ćemo se uskoro vratiti tom normalnom stanju optimizam život beograd koji kurac";
        String inputString = "Kako je plaho lepo kada su ulice definitivno pune ljudi, smeha, gužve i života. Nadam se da ćemo se uskoro vratiti tom stvarno normalnom stanju. #optimizam #život #beograd";
        String outputString = executePythonScript(inputString);

        String bosnian_tweet = "Kako je plaho lepo kada su ulice definitivno pune ljudi, smeha, gužve i života. Nadam se da ćemo se uskoro vratiti tom stvarno normalnom stanju. #optimizam #život #beograd";

    

        String[] cleaned_tweet = changeTweet(inputString);
        ArrayList<String> jugoNegative = negativeCorpus();
        ArrayList<String> jugoPositive = positiveCorpus();
        ArrayList<String> anaWords = anaCorpus();

        float[] result = difference(cleaned_tweet, jugoPositive, jugoNegative);
        int number_of_anaWords = find_anaWords(cleaned_tweet, anaWords);

        System.out.println();
        System.out.println("Tweet: ");
        System.out.println(inputString);
        System.out.println("_______________________");
        System.out.println();
        System.out.println("Processed tweet: ");
        printProcessedTweet(cleaned_tweet);
        System.out.println();
        System.out.println("_______________________");
        System.out.println();
        printPositiveWords(cleaned_tweet, jugoPositive);
        System.out.println("_______________________");
        System.out.println();
        printNegativeWords(cleaned_tweet, jugoNegative);
        System.out.println("_______________________");
        System.out.println();
        System.out.println("Basic sentiment : " + sentimentBasic(result[0], result[1], result[2]) + "");
        System.out.println();
        System.out.println("_______________________");
        System.out.println();


        System.out.println("AnA words:");
        print_anaWords(cleaned_tweet, anaWords);
        System.out.println("_______________________");
        System.out.println();
        System.out.println("Number of AnA words is: " + number_of_anaWords);
        System.out.println("_______________________");
        System.out.println();
        System.out.println("Advanced sentiment is: " + sentimentAdvanced(result, number_of_anaWords));
        System.out.println("_______________________");

        // System.out.println(outputString);

        String[] words = outputString.split(" "); //turn String to array of strings
        System.out.println();

        for (int i = 0; i < words.length; i++) {
            System.out.print(" " + words[i]);
        }

        System.out.println();

        for (int i = 0; i < cleaned_tweet.length; i++) {
            System.out.print(" " + cleaned_tweet[i]);
        }

        System.out.println();

    }

    public static String executePythonScript(String inputString) {
        String outputString = "";

        try {
            // Build the command to run the Python script
            List<String> commands = new ArrayList<String>();
            commands.add("python3");
            commands.add("/Users/ernestsmotlak/Desktop/Famnit/java_python_java/py.py");

            // Start the process to run the Python script
            ProcessBuilder pb = new ProcessBuilder(commands);
            Process p = pb.start();

            // Get the input stream of the Python process
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            // Send the input string to the Python process
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            out.write(inputString);
            out.newLine();
            out.flush();

            // Read the output of the Python process
            String line;
            while ((line = in.readLine()) != null) {
                outputString += line;
            }

            // Close the input and output streams
            in.close();
            out.close();

            // Wait for the Python process to complete
            int exitCode = p.waitFor();
            System.out.println("Python script execution completed with exit code " + exitCode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return outputString;
    }

    static String[] changeTweet(String str) {
        String[] words = str.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].replaceAll("([.,!?])", " $1");
            words[i] = words[i].replaceAll("[^A-Ža-ž0-9]", "");
            words[i] = words[i].toLowerCase();
        }
        return words;
    }

    static ArrayList<String> negativeCorpus() {
        ArrayList<String> corpus1 = new ArrayList<>(62942);
        File fileName1 = new File("/Users/ernestsmotlak/Desktop/Famnit/Diploma/Lexicons/negative_words_Jugo.txt");
        Scanner input = null;
        try {
            input = new Scanner(fileName1);
            while (input.hasNext()) {
                corpus1.add(input.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return corpus1;
    }

    static ArrayList<String> positiveCorpus() { // preberi pozitivne besede
        ArrayList<String> corpus2 = new ArrayList<>(62942);
        File fileName2 = new File("/Users/ernestsmotlak/Desktop/Famnit/Diploma/Lexicons/positive_words_Jugo.txt");
        Scanner input = null;
        try {
            input = new Scanner(fileName2);
            while (input.hasNext()) {
                corpus2.add(input.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return corpus2;
    }

    static ArrayList<String> anaCorpus() {
        ArrayList<String> corpus3 = new ArrayList<>(100);
        File filename3 = new File("/Users/ernestsmotlak/Desktop/Famnit/Diploma/Lexicons/BOSNIAN_AnAwords_Jahic(extended).txt");
        Scanner input = null;
        try {
            input = new Scanner(filename3);
            while (input.hasNext()) {
                corpus3.add(input.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return corpus3;
    }

    public static int LevenshteinDistance(String X, String Y) {
        int m = X.length();
        int n = Y.length();

        int[][] T = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            T[i][0] = i;
        }
        for (int j = 1; j <= n; j++) {
            T[0][j] = j;
        }

        int cost;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                cost = X.charAt(i - 1) == Y.charAt(j - 1) ? 0 : 1;
                T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
                        T[i - 1][j - 1] + cost);
            }
        }

        return T[m][n];
    }

    public static double findSimilarity(String x, String y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            // optionally ignore case if needed
            return (maxLength - LevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

    public static double Result(String[] words, ArrayList<String> positiveAL, ArrayList<String> negativeAL, double posWords, double negWords) {
        for (String word : words) {
            for (int j = 0; j < negativeAL.toArray().length; j++) {
                if (findSimilarity(negativeAL.get(j), word) >= 0.8) {
                    negWords++;
                    break;
                }
            }
        }

        for (String word : words) {
            for (int j = 0; j < positiveAL.toArray().length; j++) {
                if (findSimilarity(positiveAL.get(j), word) >= 0.8) {
                    posWords++;
                    break;
                }
            }
        }

        return (posWords - negWords) / words.length;
    }

    public static float[] difference(String[] words, ArrayList<String> positiveLexicon, ArrayList<String> negativeLexicon) {
        int posWords = 0;
        int negWords = 0;
        for (String word : words) {
            if (positiveLexicon.contains(word)) {
                posWords++;
            } else if (negativeLexicon.contains(word)) {
                negWords++;
            }
        }
        float stringLength = words.length;
        return new float[] { posWords, negWords, stringLength };
    }

    public static int find_anaWords(String[] words, ArrayList<String> anaCorpus) {
        int number_of_anaWords = 0;
        for (String word : words) {
            if (anaCorpus.contains(word)) {
                number_of_anaWords++;
            }
        }
        return number_of_anaWords;
    }

    public static void print_anaWords(String[] words, ArrayList<String> anaCorpus) {
        for (String word : words) {
            if (anaCorpus.contains(word)) {
                System.out.println("AnA word: " + word);
            }
        }
    }

    public static float sentimentAdvanced(float[] result, int number_of_anaWords) {
        return ((number_of_anaWords / result[2]) * (1 - sentimentBasic(result[0], result[1], result[2])));
    }

    public static void whichPositiveWords(String[] words, ArrayList<String> positiveLexicon, ArrayList<String> negativeLexicon) {
        String[] returnedPositiveWords = {};
        for (String word : words) {
            if (positiveLexicon.contains(word)) {
                System.out.println("Positive word: " + word);
            } else if (negativeLexicon.contains(word)) {
                System.out.println("Negative word: " + word);
            }
        }
    }

    public static void printPositiveWords(String[] words, ArrayList<String> positiveLexicon) {
        System.out.println("Positive words: ");
        for (String word : words) {
            if (positiveLexicon.contains(word)) {
                System.out.println(word);
            }
        }
    }

    public static void printNegativeWords(String[] words, ArrayList<String> negativeLexicon) {
        System.out.println("Negative words: ");
        for (String word : words) {
            if (negativeLexicon.contains(word)) {
                System.out.println(word);
            }
        }
    }

    public static float sentimentBasic(float numOfPositiveWords, float numOfNegativeWords, float numOfAllWords) {
        float pos = (float) numOfPositiveWords;
        float neg = (float) numOfNegativeWords;
        float all = (float) numOfAllWords;
        float sent = (pos - neg) / all;
        return sent;
    }

    public static void printProcessedTweet(String[] tweet) {
        for (int i = 0; i < tweet.length; i++) {
            System.out.print(tweet[i] + " ");
        }
    }
}
