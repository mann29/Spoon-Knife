<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy? -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:fn="http://www.w3.org/2005/xpath-functions" xmlns:mxs="http://www.mitchell.com/schemas/apddelivery">
	<xsl:template match="mxs:RepairNotification ">
		<html>
			<head>
				<style>

					.MainHeader
					{
					  font-size:16px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: arial;
					}
					
					.Header
					{
					  font-size:12px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: arial;
					}
					
					.DataHeading
					{
					  font-size:12px;
					  font-color:#000000;
					  font-weight:bold;
					  font-family: arial;
					}
					
					.Data
					{
					  font-size:12px;
					  font-color:#000000;
					  font-family: arial;
					}
					.AdText
					{
					  font-size:12px;
					  font-color:#000000;
					  font-family: arial;
					}

					.Disclaimer
					{
					  font-size:11px;
					  font-color:#000000;
					  font-family: arial;
					}

			</style>
			</head>
			<body>
				<div class="MainHeader">Greetings <xsl:value-of select="//mxs:RepairNotification/mxs:AssignedTo"/>,
			<br/>
			</div>
			<div class="Data">
				<xsl:value-of select="//mxs:RepairNotification/mxs:AssignedBy"/>
				<xsl:choose>
                    	<xsl:when test="/mxs:RepairNotification/mxs:AssignmentType = 'REPAIR_ASSIGNMENT' ">
	                    	<xsl:choose>
                    			<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Create' ">
                                    		 would like to refer the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Update' ">
                                    		 would like to update the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Complete' ">
                                    		 would like to complete the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Cancel' ">
									 would like to cancel the repair for claim 
							</xsl:when>
						</xsl:choose>
                          </xsl:when>
                          <xsl:otherwise>
                          	<xsl:choose>
                    			<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Create'  ">
                                    		 would like to request rework on the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Update' ">
                                    		 would like to update the rework request on the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Complete'  ">
                                    		 would like to complete rework on the repair for claim 
							</xsl:when>
							<xsl:when test="/mxs:RepairNotification/mxs:RequestType= 'Cancel' ">
									 would like to cancel the rework request for claim 
							</xsl:when>
						</xsl:choose>
                         </xsl:otherwise>
                    </xsl:choose>
				<xsl:value-of select="//mxs:RepairNotification/mxs:ClaimNumber"/>
			</div>
						
			<!-- ******************************** SET VEHICLE DETAILS IN THE EMAIL  *****************************************-->
			
			<xsl:if test="count(//mxs:RepairNotification/mxs:Vehicle) > 0">
				<br/>
				<span class="DataHeading">Vehicle:</span>
				<span class="Data" style="padding-left:8px;">
					<xsl:value-of select="//mxs:RepairNotification/mxs:Vehicle/mxs:Year"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Vehicle/mxs:Make"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Vehicle/mxs:Model"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Vehicle/mxs:Submodel"/>
					<xsl:text> </xsl:text>
					(<xsl:value-of select="//mxs:RepairNotification/mxs:Vehicle/mxs:Mileage"/><xsl:text> </xsl:text>miles)
				</span>
			</xsl:if>
			
			<!-- ******************************** SET OWNER DETAILS IN THE EMAIL *****************************************-->
			
			<xsl:if test="count(//mxs:RepairNotification/mxs:Owner) > 0">
				<br/>
				<span class="DataHeading">Owner:</span>
				<span class="Data" style="padding-left:8px;">
					<xsl:value-of select="//mxs:RepairNotification/mxs:Owner/mxs:FirstName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Owner/mxs:LastName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Owner/mxs:PhoneNumber"/>
				</span>
			</xsl:if>
			
			<!-- ******************************** SET INSURED DETAILS IN THE EMAIL *****************************************-->

			<xsl:if test="count(//mxs:RepairNotification/mxs:Insured) > 0">
				<br/>
				<span class="DataHeading">Insured:</span>
				<span class="Data" style="padding-left:8px;">
					<xsl:value-of select="//mxs:RepairNotification/mxs:Insured/mxs:FirstName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Insured/mxs:LastName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Insured/mxs:PhoneNumber"/>
				</span>
			</xsl:if>
			
			<!-- ******************************** SET CLAIMANT DETAILS IN THE EMAIL *****************************************-->
			
			<xsl:if test="count(//mxs:RepairNotification/mxs:Claimant) > 0">
				<br/>
				<span class="DataHeading">Claimant:</span>
				<span class="Data" style="padding-left:8px;">
					<xsl:value-of select="//mxs:RepairNotification/mxs:Claimant/mxs:FirstName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Claimant/mxs:LastName"/>
					<xsl:text> </xsl:text>
					<xsl:value-of select="//mxs:RepairNotification/mxs:Claimant/mxs:PhoneNumber"/>
				</span>
			</xsl:if>
			
			<!-- ******************************** SET ESTIMATE DETAILS IN THE EMAIL *****************************************-->

			<xsl:if test="count(//mxs:RepairNotification/mxs:Estimate) > 0">
				<br/>
				<span class="DataHeading">Current Estimate:</span>
				<span class="Data" style="padding-left:8px;">
					$<xsl:value-of select="//mxs:RepairNotification/mxs:Estimate"/>
				</span>
			</xsl:if>
			<br/>
						
			<!-- ******************************** SET NOTES FROM SENDER IN THE EMAIL *****************************************-->

			<xsl:if test="count(//mxs:RepairNotification/mxs:Notes) > 0">
				<br/>
				<span class="DataHeading">Notes from Sender:</span>
				<br/>
				<span class="Data" style="padding-left:1px;">
					<xsl:value-of select="//mxs:RepairNotification/mxs:Notes"/>
				</span>
				<br/>
			</xsl:if>
			<br/><br/>
			
			<!-- ************************  THIS IS A HOOK FOR THE SHOP TO TRY REPAIR CENTER   **********************************-->
			
			<div class="MainHeader">Turbo-boost your repair referrals! </div>
			<div class="AdText">
			Maximize your referrals with Mitchell RepairCenter, the first collision repair industry_s Shop Workspace solution. With 				RepairCenter, you can coordinate assignments and referrals to and from insurance carriers automatically. See first-hand how 	you 	can 		easily manage your repairs, your customers, and your business _ all from a personalized workspace.<br/>
				<a href="http://repaircenter.mitchell.com/Download.aspx">Test-Drive Mitchell RepairCenter for free!</a>
			</div>
			
			<!-- ************************  DISCLAIMER/COPYRIGHT   **********************************-->
			<br/><br/>
			<div class="Disclaimer">
			This e-mail is intended for use by the intended recipient(s) only and may contain information that is confidential, proprietary, copyrighted, and/or legally privileged. Any unauthorized disclosure, distribution, or use of this information is prohibited.

			<p align="center">Copyright &#169; 2010 &#xa0; &#xa0; &#xa0; <a href="http://www.mitchell.com">Mitchell International, Inc.</a>  &#xa0; &#xa0; &#xa0; All rights reserved.</p>
</div>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>
