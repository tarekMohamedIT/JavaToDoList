package Domain.Entities;

import java.util.ArrayList;
import java.util.List;

public class ChecklistNote implements Entity{
    private int id;
    private String title;
    private List<ChecklistItem> items;

    public ChecklistNote(int id, String title, List<ChecklistItem> items) {
        this.id = id;
        this.title = title;
        this.items = items == null ? new ArrayList<>() : items;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChecklistItem> getItems() {
        return items;
    }

    public void setItems(List<ChecklistItem> items) {
        this.items = items;
    }
}
