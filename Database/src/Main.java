
import java.sql.*;

public class Main {
    public static void main(String [] args)
    {
        String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String dbURL="jdbc:sqlserver://192.168.56.1;DatabaseName = 社团管理系统; integratedSecurity=false;encrypt=true;trustServerCertificate=true;";
        String userName="sa";
        String userPwd="123456";
        try
        {
            Class.forName(driverName);
            System.out.println("加载驱动成功！");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(" 加载驱动失败！");
        }
        try{
            java.sql.Connection
                    dbConn=DriverManager.getConnection(dbURL,userName,userPwd);
            System.out.println(" 连接数据库成功！ ");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.print("SQL Server 连接失败！");
        }
    }
    public PreparedStatement prepareStatement(String query) {
// TODO Auto-generated method stub
        return null;
    }
}