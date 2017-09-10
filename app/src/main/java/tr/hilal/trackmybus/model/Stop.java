package tr.hilal.trackmybus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TOSHIBA on 6/28/2015.
 */
public class Stop implements Parcelable {
    int id;
    String stopName,Towards;

    // constructor for adding sample data

    public Stop() {
    }

    public Stop(String stopName) {

        this.stopName = stopName;
    }

    public Stop(int id, String stopName) {
        this.id = id;
        this.stopName = stopName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStopName() {
        return stopName;
    }

    public String getTowards() {
        return Towards;
    }

    public void setTowards(String towards) {
        Towards = towards;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }
    public Stop(Parcel in ){
        id = in.readInt();;
        stopName = in.readString();
        Towards = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(stopName);
        dest.writeString(Towards);

    }
    public static final Parcelable.Creator<Stop> CREATOR =
            new Parcelable.Creator<Stop>(){

                @Override
                public Stop createFromParcel(Parcel source) {
                    return new Stop();
                }

                @Override
                public Stop[] newArray(int size) {
                    return new Stop[size];
                }
            };
}
