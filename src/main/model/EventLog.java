package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/*
 * Represents a log of game events.
 * We use the Singleton Design Pattern to ensure that there is only
 * one EventLog in the system and that the system has global access
 * to the single instance of the EventLog.
 * Taken from AlarmSystem https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
 */
public class EventLog implements Iterable<Event> {
    /* the only EventLog in the system (Singleton Design Pattern) */
    private static EventLog theLog;
    private Collection<Event> events;

    /* EFFECTS: constructs an log of events to record all events
     */
    private EventLog() {
        events = new ArrayList<Event>();
    }

    /* EFFECTS: get the eventlog representing all events, if there is none, create a new one
       MODIFIES: this
     */
    public static EventLog getInstance() {
        if (theLog == null) {
            theLog = new EventLog();
        }

        return theLog;
    }

    /* EFFECTS: add a new event to the many events to be recorded
       MODIFIES: this
     */
    public void logEvent(Event e) {
        events.add(e);
    }

    /* EFFECTS: clears all the events that have been recorded so far
       MODIFIES: this
     */
    public void clear() {
        events.clear();
        logEvent(new Event("Event log cleared."));
    }

    /*
      EFFECTS: return the event's iterator, so that it can be looped through
     */
    @Override
    public Iterator<Event> iterator() {
        return events.iterator();
    }
}
