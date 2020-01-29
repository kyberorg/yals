package eu.yals.test.ui.vaadin;

import eu.yals.test.ui.vaadin.commons.VaadinTest;
import eu.yals.test.ui.vaadin.pageobjects.HomeViewPageObject;
import eu.yals.test.ui.vaadin.pageobjects.NotFoundViewPageObject;
import eu.yals.test.ui.vaadin.pageobjects.external.VR;
import eu.yals.test.utils.VaadinTestMethods;
import eu.yals.test.utils.YalsTestMethods;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import static com.codeborne.selenide.Selenide.open;

public class SlashIT extends VaadinTest {

  protected HomeViewPageObject getHomePage() {
    return HomeViewPageObject.getPageObject(getDriver());
  }

  @Test
  public void saveLinkAndClickOnResult() {
    HomeViewPageObject homePage = getHomePage();
    homePage.pasteValueInFormAndSubmitIt("https://vr.fi");

    homePage.getShortLinkField().click();

    verifyThatVROpened();
  }

  @Test
  public void saveLinkAndCopyValueAndOpenIt() {
    HomeViewPageObject homePage = getHomePage();
    homePage.pasteValueInFormAndSubmitIt("https://vr.fi");

    String shortUrl = homePage.getShortLinkField().getText();
    Assert.assertTrue(StringUtils.isNotBlank(shortUrl));

    open(shortUrl);
    verifyThatVROpened();
  }

  @Test
  public void openSomethingNonExisting() {
    open("/perkele");
    verifyThatPage404Opened();
  }

  @Test
  public void openSomethingNonExistingDeeperThanSingleLevel() {
    open("/void/something/here");
    verifyThatPage404Opened();
  }

  private void verifyThatVROpened() {
    WebElement logo = findElement(VR.LOGO);
    YalsTestMethods test = YalsTestMethods.fromWebElement(logo);
    test.attr("alt", "VR");

    /*    String logoAttribute = logo.getAttribute("alt");
    Assert.assertEquals("VR", logoAttribute);*/
  }

  private void verifyThatPage404Opened() {
    NotFoundViewPageObject page404 = NotFoundViewPageObject.getPageObject(getDriver());
    VaadinTestMethods title = VaadinTestMethods.fromVaadinElement(page404.getTitle());

    title.shouldBeDisplayed();
    title.textHas("404");

   /* Assert.assertTrue(page404.getTitle().isDisplayed());
    Assert.assertTrue(page404.getTitle().getText().contains("404"));*/
  }
}
