package com.photon.phresco.selenium.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.zip.ZipException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.photon.phresco.selenium.report.Reporter;
import com.thoughtworks.selenium.CommandProcessor;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * This class is an extension of DefaultSelenium and provides certain additional
 * methods for automation. Element Locators
 * 
 * Element Locators tell Selenium which HTML element a command refers to. The
 * format of a locator is:
 * 
 * locatorType=argument
 * 
 * We support the following strategies for locating elements:
 * 
 * identifier=id: Select the element with the specified @id attribute. If no
 * match is found, select the first element whose @name attribute is id. (This
 * is normally the default; see below.) id=id: Select the element with the
 * specified @id attribute. name=name: Select the first element with the
 * specified @name attribute. o username o name=username
 * 
 * The name may optionally be followed by one or more element-filters, separated
 * from the name by whitespace. If the filterType is not specified, value is
 * assumed. o name=flavour value=chocolate dom=javascriptExpression: Find an
 * element by evaluating the specified string. This allows you to traverse the
 * HTML Document Object Model using JavaScript. Note that you must not return a
 * value in this string; simply make it the last expression in the block. o
 * dom=document.forms['myForm'].myDropdown o dom=document.images[56] o
 * dom=function foo() { return document.links[1]; }; foo();
 * xpath=xpathExpression: Locate an element using an XPath expression. o
 * xpath=//img[@alt='The image alt text'] o
 * xpath=//table[@id='table1']//tr[4]/td[2] o xpath=//a[contains(@href,'#id1')]
 * o xpath=//a[contains(@href,'#id1')]/@class o
 * xpath=(//table[@class='stylee'])//th[text()='theHeaderText']/../td o
 * xpath=//input[@name='name2' and @value='yes'] o xpath=//*[text()="right"]
 * link=textPattern: Select the link (anchor) element which contains text
 * matching the specified pattern. o link=The link text css=cssSelectorSyntax:
 * Select the element using css selectors. Please refer to CSS2 selectors, CSS3
 * selectors for more information. You can also check the TestCssLocators test
 * in the selenium test suite for an example of usage, which is included in the
 * downloaded selenium core package. o css=a[href="#id3"] o css=span#firstChild
 * + span
 * 
 * Currently the css selector locator supports all css1, css2 and css3 selectors
 * except namespace in css3, some pseudo classes(:nth-of-type,
 * :nth-last-of-type, :first-of-type, :last-of-type, :only-of-type, :visited,
 * :hover, :active, :focus, :indeterminate) and pseudo elements(::first-line,
 * ::first-letter, ::selection, ::before, ::after). ui=uiSpecifierString: Locate
 * an element by resolving the UI specifier string to another locator, and
 * evaluating it. See the Selenium UI-Element Reference for more details. o
 * ui=loginPages::loginButton() o ui=settingsPages::toggle(label=Hide Email) o
 * ui=forumPages::postBody(index=2)//a[2]
 * 
 * Without an explicit locator prefix, Selenium uses the following default
 * strategies:
 * 
 * dom, for locators starting with "document." xpath, for locators starting with
 * "//" identifier, otherwise
 * 
 * Element Filters
 * 
 * Element filters can be used with a locator to refine a list of candidate
 * elements. They are currently used only in the 'name' element-locator.
 * 
 * Filters look much like locators, ie.
 * 
 * filterType=argument
 * 
 * Supported element-filters are:
 * 
 * value=valuePattern
 * 
 * Matches elements based on their values. This is particularly useful for
 * refining a list of similarly-named toggle-buttons.
 * 
 * index=index
 * 
 * Selects a single element based on its position in the list (offset from
 * zero).
 * 
 * String-match Patterns
 * 
 * Various Pattern syntaxes are available for matching string values:
 * 
 * glob:pattern: Match a string against a "glob" (aka "wildmat") pattern. "Glob"
 * is a kind of limited regular-expression syntax typically used in command-line
 * shells. In a glob pattern, "*" represents any sequence of characters, and "?"
 * represents any single character. Glob patterns match against the entire
 * string. regexp:regexp: Match a string using a regular-expression. The full
 * power of JavaScript regular-expressions is available. regexpi:regexpi: Match
 * a string using a case-insensitive regular-expression. exact:string: Match a
 * string exactly, verbatim, without any of that fancy wildcard stuff.
 * 
 * If no pattern prefix is specified, Selenium assumes that it's a "glob"
 * pattern.
 * 
 * For commands that return multiple values (such as verifySelectOptions), the
 * string being matched is a comma-separated list of the return values, where
 * both commas and backslashes in the values are backslash-escaped. When
 * providing a pattern, the optional matching syntax (i.e. glob, regexp, etc.)
 * is specified once, as usual, at the beginning of the pattern.
 * 
 * 
 * 
 */
public class ScreenshottingSelenium extends DefaultSelenium {

	public Reporter reporter;

	private Robot robot = null;

	public String BROWSER = null;

	private long WAIT_FOR_OPEN = 120000;

	private long SMALL_SLEEP = 1000;

	private Log log = LogFactory.getLog(getClass());

	private boolean killBrowserOnStop = true;

	/**
	 * 
	 * @param processor
	 * @param reporter
	 */
	
	
	public ScreenshottingSelenium(CommandProcessor processor, Reporter reporter) {
		super(processor);
		this.reporter = reporter;
	}

	/**
	 * 
	 * @param serverHost
	 * @param serverPort
	 * @param browserStartCommand
	 * @param browserURL
	 * @param reporter
	 * @throws ScreenActionFailedException
	 */
	public ScreenshottingSelenium(String serverHost, int serverPort,
			String browserStartCommand, String browserURL, String context)
			throws ScreenActionFailedException {
		super(serverHost, serverPort, browserStartCommand, browserURL);
		validateBrowser(browserStartCommand);
		this.BROWSER = browserStartCommand;
		// this.reporter = reporter;
	}

	/**
	 * Gets the text of an element. This works for any element that contains
	 * text. This command uses either the textContent (Mozilla-like browsers) or
	 * the innerText (IE-like browsers) of the element, which is the rendered
	 * text shown to the user.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return the text of the element
	 */
	public String getText(String locator) {
		log.info("Entering: getText");
		try {
			return super.getText(locator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			return super.getText(locator);
		}
	}

	/**
	 * Kills all the instanes of the current browser type.
	 */
	public void killBrowser() {
		log.info("Entering: killBrowser");
		String[] command = null;
		if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_IE)) {
			command = new String[] { "cmd", "/c", "taskkill", "/F", "/IM",
					"iexplore.exe" };
			System.out.println("Killed All IE browser instances");
		} else if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			command = new String[] { "cmd", "/c", "taskkill", "/F", "/IM",
					"firefox.exe" };
			System.out.println("Killed All Firefox browser instances");
		}

		try {
			Runtime.getRuntime().exec(command, null, null);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * It selects the specified file in File Upload dialog that pop-up on
	 * clicking the "Browse" button for any file upload field.
	 * 
	 * It will work only on windows and is done using an exe file developed
	 * using autoIt.
	 * 
	 * Note: You should first click on the browse button to open the pop-up and
	 * then call this method.
	 * 
	 * @param filePath
	 *            file path to be set in file upload/chooser field.
	 * @param browseButtonLocator
	 *            locator of the browse button, clicking on which will pop-up
	 *            the file chooser dialog
	 * @throws IOException
	 *             If anything goes wrong
	 * @throws URISyntaxException
	 *             If anything goes wrong
	 */
	public void chooseFileInFileUploadDialog(String filePath,
			String browseButtonLocator) throws IOException, URISyntaxException {
		log.info("Entering: chooseFileInFileUploadDialog");

		filePath = filePath.replace('/', File.separatorChar);
		filePath = filePath.replace('\\', File.separatorChar);

		if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			focus(browseButtonLocator);
			type(browseButtonLocator, filePath);
		} else if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_IE)) {
			String[] command = null;
			Process exec = null;
			File chooseFilePath = new File("C:/Windows/system32/chooseFile.exe");
			if (chooseFilePath.exists()) {
				chooseFilePath.delete();
			}

			try {
				ExtractExeToLocalMachine.extractFileToFolder("chooseFile.exe",
						"C:/Windows/system32");
			} catch (Throwable t) {
			}

			System.out.println("File to choose is : " + filePath);
			if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_IE)) {
				command = new String[] { "cmd", "/c", "start",
						"chooseFile.exe", "Choose file", filePath };
				exec = Runtime.getRuntime().exec(command);
			}
			click(browseButtonLocator);
			// reporter.reportStep("Clicked on the browse button for uploading file");

			try {
				System.out.println("Waiting for the process to end");
				exec.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isWin() {
		if (File.pathSeparatorChar == '\\') {
			return true;
		} else
			return false;
	}

	/**
	 * This will click "Save" on file download dialog and then it will set the
	 * path for downlaod and finally it clicks on the close button. Currently it
	 * works only with IE.
	 * 
	 * @param filePath
	 *            path of the file where to download
	 * @throws ZipException
	 *             If anything goes wrong
	 * @throws URISyntaxException
	 *             If anything goes wrong
	 * @throws IOException
	 *             If anything goes wrong
	 */
	public void handleFileDownloadDialog(String filePath) throws ZipException,
			URISyntaxException, IOException {
		log.info("Entering: handleFileDownloadDialog");

		// use autoit for IE.
		if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_IE)) {
			Process exec = null;
			String[] command = null;
			File chooseFilePath = new File(
					"C:/Windows/system32/handleFileDownloadDialog.exe");
			if (chooseFilePath.exists()) {
				chooseFilePath.delete();
			}

			File downloadFile = new File(filePath);
			if (downloadFile.exists()) {
				downloadFile.delete();
			}

			// reporter.reportStep("Before selecting the file to download");

			ExtractExeToLocalMachine.extractFileToFolder(
					"handleFileDownloadDialog.exe", "C:/Windows/system32");

			System.out.println("File to download is : " + filePath);
			command = new String[] { "cmd", "/c",
					"handleFileDownloadDialog.exe", "File Download", filePath };
			exec = Runtime.getRuntime().exec(command);

			try {
				System.out.println("Waiting for the process to end");
				exec.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Exiting the handle File Download method");
		} else if (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_FIREFOX)) {
			// dont do anything for firefox as the profile is set to save
			// automatically, just check if the file is downloaded or not.
			// Download location is set in prefs.js in firefox profile

		}
	}

	/**
	 * Cleans up the Downloads dir where firefox stores all the downloaded
	 * files.
	 */
	public void cleanDownloadsFolder() {
		String downloadsFolder = "C:/downloads";
		File downloadsDir = new File(downloadsFolder);
		File[] listFiles = downloadsDir.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			System.out.println("Deleting file "
					+ listFiles[i].getAbsolutePath());
			listFiles[i].delete();
		}
	}

	/**
	 * Validates if the browser start command is valid or not.
	 * 
	 * @param browserStartCommand
	 *            command to be verified
	 * @throws ScreenActionFailedException
	 *             if it is invalid
	 */
	private void validateBrowser(String browserStartCommand)
			throws ScreenActionFailedException {
		log.info("Entering: validateBrowser");
		if (null == browserStartCommand) {
			throw new ScreenActionFailedException(
					"Browser cannot be null for starting selenium");
		}
		if (!browserStartCommand.equalsIgnoreCase(Constants.BROWSER_IE)
				&& !browserStartCommand
						.equalsIgnoreCase(Constants.BROWSER_FIREFOX)
				&& !browserStartCommand
						.equalsIgnoreCase(Constants.BROWSER_CHROME)
				&& !browserStartCommand
						.equalsIgnoreCase(Constants.BROWSER_SAFARI)) {
			throw new ScreenActionFailedException(
					"Invalid Browser specified for starting selenium");
		}
	}

	/** Launches the browser with a new Selenium session */
	public void start() {
		log.info("Entering: start");
		super.start();
		windowMaximize();
		windowFocus();
		super.setTimeout(Constants.DEFAULT_TIMEOUT);
	}

	public void setKillBrowserOnStop(boolean killBrowserOnStop) {
		this.killBrowserOnStop = killBrowserOnStop;
	}

	/**
	 * Ends the test session, killing the browser if {@link #killBrowserOnStop}
	 * is <code>true</code>.
	 */
	public void stop() {
		log.info("Entering: stop");
		try {
			super.stop();
		} catch (Throwable t) {
		}
		log.info("killBrowserOnStop is " + killBrowserOnStop);
		if (killBrowserOnStop) {
			killBrowser();
		}
	}

	/**
	 * Move the focus to the specified element; for example, if the element is
	 * an input field, move the cursor to that field.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */

	public void focus(String locator) {
		log.info("Entering: focus");
		try {
			super.focus(locator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.focus(locator);
		}
	}

	/**
	 * Selects a frame within the current window. (You may invoke this command
	 * multiple times to select nested frames.) To select the parent frame, use
	 * "relative=parent" as a locator; to select the top frame, use
	 * "relative=top". You can also select a frame by its 0-based index number;
	 * select the first frame with "index=0", or the third frame with "index=2".
	 * 
	 * Tries upto 20 times to select a frame.
	 * 
	 * <p>
	 * You may also use a DOM expression to identify the frame you want
	 * directly, like this: <code>dom=frames["main"].frames["subframe"]</code>
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> identifying a frame
	 *            or iframe
	 */

	public void selectFrame(String locator) {
		log.info("Entering: selectFrame");
		for (int i = 0; i < 20; i++) {
			try {
				super.selectFrame(locator);
				return;
			} catch (Throwable t) {
			}
		}
	}

	/**
	 * Determines if the specified element is visible. An element can be
	 * rendered invisible by setting the CSS "visibility" property to "hidden",
	 * or the "display" property to "none", either for the element itself or one
	 * if its ancestors. This method will fail if the element is not present.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the specified element is visible, false otherwise
	 */

	public boolean isVisible(String locator) {
		log.info("Entering: isVisible");
		boolean result = false;

		try {
			result = super.isVisible(locator);
		} catch (Throwable t) {
		}
		return result;
	}

	/**
	 * Verifies that the specified element is somewhere on the page.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the element is present, false otherwise
	 */
	public boolean isElementPresent(String locator) {
		log.info("Entering: isElementPresent");
		boolean result = false;

		try {
			result = super.isElementPresent(locator);
		} catch (Throwable t) {
		}
		return result;
	}

	/**
	 * Clicks on a link, button, checkbox or radio button. If the click action
	 * causes a new page to load (like a link usually does), call
	 * waitForPageToLoad.
	 * 
	 * @param locator
	 *            an element locator
	 */

	public void click(String locator) {
		log.info("Entering: click");
		try {
			super.click(locator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.click(locator);
		}
		// reporter.reportStep("Clicked on " + locator);
	}

	/**
	 * It selects the specified value in the combobox and then waits for another
	 * locator. It will retry the whole operation 3 times or till the wait
	 * object is visible
	 * 
	 * @param selectLocator
	 *            locator of the combo-box
	 * @param optionLocator
	 *            value to be selected
	 * @param waitLocator
	 *            element to wait for after clicking
	 * @param retryInterval
	 *            Time in milliseconds after which the click and wait will be
	 *            executed again in case the wait element is not visible
	 * @throws ScreenActionFailedException
	 *             If anything goes wrong
	 */
	public void selectAndWait(String selectLocator, String optionLocator,
			String waitLocator, String retryInterval)
			throws ScreenActionFailedException {
		log.info("Entering: selectAndWait");
		for (int i = 0; i < 3; i++) {
			try {
				select(selectLocator, optionLocator);
			} catch (Throwable t) {
			}
			try {
				waitForElementToBeVisible(waitLocator, retryInterval);
				return;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}

		if (!isVisible(waitLocator)) {
			throw new ScreenActionFailedException("Element " + waitLocator
					+ " not visible even after trying selectAndWait 3 times ");
		}
	}

	/**
	 * It clicks on the specified locator and then waits for another locator. It
	 * will retry the whole operation 3 times or till the wait object is visible
	 * 
	 * @param clickLocator
	 *            element to be clicked
	 * @param waitLocator
	 *            element to wait for after clicking
	 * @param retryInterval
	 *            Time in milliseconds after which the click and wait will be
	 *            executed again in case the wait element is not visible
	 * @throws ScreenActionFailedException
	 */
	public void clickAndWait(String clickLocator, String waitLocator,
			String retryInterval) throws ScreenActionFailedException {
		log.info("Entering: clickAndWait");
		for (int i = 0; i < 3; i++) {
			try {
				click(clickLocator);
			} catch (Throwable t) {
			}
			try {
				waitForElementToBeVisible(waitLocator, retryInterval);
				return;
			} catch (Throwable t) {
			}
		}
		if (!isVisible(waitLocator)) {
			throw new ScreenActionFailedException("Element " + waitLocator
					+ " not visible even after trying clickAndWait 3 times ");
		}
	}

	/**
	 * Clicks on the control without retrying it.
	 * 
	 * @param locator
	 *            element to be clicked
	 */
	public void clickWithoutRetry(String locator) {
		log.info("Entering: clickWithoutRetry");
		try {
			super.click(locator);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		// reporter.reportStep("Clicked on " + locator);
	}

	/**
	 * Selects a popup window using a window locator; once a popup window has
	 * been selected, all commands go to that window. To select the main window
	 * again, use null as the target.
	 * 
	 * <p>
	 * 
	 * Window locators provide different ways of specifying the window object:
	 * by title, by internal JavaScript "name," or by JavaScript variable.
	 * </p>
	 * <ul>
	 * <li><strong>title</strong>=<em>My Special Window</em>: Finds the window
	 * using the text that appears in the title bar. Be careful; two windows can
	 * share the same title. If that happens, this locator will just pick one.</li>
	 * <li><strong>name</strong>=<em>myWindow</em>: Finds the window using its
	 * internal JavaScript "name" property. This is the second parameter
	 * "windowName" passed to the JavaScript method window.open(url, windowName,
	 * windowFeatures, replaceFlag) (which Selenium intercepts).</li>
	 * <li><strong>var</strong>=<em>variableName</em>: Some pop-up windows are
	 * unnamed (anonymous), but are associated with a JavaScript variable name
	 * in the current application window, e.g. "window.foo = window.open(url);".
	 * In those cases, you can open the window using "var=foo".</li>
	 * </ul>
	 * <p>
	 * If no window locator prefix is provided, we'll try to guess what you mean
	 * like this:
	 * </p>
	 * <p>
	 * 1.) if windowID is null, (or the string "null") then it is assumed the
	 * user is referring to the original window instantiated by the browser).
	 * </p>
	 * <p>
	 * 2.) if the value of the "windowID" parameter is a JavaScript variable
	 * name in the current application window, then it is assumed that this
	 * variable contains the return value from a call to the JavaScript
	 * window.open() method.
	 * </p>
	 * <p>
	 * 3.) Otherwise, selenium looks in a hash it maintains that maps string
	 * names to window "names".
	 * </p>
	 * <p>
	 * 4.) If <em>that</em> fails, we'll try looping over all of the known
	 * windows to try to find the appropriate "title". Since "title" is not
	 * necessarily unique, this may have unexpected behavior.
	 * </p>
	 * <p>
	 * If you're having trouble figuring out the name of a window that you want
	 * to manipulate, look at the Selenium log messages which identify the names
	 * of windows created via window.open (and therefore intercepted by
	 * Selenium). You will see messages like the following for each window as it
	 * is opened:
	 * </p>
	 * <p>
	 * <code>debug: window.open call intercepted; window ID (which you can use with selectWindow()) is "myNewWindow"</code>
	 * </p>
	 * <p>
	 * In some cases, Selenium will be unable to intercept a call to window.open
	 * (if the call occurs during or before the "onLoad" event, for example).
	 * (This is bug SEL-339.) In those cases, you can force Selenium to notice
	 * the open window's name by using the Selenium openWindow command, using an
	 * empty (blank) url, like this: openWindow("", "myFunnyWindow").
	 * </p>
	 * 
	 * @param windowID
	 *            the JavaScript window ID of the window to select
	 */
	public void selectWindow(String windowID) {
		log.info("Entering: selectWindow");
		sleep(SMALL_SLEEP);
		super.selectWindow(windowID);
	}

	/**
	 * Opens an URL in the test frame. This accepts both relative and absolute
	 * URLs.
	 * 
	 * The "open" command waits for the page to load before proceeding, ie. the
	 * "AndWait" suffix is implicit.
	 * 
	 * <em>Note</em>: The URL must be on the same domain as the runner HTML due
	 * to security restrictions in the browser (Same Origin Policy). If you need
	 * to open an URL on another domain, use the Selenium Server to start a new
	 * browser session on that domain.
	 * 
	 * @param url
	 *            the URL to open; may be relative or absolute
	 */

	public void open(String url) {
		super.open(url);
		// SeleniumThread st = new SeleniumThread(this, url);
		// st.start();
		//
		// long currentTimeMillis = System.currentTimeMillis();
		// long endTime = currentTimeMillis + WAIT_FOR_OPEN;
		// while (endTime > System.currentTimeMillis()) {
		// if (!st.isAlive()) {
		// return;
		// } else {
		// sleep(SMALL_SLEEP);
		// }
		// }
		//
		// if (st.isAlive()) {
		// st.destroy();
		// killBrowser();
		// stop();
		// start();
		// open(url);
		// }
	}

	/**
	 * Sets the value of an input field, as though you typed it in.
	 * 
	 * <p>
	 * Can also be used to set the value of combo boxes, check boxes, etc. In
	 * these cases, value should be the value of the option selected, not the
	 * visible text.
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param value
	 *            the value to type
	 */

	public void type(String locator, String value) {
		log.info("Entering: type");
		if (null == value) {
			// reporter.reportStep("WARNING", "Null value cannot be typed in : "
			// + locator + " so leaving it unchanged");
			return;
		}

		try {
			super.type(locator, value);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.type(locator, value);
			// now check if the value is not set then call this method again
			String value2 = getValue(locator);
			if (null != value2 && !value2.equalsIgnoreCase(value.trim())) {
				type(locator, value);
			}
		}
		// reporter.reportStep("Typed '" + value + "'" + " in " + locator);
	}

	/**
	 * Select an option from a drop-down using an option locator.
	 * 
	 * <p>
	 * Option locators provide different ways of specifying options of an HTML
	 * Select element (e.g. for selecting a specific option, or for asserting
	 * that the selected option satisfies a specification). There are several
	 * forms of Select Option Locator.
	 * </p>
	 * <ul>
	 * <li><strong>label</strong>=<em>labelPattern</em>: matches options based
	 * on their labels, i.e. the visible text. (This is the default.)
	 * <ul class="first last simple">
	 * <li>label=regexp:^[Oo]ther</li>
	 * </ul>
	 * </li>
	 * <li><strong>value</strong>=<em>valuePattern</em>: matches options based
	 * on their values.
	 * <ul class="first last simple">
	 * <li>value=other</li>
	 * </ul>
	 * </li>
	 * <li><strong>id</strong>=<em>id</em>:
	 * 
	 * matches options based on their ids.
	 * <ul class="first last simple">
	 * <li>id=option1</li>
	 * </ul>
	 * </li>
	 * <li><strong>index</strong>=<em>index</em>: matches an option based on its
	 * index (offset from zero).
	 * <ul class="first last simple">
	 * <li>index=2</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * <p>
	 * If no option locator prefix is provided, the default behaviour is to
	 * match on <strong>label</strong>.
	 * </p>
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @param optionLocator
	 *            an option locator (a label by default)
	 */
	public void select(String selectLocator, String optionLocator) {
		log.info("Entering: select");
		if (null == optionLocator) {
			// reporter.reportStep("WARNING",
			// "Null value cannot be selected in : " + selectLocator
			// + " so leaving it unchanged");
			return;
		}

		try {
			super.select(selectLocator, optionLocator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(selectLocator,
						Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.select(selectLocator, optionLocator);
		}

		// reporter.reportStep("Selected: " + optionLocator + " in :"
		// + selectLocator);
	}

	/**
	 * Waits for a new page to load.
	 * 
	 * <p>
	 * You can use this command instead of the "AndWait" suffixes,
	 * "clickAndWait", "selectAndWait", "typeAndWait" etc. (which are only
	 * available in the JS API).
	 * </p>
	 * <p>
	 * Selenium constantly keeps track of new pages loading, and sets a
	 * "newPageLoaded" flag when it first notices a page load. Running any other
	 * Selenium command after turns the flag to false. Hence, if you want to
	 * wait for a page to load, you must wait immediately after a Selenium
	 * command that caused a page-load.
	 * </p>
	 * 
	 * @param timeout
	 *            a timeout in milliseconds, after which this command will
	 *            return with an error
	 */
	public void waitForPageToLoad(String timeout) {
		log.info("Entering: waitForPageToLoad");
		try {
			super.waitForPageToLoad(timeout);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		// reporter.reportStep("Page loaded");
	}

	/**
	 * It will wait for the specified element to become visible on the web page
	 * until the timeout period.
	 * 
	 * @param locator
	 *            element to wait for
	 * @param timeout
	 *            max. amount to time that it should wait for in milliseconds
	 * @throws ScreenActionFailedException
	 *             If the specified object is not visible on the page after the
	 *             timeout period
	 */
	public void waitForElementToBeVisible(String locator, String timeout)
			throws ScreenActionFailedException {
		log.info("Entering: waitForElementToBeVisible");
		long startTime = System.currentTimeMillis();
		long endTime = startTime + Integer.parseInt(timeout);
		while (System.currentTimeMillis() < endTime) {
			try {
				if (isVisible(locator)) {
					return;
				}
			} catch (Throwable t) {
			}
			sleep(SMALL_SLEEP);
		}

		if (super.isElementPresent(locator)) {
			if (!super.isVisible(locator)) {
				throw new ScreenActionFailedException("Element: " + locator
						+ " is present but not visible");
			}
		} else {
			throw new ScreenActionFailedException("Element: " + locator
					+ " is not present");
		}
	}

	/**
	 * It will wait for the specified element to become un-available on the web
	 * page until the timeout period.
	 * 
	 * @param locator
	 *            element to wait for
	 * @param timeout
	 *            max. amount to time that it should wait for in milliseconds
	 * @throws ScreenActionFailedException
	 *             If the specified object is still visible on the page after
	 *             the timeout period
	 */
	public void waitForElementToBeNotPresent(String locator, String timeout)
			throws ScreenActionFailedException {
		log.info("Entering: waitForElementToBeNotPresent");
		long startTime = System.currentTimeMillis();
		long endTime = startTime + Integer.parseInt(timeout);

		while (System.currentTimeMillis() < endTime) {
			try {
				if (!isElementPresent(locator)) {
					return;
				}
			} catch (Throwable t) {
			}
			sleep(SMALL_SLEEP);
		}

		if (isElementPresent(locator)) {
			throw new ScreenActionFailedException(
					"Timed out waiting for element to disappear: " + locator);
		}
	}

	/**
	 * It will wait for the specified element to be not visible on the web page
	 * until the timeout period.
	 * 
	 * @param locator
	 *            element to wait for
	 * @param timeout
	 *            max. amount to time that it should wait for in milliseconds
	 * @throws ScreenActionFailedException
	 *             If the specified object is still visible on the page after
	 *             the timeout period
	 */
	public void waitForElementToBeNotVisible(String locator, String timeout)
			throws ScreenActionFailedException {
		log.info("Entering: waitForElementToBeNotVisible");
		long startTime = System.currentTimeMillis();
		long endTime = startTime + Integer.parseInt(timeout);

		while (System.currentTimeMillis() < endTime) {
			try {
				if (!super.isVisible(locator)) {
					return;
				}
			} catch (Throwable t) {
			}
			sleep(SMALL_SLEEP);
		}

		if (super.isVisible(locator)) {
			throw new ScreenActionFailedException(
					"Timed out waiting for element to become invisible : "
							+ locator);
		}
	}

	/**
	 * It will wait for the specified element to become available on the web
	 * page until the timeout period.
	 * 
	 * @param locator
	 *            element to wait for
	 * @param timeout
	 *            max. amount to time that it should wait for in milliseconds
	 * @throws ScreenActionFailedException
	 *             If the specified object is not visible on the page after the
	 *             timeout period
	 */
	public void waitForElementToBePresent(String locator, String timeout)
			throws ScreenActionFailedException {
		log.info("Entering: waitForElementToBePresent");
		long startTime = System.currentTimeMillis();
		long endTime = startTime + Integer.parseInt(timeout);

		while (System.currentTimeMillis() < endTime) {
			try {
				if (isElementPresent(locator)) {
					return;
				}
			} catch (Throwable t) {
			}
			sleep(SMALL_SLEEP);
		}

		if (!isElementPresent(locator)) {
			throw new ScreenActionFailedException(
					"Timed out waiting for element: " + locator);
		}
	}

	/**
	 * This method can be used to check/uncheck a checkbox/radio button.
	 * 
	 * @param locator
	 *            Identification for checkbox/radio button
	 * @param checkUncheck
	 *            Value specifying whether to check or uncheck the
	 *            checkbox/radio button To check pass this value as "YES" or
	 *            "TRUE" or "ENABLE" or "ON". To uncheck pass any other value
	 */
	public void checkUncheck(String locator, String checkUncheck) {
		log.info("Entering: checkUncheck");
		if (null == checkUncheck) {
			return;
		}

		if (checkUncheck.equalsIgnoreCase("ON")
				|| checkUncheck.equalsIgnoreCase("YES")
				|| checkUncheck.equalsIgnoreCase("TRUE")
				|| checkUncheck.equalsIgnoreCase("ENABLE")) {
			check(locator);
		} else {
			uncheck(locator);
		}
	}

	/**
	 * Check a toggle-button (checkbox/radio)
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void check(String locator) {
		log.info("Entering: check");
		try {
			super.check(locator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.check(locator);
		}
		// reporter.reportStep("Checked '" + locator + "'");
	}

	/**
	 * Uncheck a toggle-button (checkbox/radio)
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void uncheck(String locator) {
		log.info("Entering: uncheck");
		try {
			super.uncheck(locator);
		} catch (Throwable t) {
			try {
				waitForElementToBeVisible(locator, Constants.DEFAULT_TIMEOUT);
			} catch (Throwable e) {
				e.printStackTrace();
			}
			super.uncheck(locator);
		}
		// reporter.reportStep("Unchecked '" + locator + "'");
	}

	/**
	 * This will make the code sleep for the specified milliseconds.
	 * 
	 * @param time
	 *            Amount of time to sleep in milliseconds
	 */
	public void sleep(long time) {
		log.info("Entering: sleep");
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * Allows javascript to be specified for the test on a per-browser session
	 * basis. The javascript will be in-play the next time a session is created;
	 * that is, typically the next time <code>start()</code> is invoked (and
	 * <code>getNewBrowserSession</code> is sent to the RC under the sheets).
	 * 
	 * @param extensionJs
	 *            a string representing the extra extension javascript to
	 *            include in the browser session. This is in addition to any
	 *            specified via the -userExtensions switch when starting the RC.
	 */
	public void setExtensionJs(String extensionJs) {
		super.setExtensionJs(extensionJs);
	}

	/**
	 * Starts a new Selenium testing session with a String, representing a
	 * configuration
	 */
	public void start(String optionsString) {
		super.start(optionsString);
	}

	/**
	 * Starts a new Selenium testing session with a configuration options object
	 */
	public void start(Object optionsObject) {
		super.start(optionsObject);
	}

	/**
	 * Shows in the RemoteRunner a banner for the current test The banner is
	 * 'classname : methodname' where those two are derived from the caller The
	 * method name will be unCamelCased with the insertion of spaces at word
	 * boundaries
	 */
	public void showContextualBanner() {
		super.showContextualBanner();
	}

	/**
	 * Shows in the RemoteRunner a banner for the current test The banner is
	 * 'classname : methodname' The method name will be unCamelCased with the
	 * insertion of spaces at word boundaries
	 */
	public void showContextualBanner(String className, String methodName) {
		super.showContextualBanner(className, methodName);
	}

	/**
	 * Double clicks on a link, button, checkbox or radio button. If the double
	 * click action causes a new page to load (like a link usually does), call
	 * waitForPageToLoad.
	 * 
	 * @param locator
	 *            an element locator
	 */
	public void doubleClick(String locator) {
		super.doubleClick(locator);
	}

	/**
	 * Simulates opening the context menu for the specified element (as might
	 * happen if the user "right-clicked" on the element).
	 * 
	 * @param locator
	 *            an element locator
	 */
	public void contextMenu(String locator) {
		super.contextMenu(locator);
	}

	/**
	 * Clicks on a link, button, checkbox or radio button. If the click action
	 * causes a new page to load (like a link usually does), call
	 * waitForPageToLoad.
	 * 
	 * @param locator
	 *            an element locator
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void clickAt(String locator, String coordString) {
		super.clickAt(locator, coordString);
	}

	/**
	 * Doubleclicks on a link, button, checkbox or radio button. If the action
	 * causes a new page to load (like a link usually does), call
	 * waitForPageToLoad.
	 * 
	 * @param locator
	 *            an element locator
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void doubleClickAt(String locator, String coordString) {
		super.doubleClickAt(locator, coordString);
	}

	/**
	 * Simulates opening the context menu for the specified element (as might
	 * happen if the user "right-clicked" on the element).
	 * 
	 * @param locator
	 *            an element locator
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void contextMenuAt(String locator, String coordString) {
		super.contextMenuAt(locator, coordString);
	}

	/**
	 * Explicitly simulate an event, to trigger the corresponding "on
	 * <em>event</em>" handler.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param eventName
	 *            the event name, e.g. "focus" or "blur"
	 */
	public void fireEvent(String locator, String eventName) {
		super.fireEvent(locator, eventName);
	}

	/**
	 * Simulates a user pressing and releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 */
	public void keyPress(String locator, String keySequence) {
		super.keyPress(locator, keySequence);
	}

	/**
	 * Press the shift key and hold it down until doShiftUp() is called or a new
	 * page is loaded.
	 */
	public void shiftKeyDown() {
		super.shiftKeyDown();
	}

	/** Release the shift key. */
	public void shiftKeyUp() {
		super.shiftKeyUp();
	}

	/**
	 * Press the meta key and hold it down until doMetaUp() is called or a new
	 * page is loaded.
	 */
	public void metaKeyDown() {
		super.metaKeyDown();
	}

	/** Release the meta key. */
	public void metaKeyUp() {
		super.metaKeyUp();
	}

	/**
	 * Press the alt key and hold it down until doAltUp() is called or a new
	 * page is loaded.
	 */
	public void altKeyDown() {
		super.altKeyDown();
	}

	/** Release the alt key. */
	public void altKeyUp() {
		super.altKeyUp();
	}

	/**
	 * Press the control key and hold it down until doControlUp() is called or a
	 * new page is loaded.
	 */
	public void controlKeyDown() {
		super.controlKeyDown();
	}

	/** Release the control key. */
	public void controlKeyUp() {
		super.controlKeyUp();
	}

	/**
	 * Simulates a user pressing a key (without releasing it yet).
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 */
	public void keyDown(String locator, String keySequence) {
		super.keyDown(locator, keySequence);
	}

	/**
	 * Simulates a user releasing a key.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param keySequence
	 *            Either be a string(
	 *            "\" followed by the numeric keycode  of the key to be pressed, normally the ASCII value of that key), or a single  character. For example: "
	 *            w", "\119".
	 */
	public void keyUp(String locator, String keySequence) {
		super.keyUp(locator, keySequence);
	}

	/**
	 * Simulates a user hovering a mouse over the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseOver(String locator) {
		super.mouseOver(locator);
	}

	/**
	 * Simulates a user moving the mouse pointer away from the specified
	 * element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseOut(String locator) {
		super.mouseOut(locator);
	}

	/**
	 * Simulates a user pressing the left mouse button (without releasing it
	 * yet) on the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseDown(String locator) {
		super.mouseDown(locator);
	}

	/**
	 * Simulates a user pressing the right mouse button (without releasing it
	 * yet) on the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseDownRight(String locator) {
		super.mouseDownRight(locator);
	}

	/**
	 * Simulates a user pressing the left mouse button (without releasing it
	 * yet) at the specified location.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void mouseDownAt(String locator, String coordString) {
		super.mouseDownAt(locator, coordString);
	}

	/**
	 * Simulates a user pressing the right mouse button (without releasing it
	 * yet) at the specified location.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void mouseDownRightAt(String locator, String coordString) {
		super.mouseDownRightAt(locator, coordString);
	}

	/**
	 * Simulates the event that occurs when the user releases the mouse button
	 * (i.e., stops holding the button down) on the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseUp(String locator) {
		super.mouseUp(locator);
	}

	/**
	 * Simulates the event that occurs when the user releases the right mouse
	 * button (i.e., stops holding the button down) on the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseUpRight(String locator) {
		super.mouseUpRight(locator);
	}

	/**
	 * Simulates the event that occurs when the user releases the mouse button
	 * (i.e., stops holding the button down) at the specified location.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void mouseUpAt(String locator, String coordString) {
		super.mouseUpAt(locator, coordString);
	}

	/**
	 * Simulates the event that occurs when the user releases the right mouse
	 * button (i.e., stops holding the button down) at the specified location.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void mouseUpRightAt(String locator, String coordString) {
		super.mouseUpRightAt(locator, coordString);
	}

	/**
	 * Simulates a user pressing the mouse button (without releasing it yet) on
	 * the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void mouseMove(String locator) {
		super.mouseMove(locator);
	}

	/**
	 * Simulates a user pressing the mouse button (without releasing it yet) on
	 * the specified element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param coordString
	 *            specifies the x,y position (i.e. - 10,20) of the mouse event
	 *            relative to the element returned by the locator.
	 */
	public void mouseMoveAt(String locator, String coordString) {
		super.mouseMoveAt(locator, coordString);
	}

	/**
	 * Simulates keystroke events on the specified element, as though you typed
	 * the value key-by-key.
	 * 
	 * <p>
	 * This is a convenience method for calling keyDown, keyUp, keyPress for
	 * every character in the specified string; this is useful for dynamic UI
	 * widgets (like auto-completing combo boxes) that require explicit key
	 * events.
	 * </p>
	 * <p>
	 * Unlike the simple "type" command, which forces the specified value into
	 * the page directly, this command may or may not have any visible effect,
	 * even in cases where typing keys would normally have a visible effect. For
	 * example, if you use "typeKeys" on a form element, you may or may not see
	 * the results of what you typed in the field.
	 * </p>
	 * <p>
	 * In some cases, you may need to use the simple "type" command to set the
	 * value of the field and then the "typeKeys" command to send the keystroke
	 * events corresponding to what you just typed.
	 * </p>
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @param value
	 *            the value to type
	 */
	public void typeKeys(String locator, String value) {
		super.typeKeys(locator, value);
	}

	/**
	 * Uses java.awt.Robot (native interface) to type the specified value in the
	 * text field.
	 * 
	 * @param locator
	 *            locator of the text field
	 * @param value
	 *            value to be types
	 * @throws AWTException
	 *             If anything goes wrong
	 */
	public void typeNative(String locator, String value) throws AWTException {
		focus(locator);
		click(locator);
		for (int i = 0; i < value.length(); i++) {
			typeCharNative(value.charAt(i));
			sleep(500);
		}
	}

	private void typeCharNative(char character) throws AWTException {
		switch (character) {
		case 'a':
			doType(KeyEvent.VK_A);
			break;
		case 'b':
			doType(KeyEvent.VK_B);
			break;
		case 'c':
			doType(KeyEvent.VK_C);
			break;
		case 'd':
			doType(KeyEvent.VK_D);
			break;
		case 'e':
			doType(KeyEvent.VK_E);
			break;
		case 'f':
			doType(KeyEvent.VK_F);
			break;
		case 'g':
			doType(KeyEvent.VK_G);
			break;
		case 'h':
			doType(KeyEvent.VK_H);
			break;
		case 'i':
			doType(KeyEvent.VK_I);
			break;
		case 'j':
			doType(KeyEvent.VK_J);
			break;
		case 'k':
			doType(KeyEvent.VK_K);
			break;
		case 'l':
			doType(KeyEvent.VK_L);
			break;
		case 'm':
			doType(KeyEvent.VK_M);
			break;
		case 'n':
			doType(KeyEvent.VK_N);
			break;
		case 'o':
			doType(KeyEvent.VK_O);
			break;
		case 'p':
			doType(KeyEvent.VK_P);
			break;
		case 'q':
			doType(KeyEvent.VK_Q);
			break;
		case 'r':
			doType(KeyEvent.VK_R);
			break;
		case 's':
			doType(KeyEvent.VK_S);
			break;
		case 't':
			doType(KeyEvent.VK_T);
			break;
		case 'u':
			doType(KeyEvent.VK_U);
			break;
		case 'v':
			doType(KeyEvent.VK_V);
			break;
		case 'w':
			doType(KeyEvent.VK_W);
			break;
		case 'x':
			doType(KeyEvent.VK_X);
			break;
		case 'y':
			doType(KeyEvent.VK_Y);
			break;
		case 'z':
			doType(KeyEvent.VK_Z);
			break;
		case 'A':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_A);
			break;
		case 'B':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_B);
			break;
		case 'C':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_C);
			break;
		case 'D':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_D);
			break;
		case 'E':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_E);
			break;
		case 'F':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_F);
			break;
		case 'G':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_G);
			break;
		case 'H':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_H);
			break;
		case 'I':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_I);
			break;
		case 'J':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_J);
			break;
		case 'K':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_K);
			break;
		case 'L':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_L);
			break;
		case 'M':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_M);
			break;
		case 'N':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_N);
			break;
		case 'O':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_O);
			break;
		case 'P':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_P);
			break;
		case 'Q':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Q);
			break;
		case 'R':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_R);
			break;
		case 'S':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_S);
			break;
		case 'T':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_T);
			break;
		case 'U':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_U);
			break;
		case 'V':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_V);
			break;
		case 'W':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_W);
			break;
		case 'X':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_X);
			break;
		case 'Y':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Y);
			break;
		case 'Z':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_Z);
			break;
		case '`':
			doType(KeyEvent.VK_BACK_QUOTE);
			break;
		case '0':
			doType(KeyEvent.VK_0);
			break;
		case '1':
			doType(KeyEvent.VK_1);
			break;
		case '2':
			doType(KeyEvent.VK_2);
			break;
		case '3':
			doType(KeyEvent.VK_3);
			break;
		case '4':
			doType(KeyEvent.VK_4);
			break;
		case '5':
			doType(KeyEvent.VK_5);
			break;
		case '6':
			doType(KeyEvent.VK_6);
			break;
		case '7':
			doType(KeyEvent.VK_7);
			break;
		case '8':
			doType(KeyEvent.VK_8);
			break;
		case '9':
			doType(KeyEvent.VK_9);
			break;

		case '-':
			doType(KeyEvent.VK_MINUS);
			break;
		case '=':
			doType(KeyEvent.VK_EQUALS);
			break;
		case '~':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_QUOTE);
			break;
		case '!':
			doType(KeyEvent.VK_EXCLAMATION_MARK);
			break;
		case '@':
			doType(KeyEvent.VK_AT);
			break;
		case '#':
			doType(KeyEvent.VK_NUMBER_SIGN);
			break;
		case '$':
			doType(KeyEvent.VK_DOLLAR);
			break;
		case '%':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_5);
			break;
		case '^':
			doType(KeyEvent.VK_CIRCUMFLEX);
			break;
		case '&':
			doType(KeyEvent.VK_AMPERSAND);
			break;
		case '*':
			doType(KeyEvent.VK_ASTERISK);
			break;
		case '(':
			doType(KeyEvent.VK_LEFT_PARENTHESIS);
			break;
		case ')':
			doType(KeyEvent.VK_RIGHT_PARENTHESIS);
			break;
		case '_':
			doType(KeyEvent.VK_UNDERSCORE);
			break;
		case '+':
			doType(KeyEvent.VK_PLUS);
			break;
		case '\t':
			doType(KeyEvent.VK_TAB);
			break;
		case '\n':
			doType(KeyEvent.VK_ENTER);
			break;
		case '[':
			doType(KeyEvent.VK_OPEN_BRACKET);
			break;
		case ']':
			doType(KeyEvent.VK_CLOSE_BRACKET);
			break;
		case '\\':
			doType(KeyEvent.VK_BACK_SLASH);
			break;
		case '{':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_OPEN_BRACKET);
			break;
		case '}':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_CLOSE_BRACKET);
			break;
		case '|':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_BACK_SLASH);
			break;
		case ';':
			doType(KeyEvent.VK_SEMICOLON);
			break;
		case ':':
			doType(KeyEvent.VK_COLON);
			break;
		case '\'':
			doType(KeyEvent.VK_QUOTE);
			break;
		case '"':
			doType(KeyEvent.VK_QUOTEDBL);
			break;
		case ',':
			doType(KeyEvent.VK_COMMA);
			break;
		case '<':
			doType(KeyEvent.VK_LESS);
			break;
		case '.':
			doType(KeyEvent.VK_PERIOD);
			break;
		case '>':
			doType(KeyEvent.VK_GREATER);
			break;
		case '/':
			doType(KeyEvent.VK_SLASH);
			break;
		case '?':
			doType(KeyEvent.VK_SHIFT, KeyEvent.VK_SLASH);
			break;
		case ' ':
			doType(KeyEvent.VK_SPACE);
			break;

		default:
			throw new IllegalArgumentException("Cannot type character "
					+ character);
		}
	}

	private void doType(int... keyCodes) throws AWTException {
		doType(keyCodes, 0, keyCodes.length);
	}

	private void doType(int[] keyCodes, int offset, int length)
			throws AWTException {
		if (length == 0) {
			return;
		}
		if (null == robot) {
			robot = new Robot();
		}
		robot.keyPress(keyCodes[offset]);
		doType(keyCodes, offset + 1, length - 1);
		robot.keyRelease(keyCodes[offset]);
	}

	/**
	 * Set execution speed (i.e., set the millisecond length of a delay which
	 * will follow each selenium operation). By default, there is no such delay,
	 * i.e., the delay is 0 milliseconds.
	 * 
	 * @param value
	 *            the number of milliseconds to pause after operation
	 */
	public void setSpeed(String value) {
		super.setSpeed(value);
	}

	/**
	 * Get execution speed (i.e., get the millisecond length of the delay
	 * following each selenium operation). By default, there is no such delay,
	 * i.e., the delay is 0 milliseconds.
	 * 
	 * See also setSpeed.
	 * 
	 * @return the execution speed in milliseconds.
	 */
	public String getSpeed() {
		return super.getSpeed();
	}

	/**
	 * Add a selection to the set of selected options in a multi-select element
	 * using an option locator.
	 * 
	 * @see #doSelect for details of option locators
	 * @param locator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            multi-select box
	 * @param optionLocator
	 *            an option locator (a label by default)
	 */
	public void addSelection(String locator, String optionLocator) {
		super.addSelection(locator, optionLocator);
	}

	/**
	 * Remove a selection from the set of selected options in a multi-select
	 * element using an option locator.
	 * 
	 * @see #doSelect for details of option locators
	 * @param locator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            multi-select box
	 * @param optionLocator
	 *            an option locator (a label by default)
	 */
	public void removeSelection(String locator, String optionLocator) {
		super.removeSelection(locator, optionLocator);
	}

	/**
	 * Unselects all of the selected options in a multi-select element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            multi-select box
	 */
	public void removeAllSelections(String locator) {
		super.removeAllSelections(locator);
	}

	/**
	 * Submit the specified form. This is particularly useful for forms without
	 * submit buttons, e.g. single-input "Search" forms.
	 * 
	 * @param formLocator
	 *            an <a href="#locators">element locator</a> for the form you
	 *            want to submit
	 */
	public void submit(String formLocator) {
		super.submit(formLocator);
	}

	/**
	 * Opens a popup window (if a window with that ID isn't already open). After
	 * opening the window, you'll need to select it using the selectWindow
	 * command.
	 * 
	 * <p>
	 * This command can also be a useful workaround for bug SEL-339. In some
	 * cases, Selenium will be unable to intercept a call to window.open (if the
	 * call occurs during or before the "onLoad" event, for example). In those
	 * cases, you can force Selenium to notice the open window's name by using
	 * the Selenium openWindow command, using an empty (blank) url, like this:
	 * openWindow("", "myFunnyWindow").
	 * </p>
	 * 
	 * @param url
	 *            the URL to open, which can be blank
	 * @param windowID
	 *            the JavaScript window ID of the window to select
	 */
	public void openWindow(String url, String windowID) {
		super.openWindow(url, windowID);
	}

	/**
	 * Simplifies the process of selecting a popup window (and does not offer
	 * functionality beyond what <code>selectWindow()</code> already provides).
	 * <ul>
	 * <li>If <code>windowID</code> is either not specified, or specified as
	 * "null", the first non-top window is selected. The top window is the one
	 * that would be selected by <code>selectWindow()</code> without providing a
	 * <code>windowID</code> . This should not be used when more than one popup
	 * window is in play.</li>
	 * <li>Otherwise, the window will be looked up considering
	 * <code>windowID</code> as the following in order: 1) the "name" of the
	 * window, as specified to <code>window.open()</code>; 2) a javascript
	 * variable which is a reference to a window; and 3) the title of the
	 * window. This is the same ordered lookup performed by
	 * <code>selectWindow</code> .</li>
	 * </ul>
	 * 
	 * @param windowID
	 *            an identifier for the popup window, which can take on a number
	 *            of different meanings
	 */
	public void selectPopUp(String windowID) {
		super.selectPopUp(windowID);
	}

	/**
	 * Selects the main window. Functionally equivalent to using
	 * <code>selectWindow()</code> and specifying no value for
	 * <code>windowID</code>.
	 */
	public void deselectPopUp() {
		super.deselectPopUp();
	}

	/**
	 * Determine whether current/locator identify the frame containing this
	 * running code.
	 * 
	 * <p>
	 * This is useful in proxy injection mode, where this code runs in every
	 * browser frame and window, and sometimes the selenium server needs to
	 * identify the "current" frame. In this case, when the test calls
	 * selectFrame, this routine is called for each frame to figure out which
	 * one has been selected. The selected frame will return true, while all
	 * others will return false.
	 * </p>
	 * 
	 * @param currentFrameString
	 *            starting frame
	 * @param target
	 *            new frame (which might be relative to the current one)
	 * @return true if the new frame is this code's window
	 */
	public boolean getWhetherThisFrameMatchFrameExpression(
			String currentFrameString, String target) {
		return super.getWhetherThisFrameMatchFrameExpression(
				currentFrameString, target);
	}

	/**
	 * Determine whether currentWindowString plus target identify the window
	 * containing this running code.
	 * 
	 * <p>
	 * This is useful in proxy injection mode, where this code runs in every
	 * browser frame and window, and sometimes the selenium server needs to
	 * identify the "current" window. In this case, when the test calls
	 * selectWindow, this routine is called for each window to figure out which
	 * one has been selected. The selected window will return true, while all
	 * others will return false.
	 * </p>
	 * 
	 * @param currentWindowString
	 *            starting window
	 * @param target
	 *            new window (which might be relative to the current one, e.g.,
	 *            "_parent")
	 * @return true if the new window is this code's window
	 */
	public boolean getWhetherThisWindowMatchWindowExpression(
			String currentWindowString, String target) {
		return super.getWhetherThisWindowMatchWindowExpression(
				currentWindowString, target);
	}

	/**
	 * Waits for a popup window to appear and load up.
	 * 
	 * @param windowID
	 *            the JavaScript window "name" of the window that will appear
	 *            (not the text of the title bar) If unspecified, or specified
	 *            as "null", this command will wait for the first non-top window
	 *            to appear (don't rely on this if you are working with multiple
	 *            popups simultaneously).
	 * @param timeout
	 *            a timeout in milliseconds, after which the action will return
	 *            with an error. If this value is not specified, the default
	 *            Selenium timeout will be used. See the setTimeout() command.
	 */
	public void waitForPopUp(String windowID, String timeout) {
		super.waitForPopUp(windowID, timeout);
	}

	/**
	 * <p>
	 * By default, Selenium's overridden window.confirm() function will return
	 * true, as if the user had manually clicked OK; after running this command,
	 * the next call to confirm() will return false, as if the user had clicked
	 * Cancel. Selenium will then resume using the default behavior for future
	 * confirmations, automatically returning true (OK) unless/until you
	 * explicitly call this command for each confirmation.
	 * </p>
	 * <p>
	 * Take note - every time a confirmation comes up, you must consume it with
	 * a corresponding getConfirmation, or else the next selenium operation will
	 * fail.
	 * </p>
	 */
	public void chooseCancelOnNextConfirmation() {
		super.chooseCancelOnNextConfirmation();
	}

	/**
	 * <p>
	 * Undo the effect of calling chooseCancelOnNextConfirmation. Note that
	 * Selenium's overridden window.confirm() function will normally
	 * automatically return true, as if the user had manually clicked OK, so you
	 * shouldn't need to use this command unless for some reason you need to
	 * change your mind prior to the next confirmation. After any confirmation,
	 * Selenium will resume using the default behavior for future confirmations,
	 * automatically returning true (OK) unless/until you explicitly call
	 * chooseCancelOnNextConfirmation for each confirmation.
	 * </p>
	 * <p>
	 * Take note - every time a confirmation comes up, you must consume it with
	 * a corresponding getConfirmation, or else the next selenium operation will
	 * fail.
	 * </p>
	 */
	public void chooseOkOnNextConfirmation() {
		super.chooseOkOnNextConfirmation();
	}

	/**
	 * Instructs Selenium to return the specified answer string in response to
	 * the next JavaScript prompt [window.prompt()].
	 * 
	 * @param answer
	 *            the answer to give in response to the prompt pop-up
	 */
	public void answerOnNextPrompt(String answer) {
		super.answerOnNextPrompt(answer);
	}

	/** Simulates the user clicking the "back" button on their browser. */
	public void goBack() {
		super.goBack();
	}

	/** Simulates the user clicking the "Refresh" button on their browser. */
	public void refresh() {
		super.refresh();
	}

	/**
	 * Simulates the user clicking the "close" button in the titlebar of a popup
	 * window or tab.
	 */
	public void close() {
		super.close();
	}

	/**
	 * Has an alert occurred?
	 * 
	 * <p>
	 * This function never throws an exception
	 * </p>
	 * 
	 * @return true if there is an alert
	 */
	public boolean isAlertPresent() {
		return super.isAlertPresent();
	}

	/**
	 * Has a prompt occurred?
	 * 
	 * <p>
	 * This function never throws an exception
	 * </p>
	 * 
	 * @return true if there is a pending prompt
	 */
	public boolean isPromptPresent() {
		return super.isPromptPresent();
	}

	/**
	 * Has confirm() been called?
	 * 
	 * <p>
	 * This function never throws an exception
	 * </p>
	 * 
	 * @return true if there is a pending confirmation
	 */
	public boolean isConfirmationPresent() {
		return super.isConfirmationPresent();
	}

	/**
	 * Retrieves the message of a JavaScript alert generated during the previous
	 * action, or fail if there were no alerts.
	 * 
	 * <p>
	 * Getting an alert has the same effect as manually clicking OK. If an alert
	 * is generated but you do not consume it with getAlert, the next Selenium
	 * action will fail.
	 * </p>
	 * <p>
	 * Under Selenium, JavaScript alerts will NOT pop up a visible alert dialog.
	 * </p>
	 * <p>
	 * Selenium does NOT support JavaScript alerts that are generated in a
	 * page's onload() event handler. In this case a visible dialog WILL be
	 * generated and Selenium will hang until someone manually clicks OK.
	 * </p>
	 * 
	 * @return The message of the most recent JavaScript alert
	 */
	public String getAlert() {
		return super.getAlert();
	}

	/**
	 * Retrieves the message of a JavaScript confirmation dialog generated
	 * during the previous action.
	 * 
	 * <p>
	 * By default, the confirm function will return true, having the same effect
	 * as manually clicking OK. This can be changed by prior execution of the
	 * chooseCancelOnNextConfirmation command.
	 * </p>
	 * <p>
	 * If an confirmation is generated but you do not consume it with
	 * getConfirmation, the next Selenium action will fail.
	 * </p>
	 * <p>
	 * NOTE: under Selenium, JavaScript confirmations will NOT pop up a visible
	 * dialog.
	 * </p>
	 * <p>
	 * NOTE: Selenium does NOT support JavaScript confirmations that are
	 * generated in a page's onload() event handler. In this case a visible
	 * dialog WILL be generated and Selenium will hang until you manually click
	 * OK.
	 * </p>
	 * 
	 * @return the message of the most recent JavaScript confirmation dialog
	 */
	public String getConfirmation() {
		return super.getConfirmation();
	}

	/**
	 * Retrieves the message of a JavaScript question prompt dialog generated
	 * during the previous action.
	 * 
	 * <p>
	 * Successful handling of the prompt requires prior execution of the
	 * answerOnNextPrompt command. If a prompt is generated but you do not
	 * get/verify it, the next Selenium action will fail.
	 * </p>
	 * <p>
	 * NOTE: under Selenium, JavaScript prompts will NOT pop up a visible
	 * dialog.
	 * </p>
	 * <p>
	 * NOTE: Selenium does NOT support JavaScript prompts that are generated in
	 * a page's onload() event handler. In this case a visible dialog WILL be
	 * generated and Selenium will hang until someone manually clicks OK.
	 * </p>
	 * 
	 * @return the message of the most recent JavaScript question prompt
	 */
	public String getPrompt() {
		return super.getPrompt();
	}

	/**
	 * Gets the absolute URL of the current page.
	 * 
	 * @return the absolute URL of the current page
	 */
	public String getLocation() {
		return super.getLocation();
	}

	/**
	 * Gets the title of the current page.
	 * 
	 * @return the title of the current page
	 */
	public String getTitle() {
		return super.getTitle();
	}

	/**
	 * Gets the entire text of the page.
	 * 
	 * @return the entire text of the page
	 */
	public String getBodyText() {
		return super.getBodyText();
	}

	/**
	 * Gets the (whitespace-trimmed) value of an input field (or anything else
	 * with a value parameter). For checkbox/radio elements, the value will be
	 * "on" or "off" depending on whether the element is checked or not.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return the element value, or "on/off" for checkbox/radio elements
	 */
	public String getValue(String locator) {
		return super.getValue(locator);
	}

	/**
	 * Briefly changes the backgroundColor of the specified element yellow.
	 * Useful for debugging.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 */
	public void highlight(String locator) {
		super.highlight(locator);
	}

	/**
	 * Gets the result of evaluating the specified JavaScript snippet. The
	 * snippet may have multiple lines, but only the result of the last line
	 * will be returned.
	 * 
	 * <p>
	 * Note that, by default, the snippet will run in the context of the
	 * "selenium" object itself, so <code>this</code> will refer to the Selenium
	 * object. Use <code>window</code> to refer to the window of your
	 * application, e.g. <code>window.document.getElementById('foo')</code>
	 * </p>
	 * <p>
	 * If you need to use a locator to refer to a single element in your
	 * application page, you can use
	 * <code>this.browserbot.findElement("id=foo")</code> where "id=foo" is your
	 * locator.
	 * </p>
	 * 
	 * @param script
	 *            the JavaScript snippet to run
	 * @return the results of evaluating the snippet
	 */
	public String getEval(String script) {
		return super.getEval(script);
	}

	/**
	 * Gets whether a toggle-button (checkbox/radio) is checked. Fails if the
	 * specified element doesn't exist or isn't a toggle-button.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to a
	 *            checkbox or radio button
	 * @return true if the checkbox is checked, false otherwise
	 */
	public boolean isChecked(String locator) {
		return super.isChecked(locator);
	}

	/**
	 * Gets the text from a cell of a table. The cellAddress syntax
	 * tableLocator.row.column, where row and column start at 0.
	 * 
	 * @param tableCellAddress
	 *            a cell address, e.g. "foo.1.4"
	 * @return the text from the specified cell
	 */
	public String getTable(String tableCellAddress) {
		return super.getTable(tableCellAddress);
	}

	/**
	 * Gets all option labels (visible text) for selected options in the
	 * specified select or multi-select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return an array of all selected option labels in the specified select
	 *         drop-down
	 */
	public String[] getSelectedLabels(String selectLocator) {
		return super.getSelectedLabels(selectLocator);
	}

	/**
	 * Gets option label (visible text) for selected option in the specified
	 * select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return the selected option label in the specified select drop-down
	 */
	public String getSelectedLabel(String selectLocator) {
		return super.getSelectedLabel(selectLocator);
	}

	/**
	 * Gets all option values (value attributes) for selected options in the
	 * specified select or multi-select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return an array of all selected option values in the specified select
	 *         drop-down
	 */
	public String[] getSelectedValues(String selectLocator) {
		return super.getSelectedValues(selectLocator);
	}

	/**
	 * Gets option value (value attribute) for selected option in the specified
	 * select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return the selected option value in the specified select drop-down
	 */
	public String getSelectedValue(String selectLocator) {
		return super.getSelectedValue(selectLocator);
	}

	/**
	 * Gets all option indexes (option number, starting at 0) for selected
	 * options in the specified select or multi-select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return an array of all selected option indexes in the specified select
	 *         drop-down
	 */
	public String[] getSelectedIndexes(String selectLocator) {
		return super.getSelectedIndexes(selectLocator);
	}

	/**
	 * Gets option index (option number, starting at 0) for selected option in
	 * the specified select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return the selected option index in the specified select drop-down
	 */
	public String getSelectedIndex(String selectLocator) {
		return super.getSelectedIndex(selectLocator);
	}

	/**
	 * Gets all option element IDs for selected options in the specified select
	 * or multi-select element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return an array of all selected option IDs in the specified select
	 *         drop-down
	 */
	public String[] getSelectedIds(String selectLocator) {
		return super.getSelectedIds(selectLocator);
	}

	/**
	 * Gets option element ID for selected option in the specified select
	 * element.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return the selected option ID in the specified select drop-down
	 */
	public String getSelectedId(String selectLocator) {
		return super.getSelectedId(selectLocator);
	}

	/**
	 * Determines whether some option in a drop-down menu is selected.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return true if some option has been selected, false otherwise
	 */
	public boolean isSomethingSelected(String selectLocator) {
		return super.isSomethingSelected(selectLocator);
	}

	/**
	 * Gets all option labels in the specified select drop-down.
	 * 
	 * @param selectLocator
	 *            an <a href="#locators">element locator</a> identifying a
	 *            drop-down menu
	 * @return an array of all option labels in the specified select drop-down
	 */
	public String[] getSelectOptions(String selectLocator) {
		return super.getSelectOptions(selectLocator);
	}

	/**
	 * Gets the value of an element attribute. The value of the attribute may
	 * differ across browsers (this is the case for the "style" attribute, for
	 * example).
	 * 
	 * @param attributeLocator
	 *            an element locator followed by an @ sign and then the name of
	 *            the attribute, e.g. "foo@bar"
	 * @return the value of the specified attribute
	 */
	public String getAttribute(String attributeLocator) {
		return super.getAttribute(attributeLocator);
	}

	/**
	 * Verifies that the specified text pattern appears somewhere on the
	 * rendered page shown to the user.
	 * 
	 * @param pattern
	 *            a <a href="#patterns">pattern</a> to match with the text of
	 *            the page
	 * @return true if the pattern matches the text, false otherwise
	 */
	public boolean isTextPresent(String pattern) {
		return super.isTextPresent(pattern);
	}

	/**
	 * Determines whether the specified input element is editable, ie hasn't
	 * been disabled. This method will fail if the specified element isn't an
	 * input element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a>
	 * @return true if the input element is editable, false otherwise
	 */
	public boolean isEditable(String locator) {
		return super.isEditable(locator);
	}

	/**
	 * Returns the IDs of all buttons on the page.
	 * 
	 * <p>
	 * If a given button has no ID, it will appear as "" in this array.
	 * </p>
	 * 
	 * @return the IDs of all buttons on the page
	 */
	public String[] getAllButtons() {
		return super.getAllButtons();
	}

	/**
	 * Returns the IDs of all links on the page.
	 * 
	 * <p>
	 * If a given link has no ID, it will appear as "" in this array.
	 * </p>
	 * 
	 * @return the IDs of all links on the page
	 */
	public String[] getAllLinks() {
		return super.getAllLinks();
	}

	/**
	 * Returns the IDs of all input fields on the page.
	 * 
	 * <p>
	 * If a given field has no ID, it will appear as "" in this array.
	 * </p>
	 * 
	 * @return the IDs of all field on the page
	 */
	public String[] getAllFields() {
		return super.getAllFields();
	}

	/**
	 * Returns every instance of some attribute from all known windows.
	 * 
	 * @param attributeName
	 *            name of an attribute on the windows
	 * @return the set of values of this attribute from all known windows.
	 */
	public String[] getAttributeFromAllWindows(String attributeName) {
		return super.getAttributeFromAllWindows(attributeName);
	}

	/**
	 * deprecated - use dragAndDrop instead
	 * 
	 * @param locator
	 *            an element locator
	 * @param movementsString
	 *            offset in pixels from the current location to which the
	 *            element should be moved, e.g., "+70,-300"
	 */
	public void dragdrop(String locator, String movementsString) {
		super.dragdrop(locator, movementsString);
	}

	/**
	 * Configure the number of pixels between "mousemove" events during
	 * dragAndDrop commands (default=10).
	 * <p>
	 * Setting this value to 0 means that we'll send a "mousemove" event to
	 * every single pixel in between the start location and the end location;
	 * that can be very slow, and may cause some browsers to force the
	 * JavaScript to timeout.
	 * </p>
	 * <p>
	 * If the mouse speed is greater than the distance between the two dragged
	 * objects, we'll just send one "mousemove" at the start location and then
	 * one final one at the end location.
	 * </p>
	 * 
	 * @param pixels
	 *            the number of pixels between "mousemove" events
	 */
	public void setMouseSpeed(String pixels) {
		super.setMouseSpeed(pixels);
	}

	/**
	 * Returns the number of pixels between "mousemove" events during
	 * dragAndDrop commands (default=10).
	 * 
	 * @return the number of pixels between "mousemove" events during
	 *         dragAndDrop commands (default=10)
	 */
	public Number getMouseSpeed() {
		return super.getMouseSpeed();
	}

	/**
	 * Drags an element a certain distance and then drops it
	 * 
	 * @param locator
	 *            an element locator
	 * @param movementsString
	 *            offset in pixels from the current location to which the
	 *            element should be moved, e.g., "+70,-300"
	 */
	public void dragAndDrop(String locator, String movementsString) {
		super.dragAndDrop(locator, movementsString);
	}

	/**
	 * Drags an element and drops it on another element
	 * 
	 * @param locatorOfObjectToBeDragged
	 *            an element to be dragged
	 * @param locatorOfDragDestinationObject
	 *            an element whose location (i.e., whose center-most pixel) will
	 *            be the point where locatorOfObjectToBeDragged is dropped
	 */
	public void dragAndDropToObject(String locatorOfObjectToBeDragged,
			String locatorOfDragDestinationObject) {
		super.dragAndDropToObject(locatorOfObjectToBeDragged,
				locatorOfDragDestinationObject);
	}

	/** Gives focus to the currently selected window */
	public void windowFocus() {
		super.windowFocus();
	}

	/** Resize currently selected window to take up the entire screen */
	public void windowMaximize() {
		super.windowMaximize();
	}

	/**
	 * Returns the IDs of all windows that the browser knows about.
	 * 
	 * @return the IDs of all windows that the browser knows about.
	 */
	public String[] getAllWindowIds() {
		return super.getAllWindowIds();
	}

	/**
	 * Returns the names of all windows that the browser knows about.
	 * 
	 * @return the names of all windows that the browser knows about.
	 */
	public String[] getAllWindowNames() {
		return super.getAllWindowNames();
	}

	/**
	 * Returns the titles of all windows that the browser knows about.
	 * 
	 * @return the titles of all windows that the browser knows about.
	 */
	public String[] getAllWindowTitles() {
		return super.getAllWindowTitles();
	}

	/**
	 * Returns the entire HTML source between the opening and closing "html"
	 * tags.
	 * 
	 * @return the entire HTML source
	 */
	public String getHtmlSource() {
		return super.getHtmlSource();
	}

	/**
	 * Moves the text cursor to the specified position in the given input
	 * element or textarea. This method will fail if the specified element isn't
	 * an input element or textarea.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            input element or textarea
	 * @param position
	 *            the numerical position of the cursor in the field; position
	 *            should be 0 to move the position to the beginning of the
	 *            field. You can also set the cursor to -1 to move it to the end
	 *            of the field.
	 */
	public void setCursorPosition(String locator, String position) {
		super.setCursorPosition(locator, position);
	}

	/**
	 * Get the relative index of an element to its parent (starting from 0). The
	 * comment node and empty text node will be ignored.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @return of relative index of the element to its parent (starting from 0)
	 */
	public Number getElementIndex(String locator) {
		return super.getElementIndex(locator);
	}

	/**
	 * Check if these two elements have same parent and are ordered siblings in
	 * the DOM. Two same elements will not be considered ordered.
	 * 
	 * @param locator1
	 *            an <a href="#locators">element locator</a> pointing to the
	 *            first element
	 * @param locator2
	 *            an <a href="#locators">element locator</a> pointing to the
	 *            second element
	 * @return true if element1 is the previous sibling of element2, false
	 *         otherwise
	 */
	public boolean isOrdered(String locator1, String locator2) {
		return super.isOrdered(locator1, locator2);
	}

	/**
	 * Retrieves the horizontal position of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element OR an element itself
	 * @return of pixels from the edge of the frame.
	 */
	public Number getElementPositionLeft(String locator) {
		return super.getElementPositionLeft(locator);
	}

	/**
	 * Retrieves the vertical position of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element OR an element itself
	 * @return of pixels from the edge of the frame.
	 */
	public Number getElementPositionTop(String locator) {
		return super.getElementPositionTop(locator);
	}

	/**
	 * Retrieves the width of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @return width of an element in pixels
	 */
	public Number getElementWidth(String locator) {
		return super.getElementWidth(locator);
	}

	/**
	 * Retrieves the height of an element
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @return height of an element in pixels
	 */
	public Number getElementHeight(String locator) {
		return super.getElementHeight(locator);
	}

	/**
	 * Retrieves the text cursor position in the given input element or
	 * textarea; beware, this may not work perfectly on all browsers.
	 * 
	 * <p>
	 * Specifically, if the cursor/selection has been cleared by JavaScript,
	 * this command will tend to return the position of the last location of the
	 * cursor, even though the cursor is now gone from the page. This is filed
	 * as <a href="http://jira.openqa.org/browse/SEL-243">SEL-243</a>.
	 * </p>
	 * This method will fail if the specified element isn't an input element or
	 * textarea, or there is no cursor in the element.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            input element or textarea
	 * @return the numerical position of the cursor in the field
	 */
	public Number getCursorPosition(String locator) {
		return super.getCursorPosition(locator);
	}

	/**
	 * Returns the specified expression.
	 * 
	 * <p>
	 * This is useful because of JavaScript preprocessing. It is used to
	 * generate commands like assertExpression and waitForExpression.
	 * </p>
	 * 
	 * @param expression
	 *            the value to return
	 * @return the value passed in
	 */
	public String getExpression(String expression) {
		return super.getExpression(expression);
	}

	/**
	 * Returns the number of nodes that match the specified xpath, eg. "//table"
	 * would give the number of tables.
	 * 
	 * @param xpath
	 *            the xpath expression to evaluate. do NOT wrap this expression
	 *            in a 'count()' function; we will do that for you.
	 * @return the number of nodes that match the specified xpath
	 */
	public Number getXpathCount(String xpath) {
		return super.getXpathCount(xpath);
	}

	/**
	 * Temporarily sets the "id" attribute of the specified element, so you can
	 * locate it in the future using its ID rather than a slow/complicated
	 * XPath. This ID will disappear once the page is reloaded.
	 * 
	 * @param locator
	 *            an <a href="#locators">element locator</a> pointing to an
	 *            element
	 * @param identifier
	 *            a string to be used as the ID of the specified element
	 */
	public void assignId(String locator, String identifier) {
		super.assignId(locator, identifier);
	}

	/**
	 * Specifies whether Selenium should use the native in-browser
	 * implementation of XPath (if any native version is available); if you pass
	 * "false" to this function, we will always use our pure-JavaScript xpath
	 * library. Using the pure-JS xpath library can improve the consistency of
	 * xpath element locators between different browser vendors, but the pure-JS
	 * version is much slower than the native implementations.
	 * 
	 * @param allow
	 *            boolean, true means we'll prefer to use native XPath; false
	 *            means we'll only use JS XPath
	 */
	public void allowNativeXpath(String allow) {
		super.allowNativeXpath(allow);
	}

	/**
	 * Specifies whether Selenium will ignore xpath attributes that have no
	 * value, i.e. are the empty string, when using the non-native xpath
	 * evaluation engine. You'd want to do this for performance reasons in IE.
	 * However, this could break certain xpaths, for example an xpath that looks
	 * for an attribute whose value is NOT the empty string.
	 * 
	 * The hope is that such xpaths are relatively rare, but the user should
	 * have the option of using them. Note that this only influences xpath
	 * evaluation when using the ajaxslt engine (i.e. not "javascript-xpath").
	 * 
	 * @param ignore
	 *            boolean, true means we'll ignore attributes without value at
	 *            the expense of xpath "correctness"; false means we'll
	 *            sacrifice speed for correctness.
	 */
	public void ignoreAttributesWithoutValue(String ignore) {
		super.ignoreAttributesWithoutValue(ignore);
	}

	/**
	 * Runs the specified JavaScript snippet repeatedly until it evaluates to
	 * "true". The snippet may have multiple lines, but only the result of the
	 * last line will be considered.
	 * 
	 * <p>
	 * Note that, by default, the snippet will be run in the runner's test
	 * window, not in the window of your application. To get the window of your
	 * application, you can use the JavaScript snippet
	 * <code>selenium.browserbot.getCurrentWindow()</code>, and then run your
	 * JavaScript in there
	 * </p>
	 * 
	 * @param script
	 *            the JavaScript snippet to run
	 * @param timeout
	 *            a timeout in milliseconds, after which this command will
	 *            return with an error
	 */
	public void waitForCondition(String script, String timeout) {
		super.waitForCondition(script, timeout);
	}

	/**
	 * Specifies the amount of time that Selenium will wait for actions to
	 * complete.
	 * 
	 * <p>
	 * Actions that require waiting include "open" and the "waitFor*" actions.
	 * </p>
	 * The default timeout is 30 seconds.
	 * 
	 * @param timeout
	 *            a timeout in milliseconds, after which the action will return
	 *            with an error
	 */
	public void setTimeout(String timeout) {
		super.setTimeout(timeout);
	}

	/**
	 * Waits for a new frame to load.
	 * 
	 * <p>
	 * Selenium constantly keeps track of new pages and frames loading, and sets
	 * a "newPageLoaded" flag when it first notices a page load.
	 * </p>
	 * 
	 * See waitForPageToLoad for more information.
	 * 
	 * @param frameAddress
	 *            FrameAddress from the server side
	 * @param timeout
	 *            a timeout in milliseconds, after which this command will
	 *            return with an error
	 */
	public void waitForFrameToLoad(String frameAddress, String timeout) {
		super.waitForFrameToLoad(frameAddress, timeout);
	}

	/**
	 * Return all cookies of the current page under test.
	 * 
	 * @return all cookies of the current page under test
	 */
	public String getCookie() {
		return super.getCookie();
	}

	/**
	 * Returns the value of the cookie with the specified name, or throws an
	 * error if the cookie is not present.
	 * 
	 * @param name
	 *            the name of the cookie
	 * @return the value of the cookie
	 */
	public String getCookieByName(String name) {
		return super.getCookieByName(name);
	}

	/**
	 * Returns true if a cookie with the specified name is present, or false
	 * otherwise.
	 * 
	 * @param name
	 *            the name of the cookie
	 * @return true if a cookie with the specified name is present, or false
	 *         otherwise.
	 */
	public boolean isCookiePresent(String name) {
		return super.isCookiePresent(name);
	}

	/**
	 * Create a new cookie whose path and domain are same with those of current
	 * page under test, unless you specified a path for this cookie explicitly.
	 * 
	 * @param nameValuePair
	 *            name and value of the cookie in a format "name=value"
	 * @param optionsString
	 *            options for the cookie. Currently supported options include
	 *            'path', 'max_age' and 'domain'. the optionsString's format is
	 *            "path=/path/, max_age=60, domain=.foo.com". The order of
	 *            options are irrelevant, the unit of the value of 'max_age' is
	 *            second. Note that specifying a domain that isn't a subset of
	 *            the current domain will usually fail.
	 */
	public void createCookie(String nameValuePair, String optionsString) {
		super.createCookie(nameValuePair, optionsString);
	}

	/**
	 * Delete a named cookie with specified path and domain. Be careful; to
	 * delete a cookie, you need to delete it using the exact same path and
	 * domain that were used to create the cookie. If the path is wrong, or the
	 * domain is wrong, the cookie simply won't be deleted. Also note that
	 * specifying a domain that isn't a subset of the current domain will
	 * usually fail.
	 * 
	 * Since there's no way to discover at runtime the original path and domain
	 * of a given cookie, we've added an option called 'recurse' to try all
	 * sub-domains of the current domain with all paths that are a subset of the
	 * current path. Beware; this option can be slow. In big-O notation, it
	 * operates in O(n*m) time, where n is the number of dots in the domain name
	 * and m is the number of slashes in the path.
	 * 
	 * @param name
	 *            the name of the cookie to be deleted
	 * @param optionsString
	 *            options for the cookie. Currently supported options include
	 *            'path', 'domain' and 'recurse.' The optionsString's format is
	 *            "path=/path/, domain=.foo.com, recurse=true". The order of
	 *            options are irrelevant. Note that specifying a domain that
	 *            isn't a subset of the current domain will usually fail.
	 */
	public void deleteCookie(String name, String optionsString) {
		super.deleteCookie(name, optionsString);
	}

	/**
	 * Calls deleteCookie with recurse=true on all cookies visible to the
	 * current page. As noted on the documentation for deleteCookie,
	 * recurse=true can be much slower than simply deleting the cookies using a
	 * known domain/path.
	 */
	public void deleteAllVisibleCookies() {
		super.deleteAllVisibleCookies();
	}

	/**
	 * Sets the threshold for browser-side logging messages; log messages
	 * beneath this threshold will be discarded. Valid logLevel strings are:
	 * "debug", "info", "warn", "error" or "off". To see the browser logs, you
	 * need to either show the log window in GUI mode, or enable browser-side
	 * logging in Selenium RC.
	 * 
	 * @param logLevel
	 *            one of the following: "debug", "info", "warn", "error" or
	 *            "off"
	 */
	public void setBrowserLogLevel(String logLevel) {
		super.setBrowserLogLevel(logLevel);
	}

	/**
	 * Creates a new "script" tag in the body of the current test window, and
	 * adds the specified text into the body of the command. Scripts run in this
	 * way can often be debugged more easily than scripts executed using
	 * Selenium's "getEval" command. Beware that JS exceptions thrown in these
	 * script tags aren't managed by Selenium, so you should probably wrap your
	 * script in try/catch blocks if there is any chance that the script will
	 * throw an exception.
	 * 
	 * @param script
	 *            the JavaScript snippet to run
	 */
	public void runScript(String script) {
		super.runScript(script);
	}

	/**
	 * Defines a new function for Selenium to locate elements on the page. For
	 * example, if you define the strategy "foo", and someone runs
	 * click("foo=blah"), we'll run your function, passing you the string
	 * "blah", and click on the element that your function returns, or throw an
	 * "Element not found" error if your function returns null.
	 * 
	 * We'll pass three arguments to your function:
	 * <ul>
	 * <li>locator: the string the user passed in</li>
	 * <li>inWindow: the currently selected window</li>
	 * <li>inDocument: the currently selected document</li>
	 * </ul>
	 * The function must return null if the element can't be found.
	 * 
	 * @param strategyName
	 *            the name of the strategy to define; this should use only
	 *            letters [a-zA-Z] with no spaces or other punctuation.
	 * @param functionDefinition
	 *            a string defining the body of a function in JavaScript. For
	 *            example:
	 *            <code>return inDocument.getElementById(locator);</code>
	 */
	public void addLocationStrategy(String strategyName,
			String functionDefinition) {
		super.addLocationStrategy(strategyName, functionDefinition);
	}

	/**
	 * Saves the entire contents of the current window canvas to a PNG file.
	 * Contrast this with the captureScreenshot command, which captures the
	 * contents of the OS viewport (i.e. whatever is currently being displayed
	 * on the monitor), and is implemented in the RC only. Currently this only
	 * works in Firefox when running in chrome mode, and in IE non-HTA using the
	 * EXPERIMENTAL "Snapsie" utility. The Firefox implementation is mostly
	 * borrowed from the Screengrab! Firefox extension. Please see
	 * http://www.screengrab.org and http://snapsie.sourceforge.net/ for
	 * details.
	 * 
	 * @param filename
	 *            the path to the file to persist the screenshot as. No filename
	 *            extension will be appended by default. Directories will not be
	 *            created if they do not exist, and an exception will be thrown,
	 *            possibly by native code.
	 * @param kwargs
	 *            a kwargs string that modifies the way the screenshot is
	 *            captured. Example: "background=#CCFFDD" . Currently valid
	 *            options:
	 *            <dl>
	 *            <dt>background</dt>
	 *            <dd>the background CSS for the HTML document. This may be
	 *            useful to set for capturing screenshots of less-than-ideal
	 *            layouts, for example where absolute positioning causes the
	 *            calculation of the canvas dimension to fail and a black
	 *            background is exposed (possibly obscuring black text).</dd>
	 *            </dl>
	 */
	public void captureEntirePageScreenshot(String filename, String kwargs) {
		super.captureEntirePageScreenshot(filename, kwargs);
	}

	/**
	 * Executes a command rollup, which is a series of commands with a unique
	 * name, and optionally arguments that control the generation of the set of
	 * commands. If any one of the rolled-up commands fails, the rollup is
	 * considered to have failed. Rollups may also contain nested rollups.
	 * 
	 * @param rollupName
	 *            the name of the rollup command
	 * @param kwargs
	 *            keyword arguments string that influences how the rollup
	 *            expands into commands
	 */
	public void rollup(String rollupName, String kwargs) {
		super.rollup(rollupName, kwargs);
	}

	/**
	 * Loads script content into a new script tag in the Selenium document. This
	 * differs from the runScript command in that runScript adds the script tag
	 * to the document of the AUT, not the Selenium document. The following
	 * entities in the script content are replaced by the characters they
	 * represent:
	 * 
	 * &lt; &gt; &amp;
	 * 
	 * The corresponding remove command is removeScript.
	 * 
	 * @param scriptContent
	 *            the Javascript content of the script to add
	 * @param scriptTagId
	 *            (optional) the id of the new script tag. If specified, and an
	 *            element with this id already exists, this operation will fail.
	 */
	public void addScript(String scriptContent, String scriptTagId) {
		super.addScript(scriptContent, scriptTagId);
	}

	/**
	 * Removes a script tag from the Selenium document identified by the given
	 * id. Does nothing if the referenced tag doesn't exist.
	 * 
	 * @param scriptTagId
	 *            the id of the script element to remove.
	 */
	public void removeScript(String scriptTagId) {
		super.removeScript(scriptTagId);
	}

	/**
	 * Allows choice of one of the available libraries.
	 * 
	 * @param libraryName
	 *            name of the desired library Only the following three can be
	 *            chosen:
	 *            <ul>
	 *            <li>"ajaxslt" - Google's library</li>
	 *            <li>"javascript-xpath" - Cybozu Labs' faster library</li>
	 *            <li>"default" - The default library. Currently the default
	 *            library is "ajaxslt" .</li>
	 *            </ul>
	 *            If libraryName isn't one of these three, then no change will
	 *            be made.
	 */
	public void useXpathLibrary(String libraryName) {
		super.useXpathLibrary(libraryName);
	}

	/**
	 * Writes a message to the status bar and adds a note to the browser-side
	 * log.
	 * 
	 * @param context
	 *            the message to be sent to the browser
	 */
	public void setContext(String context) {
		super.setContext(context);
	}

	/**
	 * Sets a file input (upload) field to the file listed in fileLocator
	 * 
	 * @param fieldLocator
	 *            an <a href="#locators">element locator</a>
	 * @param fileLocator
	 *            a URL pointing to the specified file. Before the file can be
	 *            set in the input field (fieldLocator), Selenium RC may need to
	 *            transfer the file to the local machine before attaching the
	 *            file in a web page form. This is common in selenium grid
	 *            configurations where the RC server driving the browser is not
	 *            the same machine that started the test. Supported Browsers:
	 *            Firefox ("*chrome") only.
	 */
	public void attachFile(String fieldLocator, String fileLocator) {
		super.attachFile(fieldLocator, fileLocator);
	}

	/**
	 * Captures a PNG screenshot to the specified file.
	 * 
	 * @param filename
	 *            the absolute path to the file to be written, e.g.
	 *            "c:\blah\screenshot.png"
	 */
	public void captureScreenshot(String filename) {
		super.captureScreenshot(filename);
	}

	/**
	 * Captures a PNG screenshot of the screen to the specified file.
	 * 
	 * This method uses a different strategy than the
	 * {@link #captureScreenshot(String)} method for Internet Explorer. This
	 * solves the problem with incorrect colors in screenshots taken of Internet
	 * Explorer in 256 color mode.
	 * 
	 * @param filename
	 *            the absolute path to the file to be written, e.g.
	 *            "c:\blah\screenshot.png"
	 */
	/*
	 * public void captureScreenshot2(String screenshotFilePath) { if
	 * (this.BROWSER.equalsIgnoreCase(Constants.BROWSER_IE)) { new
	 * ScreenshotCapturer.TakeScreenshotExe()
	 * .captureScreenshot(screenshotFilePath); } else { new
	 * ScreenshotCapturer.SeleniumDefault()
	 * .captureScreenshot(screenshotFilePath); } }
	 */

	/**
	 * Capture a PNG screenshot. It then returns the file as a base 64 encoded
	 * string.
	 * 
	 * @return The base 64 encoded string of the screen shot (PNG file)
	 */
	public String captureScreenshotToString() {
		return super.captureScreenshotToString();
	}

	/**
	 * Returns the network traffic seen by the browser, including headers, AJAX
	 * requests, status codes, and timings. When this function is called, the
	 * traffic log is cleared, so the returned content is only the traffic seen
	 * since the last call.
	 * 
	 * @param type
	 *            The type of data to return the network traffic as. Valid
	 *            values are: json, xml, or plain.
	 * @return A string representation in the defined type of the network
	 *         traffic seen by the browser.
	 */
	public String captureNetworkTraffic(String type) {
		return super.captureNetworkTraffic(type);
	}

	/**
	 * Tells the Selenium server to add the specificed key and value as a custom
	 * outgoing request header. This only works if the browser is configured to
	 * use the built in Selenium proxy.
	 * 
	 * @param key
	 *            the header name.
	 * @param value
	 *            the header value.
	 */
	public void addCustomRequestHeader(String key, String value) {
		super.addCustomRequestHeader(key, value);
	}

	/**
	 * Downloads a screenshot of the browser current window canvas to a based 64
	 * encoded PNG file. The <em>entire</em> windows canvas is captured,
	 * including parts rendered outside of the current view port.
	 * 
	 * Currently this only works in Mozilla and when running in chrome mode.
	 * 
	 * @param kwargs
	 *            A kwargs string that modifies the way the screenshot is
	 *            captured. Example: "background=#CCFFDD". This may be useful to
	 *            set for capturing screenshots of less-than-ideal layouts, for
	 *            example where absolute positioning causes the calculation of
	 *            the canvas dimension to fail and a black background is exposed
	 *            (possibly obscuring black text).
	 * @return The base 64 encoded string of the page screenshot (PNG file)
	 */
	public String captureEntirePageScreenshotToString(String kwargs) {
		return super.captureEntirePageScreenshotToString(kwargs);
	}

	/**
	 * Kills the running Selenium Server and all browser sessions. After you run
	 * this command, you will no longer be able to send commands to the server;
	 * you can't remotely start the server once it has been stopped. Normally
	 * you should prefer to run the "stop" command, which terminates the current
	 * browser session, rather than shutting down the entire server.
	 */
	public void shutDownSeleniumServer() {
		super.shutDownSeleniumServer();
	}

	/**
	 * Retrieve the last messages logged on a specific remote control. Useful
	 * for error reports, especially when running multiple remote controls in a
	 * distributed environment. The maximum number of log messages that can be
	 * retrieve is configured on remote control startup.
	 * 
	 * @return The last N log messages as a multi-line string.
	 */
	public String retrieveLastRemoteControlLogs() {
		return super.retrieveLastRemoteControlLogs();
	}

	/**
	 * Simulates a user pressing a key (without releasing it yet) by sending a
	 * native operating system keystroke. This function uses the java.awt.Robot
	 * class to send a keystroke; this more accurately simulates typing a key on
	 * the keyboard. It does not honor settings from the shiftKeyDown,
	 * controlKeyDown, altKeyDown and metaKeyDown commands, and does not target
	 * any particular HTML element. To send a keystroke to a particular element,
	 * focus on the element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 */
	public void keyDownNative(String keycode) {
		super.keyDownNative(keycode);
	}

	/**
	 * Simulates a user releasing a key by sending a native operating system
	 * keystroke. This function uses the java.awt.Robot class to send a
	 * keystroke; this more accurately simulates typing a key on the keyboard.
	 * It does not honor settings from the shiftKeyDown, controlKeyDown,
	 * altKeyDown and metaKeyDown commands, and does not target any particular
	 * HTML element. To send a keystroke to a particular element, focus on the
	 * element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 */
	public void keyUpNative(String keycode) {
		super.keyUpNative(keycode);
	}

	/**
	 * Simulates a user pressing and releasing a key by sending a native
	 * operating system keystroke. This function uses the java.awt.Robot class
	 * to send a keystroke; this more accurately simulates typing a key on the
	 * keyboard. It does not honor settings from the shiftKeyDown,
	 * controlKeyDown, altKeyDown and metaKeyDown commands, and does not target
	 * any particular HTML element. To send a keystroke to a particular element,
	 * focus on the element first before running this command.
	 * 
	 * @param keycode
	 *            an integer keycode number corresponding to a
	 *            java.awt.event.KeyEvent; note that Java keycodes are NOT the
	 *            same thing as JavaScript keycodes!
	 */
	public void keyPressNative(String keycode) {
		super.keyPressNative(keycode);
	}

}
