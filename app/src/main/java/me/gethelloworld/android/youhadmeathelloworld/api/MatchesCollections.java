package me.gethelloworld.android.youhadmeathelloworld.api;

import java.util.List;

/**
 * Created by david on 10/18/14.
 */
public class MatchesCollections {
    private List<String> potential;

    private List<String> interests;

    private List<String> rejects;

    private List<String> matches;

    public List<String> getPotential() {
        return potential;
    }

    public void setPotential(List<String> potential) {
        this.potential = potential;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public List<String> getRejects() {
        return rejects;
    }

    public void setRejects(List<String> rejects) {
        this.rejects = rejects;
    }

    public List<String> getMatches() {
        return matches;
    }

    public void setMatches(List<String> matches) {
        this.matches = matches;
    }
}
