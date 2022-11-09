package ca.com.project;

import android.graphics.drawable.Drawable;

import androidx.drawerlayout.widget.DrawerLayout;

public class Contact {

    String name;
    Drawable image;
    int  id;

    public Contact(String name, Drawable image, int id) {
        this.name = name;
        this.image = image;
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public  Drawable getImage() {
        return image;
    }
}


