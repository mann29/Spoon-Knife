<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy? -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:apd="http://www.mitchell.com/schemas/apddelivery" xmlns:typ="http://www.mitchell.com/common/types">
	<xsl:template match="/">
		<html>
			<head>
				<style>

					.MainHeader
					{
					  font-size:38px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: Verdana;
					}
					
					.Header
					{
					  font-size:12px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: Verdana;
					}
					
					.DataHeading
					{
					  font-size:12px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: Verdana;
					}
					
					.Data
					{
					  font-size:12px;
					  font-color:#000000;
					  font-family: Verdana;
					}
					.AdText
					{
					  font-size:11px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: Verdana;
					}

					.Disclaimer
					{
					  font-size:11px;
					  font-color:#000000;
					  font-family: Verdana;
					}

			</style>
			</head>
			<body>
				<div class="MainHeader">
					<xsl:value-of select="/apd:APDDeliveryContext/apd:APDBroadcastMessage/apd:APDCommonInfo/apd:SourceUserInfo/apd:UserInfo/typ:FirstName"/>&#xa0;
					<xsl:value-of select="/apd:APDDeliveryContext/apd:APDBroadcastMessage/apd:APDCommonInfo/apd:SourceUserInfo/apd:UserInfo/typ:LastName"/>&#xa0; from <xsl:value-of select="/apd:APDDeliveryContext/apd:APDBroadcastMessage/apd:APDCommonInfo/apd:SourceUserInfo/apd:UserInfo/typ:UserHier/typ:HierNode/@Name"/> has sent you the following broadcast alert message from WorkCenter: 
				</div>

				<hr size="2" width="100%" style="color:black" align="center"/>

				<div class="Data">
					<br/>
					<xsl:value-of select="/apd:APDDeliveryContext/apd:APDBroadcastMessage/apd:MessageContent"/>
				</div>

				<hr size="2" width="100%" style="color:black" align="center"/>		
				
				<!-- ************************  DISCLAIMER/COPYRIGHT   **********************************-->
				
				<div class="AdText">
					<br/>
					Do not reply to this email as your response would be delivered to an unmonitored account. Please contact the sender if you have questions about this message.
				</div>
				<div class="Disclaimer">
					<br/>
					This email is for use by the intended recipient(s) only and may contain information that is confidential, proprietary, copyrighted, and/or legally privileged. Any unauthorized disclosure, distribution, or use of this information is prohibited.

					<p align="center">Copyright &#169; 2010 &#xa0; &#xa0; &#xa0; <a href="http://www.mitchell.com">Mitchell International, Inc.</a>  &#xa0; &#xa0; &#xa0; All rights reserved.</p>
				</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
