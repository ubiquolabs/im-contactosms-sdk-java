package com.interactuamovil.contactosms.api.responses;

public class GroupResponse {

    class GroupMembers {

        private int total;
        private int pending;
        private int confirmed;

        public void setTotal(int i) {
            total = i;
        }

        public void setPending(int i) {
            pending = i;
        }

        public void setConfirmed(int i) {
            confirmed = i;
        }

        public int getTotal() {
            return total;
        }

        public int getPending() {
            return pending;
        }

        public int getConfirmed() {
            return confirmed;
        }

    }

    private String shortName;
    private String name;
    private String description;
    private GroupMembers members;

    public void setShortName(String s) {
        shortName = s;
    }

    public void setName(String s) {
        name = s;
    }

    public void setDescription(String s) {
        description = s;
    }

    public void setMembers(GroupMembers gm) {
        members = gm;
    }

    public String getShortName() {
        return shortName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public GroupMembers getMembers() {
        return members;
    }

}
