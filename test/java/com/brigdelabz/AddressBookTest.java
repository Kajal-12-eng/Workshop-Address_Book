package com.brigdelabz;

import bridgelabz.ContactInfo;
import com.bridgelabz.AddressBookService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AddressBookTest {
    AddressBookService addressBookService;

    @Before
    public void setUp() throws Exception {
        addressBookService = new AddressBookService();
    }

    /**
     * Purpose : To test whether the number of records present in the database matches with the expected value
     */

    @Test
    public void givenPersonInfoInDB_WhenRetrieved_ShouldMatchEmployeeCount() {
        List<ContactInfo> contactInfoList = addressBookService.readPersonInfoData(AddressBookService.IOService.DB_IO);
        Assert.assertEquals(4, contactInfoList.size());
    }

    /**
     * Purpose : To test whether the state is updated in the database and is synced with the DB
     *           - Read the values from the database
     *           - Update the state in the database
     *           - Test whether the database is correctly synced or not
     */
    @Test
    public void givenNewStateForPerson_WhenUpdated_ShouldSyncWithDB() {
        addressBookService.readPersonInfoData(AddressBookService.IOService.DB_IO);
        addressBookService.updatePersonInfo("Ankush", "Tamil Nadu");
        boolean result = addressBookService.checkPersonInfoInSyncWithDB("Ankush");
        Assert.assertTrue(result);
    }
}