package models;

import java.util.ArrayList;
import java.util.List;

public class Voting {
    private String title;
    private List<Candidate> candidates = new ArrayList<>();
    private List<Elector> participants;

    public Voting(String title, List<Candidate> candidates) {
        this.title = title;
        this.candidates = candidates;
    }

    public Voting() {
    }

    public Voting(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public void addCandidate(Candidate candidate) {
        candidates.add(candidate);
    }

    public void deleteCandidate(Candidate candidate) {
        candidates.remove(candidate);
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }
}
