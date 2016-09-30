/*
 * Name: Xiaoyong Liang
 * EID: XL5432
 */

import java.util.*;

/**
 * Your solution goes in this class.
 * <p>
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * <p>
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program1 extends AbstractProgram1 {
    /**
     * Determines whether a candidate Matching represents a solution to the
     * Stable Marriage problem. Study the description of a Matching in the
     * project documentation to help you with this.
     */
    public boolean isStableMatching(Matching marriage) {
        int womenCount = marriage.getWomenCount();
        ArrayList<Integer> matching = marriage.getWomenMatching();
        ArrayList<ArrayList<Integer>> menPreference = marriage.getMenPreference();
        ArrayList<ArrayList<Integer>> womenPreference = marriage.getWomenPreference();
        for (int w1 = 0; w1 < womenCount; w1++) {
            for (int w2 = w1; w2 < womenCount; w2++) {
                if(w1 == w2){
                    continue;
                }

                int m1 = matching.get(w1);
                int m2 = matching.get(w2);
                if ((menPreference.get(m1).get(w1) > menPreference.get(m1).get(w2) &&
                        womenPreference.get(w2).get(m2) > womenPreference.get(w2).get(m1)) ||

                        (menPreference.get(m2).get(w2) > menPreference.get(m2).get(w1) &&
                                womenPreference.get(w1).get(m1) > womenPreference.get(w1).get(m2))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Determines a solution to the Stable Marriage problem from the given input
     * set. Study the project description to understand the variables which
     * represent the input to your solution.
     *
     * @return A stable Matching.
     */
    public Matching stableMarriageGaleShapley(Matching marriage) {
        // match -1
        ArrayList<Integer> match = new ArrayList<Integer>(marriage.getWomenCount());
        for (int w = 0; w < marriage.getWomenCount(); w++) {
            match.add(new Integer(-1));
        }
        //init freemen list
        ArrayList<Integer> freemen = new ArrayList<Integer>();
        for (int w = 0; w < marriage.getWomenCount(); w++) {
            freemen.add(new Integer(w));
        }

        ArrayList<ArrayList<Integer>> menPreference = new ArrayList<>(marriage.getMenPreference());
        ArrayList<ArrayList<Integer>> womenPreference = new ArrayList<>( marriage.getWomenPreference());
        while(freemen.size()>0) {

            Integer men = freemen.get(0);
            ArrayList<Integer> currentMenPref = menPreference.get(men);
            int fiance = -1;
            for (int i = 0; i < currentMenPref.size(); i++){
                if (currentMenPref.get(i) != -1){
                    if(fiance == -1) {
                        fiance = i; continue;
                    }
                    if (currentMenPref.get(i) < currentMenPref.get(fiance)) {
                        fiance = i;
                        continue;
                    }
                }
            }

            if(match.get(fiance) != -1){
                Integer kickedMan = match.get(fiance);
                freemen.add(kickedMan);
            }

            match.set(fiance, men);
            freemen.remove(0);
            
            List<Integer> engagedWomenPref = womenPreference.get(fiance);
            Integer thisMenPrefInWomen = engagedWomenPref.get(men);
            engagedWomenPref.set(men, new Integer(-1));
            currentMenPref.set(fiance, new Integer(-1));

            for(int i = 0; i < engagedWomenPref.size(); i++){
                if(engagedWomenPref.get(i) >= thisMenPrefInWomen){
                    menPreference.get(i).set(fiance, new Integer(-1));
                    engagedWomenPref.set(i, new Integer(-1));
                }
            }
        }
        marriage.setWomanMatching(match);
        return marriage;
    }
}
