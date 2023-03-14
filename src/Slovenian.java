import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Slovenian {
    public Slovenian() {
        String tweet = "Igrali smo zelo dobro, zmagali, vendar smo resnično zamudili.";
//        String tweet = "Igrali smo zelo dobro, zmagali, vendar smo resnično zamudili.";
        String[] cleaned_tweet = changeTweet(tweet);
        ArrayList<String> sloNegative = negativeCorpus();
        ArrayList<String> sloPositive = positiveCorpus();
        float number_of_positive_words = 0;
        float number_of_negative_words = 0;

        float[] result = difference(cleaned_tweet, sloPositive, sloNegative, number_of_positive_words, number_of_negative_words);
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        System.out.println(number_of_negative_words + " " + number_of_positive_words);

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
        File fileName1 = new File("/Users/ernestsmotlak/Desktop/Famnit/Diploma/Lexicons/negative_words_Slovenian.txt");
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

    static ArrayList<String> positiveCorpus() {    //preberi pozitivne besede
        ArrayList<String> corpus2 = new ArrayList<>(62942);
        File fileName2 = new File("/Users/ernestsmotlak/Desktop/Famnit/Diploma/Lexicons/positive_words_Slovenian.txt");
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
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < negativeAL.toArray().length; j++) {
                if (findSimilarity(negativeAL.get(j), words[i]) >= 0.8) {
                    negWords++;
                    break;
                }
            }
        }

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < positiveAL.toArray().length; j++) {
                if (findSimilarity(positiveAL.get(j), words[i]) >= 0.8) {
                    posWords++;
                    break;
                }
            }
        }

        return (posWords - negWords) / words.length;
    }

    public static float[] difference(String[] words, ArrayList<String> positiveLexicon, ArrayList<String> negativeLexicon, float posWords, float negWords) {
        for (int i = 0; i < words.length; i++) {
            if (positiveLexicon.contains(words[i])) {
                posWords++;
            } else if (negativeLexicon.contains(words[i])) {
                negWords++;
            }
        }
        float stringLength = words.length;
        return new float[]{posWords, negWords, stringLength};
    }

    public static void whichPositiveWords(String[] words, ArrayList<String> positiveLexicon, ArrayList<String> negativeLexicon) {
        String[] returnedPositiveWords = {};
        for (int i = 0; i < words.length; i++) {
            if (positiveLexicon.contains(words[i])) {
                System.out.println("Positive word: " + words[i]);
            }
            else if (negativeLexicon.contains(words[i])) {
                System.out.println("Negative word: " + words[i]);
            }
        }
    }
}
