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
using System.IO.IsolatedStorage;
using System.Text;
using Microsoft.Silverlight.Testing.Harness;
using System.IO;

namespace UnitTest
{
    public class FileLogProvider : LogProvider
    {
        public const string TESTRESULTFILENAME = @"TestResults\testresults.txt";
        protected override void ProcessRemainder(LogMessage message)
        {
            base.ProcessRemainder(message);
            AppendToFile(message);
        }

        public override void Process(LogMessage logMessage)
        {
            AppendToFile(logMessage);
        }

        private void AppendToFile(LogMessage logMessage)
        {
            UTF8Encoding encoding = new UTF8Encoding();
            var carriageReturnBytes = encoding.GetBytes(new[] { '\r', '\n' });

            using (IsolatedStorageFile store = IsolatedStorageFile.GetUserStoreForApplication())
            {
                if (!store.DirectoryExists("TestResults"))
                {
                    store.CreateDirectory("TestResults");
                }
                using (IsolatedStorageFileStream isoStream =
                    store.OpenFile(TESTRESULTFILENAME, FileMode.Append))
                {
                    var byteArray = encoding.GetBytes(logMessage.Message);
                    isoStream.Write(byteArray, 0, byteArray.Length);
                    isoStream.Write(carriageReturnBytes, 0, carriageReturnBytes.Length);
                }
            }
            
            TransferFile();
        }
        private void TransferFile()
        {
            var isostorefile = IsolatedStorageFile.GetUserStoreForApplication();
            var localTestResultFileName = "EmulatorTestResults-" + "Himanshu" + ".txt";
            isostorefile.MoveFile(@"\TestResults\testresults.txt", localTestResultFileName);
        }
    }
}
