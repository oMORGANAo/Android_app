package be.kuleuven.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class StudySession implements Parcelable {
    int sessionID;
    String sessionTitle;
    String sessionLink;
    String sessionDate;
    String sessionTime;
    String sessionDescription;



    public static final Creator<StudySession> CREATOR = new Creator<StudySession>() {
        @Override
        public StudySession createFromParcel(Parcel in) {
            return new StudySession(in);
        }

        @Override
        public StudySession[] newArray(int size) {
            return new StudySession[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public StudySession( int sessionID, String sessionTitle, String sessionLink, String sessionDate, String sessionTime, String sessionDescription){
        this.sessionID=sessionID;
        this.sessionTitle=sessionTitle;
        this.sessionLink=sessionLink;
        this.sessionDate=sessionDate;
        this.sessionTime=sessionTime;
        this.sessionDescription=sessionDescription;


    }

    protected StudySession( Parcel in)
    { this(
           in.readInt(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString(),
            in.readString()
        );

    }

    public StudySession(JSONObject o)
    {
        try {
            sessionID = o.getInt("sessionID");
            sessionTitle = o.getString("title");
            sessionLink = o.getString("link");
            sessionDate = o.getString("date");
            sessionTime = o.getString("time");
            sessionDescription = o.getString("description");
        }
            catch (JSONException e) {
                e.printStackTrace();
            }

        }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sessionID);
        dest.writeString(sessionTitle);
        dest.writeString(sessionLink);
        dest.writeString(sessionDate);
        dest.writeString(sessionTime);
        dest.writeString(sessionDescription);
    }

    public int getSessionID() {
        return sessionID;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public String getSessionTime() {
        return sessionTime;
    }

    public String getSessionDate() {
        return sessionDate;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public String getSessionLink() {
        return sessionLink;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }
}
