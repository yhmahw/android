package me.gethelloworld.android.youhadmeathelloworld.api;

import java.util.List;
import java.util.Map;

/**
 * Created by david on 10/18/14.
 */
public class UserData {
    private String userId;
    private List<String> platforms;
    private List<String> languages;
    private String advert;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(List<String> platforms) {
        this.platforms = platforms;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getAdvert() {
        return advert;
    }

    public void setAdvert(String advert) {
        this.advert = advert;
    }
}
