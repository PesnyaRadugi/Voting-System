package models;

import java.util.ArrayList;
import java.util.List;

public class Voting {
    private String title;
    private List<Candidate> candidates = new ArrayList<>();

    public Voting(String title, List<Candidate> candidates) {
        this.title = title;
        this.candidates = candidates;
    }

    public String getTitle() {
        return title;
    }
}
