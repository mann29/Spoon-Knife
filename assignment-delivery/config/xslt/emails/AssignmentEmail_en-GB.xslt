<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:mxs="http://www.mitchell.com/schemas/assignmentdelivery">
<xsl:output omit-xml-declaration="yes"></xsl:output>
<!--xsl:output method="html" encoding="iso-8859-1" indent="yes"/-->
	<xsl:variable name="assignmentSubTypeDesc" select="/CreationSubject/AssignmentSubTypeCode"/>
	<xsl:variable name="assignmentSubTypeDescBdy" select="/mxs:EmailMessage/mxs:AssignmentSubTypeCode"/>
	<xsl:variable name="assignmentSubTypeDescBdyDRP" select="/mxs:EmailMessageDRP/mxs:AssignmentSubTypeCode"/>
	<xsl:variable name="assignmentSubTypeDescFax" select="/Fax/EmailMessage/AssignmentSubTypeCode"/>
	<xsl:variable name="assignmentSubTypeDescFaxDRP" select="/FaxDRP/EmailMessageDRP/AssignmentSubTypeCode"/>
<!-- overrides built-in templates -->
<xsl:template match="text()|@*"/>

<xsl:template name="css">
<style type="text/css">
a {text-decoration:underline;color:#0072bc}
a:link, a:visited, a:active, a.link:visited; a.link:link {text-decoration:underline;color:#869311}
a:hover, a.link:hover {text-decoration:underline;}
</style>   

</xsl:template>
<xsl:template match="/CreationSubject">
<xsl:choose><xsl:when test="$assignmentSubTypeDesc!=''">New <xsl:value-of select='$assignmentSubTypeDesc'/> Assignment-</xsl:when>
<xsl:otherwise>
New Assignment-</xsl:otherwise></xsl:choose><xsl:value-of select="/CreationSubject/CoName"/><xsl:if test="string-length(/CreationSubject/ClaimNumber) > 0"> for Claim:<xsl:value-of select="/CreationSubject/ClaimNumber"/></xsl:if>
</xsl:template>

<xsl:template name="body">
<body style="background-color:#999; padding:0; margin: 0;">

<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
    <tr>
    <td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
		<td align="center">
			
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
								
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">New Assignment</p>
									<p class="text" style="line-height:22px; color:#333333">You have been assigned the following <xsl:choose><xsl:when test="$assignmentSubTypeDescBdy!=''"><b><xsl:value-of select='$assignmentSubTypeDescBdy'/></b> Appraisal Assignment</xsl:when>
									<xsl:otherwise>Appraisal Assignment</xsl:otherwise></xsl:choose></p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">If you don&#39;t use RC Connect to communicate with this carrier please disregard this email
									<ul>
										<li>EXCEPTION: If you are a Non-Network shop receiving an assignment for this carrier, please save this email. It is the only link you will receive to access the information.</li>
									</ul>
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Administrative/Vehicle Information<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Coverage Type:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:CauseOfLossDesc"/>
											</td>
										</tr>
										
										<xsl:if test="/mxs:EmailMessage/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Contact Name:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:ClaimantName"/>
											</td>
										</tr>

											<xsl:if test="/mxs:EmailMessage/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Home Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:HomePhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Work Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:WorkPhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Cell Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:CellPhone"/>
												</td>
											</tr>
											</xsl:if>

										</xsl:if>

										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Vehicle Make:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:VehMake"/>, Model <xsl:value-of select="/mxs:EmailMessage/mxs:VehModel"/>, Year <xsl:value-of select="/mxs:EmailMessage/mxs:VehYear"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Point of Impact:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:Poi"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Damage Memo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:DamageMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Assignment Memo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:AssignmentMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Date Preference:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessage/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessage/mxs:PreferMonth"/>/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferDay"/>/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferYear"/></xsl:if>
											</td>
										</tr>
									</table>
									
									<br/>
									<hr/>
									<br/>
									<p>
									<xsl:call-template name="url"/><br/>
									Click the link above and enter the Vehicle Identification Number associated with this claim.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									
									<p class="text" style="line-height:22px; color:#333333">Regards,<br/><xsl:value-of select="/mxs:EmailMessage/mxs:CoName"/></p>
									<!-- p class="text" style="line-height:22px; color:#333333"><xsl:value-of select="/EmailMessage/CoName"/></p -->
								</td>        
							</tr>
						
						</table>
					</td>
				</tr>
			</table>
		
		</td>
	  </tr>
	  <tr>
	  <td width="500" valign="middle">
	  </td>
	  </tr>
	</table>

    </td>
    </tr>
</table>

</body>
</xsl:template>

<xsl:template name="bodyDRP">
<body style="background-color:#999; padding:0; margin: 0;">

<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
    <tr>
    <td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
		<td align="center">
			
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
								
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">New Assignment</p>
									<p class="text" style="line-height:22px; color:#333333">You have been assigned the following <xsl:choose><xsl:when test="$assignmentSubTypeDescBdyDRP!=''"><b><xsl:value-of select='$assignmentSubTypeDescBdyDRP'/></b> Appraisal Assignment</xsl:when>
									<xsl:otherwise>Appraisal Assignment</xsl:otherwise></xsl:choose></p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">If you don&#39;t use RC Connect to communicate with this carrier please disregard this email
									<ul>
										<li>EXCEPTION: If you are a Non-Network shop receiving an assignment for this carrier, please save this email. It is the only link you will receive to access the information.</li>
									</ul> 
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Administrative/Vehicle Information<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Coverage Type:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CauseOfLossDesc"/>
											</td>
										</tr>
										<xsl:if test="/mxs:EmailMessageDRP/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Contact Name:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:ClaimantName"/>
											</td>
										</tr>
											<xsl:if test="/mxs:EmailMessageDRP/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Home Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:HomePhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Work Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:WorkPhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Cell Phone:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CellPhone"/>
												</td>
											</tr>
											</xsl:if>

										</xsl:if>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Vehicle Make:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehMake"/>, Model <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehModel"/>, Year <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehYear"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Point of Impact:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:Poi"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Damage Memo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:DamageMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Assignment Memo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:AssignmentMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Date Preference:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessageDRP/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferMonth"/>/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferDay"/>/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferYear"/></xsl:if>
											</td>
										</tr>
									</table>
									
									<br/>
									<hr/>
									<br/>
									<p>
									<xsl:call-template name="urlDRP"/><br/>
									Click the link above and enter the Vehicle Identification Number associated with this claim.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									<p class="text" style="line-height:22px; color:#333333">Regards,<br/><xsl:value-of select="/mxs:EmailMessageDRP/mxs:CoName"/></p>
									<!-- p class="text" style="line-height:22px; color:#333333"><xsl:value-of select="/EmailMessageDRP/CoName"/></p -->
								</td>        
							</tr>
						
						</table>
					</td>
				</tr>
			</table>
		
		</td>
	  </tr>
	  <tr>
	  <td width="500" valign="middle">
	  </td>
	  </tr>
	</table>

    </td>
    </tr>
</table>

</body>
</xsl:template>

<xsl:template name="url">
<xsl:variable name="vlink">
<xsl:value-of select="/mxs:EmailMessage/mxs:URLLink"/>?coCd=<xsl:value-of select="/mxs:EmailMessage/mxs:CoCd"/>&amp;taskId=<xsl:value-of select="/mxs:EmailMessage/mxs:TaskId"/>
</xsl:variable>
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">View full assignment</a>
</xsl:template>

<xsl:template name="urlDRP">
<xsl:variable name="vlink">
<xsl:value-of select="/mxs:EmailMessageDRP/mxs:URLLink"/>
</xsl:variable>
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">View full assignment</a>
</xsl:template>

<xsl:template name="uploadbody">
<body style="background-color:#999; padding:0; margin: 0;">
<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
	<tr>
	<td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
	  
		<td align="center">
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
						
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
							
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Estimate Uploaded Successfully!</p>
									
									
									<p class="text" style="line-height:22px; color:#333333">Your estimate was successfully uploaded to <xsl:value-of select="/UploadSuccess/CoName"/>.</p>
									
									<p class="text" style="line-height:22px; color:#333333"><br />
										</p>
										
								</td>        
							</tr>
							
						</table>
			
					</td>
				</tr>
		  
			</table>
		</td>
	  </tr>
	</table>
	</td>
	</tr>
</table>

</body>
</xsl:template>


<xsl:template name="uploadbodyDRP">
<body style="background-color:#999; padding:0; margin: 0;">
<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
	<tr>
	<td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
		<td align="center">
			
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Estimate Uploaded Successfully!</p>
									
									<p class="text" style="line-height:22px; color:#333333">Your estimate was successfully uploaded to <xsl:value-of select="/UploadSuccessDRP/CoName"/>.</p>
									
									<p class="text" style="line-height:22px; color:#333333"><br />
										</p>
								</td>        
							</tr>
						</table>
			
					</td>
				</tr>
		  
			</table>
		</td>
	  </tr>
	</table>
	</td>
	</tr>
</table>
</body>
</xsl:template>


<xsl:template name="uploadFailbody">
<body style="background-color:#999; padding:0; margin: 0;">
<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
	<tr>
	<td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
		<td align="center">
			
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Estimate Failed Upload</p>
									
									<p class="text" style="line-height:22px; color:#333333">Your estimate could not be processed by <xsl:value-of select="/UploadFail/CoName"/>.</p>
									<xsl:if test="string-length(/UploadFail/Message)>0"> For the following reasons:<br/>
									<p><xsl:value-of select="/UploadFail/Message"/></p></xsl:if>
									<p>Please re-upload your estimate using the link provided in the original Assignment e-mail.</p>
									
									<p class="text" style="line-height:22px; color:#333333"><br/>
										</p>
								</td>        
							 </tr>
							
						</table>
			
					</td>
				</tr>
		  
			</table>
		</td>
	  </tr>
	  <!--
	  <tr>
		 <td width="500" valign="middle">
		 </td>
	  </tr>
	  -->

	</table>
	</td>
	</tr>
</table>
</body>
</xsl:template>

<xsl:template name="uploadFailbodyDRP">
<body style="background-color:#999; padding:0; margin: 0;">
<table width="100%" align="center" cellpadding="20" style="background-color:#999; margin:0; padding:0;">
	<tr>
	<td>

	<table width="724" cellpadding="5" cellspacing="0" style=" font-family: Verdana, Geneva, sans-serif; background-color:#fff;" align="center">
	  <tr>
		<td align="center">
			
			<table width="710" cellspacing="0" cellpadding="0" style="border:1px solid #333;">
				<tr>
					<td width="600" align="center">
						
						<table width="710" border="0" align="center" cellpadding="5" cellspacing="0"  style="background-color:#fff">
							<tr bgcolor="#FFFFFF">
								<td colspan="2"><img src="http://www.mitchell.com/emarketing/images/generic-email-header.jpg" width="700" height="81" alt="Mitchell WorkCenter" /></td>
							</tr>
							<tr>
								<td colspan="2" valign="top" style="text-align:left; padding-left:35px; padding-top:25px; padding-right:35px; padding-bottom:15px; font-family: Verdana, Geneva, sans-serif; color: #333; font-size: 12px;">
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Estimate Failed Upload</p>
									
									<p class="text" style="line-height:22px; color:#333333">Your estimate could not be processed by <xsl:value-of select="/UploadFailDRP/CoName"/>.</p>
									<xsl:if test="string-length(/UploadFailDRP/Message)>0"> For the following reasons:<br/>
									<p><xsl:value-of select="/UploadFailDRP/Message"/></p></xsl:if>
									<p>Please re-upload your estimate using the link provided in the original Assignment e-mail.</p>
									
									<p class="text" style="line-height:22px; color:#333333"><br/>
										</p>
								</td>        
							 </tr>
							
						</table>
			
					</td>
				</tr>
		  
			</table>
		</td>
	  </tr>
	  <!--
	  <tr>
		 <td width="500" valign="middle">
		 </td>
	  </tr>
	  -->

	</table>
	</td>
	</tr>
</table>
</body>
</xsl:template>


<xsl:template match="/mxs:EmailMessage">
<html>
<head>
<!--meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/-->
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="body"/>
</html>
</xsl:template>

<xsl:template match="/mxs:EmailMessageDRP">
<html>
<head>
<!--meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/-->
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="bodyDRP"/>
</html>
</xsl:template>


<xsl:template match="/UploadSuccess">
<html>
<head>
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="uploadbody"/>
</html>
</xsl:template>

<xsl:template match="/UploadSuccessDRP">
<html>
<head>
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="uploadbodyDRP"/>
</html>
</xsl:template>


<xsl:template match="/UploadSuccessSubject">
Estimate Uploaded Successfully for <xsl:value-of select="/UploadSuccessSubject/CoName"/> Claim: <xsl:value-of select="/UploadSuccessSubject/ClaimNumber"/>
</xsl:template>

<xsl:template match="/UploadFailSubject">
Estimate Upload Failed for <xsl:value-of select="/UploadFailSubject/CoName"/> Claim: <xsl:value-of select="/UploadFailSubject/ClaimNumber"/>
</xsl:template>

<xsl:template match="/UploadFail">
<html>
<head>
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="uploadFailbody"/>
</html>
</xsl:template>

<xsl:template match="/UploadFailDRP">
<html>
<head>
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="uploadFailbodyDRP"/>
</html>
</xsl:template>


<xsl:template match="/NonDrpSuppSubject">
	<xsl:choose>
		<xsl:when test="string-length(/NonDrpSuppSubject/CoName) > 0">
New Supplement Assignment from <xsl:value-of select="/NonDrpSuppSubject/CoName"/>, Claim: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber"/>			  
        </xsl:when>
		<xsl:otherwise>
New Supplement Assignment, Claim: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber"/>		
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="faxbody">
New Assignment
You have been assigned the following <xsl:choose><xsl:when test="$assignmentSubTypeDescFax!=''"><b><xsl:value-of select='$assignmentSubTypeDescFax'/></b> Appraisal Assignment</xsl:when>
									<xsl:otherwise>Appraisal Assignment</xsl:otherwise></xsl:choose>

Administrative/Vehicle Information

Type: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc"/>
<xsl:if test="/Fax/EmailMessage/ClaimantName">
Contact Name: <xsl:value-of select="/Fax/EmailMessage/ClaimantName"/>
<xsl:if test="/Fax/EmailMessage/HomePhone">
Home Phone: <xsl:value-of select="/Fax/EmailMessage/HomePhone"/>
</xsl:if>
<xsl:if test="/Fax/EmailMessage/WorkPhone">
Work Phone: <xsl:value-of select="/Fax/EmailMessage/WorkPhone"/>
</xsl:if>
<xsl:if test="/Fax/EmailMessage/CellPhone">
Cell Phone: <xsl:value-of select="/Fax/EmailMessage/CellPhone"/>
</xsl:if>
</xsl:if>
Vehicle Make: <xsl:value-of select="/Fax/EmailMessage/VehMake"/>, Model <xsl:value-of select="/Fax/EmailMessage/VehModel"/>, Year <xsl:value-of select="/Fax/EmailMessage/VehYear"/>
Point of Impact: <xsl:value-of select="/Fax/EmailMessage/Poi"/>
Damage Memo: <xsl:value-of select="/Fax/EmailMessage/DamageMemo"/>
Assignment Memo: <xsl:value-of select="/Fax/EmailMessage/AssignmentMemo"/>
Date Preference: <xsl:if test="string-length(/Fax/EmailMessage/PreferMonth) > 0"><xsl:value-of select="/Fax/EmailMessage/PreferMonth"/>/<xsl:value-of select="/Fax/EmailMessage/PreferDay"/>/<xsl:value-of select="/Fax/EmailMessage/PreferYear"/></xsl:if>

Notes
If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.

Regards,
<xsl:value-of select="/Fax/EmailMessage/CoName"/>
</xsl:template>

<xsl:template match="/Fax/EmailMessage">
<xsl:call-template name="faxbody"/>
</xsl:template>

<xsl:template name="faxbodyDRP">
New Assignment
You have been assigned the following <xsl:choose><xsl:when test="$assignmentSubTypeDescFaxDRP!=''"><b><xsl:value-of select='$assignmentSubTypeDescFaxDRP'/></b> Appraisal Assignment</xsl:when><xsl:otherwise>Appraisal Assignment</xsl:otherwise></xsl:choose>

Administrative/Vehicle Information

Type: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc"/>
<xsl:if test="/FaxDRP/EmailMessageDRP/ClaimantName">
Contact Name: <xsl:value-of select="/FaxDRP/EmailMessageDRP/ClaimantName"/>
<xsl:if test="/FaxDRP/EmailMessageDRP/HomePhone">
Home Phone: <xsl:value-of select="/FaxDRP/EmailMessageDRP/HomePhone"/>
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/WorkPhone">
Work Phone: <xsl:value-of select="/FaxDRP/EmailMessageDRP/WorkPhone"/>
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/CellPhone">
Cell Phone: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CellPhone"/>
</xsl:if>
</xsl:if>
Vehicle Make: <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehMake"/>, Model <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehModel"/>, Year <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehYear"/>
Point of Impact: <xsl:value-of select="/FaxDRP/EmailMessageDRP/Poi"/>
Damage Memo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/DamageMemo"/>
Assignment Memo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/AssignmentMemo"/>
Date Preference: <xsl:if test="string-length(/FaxDRP/EmailMessageDRP/PreferMonth) > 0"><xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferMonth"/>/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferDay"/>/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferYear"/></xsl:if>

Notes
If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.

Regards,
<xsl:value-of select="/FaxDRP/EmailMessageDRP/CoName"/>
</xsl:template>

<xsl:template match="/FaxDRP/EmailMessageDRP">
<xsl:call-template name="faxbodyDRP"/>
</xsl:template>
</xsl:transform>