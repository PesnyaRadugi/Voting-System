package models;

public class Elector extends User {

    private boolean voted = false;

    public boolean isVoted() {
        return voted;
    }

    private void vote() {
        voted = true;
    }
}
