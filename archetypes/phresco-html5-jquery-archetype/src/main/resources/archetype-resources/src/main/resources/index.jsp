<%@ page import="java.io.InputStream" %>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File" %>
<%@ page import="java.util.Properties" %>			
<%@ page import="com.photon.phresco.configuration.ConfigReader" %>
<%@ page import="com.photon.phresco.configuration.Configuration" %>
<!DOCTYPE html> 
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:eshop="http://phresco.com/">
    <head>
        <title>E-Shop | Phresco</title>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta name="viewport" content="user-scalable=0, width=device-width, minimum-scale=1.0, maximum-scale=1.0">
		
        <!-- Linking styles -->
        <link rel="stylesheet" href="css/eshop/bootstrap.css" type="text/css" />
        <link rel="stylesheet" href="css/eshop/reset.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/eshop/phresco_style.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/eshop/mediaquery.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/eshop/mediaquery_ipad.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/eshop/nivo-slider.css" type="text/css" media="screen">
        <link rel="stylesheet" href="css/eshop/jquery.loadmask.css" type="text/css" />
        
        <script type="text/javascript" src="js/org/codehaus/mojo/almond/0.0.2-alpha-1/almond-0.0.2-alpha-1.js"></script>
<script type="text/javascript" src="js/jslibraries/files/jslib_jquery-amd/1.7.1-alpha-1/jslib_jquery-amd-1.7.1-alpha-1.js"></script>
<script type="text/javascript" src="js/eshop/widgets/sjcl.js"></script>
<script type="text/javascript" src="js/framework/Listener.js"></script>
<script type="text/javascript" src="js/framework/Clazz.js"></script>
<script type="text/javascript" src="js/framework/Base.js"></script>
<script type="text/javascript" src="js/framework/Widget.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Products.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ProductsBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Register.js"></script>
<script type="text/javascript" src="js/eshop/widgets/RegisterBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Contactus.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ContactusBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Search.js"></script>
<script type="text/javascript" src="js/eshop/widgets/SearchBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/RegisterSuccess.js"></script>
<script type="text/javascript" src="js/eshop/widgets/RegisterSuccessBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderSuccess.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderSuccessBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Category.js"></script>
<script type="text/javascript" src="js/eshop/widgets/CategoryBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Login.js"></script>
<script type="text/javascript" src="js/eshop/widgets/LoginBootstrap.js"></script>
<script type="text/javascript" src="js/jslibraries/files/jslib_jsonpath-amd/0.8.0/jslib_jsonpath-amd-0.8.0.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Phresco.js"></script>
<script type="text/javascript" src="js/eshop/widgets/MyCart.js"></script>
<script type="text/javascript" src="js/eshop/widgets/MyCartBootstrap.js"></script>
<script type="text/javascript" src="js/jslibraries/files/jslib_xml2json-amd/1.1/jslib_xml2json-amd-1.1.js"></script>
<script type="text/javascript" src="js/jslibraries/files/jslib_jquery-ui-amd/1.8.16-alpha-1/jslib_jquery-ui-amd-1.8.16-alpha-1.js"></script>
<script type="text/javascript" src="js/framework/Encrypt_Decrypt.js"></script>


	
		<%
			String currentEnv = System.getProperty("SERVER_ENVIRONMENT");
			String path = getServletContext().getRealPath("/WEB-INF/resources/phresco-env-config.xml");
			File file = new File(path);
			ConfigReader reader = new ConfigReader(file);
			String configJson = reader.getConfigAsJSON(currentEnv, "WebService");
		%>
		
		<script type="text/javascript">
			var configJsonData = $.parseJSON('<%= configJson%>'); 
		</script>
		
<script type="text/javascript" src="js/eshop/widgets/EShopAPI.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderFormView.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderFormViewBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ProductDetails.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ProductDetailsBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderForm.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderFormBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Navigation.js"></script>
<script type="text/javascript" src="js/eshop/widgets/NavigationBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderHistory.js"></script>
<script type="text/javascript" src="js/eshop/widgets/OrderHistoryBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Aboutus.js"></script>
<script type="text/javascript" src="js/eshop/widgets/AboutusBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/NewProducts.js"></script>
<script type="text/javascript" src="js/eshop/widgets/NewProductsBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ShoppingCart.js"></script>
<script type="text/javascript" src="js/eshop/widgets/ShoppingCartBootstrap.js"></script>
<script type="text/javascript" src="js/eshop/widgets/Init.js"></script>

    </head>

    <body>
        <div class="wel_come"></div>
        <div id="popup" class="loginModal" style="display:none;z-index:60000;"></div>
        <div id="registerpopup" class="loginModal" style="display:none;z-index:60000;"></div>
        <div class="container">
            <header>
                <!-- Defining the header section of the page -->
                <eshop:navigation-widget api=api></eshop:navigation-widget>

                <!-- Defining the top head element -->
                <eshop:search-widget api=api></eshop:search-widget>

                <!-- Defining the sub menu -->
                <eshop:category-widget api=api></eshop:category-widget>
            </header>

            <div id="newContent">
                <eshop:register-widget api=api></eshop:register-widget>
                <eshop:login-widget api=api></eshop:login-widget>
                <eshop:aboutus-widget api=api></eshop:aboutus-widget>
                <eshop:contactus-widget api=api></eshop:contactus-widget>
                <eshop:loginsuccess-widget api=api></eshop:loginsuccess-widget>
                <eshop:registersuccess-widget api=api></eshop:registersuccess-widget>
                <eshop:orderhistory-widget api=api></eshop:orderhistory-widget>
            </div>   

         <div id="slider"><!-- Defining the main content section -->
            <!-- Promo slider -->
                <section id="slider-wrapper">
                    <div id="slider" class="nivoSlider">
                       <img src="images/eshop/promo1.jpg" alt="" title="#htmlcaption-1">
                        <img style="display: none;" src="images/eshop/promo2.jpg" alt="" title="#htmlcaption-2">
                    </div>
                </section>
            </div>

            <div id="wrapper">
            </div>

            <div id="main"><!-- Defining submain content section -->
                <section id="content"><!-- Defining the content section #2 -->
                    <div id="left">
                        <eshop:products-widget api=api></eshop:products-widget>
                        <eshop:productdetails-widget api=api></eshop:productdetails-widget>
                        <eshop:shoppingcart-widget api=api></eshop:shoppingcart-widget> 
                        <eshop:orderform-widget api=api></eshop:orderform-widget>
                        <eshop:orderformview-widget api=api></eshop:orderformview-widget>
                        <eshop:ordersuccess-widget api=api></eshop:ordersuccess-widget>
                    </div>
                    <div id="cashbanner">
                        <eshop:mycart-widget api=api></eshop:mycart-widget>
                    </div>
                    <aside>
                        <div id="right">
                            <eshop:newproducts-widget api=api></eshop:newproducts-widget> 
                        </div>
                    </aside>
                </section>
            </div>

            <footer><!-- Defining the footer section of the page -->
                <div id="privacy">
					<strong> E-Shop Phresco &copy; 2012 </strong>
					<a class="link" href="#">Privacy Policy</a>
					<br>
					<a class="link" href="#">powered by Photon</a>
                </div>
            </footer>
        </div>
    </body>
</html>