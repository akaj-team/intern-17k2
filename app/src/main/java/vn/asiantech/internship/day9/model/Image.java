package vn.asiantech.internship.day9.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Copyright © 2017 AsianTech inc.
 * Created by at-hoavo on 20/06/2017.
 */
public class Image {
    private int _id;
    private String title;
    private String description;
    private String link;
    private List<String>  links;

    public Image(int _id, String title, String description, String link) {
        this._id = _id;
        this.title = title;
        this.description = description;
        this.link = link;
        links=new ArrayList<>();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getLinks() {
        return links;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }
}
