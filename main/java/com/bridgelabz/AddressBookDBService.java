package com.bridgelabz;

import bridgelabz.ContactInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    public class AddressBookDBService {
        private static AddressBookDBService addressBookDBService;
        private PreparedStatement personInfoDataStatement;
        private AddressBookDBService() {
        }

        /**
         * Purpose : For creating a singleton object
         *
         * @return
         */
        public static AddressBookDBService getInstance() {
            if ( addressBookDBService == null)
                addressBookDBService = new AddressBookDBService();
            return addressBookDBService;
        }

        /**
         * Purpose : Create connection with the database
         *
         * @return
         * @throws SQLException
         */
        private Connection getConnection() throws SQLException {
            String jdbcURL = "jdbc:mysql://localhost:3306/address_book?useSSL=false";
            String userName = "root";
            String password = "samarth1920";
            Connection connection;
            System.out.println("Connecting to database: "+jdbcURL);
            connection = DriverManager.getConnection(jdbcURL, userName, password);
            System.out.println("Connection is successful! " +connection);
            return connection;
        }

        /**
         * Purpose : Create connection to execute query and read the value from the database
         *           Assign the value in a list variable
         *
         * @param sql
         * @return
         */
        private List<ContactInfo> getEmployeePayrollDataUsingDB(String sql) {
            List<ContactInfo> personInfoList = new ArrayList<>();
            try (Connection connection = this.getConnection()) {
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    int SlNo = result.getInt("SlNo");
                    String type = result.getString("type");
                    String fname = result.getString("firstname");
                    String lname = result.getString("lastname");
                    String address = result.getString("address");
                    String city = result.getString("city");
                    String state = result.getString("state");
                    int zip = result.getInt("zip");
                    String phoneNo = result.getString("phonenumber");
                    String email = result.getString("emailid");
                    personInfoList.add(new ContactInfo(SlNo, type, fname, lname, address, city, state, zip, phoneNo, email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return personInfoList;
        }

        /**
         * Purpose : Read the person info from the database
         *
         * @return
         */
        public List<ContactInfo> readData() {
            String sql = "SELECT * FROM addressbook";
            return getEmployeePayrollDataUsingDB(sql);
        }
        /**
         * Purpose : Update the state in the DB using Statement Interface
         *
         * @param name
         * @param state
         * @return
         */
        public int updatePersonInfo(String name, String state) {
            return this.updatePersonInfoUsingStatement(name,state);
        }

        /**
         * Purpose : Update the state in the DB using Statement Interface
         *
         * @param name
         * @param state
         * @return
         */
        private int updatePersonInfoUsingStatement(String name, String state) {
            String sql = String.format("UPDATE addressbook SET state = '%s' WHERE firstname = '%s';", state, name);
            try (Connection connection = this.getConnection()){
                Statement statement = connection.createStatement();
                return statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return 0;
        }

        /**
         * Purpose : Get the list of PersonInfoData using the assigned name
         *           setString() is used to set the assigned name value in the sql query
         *           Return all the attribute values listed for a particular name
         *
         * @param name
         * @return
         */
        public List<ContactInfo> getPersonInfoData(String name) {
            List<ContactInfo> personInfoList = null;
            if(this.personInfoDataStatement == null)
                this.preparedStatementForPersonInfo();
            try {
                personInfoDataStatement.setString(1, name);
                ResultSet resultSet = personInfoDataStatement.executeQuery();
                personInfoList = this.getPersonInfoData(resultSet);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return personInfoList;
        }

        /**
         * Purpose : Assign the value of the attributes in a list and return it
         *
         * @param resultSet
         * @return
         */
        private List<ContactInfo> getPersonInfoData(ResultSet resultSet) {
            List<ContactInfo> personInfoList = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    int SlNo = resultSet.getInt("SlNo");
                    String type = resultSet.getString("type");
                    String fname = resultSet.getString("firstname");
                    String lname = resultSet.getString("lastname");
                    String address = resultSet.getString("address");
                    String city = resultSet.getString("city");
                    String state = resultSet.getString("state");
                    int zip = resultSet.getInt("zip");
                    String phoneNo = resultSet.getString("phonenumber");
                    String email = resultSet.getString("emailid");
                    personInfoList.add(new ContactInfo(SlNo, type, fname, lname, address, city, state, zip, phoneNo, email));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return personInfoList;
        }

        /**
         * Purpose : To get the details of a particular person from the DB using PreparedStatement Interface
         */

        private void preparedStatementForPersonInfo() {
            try {
                Connection connection = this.getConnection();
                String sql = "SELECT * FROM addressbook WHERE firstname = ?";
                personInfoDataStatement = connection.prepareStatement(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



