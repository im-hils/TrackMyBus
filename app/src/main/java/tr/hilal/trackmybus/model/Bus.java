package tr.hilal.trackmybus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TOSHIBA on 6/28/2015.
 */
public class Bus implements Parcelable {
    int id;
    String busName,className;
    public Bus() {
    }

    public Bus(int id, String busName, String className) {
        this.id = id;
        this.busName = busName;
        this.className = className;
    }

    public Bus(String busName, String className) {
        this.busName = busName;
        this.className = className;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }
    public String getBusName(){
        return busName;
    }
    public void setBusName(String busName){
        this.busName = busName;
    }
    public String getClassName(){
        return className;
    }
    public void setClassName(String className){
        this.className = className;
    }

    public Bus(Parcel in ){
        id = in.readInt();;
        busName = in.readString();
        className = in.readString();

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(busName);
        dest.writeString(className);

    }
    public static final Parcelable.Creator<Bus> CREATOR =
            new Parcelable.Creator<Bus>(){

                @Override
                public Bus createFromParcel(Parcel source) {
                    return new Bus();
                }

                @Override
                public Bus[] newArray(int size) {
                    return new Bus[size];
                }
            };
}
