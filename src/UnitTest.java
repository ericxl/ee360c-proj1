/*
 * Name: Xiaoyong Liang
 * EID: XL5432
 */

import org.junit.Assert;
import org.junit.Test;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;


public class UnitTest {
    @Test
    public void testIsStableMatching() {
        Program1 program = new Program1();
        Matching m = parseMatchingFromString("4 4\n" +
                "2 1 1 1 \n" +
                "1 1 2 2 \n" +
                "1 1 1 1 \n" +
                "1 1 1 1 \n" +
                "2 3 1 1 \n" +
                "1 2 2 2 \n" +
                "1 1 1 1 \n" +
                "1 2 1 1 \n");
        Matching bf = program.stableMarriageBruteForce(m);
        Matching g = program.stableMarriageGaleShapley(m);
        Assert.assertTrue(program.isStableMatching(bf));
    }

    @Test
    public void testStable1() throws Exception {
        Program1 program = new Program1();
        Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

        ArrayList<Integer> newMatches = new ArrayList<>();
        newMatches.add(2);
        newMatches.add(0);
        newMatches.add(1);
        newMatches.add(3);
        matching = new Matching(matching, newMatches);

        Assert.assertTrue("Should be stable", program.isStableMatching(matching));
    }

    @Test
    public void testStable2() throws Exception {
        Program1 program = new Program1();
        Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

        ArrayList<Integer> newMatches = new ArrayList<>();
        newMatches.add(3);
        newMatches.add(2);
        newMatches.add(1);
        newMatches.add(0);
        matching = new Matching(matching, newMatches);

        System.out.println(matching);

        Assert.assertTrue("Should be stable", program.isStableMatching(matching));
    }

    @Test
    public void testUnstable() throws Exception {
        Program1 program = new Program1();
        Matching matching = Driver.parseMatchingProblem("small_inputs/4.in");

        ArrayList<Integer> newMatches = new ArrayList<>();
        newMatches.add(0);
        newMatches.add(2);
        newMatches.add(3);
        newMatches.add(1);
        matching = new Matching(matching, newMatches);

        System.out.println(matching);

        Assert.assertFalse("Should not be stable", program.isStableMatching(matching));
    }




    @Test
    public void testSmallSuite1BruteForce() throws Exception {
        for(int i = 4; i <= 10; i++) {
            Assert.assertTrue("Should be stable", checkBruteStability("small-suite-1/" + i + ".in"));
        }
    }

    @Test
    public void testSmallSuite1GaleShapley() throws Exception {
        for(int i = 4; i <= 10; i++) {
            Assert.assertTrue("Should be stable", checkGSStability("small-suite-1/" + i + ".in"));
        }
    }

    @Test
    public void testStableGaleShapleyLargeInput() throws Exception {
        for(int i = 320; i <= 1280; i *= 2) {
            Assert.assertTrue("Should be stable", checkGSStability("large_inputs/" + i + ".in"));
        }
    }



    public boolean checkBruteStability(String fileName) throws Exception {
        Program1 program = new Program1();
        Matching matching = Driver.parseMatchingProblem(fileName);
        long start = System.nanoTime();
        Matching result = program.stableMarriageBruteForce(matching);
        long end = System.nanoTime();
        System.out.printf("\n%s:\n%s\n\n", fileName, result.toString());
        System.out.printf("BF - Run time of %20s: %10.8f seconds\n", fileName, (end - start) / 1.0e9);
        return program.isStableMatching(result);
    }


    public boolean checkGSStability(String fileName) throws Exception {
        Program1 program = new Program1();
        Matching matching = Driver.parseMatchingProblem(fileName);
        long start = System.nanoTime();
        Matching result = program.stableMarriageGaleShapley(matching);
        long end = System.nanoTime();
        System.out.printf("\n%s:\n%s\n\n", fileName, result.toString());
        System.out.printf("GS - Run time of %20s: %10.8f seconds\n", fileName, (end - start) / 1.0e9);
        return program.isStableMatching(result);
    }

    private Matching parseMatchingFromString(String input) {
        int m = 0;
        int n = 0;
        ArrayList<ArrayList<Integer>> menPrefs, womenPrefs;
        ArrayList<Integer> menCount;

        Scanner sc = new Scanner(input);
        String[] inputSizes = sc.nextLine().split(" ");

        m = Integer.parseInt(inputSizes[0]);
        n = Integer.parseInt(inputSizes[1]);
        menCount = Driver.readCountsList(sc, m);

        menPrefs = Driver.readPreferenceLists(sc, m);
        womenPrefs = Driver.readPreferenceLists(sc, n);

        Matching problem = new Matching(m, n, menPrefs, womenPrefs,
                menCount);

        return problem;
    }

    private Matching parseMatchingFromFile(String inputFile) {
        Matching m = null;
        try {
            m = Driver.parseMatchingProblem(inputFile);
        }
        catch (Exception e) {
        }
        return m;
    }
}
