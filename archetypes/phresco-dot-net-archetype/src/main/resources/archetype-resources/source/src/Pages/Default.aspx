<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="Default.aspx.cs" Inherits="SampleWebApp._Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Phresco</title>
    
    <script language="javascript" type="text/javascript">

        function Msg() {
            alert("Welcome to Phresco world");
            return false;
        }
    
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
        <h1 style="padding-left: 50%">
            Phresco Hello world</h1>
    </div>
    <div>
        <asp:Button ID="btnClickMe" runat="server" Text="Click Me" OnClick="btnClickMe_OnClick" OnClientClick="Msg()" />
    </div>
    </form>
</body>
</html>
