import Utils.MinHeap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

class Test implements Comparable<Test>{
    public Test(int data) {
        this.data = data;
    }
    public int data;

    @Override
    public int compareTo(Test other) {
        if (other == null) return 1;
        if (data < other.data) return -1;
        if (data > other.data) return 1;
        return 0;
    }
}

public class TestMain {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new FileReader("src/main/resources/Map/map1.txt"));
            int gridSize = scanner.nextInt();
            int mapGridWidth = scanner.nextInt();
            int mapGridHeight = scanner.nextInt();
            System.out.println(scanner.nextLine());
            for (int i = 0; i < mapGridHeight; ++i) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {

        }
    }
}
