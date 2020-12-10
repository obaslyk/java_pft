package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class ContactModificationTests extends TestBase {

  @BeforeMethod
  public  void ensurePreconditions() {
    if (app.contact().all().size() == 0) {
      app.goTo().homePage();
      app.contact().create(new ContactData()
              .withFirstname("Olga").withLastname("Petrova").withHome("123456789")
              .withEmail("12345@mail.ru").withGroup("[none]"));
    }
  }

  @Test
  public void testContactModification() throws InterruptedException {
    Contacts before = app.contact().all();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData()
            .withId(modifiedContact.getId()).withFirstname("Olga").withLastname("Petrova")
            .withHome("123456789").withEmail("12345@mail.ru");
    app.goTo().homePage();
    app.contact().modify(contact);
    app.goTo().homePage();
    Thread.sleep(3000);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withOut(modifiedContact).withAdded(contact)));
  }
}
