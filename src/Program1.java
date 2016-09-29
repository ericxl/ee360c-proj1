/*
 * Name: Xiaoyong Liang
 * EID: XL5432
 */

import java.util.ArrayList;
import java.util.Vector;

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
                if (menPreference.get(m1).get(w1) > menPreference.get(m1).get(w2) &&
                        menPreference.get(m2).get(w2) > menPreference.get(m2).get(w1) &&
                        womenPreference.get(w1).get(m1) > womenPreference.get(w1).get(m2) &&
                        womenPreference.get(w2).get(m2) > womenPreference.get(w2).get(m1)) {
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
        // matching result temporary storage and initialized with -1, which means this women hasn't been matched
        ArrayList<Integer> result_matching = new ArrayList<Integer>(marriage.getWomenCount());
        for (int w = 0; w < marriage.getWomenCount(); w++) {
            result_matching.add(new Integer(-1));
        }
        // table indicate if the corresponding man m had proposed to corresponding women w, 0 means false 1 means true
        ArrayList<ArrayList<Integer>> proposed_table = new ArrayList<ArrayList<Integer>>(marriage.getMenCount());
        for (int m = 0; m < marriage.getMenCount(); m++) {
            ArrayList<Integer> temp = new ArrayList<Integer>(marriage.getWomenCount());
            for (int w = 0; w < marriage.getWomenCount(); w++) {
                temp.add(new Integer(0));
            }
            proposed_table.add(temp);
        }
        // man number vector to keep tracking on the man left
        Vector<Integer> man_number = new Vector<Integer>();
        for (int m = 0; m < marriage.getMenCount(); m++) {
            man_number.addElement(new Integer(m));
        }
        // consume all element in men vector
        while (!man_number.isEmpty()) {
            int m = man_number.firstElement();
            // find max rank of the given man m
            int max_rank = 1;
            for (int w = 0; w < marriage.getWomenCount(); w++) {
                if (marriage.getMenPreference().get(m).get(w) > max_rank)
                    max_rank = marriage.getMenPreference().get(m).get(w);
            }
            // loop through all the rank starting from 1
            int rank = 1;
            boolean proposal_made = false;
            while (rank <= max_rank) {
                for (int w = 0; w < marriage.getWomenCount(); w++) {
                    // choose the highest ranking women which hasn't been proposed by m yet
                    if (rank == marriage.getMenPreference().get(m).get(w) && proposed_table.get(m).get(w) == 0) {
                        // if this women is free, then put them together first
                        if (result_matching.get(w) == -1) {
                            //remove first element and update result
                            man_number.remove(0);
                            result_matching.remove(w);
                            result_matching.add(w, new Integer(m));
                            // update proposed table
                            ArrayList<Integer> temp = proposed_table.get(m);
                            assert (temp.get(w) == 0);
                            temp.remove(w);
                            temp.add(w, new Integer(1));
                            proposed_table.remove(m);
                            proposed_table.add(m, temp);
                            proposal_made = true;
                            break;
                        }
                        // if not, two man should fight. the one with higher preference in w's list wins
                        else {
                            int illfightforyou = result_matching.get(w);
                            // challenger only wins when ranking higher than the previous one, lose even when tie
                            if (m < marriage.getWomenPreference().get(w).get(illfightforyou)) {
                                // push loser to the queue of the man, and remove the first element
                                Integer loser = result_matching.get(w);
                                man_number.addElement(loser);
                                man_number.remove(0);
                                // update matching result
                                result_matching.remove(w);
                                result_matching.add(w, new Integer(m));
                                // update proposed table
                                ArrayList<Integer> temp = proposed_table.get(m);
                                assert (temp.get(w) == 0);
                                temp.remove(w);
                                temp.add(w, new Integer(1));
                                proposed_table.remove(m);
                                proposed_table.add(m, temp);
                                proposal_made = true;
                                break;
                            }
                        }
                    }
                }
                // if proposal made then move on to next man, find next lower rank if not
                if (proposal_made) break;
                rank++;
            }
        }
        marriage.setWomanMatching(result_matching);
        return marriage;
    }
}
