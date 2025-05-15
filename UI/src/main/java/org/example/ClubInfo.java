package org.example;

public class ClubInfo {
    private String clubId;

    @Override
    public String toString() {
        return "ClubInfo{" +
                "clubId='" + clubId + '\'' +
                ", clubName='" + clubName + '\'' +
                ", clubType='" + clubType + '\'' +
                ", leaderId='" + leaderId + '\'' +
                ", instructor='" + instructor + '\'' +
                ", memberCount=" + memberCount +
                ", clubLocation='" + clubLocation + '\'' +
                '}';
    }

    private String clubName;
    private String clubType;
    private String leaderId;
    private String instructor;
    private int memberCount;
    private String clubLocation;

    // 构造方法
    public ClubInfo(String clubId, String clubName, String clubType, String leaderId, String instructor, int memberCount, String clubLocation) {
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubType = clubType;
        this.leaderId = leaderId;
        this.instructor = instructor;
        this.memberCount = memberCount;
        this.clubLocation = clubLocation;
    }

    // Getter 和 Setter 方法
    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubType() {
        return clubType;
    }

    public void setClubType(String clubType) {
        this.clubType = clubType;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getClubLocation() {
        return clubLocation;
    }

    public void setClubLocation(String clubLocation) {
        this.clubLocation = clubLocation;
    }


}

