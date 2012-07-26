using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;

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
            Assert.IsTrue(!HELLO_WORLD.Equals(HELLO_WORLD_CHECK));
        }

        [TestMethod]
        public void AssertFalse()
        {
            Assert.IsFalse(HELLO_WORLD.Equals(HELLO_WORLD_CHECK));
        }
    }
}
