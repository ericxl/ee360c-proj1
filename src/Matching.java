import java.util.ArrayList;

/**
 * A Matching represents a candidate solution to the Stable Marriage problem. A
 * Matching may or may not be stable.
 */
public class Matching {
    /** Number of men. */
    private Integer m;

    /** Number of women. */
    private Integer n;

    /** A list containing each man's preference list. */
    private ArrayList<ArrayList<Integer>> men_preference;

    /** A list containing each woman's preference list. */
    private ArrayList<ArrayList<Integer>> women_preference;

    /** Number of men. */
    private ArrayList<Integer> men_count;  // ignore this artifact of a past project

    /**
     * Matching information representing the index of man a woman is
     * matched to, -1 if not matched.
     * 
     * An empty matching is represented by a null value for this field.
     */
    private ArrayList<Integer> woman_matching;

    public Matching(Integer m, Integer n,
            ArrayList<ArrayList<Integer>> men_preference,
            ArrayList<ArrayList<Integer>> women_preference,
            ArrayList<Integer> men_count) {
        this.m = m;
        this.n = n;
        this.men_preference = men_preference;
        this.women_preference = women_preference;
        this.men_count = men_count;
        this.woman_matching = null;
    }

    public Matching(Integer m, Integer n,
            ArrayList<ArrayList<Integer>> men_preference,
            ArrayList<ArrayList<Integer>> women_preference,
            ArrayList<Integer> men_count,
            ArrayList<Integer> woman_matching) {
        this.m = m;
        this.n = n;
        this.men_preference = men_preference;
        this.women_preference = women_preference;
        this.men_count = men_count;
        this.woman_matching = woman_matching;
    }

    /**
     * Constructs a solution to the stable marriage problem, given the problem
     * as a Matching. Take a Matching which represents the problem data with no
     * solution, and a woman_matching which solves the problem given in data.
     * 
     * @param data
     *            The given problem to solve.
     * @param woman_matching
     *            The solution to the problem.
     */
    public Matching(Matching data, ArrayList<Integer> woman_matching) {
        this(data.m, data.n, data.men_preference,
                data.women_preference, data.men_count,
                woman_matching);
    }

    /**
     * Creates a Matching from data which includes an empty solution.
     * 
     * @param data
     *            The Matching containing the problem to solve.
     */
    public Matching(Matching data) {
        this(data.m, data.n, data.men_preference,
                data.women_preference, data.men_count,
                new ArrayList<Integer>(0));
    }

    public void setWomanMatching(ArrayList<Integer> woman_matching) {
        this.woman_matching = woman_matching;
    }

    public Integer getMenCount() {
        return m;
    }

    public Integer getWomenCount() {
        return n;
    }

    public ArrayList<ArrayList<Integer>> getMenPreference() {
        return men_preference;
    }

    public ArrayList<ArrayList<Integer>> getWomenPreference() {
        return women_preference;
    }

    public ArrayList<Integer> getMenSlots() {
        return men_count;
    }

    public ArrayList<Integer> getWomenMatching() {
        return woman_matching;
    }

    public int totalMenCount() {
        int count = 0;
        for (int i = 0; i < m; i++) {
            count += men_count.get(i);
        }
        return count;
    }
    
    public String getInputSizeString() {
        return String.format("m=%d n=%d\n", m, n);
    }
    
    public String getSolutionString() {
        if (woman_matching == null) {
            return "";
        }
        
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < woman_matching.size(); i++) {
            String str = String.format("Woman %d Man %d", i, woman_matching.get(i));
            s.append(str);
            if (i != woman_matching.size() - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }
    
    public String toString() {
        return getInputSizeString() + getSolutionString();
    }
}
