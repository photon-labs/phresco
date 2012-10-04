using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;

namespace Metro.UnitTests
{
    [TestClass]
    public class UnitTest1
    {
        [TestMethod]
        public void TestMethod1()
        {
Assert.AreEqual("HELLO" , "HELLO");
        }
    }
}
