package me.gethelloworld.android.youhadmeathelloworld.api;

/**
 * Created by david on 10/18/14.
 */
public class GitHubUser {

    private String login;
    private String avatar_url;
    private String name;

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
