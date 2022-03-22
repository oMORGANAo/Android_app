package be.kuleuven.myapplication;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class Users implements Parcelable{
    String name;
    String email;
    String password;
    int userID;
    String major;

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    public String getPassword() {
        return password;
    }

    public int getUserID() {
        return userID;
    }

    public String getMajor() {
        return major;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Users(String name, String email, String password, String major, int userID)
 {
     this.userID=userID;
     this.name=name;
     this.password=password;
     this.email=email;
     this.major=major;
 }

  protected Users (Parcel in)
  {
      this(
              in.readString(), in.readString(), in.readString(), in.readString(), in.readInt()
      );
  }

  public Users (JSONObject j)
  {
      try {
          name=j.getString("name");
          password=j.getString("password");
          major=j.getString("major");
          email=j.getString("email");
          userID=j.getInt("userID");


      }catch (JSONException e) {
          e.printStackTrace();
      }


  }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeInt(userID);
        dest.writeString(major);
    }
}
