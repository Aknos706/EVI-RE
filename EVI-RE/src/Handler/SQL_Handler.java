package Handler;

import java.sql.*;
import java.util.LinkedList;

public abstract class SQL_Handler extends Handler_Base{
private Connection con;  
private LinkedList<String> LastQuery= new LinkedList<>();

protected SQL_Handler(DB DB_Enum){
this.db_connect("root", DB_Enum.Name, "");
}

protected SQL_Handler(String User,DB DB_Enum, String Password){
this.db_connect(User, DB_Enum.Name, Password);
}    
    
private void db_connect(String User,String DBM, String Password){
    try {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionUrl = "jdbc:mysql://localhost/"+DBM+"?user="+User+"&password="+Password;
        con = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
        } catch (ClassNotFoundException cE) {
            System.out.println("Class Not Found Exception: "+ cE.toString());
        }
}

protected LinkedList<String> Query(String QueryString,String Label){
    LastQuery= new LinkedList<>(); // Replacing the old last Query
    try{
        String SQL = QueryString;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            LastQuery.add(rs.getString(Label));
        }
    }
    catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
    }  
    return LastQuery;
}

protected String SingleQuery(String QueryString,String Label){
    LastQuery= new LinkedList<>(); // Replacing the old last Query
    try{
        String SQL = QueryString;
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(SQL);
        while (rs.next()){
            LastQuery.add(rs.getString(Label));
        }
    }
    catch (SQLException e) {
            System.out.println("SQL Exception: "+ e.toString());
    }  
    return LastQuery.getFirst();
}

protected ResultSet Query(String QueryString){
    ResultSet rs = null;
    try{
        String SQL = QueryString;
        Statement stmt = con.createStatement();
        rs = stmt.executeQuery(SQL);
    }
    catch (SQLException e) {
        System.out.println("SQL Exception: "+ e.toString());
    }  
    return rs;
}


protected boolean Input(String InputString){
boolean R=true;
    try{
        //Statement stmt = null;
        //ResultSet rs = null;
        //SQL query command
        String SQL = InputString;
        Statement stmt = con.createStatement();
        stmt.executeUpdate(SQL);
    }
    catch (SQLException e) {
            System.out.println();
            System.out.println("SQL Exception: "+ e.toString());
            System.out.println(InputString);
    }  



return R;
}
   
protected void CopyTable(String Original, String Copy){
    this.Input("CREATE TABLE "+Copy.toLowerCase()+" LIKE "+Original.toLowerCase()+";");
}


protected enum DB{
    Default_SDE("ac119.3"),
    SDE_YC118_1("yc118.1"),
    Ascension("ascension"),
    SDE_YC119_3("yc119.3");
    private final String Name;
    DB(String N){
        this.Name=N;
    }
}

}
