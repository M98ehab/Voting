package aapg.aapgsusc.voting.Competition;

public class Candidate {
    String name;
    int id;
    int votes;

    public Candidate(){

    }

    public Candidate(String name, int id , int votes) {
        this.name = name;
        this.id = id;
        this.votes=votes;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getVotes() {
        return votes;
    }
}

