package by.dubrovskaya.command.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {
    private String url;
    private String russianName;
    private String englishName;
    private List<MenuItem> menuItemsDropDown;

    public MenuItem(String url, String russianName, String englishName, List<MenuItem> menuItems) {
        this.url = url;
        this.russianName = russianName;
        this.englishName = englishName;
        this.menuItemsDropDown = menuItems;
    }

    public MenuItem(String russianName, String englishName, List<MenuItem> menuItems) {
        this.russianName = russianName;
        this.englishName = englishName;
        this.menuItemsDropDown = menuItems;
    }

    //TODO
    public MenuItem(String url, String russianName, String englishName) {
        this.url = url;
        this.russianName = russianName;
        this.englishName = englishName;
        this.menuItemsDropDown = new ArrayList<>();
    }


    public String getUrl() {
        return url;
    }

    public String getRussianName() {
        return russianName;
    }

    public List<MenuItem> getMenuItemsDropDown() {
        return menuItemsDropDown;
    }

    public String getEnglishName() {
        return englishName;
    }
}
