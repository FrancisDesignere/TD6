package net.designere.td6.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Créer par Francis Désignère le 19/02/2019.
 */
public class Contact implements Parcelable {
    private String name;
    private String firstName;
    private String phoneNumber;

    public Contact(String name, String firstName, String phoneNumber) {
        this.name = name;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    // Getters / Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.firstName);
        dest.writeString(this.phoneNumber);

    }

    protected Contact(Parcel in) {
        name = in.readString();
        firstName = in.readString();
        phoneNumber = in.readString();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
