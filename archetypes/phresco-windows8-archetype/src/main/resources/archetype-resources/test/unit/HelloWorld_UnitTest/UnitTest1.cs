using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestPlatform.UnitTestFramework;

namespace HelloWorld_UnitTest
{
    [TestClass]
    public class UnitTest1
    {
        private static string HELLO_WORLD = "Hello World!!";
        private static string HELLO_WORLD_CHECK = "HelloWorld";

        [TestMethod]
        public void AssertTrue()
        {
            Assert.IsTrue(!HELLO_WORLD.Equals(HELLO_WORLD_CHECK), "Test Successful");
        }

        [TestMethod]
        public void AssertFalse()
        {
            Assert.IsFalse(HELLO_WORLD.Equals(HELLO_WORLD_CHECK), "Test could not be run");
        }
    }
}
