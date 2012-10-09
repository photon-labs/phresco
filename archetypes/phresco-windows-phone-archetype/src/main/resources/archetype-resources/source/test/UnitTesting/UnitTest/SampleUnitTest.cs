using System;
using System.Net;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Shapes;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using Microsoft.Silverlight.Testing;

namespace UnitTest
{
    [TestClass]
    public class SampleUnitTest
    {
        [TestMethod]
        
        [Tag("Add")]
        public void AddNumbers()
        {
            int x = 10; int y = 20;
            int z = 10 + 20;
            Assert.IsTrue((30 == z), "Addition failed");
        }

    }
}
