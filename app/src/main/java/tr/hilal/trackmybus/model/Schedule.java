package tr.hilal.trackmybus.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by TOSHIBA on 6/28/2015.
 */
public class Schedule implements Parcelable{
    private int id;
    private String time,arrival;

    private Bus bus;
    private Stop stop;

    public Schedule() {
    }

    public Schedule(int id, String time, int halt) {
        this.id = id;
        this.time = time;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

       public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public Stop getStop() {
        return stop;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Schedule(Parcel in){
        super();
        this.id=in.readInt();
        this.time=in.readString();

        this.bus=in.readParcelable(Bus.class.getClassLoader());
        this.stop=in.readParcelable(Stop.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(getId());
        parcel.writeString(getTime());
        parcel.writeParcelable( getStop(), flags);
        parcel.writeParcelable( getBus(),flags);
    }

    public static final Parcelable.Creator<Schedule> CREATOR = new Parcelable.Creator<Schedule>() {
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        public Schedule[] newArray(int size) {
            return new Schedule[size];
        }
    };
}
