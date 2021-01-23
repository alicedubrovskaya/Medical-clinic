package controller.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {
    private String url;
    private String name;
    private List<MenuItem> menuItemsDropDown;

    public MenuItem(String url, String name, List<MenuItem> menuItems) {
        this.url = url;
        this.name = name;
        this.menuItemsDropDown = menuItems;
    }

    public MenuItem(String url, String name) {
        this.url = url;
        this.name = name;
        this.menuItemsDropDown = new ArrayList<>();
    }


    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getMenuItemsDropDown() {
        return menuItemsDropDown;
    }
}
