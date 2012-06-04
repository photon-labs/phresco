package com.photon.phresco.Screens;

import java.io.IOException;

import org.openqa.selenium.By;

import com.photon.phresco.selenium.util.GetCurrentDir;
import com.photon.phresco.selenium.util.ScreenActionFailedException;

public abstract class AbstractBaseScreen extends BaseScreen {

	protected long THREAD_SLEEP_MIL_SEC = 5000;
	protected long SLEEP_FOR_WAIT_ELEM_MIL_SEC = 2000;
	protected long WAIT_FOR_REFRESH_MIL_SEC = 2000;
	protected long WAIT_FOR_ELEM_SEC = 120;

	protected AbstractBaseScreen() {
		if (driver == null) {
			throw new IllegalStateException(
					"The BaseScreen.initialize method must have been "
							+ "called before creating a screen instance.");
		}
	}

	// protected AbstractBaseScreen(String host,int port,String browser, String
	// url, String speed,String context) throws IOException,
	// ScreenActionFailedException {
	// initalizeSelenium(host, port, browser, url, speed, context);
	//
	// }

	protected AbstractBaseScreen(String host, int port, String browser,
			String url, String speed, String context) throws IOException,
			ScreenActionFailedException {
		initalizeSelenium(host, port, browser, url, speed, context);
	}

	public static void initalizeSelenium(String host, int port, String browser,
			String url, String speed, String context)
			throws ScreenActionFailedException {
		initialize(host, port, browser, url, speed, context);

	}

	public void sleep(long time) throws InterruptedException {
		msg("AbstractBaseScreen.sleep for " + time);
		Thread.sleep(time);
	}

	/**
	 * @throws Exception
	 * @deprecated Use {@link MenuScreen#clickRefresh()} instead.
	 *             <p>
	 *             (annotated as deprecated since 8 August 2011)
	 *             </p>
	 */
	@Deprecated
	/*
	 * public void closeAllTabs() throws InterruptedException {
	 * msg("AbstractBaseScreen.closeAllTabs close all current Tabs");
	 * clickAt(photonUiConstants.CS_BUTTON_CLOSE_ALL_TABS, "");
	 * waitForElementPresent(photonUiConstants.CS_FRONT_PAGE_LOGO); }
	 */
	protected void contextMenuAt(String link, String value) throws Exception {
		waitForElementPresent(link);
		waitForContextMenuAt(link, value);
	}

	protected void select(String elem, String value) throws Exception {
		waitForElementPresent(elem);
		selenium.select(elem, value);
	}

	protected void waitForNumOfOKsAfterImport(String numElement, int numOfOKs)
			throws Exception {
		int num;
		waitForElementPresent(numElement);
		for (int second = 0;; second++) {
			String text = selenium.getText(numElement);
			msg("AbstractBaseScreen.waitForNumOfOKsAfterImport: numOKs: |"
					+ text + "|");
			num = Integer.parseInt(text);
			if (second >= WAIT_FOR_ELEM_SEC)
				throw new Exception("timeout: Num of OKs is still 0");
			try {
				if (num >= numOfOKs)
					break;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}

	}

	protected void waitForContextMenuAt(String link, String value,
			String waitForElem) throws Exception {
		msg("AbstractBaseScreen.waitForContextMenuAt " + link);
		for (int second = 0;; second++) {
			selenium.contextMenuAt(link, value);
			if (second >= WAIT_FOR_ELEM_SEC)
				throw new Exception("timeout: Element " + link
						+ " is not present");
			try {
				if (selenium.isElementPresent(waitForElem))
					break;
			} catch (Exception e) {
			}

			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
	}

	protected void waitForContextMenuAt(String link, String value)
			throws Exception {
		msg("AbstractBaseScreen.waitForContextMenuAt " + link);
		for (int second = 0;; second++) {
			selenium.contextMenuAt(link, value);
			if (second >= WAIT_FOR_ELEM_SEC)
				throw new Exception("timeout: Element " + link
						+ " is not present");
			try {
				// if
				// (selenium.isElementPresent(photonUiConstants.CS_LINK_DELETE))
				break;
			} catch (Exception e) {
			}

			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
	}

	protected void mouseOver(String link) throws Exception {
		waitForElementPresent(link);
		msg("AbstractBaseScreen.mouseOver on " + link);
		selenium.mouseOver(link);
	}

	/**
	 * @deprecated Use {@link MenuScreen#clickRefresh()} instead.
	 *             <p>
	 *             (annotated as deprecated since 8 August 2011)
	 *             </p>
	 */
	@Deprecated
	public void logout() throws InterruptedException {
		msg("AbstractBaseScreen.logout: Logout from PHRESCO");
		// start the logout process ...
		selenium.selectFrame("relative=up");
		selenium.selectFrame("HEADER");
		/*
		 * selenium.click(phrsc.CS_LINK_LOG_OFF);
		 * waitForElementPresent(phrsc.CS_FIELD_USER);
		 * selenium.click(phrsc.CS_FIELD_USER);
		 */
	}

	protected void clickOnLink(String elemName) throws Exception {
		String link = "link=" + elemName;
		clickAt(link, "");
	}

	protected void click(String elem) throws Exception {

		waitForElementPresent(elem);
		msg("AbstractBaseScreen.click on " + elem);
		selenium.click(elem);
	}

	protected void clickAt(String elem, String value) throws Exception {
		waitForElementPresent(elem);
		msg("AbstractBaseScreen.clickAt on " + elem);
		selenium.clickAt(elem, value);
	}

	protected void clickAt(String elem) throws Exception {
		clickAt(elem, "");
	}

	protected void doubleClickAt(String elem) throws Exception {
		waitForElementPresent(elem);
		msg("AbstractBaseScreen.doubleClickAt on" + elem);
		selenium.doubleClickAt(elem, "0,0");
	}

	protected void type(String elem, String value) throws Exception {
		waitForElementPresent(elem);
		msg("type in " + elem + " value= " + value);
		selenium.type(elem, value);
	}

	protected void typeKeys(String elem, String value) throws Exception {
		waitForElementPresent(elem);
		msg("type in " + elem + " value= " + value);
		selenium.typeKeys(elem, value);
	}

	/**
	 * @param check
	 *            <code>true</code> if the checkbox should be checked,
	 *            <code>false</code> if it should be unchecked.
	 * @throws Exception
	 * @throws
	 * @throws InterruptedException
	 */
	protected void check(String elem, boolean check) throws Exception {
		waitForElementPresent(elem);
		if (check) {
			msg("check " + elem);
			// selenium.check(elem);
		} else {
			msg("uncheck " + elem);
			// selenium.uncheck(elem);
		}
		// check() and uncheck() seem not to work. Workaround:
		if (selenium.isChecked(elem) != check) {
			clickAt(elem);
		}

	}

	/**
	 * @param check
	 *            <code>true</code> if the checkbox should be checked,
	 *            <code>false</code> if it should be unchecked.
	 */
	protected void checkFakeCheckbox(final String elem, final boolean check)
			throws Exception {
		if (isCheckedFakeCheckbox(elem) != check) {
			clickAt(elem);
		}
	}

	protected boolean isCheckedFakeCheckbox(final String elem) throws Exception {
		waitForElementPresent(elem);
		final String src = selenium.getAttribute(elem + "//img@src");
		if (src.endsWith("/HTMLBasedGUI/images/checkbox.gif")) {
			return false;
		} else if (src.endsWith("/HTMLBasedGUI/images/checkbox2.gif")) {
			return true;
		} else {
			throw new RuntimeException("src attribute of " + elem
					+ " has unexpected value " + src);
		}
	}

	protected boolean isElementPresent(String elem) throws InterruptedException {
		msg("AbstractBaseScreen.isElementPresent " + elem);
		for (int second = 0; second < 10; second++) {
			try {
				if (selenium.isElementPresent(elem))
					return true;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
		return false;
	}

	protected void waitForLinkElementPresent(String elemName) throws Exception {
		String link = "link=" + elemName;
		waitForElementPresent(link);
	}

	protected void waitForElementPresent(String elem) throws Exception {
		msg("AbstractBaseScreen.waitForElementPresent " + elem);
		for (int second = 0;; second++) {
			if (second >= WAIT_FOR_ELEM_SEC) {
				selenium.captureEntirePageScreenshot(GetCurrentDir.getCurrentDirectory()+"\\Failure.png", "background=#CCFFDD");
				throw new Exception("timeout: Element " + elem
						+ " is not present");
			}
			try {
				if (selenium.isElementPresent(elem))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
	}

	protected void waitForTextPresent(String text) throws Exception {
		msg("AbstractBaseScreen.waitForTextPresent " + text);
		for (int second = 0;; second++) {
			if (second >= WAIT_FOR_ELEM_SEC) {
				selenium.captureScreenshot(GetCurrentDir.getCurrentDirectory()+"\\Failure.png");
				throw new Exception("timeout: Text " + text + " is not present");
			}
			try {
				if (selenium.isTextPresent(text))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
		msg("AbstractBaseScreen.waitForTextPresent end");
	}

	/**
	 * Note that this seems to be extremely slow with Internet Explorer.
	 * 
	 * @throws Exception
	 */
	
	protected boolean waitForTextPresentConsole(String text) throws Exception {
		msg("AbstractBaseScreen.waitForTextPresent " + text);
		for (int second = 0; second < 20; second++) {
			if (second >= WAIT_FOR_ELEM_SEC) {
				selenium.captureScreenshot(GetCurrentDir.getCurrentDirectory()+"\\Failure.png");
				throw new Exception("timeout: Text " + text + " is not present");
			}
			try {
				if (selenium.isTextPresent(text)) {
					return true;
				} else {
					Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		msg("AbstractBaseScreen.waitForTextPresentConsole end");
		return false;
	}
	protected void waitForElementIsVisible(String elem) throws Exception {
		msg("AbstractBaseScreen.waitForElementIsVisible " + elem);
		for (int second = 0;; second++) {
			if (second >= WAIT_FOR_ELEM_SEC) {
				selenium.captureScreenshot(GetCurrentDir.getCurrentDirectory()+"\\Failure.png");
				throw new Exception("timeout: Element " + elem
						+ " is not visible");
			}
			try {
				if (selenium.isVisible(elem))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
	}

	protected void waitForElementNotPresent(String elem) throws Exception {
		msg("AbstractBaseScreen:waitForElementNotPresent " + elem);
		for (int second = 0;; second++) {
			if (second >= WAIT_FOR_ELEM_SEC)
				throw new Exception("timeout: Element " + elem
						+ " is still present");
			try {
				if (!selenium.isElementPresent(elem))
					break;
			} catch (Exception e) {
			}
			Thread.sleep(SLEEP_FOR_WAIT_ELEM_MIL_SEC);
		}
	}

	protected void msg(String message) {
		System.out.println(message);
	}

	/**
	 * @deprecated Use {@link MenuScreen#clickRefresh()} instead.
	 *             <p>
	 *             (annotated as deprecated since 8 August 2011)
	 *             </p>
	 */
	@Deprecated
	public void refresh() throws InterruptedException {
		msg("AbstractBaseScreen.refresh");
		// selenium.click(photonUiConstants.CS_BUTTON_REFRESH);
		sleep(WAIT_FOR_REFRESH_MIL_SEC);
	}

	protected void focus(String elem) throws Exception {
		msg("AbstractBaseScreen.focus at " + elem);
		waitForElementPresent(elem);
		selenium.focus(elem);
	}

	protected String getText(String elem) throws Exception {
		msg("AbstractBaseScreen.getText " + elem);
		waitForElementPresent(elem);
		return selenium.getText(elem);
	}

	protected Number getXpathCount(String elem) throws InterruptedException {
		msg("AbstractBaseScreen.getXpathCount" + elem);
		return selenium.getXpathCount(elem);

	}

	protected void clickLink(String elemName) throws Exception {
		clickAt("link=" + elemName);
	}

	protected void scrollDown(String objectXpath, String tableXpath,
			String xpathScrollbar) throws Exception {

		if (tableXpath != null && tableXpath.trim().length() > 0
				&& isElementPresent(tableXpath)) {
			click(tableXpath);
		}
		focus(xpathScrollbar);
		click(xpathScrollbar);

		boolean check = isElementPresent(objectXpath);

		while (!check) {
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");

			check = isElementPresent(objectXpath);

		}

		click(objectXpath);

	}

	protected boolean scrollDownAndFind(String objectXpath, String tableXpath,
			String xpathScrollbar, int scrollCountLimit) throws Exception {

		if (tableXpath != null && tableXpath.trim().length() > 0
				&& isElementPresent(tableXpath)) {
			click(tableXpath);
		}
		focus(xpathScrollbar);
		click(xpathScrollbar);

		boolean check = isElementPresent(objectXpath);
		int counter = 0;
		while (!check && counter++ < scrollCountLimit) {
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");
			selenium.keyPress(xpathScrollbar, "40");

			check = isElementPresent(objectXpath);

		}

		return check;
		// click(objectXpath);

	}
	
	
	
	
}
