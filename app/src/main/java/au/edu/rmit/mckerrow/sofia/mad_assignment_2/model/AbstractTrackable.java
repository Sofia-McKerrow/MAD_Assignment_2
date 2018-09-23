package au.edu.rmit.mckerrow.sofia.mad_assignment_2.model;

public abstract class AbstractTrackable {

    private int trackableID;
    private String name;
    private String description;
    private String url;
    private String category;
    private String image;

    public AbstractTrackable(int trackableID, String name, String description, String url, String category, String image) {
        this.trackableID = trackableID;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.image = image;
    }

    public int getTrackableID() {
        return trackableID;
    }

    public void setTrackableID(int trackableID) {
        this.trackableID = trackableID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() { return image; }

    public void setImage(String image) { this.image = image; }

    @Override
    public String toString() {
        return "AbstractTrackable{" +
                "trackableID=" + trackableID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
