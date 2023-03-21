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

    public Voting() {
    }

    public Voting(String title) {
        this.title = title;
        candidates = null;
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<String> getCandidatesNames() {
        List<String> candidatesNames = new ArrayList<>();

        for (Candidate candidate : candidates) {
            candidatesNames.add(candidate.getName());
        }

        return candidatesNames;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
