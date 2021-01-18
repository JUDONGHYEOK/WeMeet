package com.example.wemeet;

public class GroupData {
    public String GroupName;
    public String Groups;
    public int person;

    public GroupData(String groupName, String groups, int person) {
        GroupName = groupName;
        Groups = groups;
        this.person = person;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }

    public String getGroups() {
        return Groups;
    }

    public void setGroups(String groups) {
        Groups = groups;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }
}
