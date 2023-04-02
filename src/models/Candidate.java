package models;

public class Candidate {
    private String name;
    private int voices;

    public Candidate(String name, int voices) {
        this.name = name;
        this.voices = voices;
    }

    public Candidate(String name) {
        this.name = name;
        voices = 0;
    }

    public void addVoice() {
        voices++;
    }
    
    public String getName() {
        return name;
    }

    public int getVoices() {
        return voices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVoices(int voices) {
        this.voices = voices;
    }

}
