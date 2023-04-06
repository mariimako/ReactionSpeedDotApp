package model;

import java.util.Calendar;
import java.util.Date;


/* represents an event that has occurted in the game
 */
public class Event {
    private static final int HASH_CONSTANT = 13;
    private Date dateLogged;
    private String description;

    /* EFFECTS: creates a new event with is time that it occurred and  a description
     */
    public Event(String description) {
        dateLogged = Calendar.getInstance().getTime();
        this.description = description;
    }

    public Date getDate() {
        return dateLogged;
    }

    public String getDescription() {
        return description;
    }

    /*
    EFFECTS: returns if given event was recorded in the same date and time and same description
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Event otherEvent = (Event) other;

        return (this.dateLogged.equals(otherEvent.dateLogged) && this.description.equals(otherEvent.description));
    }

    /*
    EFFECTS: creates a hashcode identifier for this object
     */
    @Override
    public int hashCode() {
        return (HASH_CONSTANT * dateLogged.hashCode() + description.hashCode());
    }

    /*
    EFFECTS: shows the date and time of the event plus its description
     */
    @Override
    public String toString() {
        return dateLogged.toString() + "\n" + description;
    }
}
