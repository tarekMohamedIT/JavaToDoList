package Domain.QueryObjects;

public class SimpleNoteQuery {
    private int _id;

    private String _title;

    public String getTitle() {
        return _title;
    }

    public SimpleNoteQuery setTitle(String title) {
        this._title = title;
        return this;
    }

    public int getId() {
        return _id;
    }

    public SimpleNoteQuery setId(int _id) {
        this._id = _id;
        return this;
    }
}
