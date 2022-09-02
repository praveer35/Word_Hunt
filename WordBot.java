import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.lang.Integer;
import java.util.ArrayList;
import java.util.Collections;

public class WordBot {
    public static String [][] twoLtrSort = new String[26][26];
    public static String [][] map = new String[4][4];
    public static ArrayList<String> codeList = new ArrayList<String>();
    public static ArrayList<String> wordList = new ArrayList<String>();
    public static void createTwoLtrSort() {
        try {
            File myObj = new File("dictionary.txt");
            Scanner myReader = new Scanner(myObj);
            char ltr1 = 'a';
            char ltr2 = 'a';
            int index1 = 0;
            int index2 = 0;
            String data;
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                if (data.charAt(0) != ltr1 || data.charAt(1) != ltr2) {
                    index1 = (int) data.charAt(0) - 97;
                    index2 = (int) data.charAt(1) - 97;
                    ltr1 = data.charAt(0);
                    ltr2 = data.charAt(1);
                    twoLtrSort[index1][index2] = "";
                }
                twoLtrSort[index1][index2] += " " + data;
            }
            for (int i = 0; i < 26; i++) {
                for (int j = 0; j < 26; j++)
                    twoLtrSort[i][j] += " ";
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static void fillMap(String ltrs) {
        for (int i = 0; i < 15; i++)
            map[i / 4][i % 4] = ltrs.substring(i, i + 1);
    }
    public static String codeToString(String numCode) {
        String [] nums = numCode.substring(1).split(" ");
        String word = "";
        for (int i = 0; i < nums.length; i++)
            word += map[Integer.parseInt(nums[i]) / 4][Integer.parseInt(nums[i]) % 4];
        return word;
    }
    public static void codeListToStringList() {
        for (int i = 32; i >= 6; i--) {
            for (int j = 0; j < codeList.size(); j++) {
                if (codeList.get(j).length() == i && !wordList.contains(codeToString(codeList.get(j)))) {
                    System.out.println(codeToString(codeList.get(j)));
                    generateUserMap(codeList.get(j));
                    System.out.println("\n");
                    wordList.add(codeToString(codeList.get(j)));
                }
            }
        }
    }
    public static void generateUserMap(String codeNum) {
        int [] userMap = new int[16];
        String [] nums = codeNum.substring(1).split(" ");
        for (int i = 0; i < nums.length; i++)
            userMap[Integer.parseInt(nums[i])] = i + 1;
        String [] sUM = new String[16];
        for (int i = 0; i < userMap.length; i++) {
            if (userMap[i] == 0)
                sUM[i] = " ";
            else
                sUM[i] = "" + userMap[i];
        }
        System.out.println("| " + sUM[0] + " | " + sUM[1] + " | " + sUM[2] + " | " + sUM[3] + " |");
        System.out.println(" ___ ___ ___ ___ ");
        System.out.println("| " + sUM[4] + " | " + sUM[5] + " | " + sUM[6] + " | " + sUM[7] + " |");
        System.out.println(" ___ ___ ___ ___ ");
        System.out.println("| " + sUM[8] + " | " + sUM[9] + " | " + sUM[10] + " | " + sUM[11] + " |");
        System.out.println(" ___ ___ ___ ___ ");
        System.out.println("| " + sUM[12] + " | " + sUM[13] + " | " + sUM[14] + " | " + sUM[15] + " |");
    }
    public static boolean findWordsFromStartLtr(int index, String numCode) {
        String word = codeToString(numCode);
        if (word.length() > 1) {
            String wordOptions = twoLtrSort[(int) word.charAt(0) - 97][(int) word.charAt(1) - 97];
            if (wordOptions.length() < 2 || !(wordOptions.contains(" " + word))) {
                return false;
            }
        }
        if (index - 1 > -1 && (index - 1) % 4 != 3 && !numCode.contains(" " + (index - 1) + " "))     // left
            findWordsFromStartLtr(index - 1, numCode + (index - 1) + " ");
        if (index + 1 < 16 && (index + 1) % 4 != 0 && !numCode.contains(" " + (index + 1) + " "))     // right
            findWordsFromStartLtr(index + 1, numCode + (index + 1) + " ");
        if (index + 4 < 16 && !numCode.contains(" " + (index + 4) + " "))                             // down
            findWordsFromStartLtr(index + 4, numCode + (index + 4) + " ");
        if (index - 4 > -1 && !numCode.contains(" " + (index - 4) + " "))                             // up
            findWordsFromStartLtr(index - 4, numCode + (index - 4) + " ");
        if (index + 3 < 16 && (index + 3) % 4 != 3 && !numCode.contains(" " + (index + 3) + " "))     // down-left
            findWordsFromStartLtr(index + 3, numCode + (index + 3) + " ");
        if (index + 5 < 16 && (index + 5) % 4 != 0 && !numCode.contains(" " + (index + 5) + " "))     // down-right
            findWordsFromStartLtr(index + 5, numCode + (index + 5) + " ");
        if (index - 5 > -1 && (index - 5) % 4 != 3 && !numCode.contains(" " + (index - 5) + " "))     // up-left
            findWordsFromStartLtr(index - 5, numCode + (index - 5) + " ");
        if (index - 3 > -1 && (index - 3) % 4 != 0 && !numCode.contains(" " + (index - 3) + " "))     // up-right
            findWordsFromStartLtr(index - 3, numCode + (index - 3) + " ");
        if (word.length() > 2) {
            if (twoLtrSort[(int) word.charAt(0) - 97][(int) word.charAt(1) - 97].contains(" " + word + " "))
                codeList.add(numCode);
        }
        return true;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("From left to right, then top to bottom, all lowercase, no spaces: ");
        String ltrs = input.next();
        fillMap(ltrs);
        createTwoLtrSort();
        for (int i = 0; i < 15; i++)
            findWordsFromStartLtr(i, " " + i + " ");
        codeListToStringList();
    }
}