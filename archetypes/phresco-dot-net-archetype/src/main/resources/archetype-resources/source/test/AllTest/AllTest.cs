using System;
using NUnit.Framework;

namespace AllTest.UnitTesting
{
    [TestFixture]
    public class Hello
    {
        public void get()
        {
            Console.Write("Hello World");

        }

        [Test]
        public void HelloTests()
        {
            Hello Hi = new Hello();
            Hi.get();
            Assert.IsTrue(true);
        }

    }   //for testfixture. 

}//for namespace

