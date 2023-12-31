package com.phonebooktests;

import com.phonebook.fw.DataProviders;
import com.phonebook.model.Contact;
import com.phonebook.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


import java.util.List;


public class AddContactsTests extends TestBase {
  @BeforeMethod
  public void ensurePrecondition() {
    if (!app.getUser().isLoginLinkPresent()) {
      app.getUser().clickOnSignButton();
    }
    app.getUser().clickOnLoginLink();
    app.getUser().fillLoginRegistrationForm(new User().setEmail("tykhonenko@gmail.com").setPassword("R!q121212"));
    app.getUser().click(By.name("login"));

  }

  @Test
  public void addContactPositiveTest() {
    app.getContact().clickOnAddLink();
    app.getContact().fillAddContactForm(new Contact()
        .setName("Misha")
        .setLastName("Tykhonenko")
        .setPhone("1234567890")
        .setEmal("aaa@gmail.com")
        .setAddress("address")
        .setDescription("description"));
    app.getContact().clickOnSaveButton();
    Assert.assertTrue(isContactCreated("Misha"));


  }

  @Test( enabled = false, dataProvider = "addContact", dataProviderClass = DataProviders.class)
  public void addContactDataProviderTest(String name, String lastname, String phone, String email, String address, String description) {
    app.getContact().clickOnAddLink();
    app.getContact().fillAddContactForm(new Contact()
        .setName(name)
        .setLastName(lastname)
        .setPhone(phone)
        .setEmal(email)
        .setAddress(address)
        .setDescription(description));
    app.getContact().clickOnSaveButton();


  }

  @Test(enabled = false, dataProvider = "addContactFromCsvFilePositive", dataProviderClass = DataProviders.class)
  public void addContactDataProviderCsvFileTest(Contact contact) {
    app.getContact().clickOnAddLink();
    app.getContact().fillAddContactForm(contact);
    app.getContact().clickOnSaveButton();

  }

  @Test(enabled = false , dataProvider = "addContactFromCsvFileNegativePhone", dataProviderClass = DataProviders.class)
  public void addContactDataProviderCsvFileNegativePhone(Contact contact) {
    app.getContact().clickOnAddLink();
    app.getContact().fillAddContactForm(contact);
    app.getContact().clickOnSaveButton();
    app.getContact().pause(200);
    app.getContact().closeAlert();
    app.getContact().clickOnContactLink();
    app.getContact().pause(200);



  }

  @AfterMethod
  public void removeContactItem() {
    if (!app.getContact().isPresentContactItems()) {
      app.getContact().clickOnContactItem();
      app.getContact().clickOnDeleteButton();
    }
  }

  public boolean isContactCreated(String name) {
    List<WebElement> contacts = app.driver.findElements(By.cssSelector("h2"));
    for (WebElement element : contacts) {
      if (element.getText().contains(name)) {
        return true;
      }
    }
    return false;
  }
}
