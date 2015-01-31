package com.example.android.pathlossestimator;

/**
 * Created by raul on 28.1.2015.
 */
public class Measurement {


    private String mBssid;

    private int mRss;
    private int mNIterations;
    private int mMetersAway;
    private int mRssTotal;
    private int mRssMean;

    public Measurement(){
        mRss = 0;
        mNIterations = 0;
        mMetersAway = 0;
        mRssTotal = 0;
        mRssMean = 0;
    }

    public String getmBssid() {
        return mBssid;
    }

    public void setmBssid(String mBssid) {
        this.mBssid = mBssid;
    }

    public int getmRss() {
        return mRss;
    }

    public void setmRss(int mRss) {
        this.mRss = mRss;
    }

    public int getmNIterations() {
        return mNIterations;
    }

    public void setmNIterations(int mNIterations) {
        this.mNIterations = mNIterations;
    }

    public void increaseIterations(){
        this.mNIterations++;
    }

    public int getmMetersAway() {
        return mMetersAway;
    }

    public void setmMetersAway(int mMetersAway) {
        this.mMetersAway = mMetersAway;
    }

    public int getmRssTotal() {
        return mRssTotal;
    }

    public void setmRssTotal(int mRssTotal) {
        this.mRssTotal = mRssTotal;
    }

    public int getmRssMean() {
        return mRssMean;
    }

    public void setmRssMean(int mRssMean) {
        this.mRssMean = mRssMean;
    }
}
