package com.interactuamovil.contactosms.api.responses;

import java.util.Date;
import java.util.List;

public class ScheduleMessageResponse {

    class MessageGroup {
        private String shortName;

        public void setShortName(String s) {
            shortName = s;
        }

        public String getShortName() {
            return shortName;
        }
    }

    private int id;
    private String name;
    private String frequency;
    private String message;
    private Date dateExpires;
    private List<MessageGroup> groups;

    public void setId(int i) {
        id = i;
    }

    public void setName(String s) {
        name = s;
    }

    public void setFrequency(String s) {
        frequency = s;
    }

    public void setMessage(String s) {
        message = s;
    }

    public void setDateExpires(Date d) {
        dateExpires = d;
    }

    public void setGroups(List<MessageGroup> mg) {
        groups = mg;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFrequency() {
        return frequency;
    }

    public String getMessage() {
        return message;
    }

    public Date getDateExpires() {
        return dateExpires;
    }

    public List<MessageGroup> getGroups() {
        return groups;
    }

}
