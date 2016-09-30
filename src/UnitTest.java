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
    public void testIsStable1() {
        Program1 program = new Program1();
        Matching m = parseMatchingFromString("2 2\n" +
                "1 2\n" +
                "2 1\n" +
                "1 2\n" +
                "2 1");
        ArrayList<Integer> match = new ArrayList<>();
        match.add(0,1);
        match.add(1,0);
        m.setWomanMatching(match);
        Assert.assertFalse(program.isStableMatching(m));
    }

    @Test
    public void testIsStable2() {
        Program1 program = new Program1();
        Matching m = parseMatchingFromString("2 2\n" +
                "1 2\n" +
                "1 2\n" +
                "1 2\n" +
                "1 2");
        ArrayList<Integer> match = new ArrayList<>();
        match.add(0,0);
        match.add(1,1);
        m.setWomanMatching(match);
        Assert.assertTrue(program.isStableMatching(m));
    }

    @Test
    public void testIsStable3() {
        Program1 program = new Program1();
        Matching m = parseMatchingFromString("2 2\n" +
                "2 1\n" +
                "2 1\n" +
                "1 2\n" +
                "1 2");
        ArrayList<Integer> match1 = new ArrayList<>();
        match1.add(0,1);
        match1.add(1,0);
        m.setWomanMatching(match1);
        Assert.assertTrue(program.isStableMatching(m));

        ArrayList<Integer> match2 = new ArrayList<>();
        match2.add(0,0);
        match2.add(1,1);
        m.setWomanMatching(match2);
        Assert.assertFalse(
                program.isStableMatching(m)
        );
    }

    @Test
    public void testIsStable4() {
        Program1 program = new Program1();
        Matching m = parseMatchingFromFile("small_inputs/4.in");

        ArrayList<Integer> match1 = new ArrayList<>();
        match1.add(2);
        match1.add(0);
        match1.add(1);
        match1.add(3);
        m.setWomanMatching(match1);
        Assert.assertTrue("Should be stable", program.isStableMatching(m));

        ArrayList<Integer> match2 = new ArrayList<>();
        match2.add(3);
        match2.add(2);
        match2.add(1);
        match2.add(0);
        m.setWomanMatching(match2);
        Assert.assertTrue("Should be stable", program.isStableMatching(m));

        ArrayList<Integer> match3 = new ArrayList<>();
        match3.add(0);
        match3.add(2);
        match3.add(3);
        match3.add(1);
        m.setWomanMatching(match3);
        Assert.assertFalse("Should not be stable", program.isStableMatching(m));
    }

    @Test
    public void testStableGSSnallInput() throws Exception {
        for(int i = 4; i <= 10; i += 2) {
            Assert.assertTrue("Should be stable", checkGSStability("small_inputs/" + i + ".in"));
        }
    }

    @Test
    public void testStableGSLargeInput() throws Exception {
        for(int i = 320; i <= 1280; i *= 2) {
            Assert.assertTrue("Should be stable", checkGSStability("large_inputs/" + i + ".in"));
        }
    }


    @Test
    public void testBrutalSmall1() {
        Assert.assertTrue("Should be stable", checkBruteStability("small_inputs/4.in"));
    }

    @Test
    public void testBrutalSmall2() {
        Assert.assertTrue("Should be stable", checkBruteStability("small_inputs/6.in"));
    }

    @Test
    public void testBrutalSmall3() {
        Assert.assertTrue("Should be stable", checkBruteStability("small_inputs/8.in"));
    }

    @Test
    public void testBrutalSmall4() {
        Assert.assertTrue("Should be stable", checkBruteStability("small_inputs/10.in"));
    }

    public boolean checkBruteStability(String fileName) {
        Program1 program = new Program1();
        Matching matching = parseMatchingFromFile(fileName);
        long start = System.nanoTime();
        Matching result = program.stableMarriageBruteForce(matching);
        long end = System.nanoTime();
        System.out.printf("\n%s:\n%s\n\n", fileName, result.toString());
        System.out.printf("BF - Run time of %20s: %10.8f seconds\n", fileName, (end - start) / 1.0e9);
        return program.isStableMatching(result);
    }


    public boolean checkGSStability(String fileName) {
        Program1 program = new Program1();
        Matching matching = parseMatchingFromFile(fileName);
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
