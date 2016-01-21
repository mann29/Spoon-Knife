﻿<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:mxs="http://www.mitchell.com/schemas/assignmentdelivery">
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
<xsl:choose><xsl:when test="$assignmentSubTypeDesc!=''">Nuovo <xsl:value-of select="$assignmentSubTypeDesc" /> incarico-</xsl:when>
<xsl:otherwise>
Nuovo incarico-</xsl:otherwise></xsl:choose><xsl:value-of select="/CreationSubject/CoName" /><xsl:if test="string-length(/CreationSubject/ClaimNumber) > 0"> per denuncia:<xsl:value-of select="/CreationSubject/ClaimNumber" /></xsl:if>
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
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Nuovo incarico</p>
									<p class="text" style="line-height:22px; color:#333333">È stato assegnato il seguente <xsl:choose><xsl:when test="$assignmentSubTypeDescBdy!=''"><b><xsl:value-of select="$assignmentSubTypeDescBdy" /></b> Incarico di perizia</xsl:when>
									<xsl:otherwise>Incarico di perizia</xsl:otherwise></xsl:choose></p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">Se non si usa RC Connect per comunicare con questo operatore, ignorare questo messaggio <ul>
										<li>ECCEZIONE: se si riceve un incarico da questo operatore e l'officina non è convenzionata, conservare questa mail. Contiene l'unico collegamento al quale ricevere l'accesso alle informazioni.</li>
									</ul>
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Informazioni amministrative/veicolo<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Tipo di copertura:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:CauseOfLossDesc" />
											</td>
										</tr>
										
										<xsl:if test="/mxs:EmailMessage/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Nome contatto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:ClaimantName" />
											</td>
										</tr>

											<xsl:if test="/mxs:EmailMessage/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Telefono casa:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:HomePhone" />
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Telefono ufficio:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:WorkPhone" />
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Cellulare:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:CellPhone" />
												</td>
											</tr>
											</xsl:if>

										</xsl:if>

										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Marca veicolo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:VehMake" />, modello <xsl:value-of select="/mxs:EmailMessage/mxs:VehModel" />, anno <xsl:value-of select="/mxs:EmailMessage/mxs:VehYear" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Punto d'impatto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:Poi" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Memo danno:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:DamageMemo" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Memo incarico:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:AssignmentMemo" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Data preferita:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessage/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessage/mxs:PreferMonth" />/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferDay" />/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferYear" /></xsl:if>
											</td>
										</tr>
									</table>
									
									<br/>
									<hr/>
									<br/>
									<p>
									<xsl:call-template name="url"/><br/> Fare clic sul link precedente e inserire il numero d'immatricolazione del veicolo associato a questa denuncia.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									
									<p class="text" style="line-height:22px; color:#333333">Cordiali saluti,<br/><xsl:value-of select="/mxs:EmailMessage/mxs:CoName" /></p>
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
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Nuovo incarico</p>
									<p class="text" style="line-height:22px; color:#333333">È stato assegnato il seguente <xsl:choose><xsl:when test="$assignmentSubTypeDescBdyDRP!=''"><b><xsl:value-of select="$assignmentSubTypeDescBdyDRP" /></b> Incarico di perizia</xsl:when>
									<xsl:otherwise>Incarico di perizia</xsl:otherwise></xsl:choose></p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">Se non si usa RC Connect per comunicare con questo operatore, ignorare questo messaggio <ul>
										<li>ECCEZIONE: se si riceve un incarico da questo operatore e l'officina non è convenzionata, conservare questa mail. Contiene l'unico collegamento al quale ricevere l'accesso alle informazioni.</li>
									</ul> 
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Informazioni amministrative/veicolo<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Tipo di copertura:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CauseOfLossDesc" />
											</td>
										</tr>
										<xsl:if test="/mxs:EmailMessageDRP/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Nome contatto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:ClaimantName" />
											</td>
										</tr>
											<xsl:if test="/mxs:EmailMessageDRP/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Telefono casa:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:HomePhone" />
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Telefono ufficio:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:WorkPhone" />
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													Cellulare:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CellPhone" />
												</td>
											</tr>
											</xsl:if>

										</xsl:if>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Marca veicolo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehMake" />, modello <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehModel" />, anno <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehYear" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Punto d'impatto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:Poi" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Memo danno:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:DamageMemo" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Memo incarico:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:AssignmentMemo" />
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Data preferita:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessageDRP/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferMonth" />/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferDay" />/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferYear" /></xsl:if>
											</td>
										</tr>
									</table>
									
									<br/>
									<hr/>
									<br/>
									<p>
									<xsl:call-template name="urlDRP"/><br/> Fare clic sul link precedente e inserire il numero d'immatricolazione del veicolo associato a questa denuncia.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									<p class="text" style="line-height:22px; color:#333333">Cordiali saluti,<br/><xsl:value-of select="/mxs:EmailMessageDRP/mxs:CoName" /></p>
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
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">Visualizza intero incarico</a>
</xsl:template>

<xsl:template name="urlDRP">
<xsl:variable name="vlink">
<xsl:value-of select="/mxs:EmailMessageDRP/mxs:URLLink"/>
</xsl:variable>
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">Visualizza intero incarico</a>
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Preventivo caricato.</p>
									
									
									<p class="text" style="line-height:22px; color:#333333">Il preventivo è stato caricato a <xsl:value-of select="/UploadSuccess/CoName" />.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Preventivo caricato.</p>
									
									<p class="text" style="line-height:22px; color:#333333">Il preventivo è stato caricato a <xsl:value-of select="/UploadSuccessDRP/CoName" />.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Il preventivo non è stato caricato</p>
									
									<p class="text" style="line-height:22px; color:#333333">Il preventivo non ha potuto essere elaborato da <xsl:value-of select="/UploadFail/CoName" />.</p>
									<xsl:if test="string-length(/UploadFail/Message)>0"> Per i seguenti motivi:<br/>
									<p><xsl:value-of select="/UploadFail/Message" /></p></xsl:if>
									<p>Caricare nuovamente il preventivo utilizzando il link apposito presente nella mail originaria dell'incarico.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Il preventivo non è stato caricato</p>
									
									<p class="text" style="line-height:22px; color:#333333">Il preventivo non ha potuto essere elaborato da <xsl:value-of select="/UploadFailDRP/CoName" />.</p>
									<xsl:if test="string-length(/UploadFailDRP/Message)>0"> Per i seguenti motivi:<br/>
									<p><xsl:value-of select="/UploadFailDRP/Message" /></p></xsl:if>
									<p>Caricare nuovamente il preventivo utilizzando il link apposito presente nella mail originaria dell'incarico.</p>
									
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
Preventivo caricato correttamente per denuncia <xsl:value-of select="/UploadSuccessSubject/CoName" />: <xsl:value-of select="/UploadSuccessSubject/ClaimNumber" />
</xsl:template>

<xsl:template match="/UploadFailSubject">
Preventivo non caricato correttamente per denuncia <xsl:value-of select="/UploadFailSubject/CoName" />: <xsl:value-of select="/UploadFailSubject/ClaimNumber" />
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
Nuovo incarico di supplemento da <xsl:value-of select="/NonDrpSuppSubject/CoName" />, denuncia: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber" />			  
        </xsl:when>
		<xsl:otherwise>
Nuovo incarico di supplemento, denuncia: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber" />		
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="faxbody">
Nuovo incarico. È stato assegnato il seguente <xsl:choose><xsl:when test="$assignmentSubTypeDescFax!=''"><b><xsl:value-of select="$assignmentSubTypeDescFax" /></b> Incarico di perizia</xsl:when>
									<xsl:otherwise>Incarico di perizia</xsl:otherwise></xsl:choose> Tipo di informazioni amministrative/veicolo: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
<xsl:if test="/Fax/EmailMessage/ClaimantName">
Nome contatto: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
<xsl:if test="/Fax/EmailMessage/HomePhone">
Telefono casa: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
</xsl:if>
<xsl:if test="/Fax/EmailMessage/WorkPhone">
Telefono ufficio: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
</xsl:if>
<xsl:if test="/Fax/EmailMessage/CellPhone">
Cellulare: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
</xsl:if>
</xsl:if> Marca veicolo: <xsl:value-of select="/Fax/EmailMessage/VehMake" />, modello <xsl:value-of select="/Fax/EmailMessage/VehModel" />, anno <xsl:value-of select="/Fax/EmailMessage/VehYear" /> Punto d’impatto: <xsl:value-of select="/Fax/EmailMessage/Poi" /> Memo danno: <xsl:value-of select="/Fax/EmailMessage/DamageMemo" /> Memo incarico: <xsl:value-of select="/Fax/EmailMessage/AssignmentMemo" /> Data preferita: <xsl:if test="string-length(/Fax/EmailMessage/PreferMonth) > 0"><xsl:value-of select="/Fax/EmailMessage/PreferMonth" />/<xsl:value-of select="/Fax/EmailMessage/PreferDay" />/<xsl:value-of select="/Fax/EmailMessage/PreferYear" /></xsl:if> Note - Se la configurazione consente l'utilizzo di Mitchell eClaim Manager questo incarico potrebbe essere già stato ricevuto. Controllare la tua posta in arrivo in eClaim per accedere ai dettagli dell'incarico. Cordiali saluti, <xsl:value-of select="/Fax/EmailMessage/CoName" />
</xsl:template>

<xsl:template match="/Fax/EmailMessage">
<xsl:call-template name="faxbody"/>
</xsl:template>

<xsl:template name="faxbodyDRP">
Nuovo incarico. È stato assegnato il seguente <xsl:choose><xsl:when test="$assignmentSubTypeDescFaxDRP!=''"><b><xsl:value-of select="$assignmentSubTypeDescFaxDRP" /></b> Incarico di perizia</xsl:when><xsl:otherwise>Incarico di perizia</xsl:otherwise></xsl:choose> Tipo di informazioni amministrative/veicolo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc" />
<xsl:if test="/FaxDRP/EmailMessageDRP/ClaimantName">
Nome contatto: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
<xsl:if test="/FaxDRP/EmailMessageDRP/HomePhone">
Telefono casa: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc" />
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/WorkPhone">
Telefono ufficio: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc" />
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/CellPhone">
Cellulare: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
</xsl:if>
</xsl:if> Marca veicolo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehMake" />, modello <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehModel" />, anno <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehYear" /> Punto d’impatto: <xsl:value-of select="/FaxDRP/EmailMessageDRP/Poi" /> Memo danno: <xsl:value-of select="/FaxDRP/EmailMessageDRP/DamageMemo" /> Memo incarico: <xsl:value-of select="/FaxDRP/EmailMessageDRP/AssignmentMemo" /> Data preferita: <xsl:if test="string-length(/FaxDRP/EmailMessageDRP/PreferMonth) > 0"><xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferMonth" />/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferDay" />/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferYear" /></xsl:if> Note - Se la configurazione consente l'utilizzo di Mitchell eClaim Manager questo incarico potrebbe essere già stato ricevuto. Controllare la tua posta in arrivo in eClaim per accedere ai dettagli dell'incarico. Cordiali saluti, <xsl:value-of select="/FaxDRP/EmailMessageDRP/CoName" />
</xsl:template>

<xsl:template match="/FaxDRP/EmailMessageDRP">
<xsl:call-template name="faxbodyDRP"/>
</xsl:template>
</xsl:transform>