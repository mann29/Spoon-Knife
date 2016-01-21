<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:sup="http://www.mitchell.com/schemas/appraisalassignment/supplementrequestemail"
xmlns:resourceConversion="xalan://com.mitchell.services.business.partialloss.appraisalassignment.util.InternationlizeDataUtil">



<!-- overrides built-in templates -->
<xsl:template match="text()|@*"/>

<xsl:template name="css">
<style type="text/css">
body{
	background-color: White;
}
.section{
	font-size: 18px;
	font-family: arial, sans-serif;
	font-weight: 800;
	border-bottom: 2px solid black;
	padding: 5px, 7px, 2px, 7px;
	page-break-inside: avoid;
	background-color: #FFFFFF;
	width: 100%;
}

.section-bottom{
	 padding-bottom: 25px;
}

section-padding{
	 padding-top: 25px;
	 padding-bottom: 25px;
}

.section-content{
	padding: 0px, 7px, 24px, 7px;
	page-break-inside: avoid;
	background-color: #FFFFFF;
}

.subsection{
	font-size: 16px;
	font-family: arial, sans-serif;
	font-weight: 800;
	page-break-inside: avoid;
	background-color: #FFFFFF;
	padding: 5px, 7px, 15px, 7px;
}

.subsection-title{
	font-size: 16px;
	font-family: arial, sans-serif;
	font-weight: 800;
	padding: 5px, 0px, 2px, 0px;
	page-break-inside: avoid;
	background-color: #FFFFFF;
	margin-bottom: -20px;
	margin-top: 20px;
	padding-left: 7px;
}

.subsection-content{
	padding: 0px, 0px, 12px, 0px;
	page-break-inside: avoid;
	background-color: #FFFFFF;
}

.data{
	font-size: 12px;
	font-family: arial, sans-serif;
	vertical-align: top;
	padding: 2px, 0px, 2px, 0px;
	background-color: #FFFFFF;
}

.data-right{
	font-size: 12px;
	font-family: arial, sans-serif;
	vertical-align: top;
	padding: 2px, 0px, 2px, 0px;
	background-color: #FFFFFF;
	text-align: right;
}

.data-list{
	font-size: 12px;
	font-family: arial, sans-serif;
	vertical-align: top;
	padding: 0px;
	margin: 0px;
	background-color: #FFFFFF;
}

.paragraph{
	font-size: 12px;
	font-family: arial, sans-serif;
	vertical-align: top;
	background-color: #FFFFFF;
	padding: 2px, 7px, 2px, 7px;
	page-break-inside: avoid;
}

.data-field{
	font-size: 12px;
	font-weight: 800;
	text-align: right;
	padding: 2px, 5px, 2px, 0px;
	font-family: arial, sans-serif;
	vertical-align: top;
	background-color: #FFFFFF;
	white-space: nowrap;
}

.data-table-title{
	font-size: 12px;
	font-family: arial, sans-serif;
	vertical-align: top;
	padding: 2px, 0px, 2px, 0px;
	background-color: #FFFFFF;
	margin-bottom: -20px;
	margin-top: 20px;
	padding-left: 7px;
}

.data-table{
	border-top: 1px solid black;
	page-break-inside: avoid;
	width=100%;
}

.data-table-head-first{
	font-size: 12px;
	height: 20 px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 7px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
}

.data-table-head{
	font-size: 12px;
	height: 20 px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
}

.data-table-head-right{
	font-size: 12px;
	height: 20 px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 5px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
	text-align: right;
}

.data-table-head-end{
	font-size: 12px;
	height: 20 px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 7px, 2px, 2px;
	border-bottom: 1px solid black;
}

.data-table-head-end-right{
	font-size: 12px;
	height: 20 px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 7px, 2px, 2px;
	border-bottom: 1px solid black;
	text-align: right;
}

.data-table-row-first{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 7px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
	background-color: #FFFFFF;
}

.data-table-row-first-right{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 5px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
	text-align: right;
	background-color: #FFFFFF;
}

.data-table-row{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
	background-color: #FFFFFF;
}

.data-table-row-right{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 5px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
	text-align: right;
	background-color: #FFFFFF;
}

.data-table-row-end{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 7px, 2px, 2px;
	border-bottom: 1px solid black;
	background-color: #FFFFFF;
}

.data-table-row-end-right{
	font-size: 12px;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 7px, 2px, 2px;
	text-align: right;
	border-bottom: 1px solid black;
	background-color: #FFFFFF;
}

.col-bg-large{
	width:100%;
	border-bottom: 40px solid #e5e5e5;
	margin-bottom: -40px;
}

.col-bg{
	width:100%;
	border-bottom: 20px solid #e5e5e5;
	margin-bottom: -20px;
}
</style>   
</xsl:template>

<xsl:template name="body">
<body>
<table border="0" cellpadding="0" cellspacing="0" width="660">
<xsl:call-template name="claimheader"/>
<xsl:call-template name="adminvehicle"/>
<xsl:call-template name="deskreviewinfo"/>
<!-- fixing bug:290010<xsl:call-template name="instruction"/>  -->
<xsl:call-template name="conf"/>
</table>
</body>
</xsl:template>

<xsl:template name="convertPriceToFrenchFormat">
    <xsl:param name="value"/>  
	<xsl:variable name="replaceDotValue" select="translate($value, '.', ',')"/>		
	
	<xsl:choose>
	  <xsl:when test="contains($replaceDotValue, '$')">
		<xsl:variable name="lValue" select="translate($replaceDotValue, '$', '')"/>				
		<xsl:value-of select="concat($lValue, '$')"/>
	  </xsl:when>
	  <xsl:otherwise>
		<xsl:value-of select="$replaceDotValue"/>
	  </xsl:otherwise>
	</xsl:choose>			

</xsl:template>

<xsl:template name="deskreviewinfo">
<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0 or string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
<tr>
	<td class="section">
	<!--Section-->
	Desk Review Information
	</td>
</tr>

	<xsl:if test="string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
	<tr>
		<td class="subsection">
		<!--Subsection-->
		Desk Review Comments
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<pre>
		<span  class="data">
		<xsl:value-of select="/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments"/>
		</span>
		</pre>
		</td>
	</tr>
    </xsl:if>
    <xsl:call-template name="lineitem"/>
 </xsl:if>   
</xsl:template>

<xsl:template name="outputline" match="/sup:SupplementRequest/sup:LineItemsInfo">
  <xsl:for-each select="//sup:LineItemInfo">
  <xsl:sort select="sup:LineItem/sup:LineNum" data-type="number"/>
  <xsl:if test="string-length(sup:LineItem/sup:Operation) > 0">
       <xsl:choose>
			<xsl:when test="sup:LineItem/@StrikeThru = true()">
			   <xsl:text disable-output-escaping="yes">&lt;tr style="text-decoration: line-through;"></xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text disable-output-escaping="yes">&lt;tr></xsl:text>
			</xsl:otherwise>
    	</xsl:choose>

				<td class="data-table-row-first"><xsl:call-template name="outputnumber">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:LineNum"/>
				</xsl:call-template></td>
				<td class="data-table-row">
					<xsl:variable name="displayvalue" select="sup:LineItem/sup:Operation"/>
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="resourceConversion:getTranslatedValue($displayvalue,'en')"/>
				</xsl:call-template>
				</td>
				<td class="data-table-row"><xsl:call-template name="outputnumber">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:LineDesc"/>
				</xsl:call-template></td>
				<td class="data-table-row"><xsl:call-template name="outputnumber">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartType"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumber">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartPrice"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumber">
				<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborHours"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumber">
				<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborCost"/>
				</xsl:call-template></td>
				<td class="data-table-row-end-right"><xsl:call-template name="outputnumber">
				<xsl:with-param name="nm" select="sup:LineItem/sup:Amount"/>
				</xsl:call-template></td>
			<xsl:text disable-output-escaping="yes">&lt;tr></xsl:text>
			<xsl:if test="count(sup:Annotation) > 0">
			<xsl:for-each select="sup:Annotation">
			<tr>
				<td class="data-table-row-first"><br/></td>
				<td class="data-table-row"><b><xsl:call-template name="outputnumber">
				<xsl:with-param name="nm" select="sup:LineDesc"/>
				</xsl:call-template></b></td>
				<td class="data-table-row-end" colspan="6"><xsl:call-template name="outputnumber">
				<xsl:with-param name="nm" select="sup:Operation"/>
				</xsl:call-template></td>
			</tr>
			</xsl:for-each>
			</xsl:if>
			<xsl:if test="count(sup:Compliance/sup:Operation) > 0">
			<tr>
				<td class="data-table-row-first"><br/></td>
				<td class="data-table-row"><xsl:value-of select="sup:Compliance/sup:Operation"/></td>
				<td class="data-table-row-end" colspan="6"><xsl:value-of select="sup:Compliance/sup:LineDesc"/></td>
			</tr>
			</xsl:if>
			</xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="outputnumber">
<xsl:param name="nm"/>
<xsl:choose>
	<xsl:when test="string-length($nm) > 0">
	<xsl:value-of select="$nm"/>
	</xsl:when>
	<xsl:otherwise>
	&#160;
	</xsl:otherwise>
</xsl:choose>
</xsl:template>

<xsl:template name="lineitem">
	<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0">
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<div class="col-bg-large"><span class="subsection">Line Item Annotations</span></div>
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table3">
			<tr>
				<td class="data-table-head-first" rowspan="2">Line</td>
				<td class="data-table-head" rowspan="2">Operation</td>
				<td class="data-table-head" rowspan="2">Description</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Parts</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Labor</td>
				<td class="data-table-head-end" rowspan="2">Amount</td>
			</tr>
			<tr>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Type</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Price</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Hours</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Cost</td>
			</tr>
			<xsl:call-template name="outputline"/>
			</table>
		</td>
	</tr>
	</xsl:if>
</xsl:template>

<xsl:template name="url">
<xsl:variable name="vlink">
<xsl:choose>
<xsl:when test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:TaskId) > 0">
<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:URLLink"/>?coCd=<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:CoCd"/>&amp;taskId=<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:TaskId"/>
</xsl:when>
<xsl:otherwise>
<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:URLLink"/>
</xsl:otherwise>
</xsl:choose>
</xsl:variable>
<a href="{$vlink}">Link</a>
</xsl:template>

<xsl:template name="claimheader">
<!--Section Supplement Request Info -->
<tr>
	<td class="section">
	<!--Section-->
	Supplement Request for Claim # <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber"/>
	</td>
</tr>
<tr> 
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) > 0">
To see the full assignment, please click the following link and enter the Vehicle Identification Number associated with this claim.<br/>
			<xsl:call-template name="url"/><br/>
			</xsl:if>
</tr>
</xsl:template>

<xsl:template name="date">
<xsl:param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/>
<xsl:value-of select="substring($datestr,1,4)"/>-<xsl:value-of select="substring($datestr,6,2)"/>-<xsl:value-of select="substring($datestr,9,2)"/>
</xsl:template>

<xsl:template name="adminvehicle">
<tr>
	<td class="section-content">
	<!--Section Content-->
	<table border="0" cellpadding="0" cellspacing="0" width="100%" ID="Table1">
	<tr>
		<td class="subsection">
		<!--Subsection-->
		Admin Information
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table2">
		<tr>
			<td width="25%" class="data-field">Claim #:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber"/></td>
			<td width="25%" class="data-field">Estimate ID:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClientEstimateId"/></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Sent:</td>
			<td width="25%" class="data"><xsl:call-template name="date">
			<!--xsl:with-param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/-->
			</xsl:call-template></td>
			<td width="25%" class="data-field">Insurance Company:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:InsuranceCo"/></td>
		</tr>
		<tr>
			<td width="25%" class="data-field"><br/></td>
			<td width="25%" class="data">
			<br/>
			</td>
			<td width="25%" class="data-field"><br/></td>
			<td width="25%" class="data">
			<br/>
			</td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Recipient:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Name"/>
			</td>
			<td width="25%" class="data-field">Sender:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Name"/>
			</td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Phone:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Phone"/></td>
			<td width="25%" class="data-field">Phone:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Phone"/></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">E-mail:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Email"/></td>
			<td width="25%" class="data-field">E-mail:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Email"/></td>
		</tr>

		<tr>
			<td width="25%" class="data-field">Fax:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Fax"/></td>
			<td width="25%" class="data-field">Fax:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Fax"/></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Address:</td>
			<td width="25%" class="data">
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1) > 0"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1"/><br/></xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2) > 0">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2"/><br/>
			</xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City) > 0">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City"/>
			</xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State) > 0 or string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip) > 0">
			, <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State"/> <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip"/><br/>
			</xsl:if>
			</td>
			<td width="25%" class="data-field">&#160;</td>
			<td width="25%" class="data">&#160;</td>
		</tr>

		</table>
		</td>
	</tr>
	<tr>
		<td class="subsection">
		<!--Subsection-->
		Vehicle Information
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table6">
		<tr>
			<td width="25%" class="data-field">Year:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Year"/></td>
			<td width="25%" class="data-field"> Vehicle Owner Name:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:OwnerName"/></td>

		</tr>
		<tr>
			<td width="25%" class="data-field">Make:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Make"/></td>
			<td width="25%" class="data-field">Color:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Color"/></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Model:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Model"/></td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</tr>

</xsl:template>

<xsl:template name="instruction">
<!--Section Instructions -->
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) &lt;= 0">
<tr>
	<td class="section">
	<!--Section-->

	Supplement Instructions for Estimates

	</td>
</tr>

<tr>
	<td class="subsection-content">
	<!--Subsection Content-->


	<table>
	<tr><td class="data-right">1.&#160;</td><td class="data">Open eClaim Manager</td></tr>
	<tr><td class="data-right">2.&#160;</td><td class="data">Locate and open the Claim Folder in eClaim Manager</td></tr>
	<tr><td class="data-right">3.&#160;</td><td class="data">Do one of the following:</td></tr>
	<tr><td class="data-right">&#160;</td><td class="data">If you are using UltraMate:</td></tr>
	<tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3a.&#160;Launch UltraMate from within eClaim Manager.</td></tr>
	<tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3b.&#160;Update and calculate the estimate, and then click the eCM button to committ the estimate and return to eClaim Manager.</td></tr>
    <tr><td class="data-right">&#160;</td><td class="data">If you are not using UltraMate:</td></tr>
	<tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3a.&#160;Open your estimating software.</td></tr>
	<tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3b.&#160;Update and then Commit/Lock the estimate.</td></tr>
    <tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3c.&#160;Export the EMS for the estimate from your estimating software.</td></tr>
    <tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3d.&#160;Create an Estimate Print Image in PDF Format.</td></tr>	
    <tr><td class="data-right">&#160;&#160;&#160;</td><td class="data">3e.&#160;In eClaim Manager, import the supplemented EMS and PDF.</td></tr>	    
    <tr><td class="data-right">4.&#160;</td><td class="data">In eClaim Manager, mark the folder as Ready to Send, then click the lightning bolt button.</td></tr>    
	</table>
	</td>
</tr>
</xsl:if>
</xsl:template>

<xsl:template name="conf">
<tr>
	<td class="section">
	<!--Section-->
	Confidentiality Statement
	</td>
</tr>
<tr>
	<td class="subsection-content">
	<!--Subsection Content-->
	<span class="data" style="text-align: center;">
		The information contained in this message is CONFIDENTIAL and is for the intended addressee only. <br/>
		Any unauthorized use, dissemination of the information, or copying of this message is prohibited. <br/>
		If you are not the intended addressee, please notify the sender immediately and delete this message.<br/>
	</span>
	</td>
</tr>
</xsl:template>

<!-- fr body started -->


<xsl:template name="bodyFr">
<body>
<table border="0" cellpadding="0" cellspacing="0" width="660">
<xsl:call-template name="claimheaderFr"/>
<xsl:call-template name="adminvehicleFr"/>
<xsl:call-template name="deskreviewinfoFr"/>
<!-- fixing bug:290010<xsl:call-template name="instructionFr"/>  -->
<xsl:call-template name="confFr"/>
</table>
</body>
</xsl:template>

<xsl:template name="deskreviewinfoFr">
<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0 or string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
<tr>
	<td class="section">
	<!--Section--> Information sur la révision du dossier
	</td>
</tr>

	<xsl:if test="string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
	<tr>
		<td class="subsection">
		<!--Subsection--> Commentaires sur la révision du dossier
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<pre>
		<span class="data"> <xsl:value-of select="/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments" /> </span>
		</pre>
		</td>
	</tr>
    </xsl:if>
    <xsl:call-template name="lineitemFr"/>
 </xsl:if>   
</xsl:template>

<xsl:template name="outputlineFr" match="/sup:SupplementRequest/sup:LineItemsInfo">
  <xsl:for-each select="//sup:LineItemInfo">
  <xsl:sort select="sup:LineItem/sup:LineNum" data-type="number"/>
  <xsl:if test="string-length(sup:LineItem/sup:Operation) > 0">
       <xsl:choose>
			<xsl:when test="sup:LineItem/@StrikeThru = true()">
			   <xsl:text disable-output-escaping="yes">&lt;tr style="text-decoration: line-through;"></xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text disable-output-escaping="yes">&lt;tr></xsl:text>
			</xsl:otherwise>
    	</xsl:choose>

				<td class="data-table-row-first"><xsl:call-template name="outputnumberFr">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:LineNum"/>
				</xsl:call-template></td>
				<td class="data-table-row"><xsl:variable name="displayvalue" select="sup:LineItem/sup:Operation"/>
				<xsl:call-template name="outputnumberFr">
					<xsl:with-param name="nm" select="resourceConversion:getTranslatedValue($displayvalue,'fr-ca_en-us')"/>
				</xsl:call-template></td>
				<td class="data-table-row"><xsl:call-template name="outputnumberFr">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:LineDesc"/>
				</xsl:call-template></td>
				<td class="data-table-row"><xsl:call-template name="outputnumberFr">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartType"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumberFr">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartPrice"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumberFr">
				<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborHours"/>
				</xsl:call-template></td>
				<td class="data-table-row-right"><xsl:call-template name="outputnumberFr">
				<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborCost"/>
				</xsl:call-template></td>
				<td class="data-table-row-end-right"><xsl:call-template name="outputnumberFr">
				<xsl:with-param name="nm" select="sup:LineItem/sup:Amount"/>
				</xsl:call-template></td>
			<xsl:text disable-output-escaping="yes">&lt;tr></xsl:text>
			<xsl:if test="count(sup:Annotation) > 0">
			<xsl:for-each select="sup:Annotation">
			<tr>
				<td class="data-table-row-first"><br/></td>
				<td class="data-table-row"><b><xsl:call-template name="outputnumberFr">
				<xsl:with-param name="nm" select="sup:LineDesc"/>
				</xsl:call-template></b></td>
				<td class="data-table-row-end" colspan="6"><xsl:call-template name="outputnumberFr">
				<xsl:with-param name="nm" select="sup:Operation"/>
				</xsl:call-template></td>
			</tr>
			</xsl:for-each>
			</xsl:if>
			<xsl:if test="count(sup:Compliance/sup:Operation) > 0">
			<tr>
				<td class="data-table-row-first"><br/></td>
				<td class="data-table-row"><xsl:value-of select="sup:Compliance/sup:Operation" /></td>
				<td class="data-table-row-end" colspan="6"><xsl:value-of select="sup:Compliance/sup:LineDesc" /></td>
			</tr>
			</xsl:if>
			</xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="outputnumberFr">
<xsl:param name="nm"/>
<xsl:choose>
	<xsl:when test="string-length($nm) > 0">
		<xsl:call-template name="convertPriceToFrenchFormat"><xsl:with-param name="value" select="$nm"/></xsl:call-template>
	</xsl:when>
	<xsl:otherwise>
	 
	</xsl:otherwise>
</xsl:choose>
</xsl:template>

<xsl:template name="lineitemFr">
	<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0">
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<div class="col-bg-large"><span class="subsection">Annotations des postes</span></div>
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table3">
			<tr>
				<td class="data-table-head-first" rowspan="2">Ligne</td>
				<td class="data-table-head" rowspan="2">Opération</td>
				<td class="data-table-head" rowspan="2">Description</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Pièces</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Main-d'œuvre</td>
				<td class="data-table-head-end" rowspan="2">Montant</td>
			</tr>
			<tr>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Type</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Prix</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Heures</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Coût</td>
			</tr>
			<xsl:call-template name="outputlineFr"/>
			</table>
		</td>
	</tr>
	</xsl:if>
</xsl:template>

<xsl:template name="urlFr">
<xsl:variable name="vlink">
<xsl:choose>
<xsl:when test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:TaskId) > 0">
<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:URLLink"/>?coCd=<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:CoCd"/>&amp;taskId=<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:TaskId"/>
</xsl:when>
<xsl:otherwise>
<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:URLLink"/>
</xsl:otherwise>
</xsl:choose>
</xsl:variable>
<a href="{$vlink}">Lien</a>
</xsl:template>

<xsl:template name="claimheaderFr">
<!--Section Supplement Request Info -->
<tr>
	<td class="section">
	<!--Section--> Demande de supplément pour le numéro de réclamation <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" />
	</td>
</tr>
<tr> 
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) > 0">
Pour voir l'assignation dans son ensemble, veuillez cliquer sur le lien suivant et entrer le numéro d'identification du véhicule associé à cette réclamation.<br/>
			<xsl:call-template name="urlFr"/><br/>
			</xsl:if>
</tr>
</xsl:template>

<xsl:template name="dateFr">
<xsl:param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/>
<xsl:value-of select="substring($datestr,1,4)" />-<xsl:value-of select="substring($datestr,6,2)" />-<xsl:value-of select="substring($datestr,9,2)" />
</xsl:template>

<xsl:template name="adminvehicleFr">
<tr>
	<td class="section-content">
	<!--Section Content-->
	<table border="0" cellpadding="0" cellspacing="0" width="100%" ID="Table1">
	<tr>
		<td class="subsection">
		<!--Subsection--> Info admin.
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table2">
		<tr>
			<td width="25%" class="data-field">N° de réclamation&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" /></td>
			<td width="25%" class="data-field">Identifiant d'estimation&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClientEstimateId" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Envoyé&#160;:</td>
			<td width="25%" class="data"><xsl:call-template name="dateFr">
			<!--xsl:with-param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/-->
			</xsl:call-template></td>
			<td width="25%" class="data-field">Assureur&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:InsuranceCo" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field"><br/></td>
			<td width="25%" class="data">
			<br/>
			</td>
			<td width="25%" class="data-field"><br/></td>
			<td width="25%" class="data">
			<br/>
			</td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Destinataire&#160;:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Name" />
			</td>
			<td width="25%" class="data-field">Expéditeur&#160;:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Name" />
			</td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Téléphone&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Phone" /></td>
			<td width="25%" class="data-field">Téléphone&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Phone" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Courriel&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Email" /></td>
			<td width="25%" class="data-field">Courriel&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Email" /></td>
		</tr>

		<tr>
			<td width="25%" class="data-field">Télécopieur&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Fax" /></td>
			<td width="25%" class="data-field">Télécopieur&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Fax" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Adresse&#160;:</td>
			<td width="25%" class="data">
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1) > 0"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1" /><br/></xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2) > 0">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2" /><br/>
			</xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City) > 0">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City" />
			</xsl:if>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State) > 0 or string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip) > 0">
			, <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State" /> <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip" /><br/>
			</xsl:if>
			</td>
			<td width="25%" class="data-field"> </td>
			<td width="25%" class="data"> </td>
		</tr>

		</table>
		</td>
	</tr>
	<tr>
		<td class="subsection">
		<!--Subsection--> Information sur le véhicule
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table6">
		<tr>
			<td width="25%" class="data-field">Année&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Year" /></td>
			<td width="25%" class="data-field"> Nom du propriétaire du véhicule&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:OwnerName" /></td>

		</tr>
		<tr>
			<td width="25%" class="data-field">Marque&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Make" /></td>
			<td width="25%" class="data-field">Couleur&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Color" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Modèle&#160;:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Model" /></td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</tr>

</xsl:template>

<xsl:template name="instructionFr">
<!--Section Instructions -->
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) &lt;= 0">
<tr>
	<td class="section">
	<!--Section--> Instructions du supplément pour les estimations

	</td>
</tr>

<tr>
	<td class="subsection-content">
	<!--Subsection Content-->


	<table>
	<tr><td class="data-right">1.&#160;</td><td class="data">Ouvrir le gestionnaire eClaim</td></tr>
	<tr><td class="data-right">2.&#160;</td><td class="data">Trouvez le dossier Réclamation dans le gestionnaire eClaim et ouvrez-le</td></tr>
	<tr><td class="data-right">3.&#160;</td><td class="data">Faites l'une des opérations suivantes&#160;:</td></tr>
	<tr><td class="data-right"> </td><td class="data">Si vous utilisez Ultramate&#160;:</td></tr>
	<tr><td class="data-right">   </td><td class="data">3a.&#160;Lancez Ultramate à partir du gestionnaire eClaim.</td></tr>
	<tr><td class="data-right">   </td><td class="data">3b.&#160;Mettez à jour et calculez l'estimation, puis cliquez sur le bouton eCM pour valider l'estimation et revenir au gestionnaire eClaim.</td></tr>
    <tr><td class="data-right"> </td><td class="data">Si vous n'utilisez pas Ultramate&#160;:</td></tr>
	<tr><td class="data-right">   </td><td class="data">3a.&#160;Ouvrez votre logiciel d'estimation.</td></tr>
	<tr><td class="data-right">   </td><td class="data">3b.&#160;Mettez à jour, puis validez ou verrouillez l'estimation.</td></tr>
    <tr><td class="data-right">   </td><td class="data">3c.&#160;Exportez l'EMS de l'estimation à partir de votre logiciel d'estimation.</td></tr>
    <tr><td class="data-right">   </td><td class="data">3d.&#160;Créez une image d'impression de l'estimation au format PDF.</td></tr>	
    <tr><td class="data-right">   </td><td class="data">3e.&#160;Dans le gestionnaire eClaim, importez le supplément EMS et le fichier PDF.</td></tr>	    
    <tr><td class="data-right">4.&#160;</td><td class="data">Dans le gestionnaire eClaim, marquez le dossier comme étant Prêt à l'envoi, puis cliquez sur le bouton éclair.</td></tr>    
	</table>
	</td>
</tr>
</xsl:if>
</xsl:template>

<xsl:template name="confFr">
<tr>
	<td class="section">
	<!--Section--> Déclaration de confidentialité
	</td>
</tr>
<tr>
	<td class="subsection-content">
	<!--Subsection Content-->
	<span class="data" style="text-align: center;">Les renseignements contenus dans ce message sont CONFIDENTIELS et s'adressent uniquement au destinataire. <br/> Toute utilisation ou diffusion non autorisée des renseignements ou toute copie de ce message est interdite. <br/> Si vous n'êtes pas le destinataire, veuillez aviser l'expéditeur immédiatement et supprimer ce message.<br/>
	</span>
	</td>
</tr>
</xsl:template>

<xsl:template match="/">
<html>
			<head>
				<xsl:call-template name="css"/>
			</head>
			<body>
			
			<table width="95%" align="center">
				
				<tr>
					<td align="right">
						<a name="French"></a>										  
						<a href="#English" title="Click to view mail in English" style="font-family:verdana; font-size:80%;">View email in English</a>
					</td>
				</tr>

				
				
				<tr>
					<td>
						<xsl:call-template name="bodyFr"/>
					</td>
				</tr>
				
				<tr> 
					<hr style="color:#04B4AE; height:1px;" />
					<td align="right">
						<a name="English"></a>
						<a href="#French" title="Click to view mail in French" style="font-family:verdana; font-size:80%;">Visualiser le courriel en Français</a>
					</td> 
				</tr>	
							
				<tr>
					<td>
						<xsl:call-template name="body"/>
					</td> 
				</tr>
			</table>
			</body>
		</html>
</xsl:template>

</xsl:transform>