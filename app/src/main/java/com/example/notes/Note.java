package com.example.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * homework com.example.notes
 *
 * @author Amina
 * 06.06.2021
 */
public class Note implements Parcelable {

    private final String name;
    private final String description;
    private Date date;
    private final String author;

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
        this.date = new Date();
        this.author = System.getProperty("user.name");
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        author = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(author);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }
}
