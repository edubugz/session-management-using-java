package com.example.dblogic;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBlogic {

    public DBlogic ()
    {
        getDBConnection();
    }

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/student_login";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";


    //establish connection
    public static Connection dbConnection = null;

    public static void getDBConnection() {



        try {
            Class.forName(DB_DRIVER);

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);


        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }
        catch (ClassNotFoundException e){}



    }

   public String[] insertStudent(String id, String name) throws SQLException
   {
       getDBConnection();

       String[] responseString= new String[2];
       dbConnection.setAutoCommit(false);


           // prepared statement for student and guardian
           String updateString = "insert into student values(?,?,?)";
           PreparedStatement updateStaff = dbConnection.prepareStatement(updateString);

           //set the values in the prepared statements to the values extracted from the object passed
           updateStaff.setString(1,id );
           updateStaff.setString(2, name);

           if(id.equals("4545"))
           {
               updateStaff.setString(3,"yes");
               responseString[0] ="yes" ;
           }
           else
           {
               updateStaff.setString(3,"no");
               responseString[0] ="no" ;
           }


           if( updateStaff.executeUpdate()>0 )
           {
               dbConnection.commit();
              responseString[1] ="register successful" ;
           }

           else
               {
               //if it fails, roll back
               dbConnection.rollback();
               responseString[1] ="register not successful" ;
               }

       return responseString;
       }


   public Map<String,String> readAll( ) throws SQLException {

        getDBConnection();

        ArrayList<String> fetchedData= new ArrayList<>();
       Map<String,String> mapArr = new HashMap<>();


           String responseString = "select * from student ";
           PreparedStatement queryToExecute = dbConnection.prepareStatement(responseString);

           ResultSet studentsData = queryToExecute.executeQuery();

           // if the result set has any data assign the data to strings and print it

           if(studentsData != null  )
           {
               while(studentsData.next())
               {
//                   fetchedData.add("id: "+studentsData.getString("id")+"  _  name:"+studentsData.getString("name")
//                           +"  _  is admin:"+studentsData.getString("admin"));

                   mapArr.put(studentsData.getString("id"),"<<>>"+studentsData.getString("name") +"<<>>"+studentsData.getString("admin"));
               }

               return mapArr;
           }

           return  mapArr;

       }


    public String [] readFromDB(String id,String name) throws SQLException {

        getDBConnection();

        String [] fetchedData= new String[3];

        String responseString = "select * from student where id =? and name = ?";
        PreparedStatement queryToExecute = dbConnection.prepareStatement(responseString);

        queryToExecute.setString(1,id );
        queryToExecute.setString(2, name);

        ResultSet studentsData = queryToExecute.executeQuery();

        // if the result set has any data assign the data to strings and print it

        if(studentsData.next())
        {
            //studentsData.beforeFirst();
            fetchedData[0]= studentsData.getString("name");
            fetchedData[1] = studentsData.getString("admin");
            fetchedData [2]= studentsData.getString("id");
        }

        else
        {
            fetchedData [0] = "user not found in db";
            fetchedData [1] = "";
        }


        return  fetchedData;

    }


    public String[] editDB(String newId, String newName, String identifier) throws SQLException {
        System.out.println(newId+" "+newName+" "+identifier+"before method");
        getDBConnection();
        dbConnection.setAutoCommit(false);
        String query="";
        String [] status=new String [3];
        PreparedStatement query2= null;
        if( (newId.isEmpty()) &&  !newName.isEmpty() )
        {
            query = "UPDATE STUDENT SET name =? WHERE ID =?";

            query2 = dbConnection.prepareStatement(query);
            query2.setString(1, newName);
            query2.setString(2, identifier);
            int executed = query2.executeUpdate();

            if (executed>0) {
               dbConnection.commit();
                status [0]= "successfuly updated user";
                status [1]=newName;
                status [2]=identifier;

            } else {
                dbConnection.rollback();
                status [0]= "update failed";
                status [1]="";
                status [2]=identifier;
            }
        }

        else if( newName.isEmpty()   && !newId.isEmpty()  )
        {

                query = "UPDATE STUDENT SET ID =?  WHERE ID =?";
                query2 = dbConnection.prepareStatement(query);
                query2.setString(1, newId);
                // query2.setString(2,"no");
                query2.setString(2, identifier);


            int executed = query2.executeUpdate();

            if (executed>0) {
                dbConnection.commit();
                status [0]= "successfuly updated user";
                status [1]= "";
                status [2]=newId;

            } else {
                dbConnection.rollback();
                status [0]= "update failed";
                status [1]="";
                status [2]=identifier;
            }
        }

        else if(!newId.isEmpty() && !newId.isEmpty())
        {
            System.out.println(newId+" "+newName+" "+identifier+"on condition");
            query = "UPDATE STUDENT SET ID =?, NAME=? WHERE ID =?";
            query2 = dbConnection.prepareStatement(query);
            query2.setString(1, newId);
            query2.setString(2,newName);
            query2.setString(3, identifier);
            int executed = query2.executeUpdate();

            if (executed>0) {
                dbConnection.commit();
                status [0]= "successfuly updated user";
                status [1]=newName;
                status [2]=newId;


            } else {
                dbConnection.rollback();
                status [0]= "update failed";
                status [1]="";
                status [2]=identifier;
            }



        }

        else
            //if(query.isEmpty())
        {
            status [0]= "no operation performed";
            status [1]="";
            status [2]=identifier;
        }

        return status;
    }


    public String deleteUser(String id) throws SQLException
    {
        System.out.println(id);

        String returnString ="";
        if(!id.isEmpty()) {
            getDBConnection();
            dbConnection.setAutoCommit(false);


            String query = "DELETE  FROM STUDENT WHERE id = ?";
            PreparedStatement queryToExecute = dbConnection.prepareStatement(query);
            queryToExecute.setString(1, id);
            int status = queryToExecute.executeUpdate();
            if (status > 0) {
                dbConnection.commit();
                returnString += "sucessfully deleted";

            } else {
                dbConnection.rollback();
                returnString += "deletion failed";
            }
        }
        else
        {
            returnString +="no user was selected";
        }


        return returnString;
    }


}







