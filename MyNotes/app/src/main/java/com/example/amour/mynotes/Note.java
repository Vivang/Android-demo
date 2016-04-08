package com.example.amour.mynotes;

/**
 * Created by Amour on 2016/4/7.
 */
public class Note {
    private int mId;
    private String mTitle;
    private String mText;
    private String mTime;

    public Note() {
    }

    public Note(int mId,String mtitle, String mText, String mTime) {
        this.mId=mId;
        this.mTitle = mtitle;
        this.mText = mText;
        this.mTime = mTime;
    }


    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
}
