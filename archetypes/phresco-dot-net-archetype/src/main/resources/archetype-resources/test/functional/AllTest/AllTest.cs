
/*
Author by {phresco} QA Automation Team
*/
using System;
using System.Configuration;
using System.Data;
using System.IO;
using System.Text;
using System.Threading;
using NUnit.Framework;
using OpenQA.Selenium;
using OpenQA.Selenium.IE;
using Selenium;

namespace AllTest
{
    [TestFixture]
    public class HelloWorld
    {
        public IWebDriver driver;
        private StringBuilder verificationErrors;
        String baseUrl;
        [SetUp]
        public void SetupTest()
        {
            DataSet helloworld = new DataSet();
            helloworld.ReadXml(ConfigurationSettings.AppSettings["XmlPath"].ToString());
            baseUrl = helloworld.Tables[1].Rows[0].ItemArray[1].ToString() + ":" + "//" + helloworld.Tables[1].Rows[0].ItemArray[2].ToString() + ":" + helloworld.Tables[1].Rows[0].ItemArray[3].ToString() + "/" + helloworld.Tables[1].Rows[0].ItemArray[0].ToString();
            try
            {
                driver = new InternetExplorerDriver();
                driver.Navigate().GoToUrl(baseUrl);
                verificationErrors = new StringBuilder();
                
            }
            catch (Exception e)
            {
                Assert.AreEqual(helloworld.Tables[0].Rows[0].ItemArray[0].ToString(), e.ToString());
            }
        }
        public void TakeScreenshot(IWebDriver driver, string path)
        {
            ITakesScreenshot screenshotDriver = driver as ITakesScreenshot;
            Screenshot screenshot = screenshotDriver.GetScreenshot();
            screenshot.SaveAsFile(path, System.Drawing.Imaging.ImageFormat.Png);
            screenshot.ToString();
        }
        [TearDown]
        public void TeardownTest()
        {
            try
            {

                driver.Quit();
            }
            catch (Exception)
            {
                // Ignore errors if unable to close the browser
            }
            Assert.AreEqual("", verificationErrors.ToString());
        }
        [Test]
        public void helloWorldTest()
        {
            DataSet helloworld = new DataSet();
            helloworld.ReadXml(ConfigurationSettings.AppSettings["XmlPath"].ToString());
            baseUrl = helloworld.Tables[1].Rows[0].ItemArray[1].ToString() + ":" + "//" + helloworld.Tables[1].Rows[0].ItemArray[2].ToString() + ":" + helloworld.Tables[1].Rows[0].ItemArray[3].ToString() + "/" + helloworld.Tables[1].Rows[0].ItemArray[0].ToString();
            Directory.CreateDirectory(helloworld.Tables[0].Rows[0].ItemArray[4].ToString());
            
            try
            {
                //Opens Phresco Home Page.   
                driver.Navigate().GoToUrl(baseUrl);
                Thread.Sleep(Convert.ToInt32(helloworld.Tables[0].Rows[0].ItemArray[2].ToString()));
                         
               }
            catch (Exception e)
            {
                TakeScreenshot(driver, helloworld.Tables[0].Rows[0].ItemArray[3].ToString());
                throw e;
            }
        }
    }
}