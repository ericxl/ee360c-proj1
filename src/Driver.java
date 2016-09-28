import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Driver {
    public static String filename;
    public static boolean testBruteForce;
    public static boolean testGS;
    
    public static void main(String[] args) throws Exception {
        parseArgs(args);
        
        Matching problem = parseMatchingProblem(filename);
        testRun(problem);
    }

    private static void usage() {
        System.err.println("usage: java Driver [-g] [-b] <filename>");
        System.err.println("\t-b\tTest Brute Force implementation");
        System.err.println("\t-g\tTest Gale-Shapley implementation");
        System.exit(1);
    }
    
    public static void parseArgs(String[] args) {
        if (args.length == 0) {
            usage();
        }
        
        filename = "";
        testBruteForce = false;
        testGS = false;
        boolean flagsPresent = false;
        
        for (String s : args) {
            if(s.equals("-g")) {
                flagsPresent = true;
                testGS = true;
            } else if(s.equals("-b")) {
                flagsPresent = true;
                testBruteForce = true;
            } else if(!s.startsWith("-")) {
                filename = s;
            } else {
                System.err.printf("Unknown option: %s\n", s);
                usage();
            }
        }
        
        if(!flagsPresent) {
            testBruteForce = true;
            testGS = true;
        }
    }

    public static Matching parseMatchingProblem(String inputFile)
            throws Exception {
        int m = 0;
        int n = 0;
        ArrayList<ArrayList<Integer>> menPrefs, womenPrefs;
        ArrayList<Integer> menCount;

        Scanner sc = new Scanner(new File(inputFile));
        String[] inputSizes = sc.nextLine().split(" ");

        m = Integer.parseInt(inputSizes[0]);
        n = Integer.parseInt(inputSizes[1]);
        menCount = readCountsList(sc, m);
	
//	System.out.println(menCount);
        menPrefs = readPreferenceLists(sc, m);
        womenPrefs = readPreferenceLists(sc, n);

        Matching problem = new Matching(m, n, menPrefs, womenPrefs,
                menCount);

        return problem;
    }

    private static ArrayList<Integer> readCountsList(Scanner sc, int n) {
        ArrayList<Integer> menCount = new ArrayList<Integer>(1);

//        String[] m = sc.nextLine().split(" ");
//	System.out.println(m);
        for (int i = 0; i < n; i++) {
		menCount.add(1);
//            menCount.add(Integer.parseInt(m[i]));
        }

        return menCount;
    }

    private static ArrayList<ArrayList<Integer>> readPreferenceLists(
            Scanner sc, int m) {
        ArrayList<ArrayList<Integer>> preferenceLists;
        preferenceLists = new ArrayList<ArrayList<Integer>>(0);

//	System.out.println("Hit " + m);
//	System.out.println();
        for (int i = 0; i < m; i++) {
            String line = sc.nextLine();
//		System.out.println(line);
            String[] preferences = line.split(" ");
            ArrayList<Integer> preferenceList = new ArrayList<Integer>(0);

//		System.out.println(preferences.length);
            for (Integer j = 0; j < preferences.length; j++) {
                preferenceList.add(Integer.parseInt(preferences[j]));
            }
            preferenceLists.add(preferenceList);
        }
//	System.out.println("$ " + m);

        return preferenceLists;
    }

    public static void testRun(Matching problem) {
        Program1 program = new Program1();
        boolean isStable;

        if (testGS) {
            Matching GSMatching = program.stableMarriageGaleShapley(problem);
            System.out.println(GSMatching);
            isStable = program.isStableMatching(GSMatching);
            System.out.printf("%s: stable? %s\n", "Gale-Shapley", isStable);
            System.out.println();
        }

        if (testBruteForce) {
            Matching BFMatching = program.stableMarriageBruteForce(problem);
            System.out.println(BFMatching);
            isStable = program.isStableMatching(BFMatching);
            System.out.printf("%s: stable? %s\n", "Brute Force", isStable);
            System.out.println();
        }
    }
}
