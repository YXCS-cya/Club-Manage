# Club Management System (Club-Manage)
> A database-driven management system built with Java and SQL Server 2012

---
## 🧩 Project Overview
This project was developed as part of a **Database Systems course**.  
It aims to design and implement a management system for student clubs using **Java (JDBC)** and **SQL Server 2012**.

The system follows **Third Normal Form (3NF)** principles, modeled via **Power Designer**, and integrates:
- **SQL Views** for modular data access  
- **Transactions** for integrity control  
- **Parameterized Queries** for security  
- **Index Optimization** for performance  

**Goal:** To build a practical, secure, and normalized database management system demonstrating full-stack data handling and design ability.

---

## 📘 Database Design

### 1. Design Principles
- Fully compliant with the **Third Normal Form (3NF)**  
- Database modeled with **Power Designer**  
- Six main tables and two views designed for data normalization and referential integrity  

### 2. Core Table Structures

#### Club Table
```sql
CREATE TABLE Club (
    Club_ID INT PRIMARY KEY,
    Club_Name VARCHAR(255) NOT NULL,
    Club_Type VARCHAR(100),
    Club_ManagerID INT REFERENCES Member(Member_ID),
    Club_Teacher VARCHAR(100),
    Club_Number INT CHECK(Club_Number >= 0),
    Club_Add VARCHAR(255)
);
```

#### Many-to-Many Relationship Table (ClubMember)
```sql
CREATE TABLE ClubMember (
    Club_ID INT REFERENCES Club(Club_ID),
    Member_ID INT REFERENCES Member(Member_ID),
    PRIMARY KEY (Club_ID, Member_ID)
);
```
#### Optimized View Example
```sql
CREATE VIEW ClubactView AS
SELECT c.Club_Name, a.Activity_Name, r.AttendanceCount 
FROM Activity a
JOIN Club c ON a.Club_ID = c.Club_ID
LEFT JOIN ClubActivityRecord r ON a.Activity_ID = r.Activity_ID;

```

## 💻 System Implementation
Full source code available at GitHub Repository

### JDBC Connection Helper
```java

public class DatabaseHelper {
    private static final String URL = "jdbc:sqlserver://localhost:1433;...";
    
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, "sa", "123456");
    }
}
```
### Transaction Template
```java

public void updateClubInfo(Club club) {
    Connection conn = null;
    try {
        conn = DatabaseHelper.getConnection();
        conn.setAutoCommit(false);
        
        updateBasicInfo(conn, club);
        updateMemberCount(conn, club.getClub_ID());
        
        conn.commit();
    } catch (SQLException e) {
        if(conn != null) try { conn.rollback(); } catch (SQLException ex) {}
    } finally {
        if(conn != null) try { conn.close(); } catch (SQLException e) {}
    }
}
```
## 🔐 Security and Optimization
### 1. Security Controls
- View Encapsulation: Restricts data access using ClubLeaderView to protect sensitive fields.

- Parameterized Queries: Prevent SQL injection through prepared statements.

```java

PreparedStatement ps = conn.prepareStatement(
    "SELECT * FROM Member WHERE Member_Name LIKE ?"
);
ps.setString(1, "%" + keyword + "%");
```
### 2. Performance Optimization
- Indexing Strategy: Added indexes on foreign key fields (Club_ID, Member_ID) to improve query efficiency.

- Batch Processing: Used JDBC batch operations to speed up large-scale data import.

```java

public void batchInsertMembers(List<Member> members) {
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(
             "INSERT INTO Member VALUES (?,?,?,?,?)")) {
        
        for (Member m : members) {
            ps.setInt(1, m.getId());
            ps.setString(2, m.getName());
            ps.addBatch();
        }
        ps.executeBatch();
    }
}
```
## 🧠 System Features
| Module                         | Description                                                                    |
| ------------------------------ | ------------------------------------------------------------------------------ |
| **Login & Permission Control** | Role-based authentication for admins, leaders, and regular members.            |
| **Club Management**            | View, update, and delete club information with cascading relationships.        |
| **Member Management**          | Manage many-to-many mappings between clubs and members.                        |
| **Activity Reports**           | Generate reports via SQL views and fuzzy search queries.                       |
| **Data Security**              | All operations use transactions and parameterized queries to ensure integrity. |


## 🪟 Interface Previews
<img width="1380" height="1040" alt="image" src="https://github.com/user-attachments/assets/4e4c592f-41f4-447b-8964-561424be051a" />
<img width="1058" height="464" alt="image" src="https://github.com/user-attachments/assets/9d616898-b2de-4d9b-ba2d-d8af0755a805" />
<img width="606" height="266" alt="image" src="https://github.com/user-attachments/assets/3a1676ea-62d6-4b70-baa3-328a96f1c87d" />


## ⚙️ How to Run
1. Attach the database files:
/数据库文件/社团管理系统.mdf and /数据库文件/社团管理系统.ldf in SQL Server Management Studio.

2. Update the connection configuration in DatabaseHelper.java.

3. Run the main program to start the application.

4. Default administrator account:

- Username: CYA
- Password: 1234

> All operations are executed in real time and synchronized with the SQL Server database.


## 🧾 Notes & Acknowledgements
This system was developed for academic purposes and focuses on database design, transaction control, and secure data access.
Some UI features (such as detailed logging and report exports) were not implemented due to course time constraints.

For a full technical summary (in Chinese), including E-R diagrams and process explanations, see:
🔗 https://www.cnblogs.com/YXCS-cya/p/18878598

---
## 🛠️ Build & Run Note
1. This project has been performed successfully with “SQL Server 2012”.
2. Follow the steps in "⚙️ How to Run" which shows above.
