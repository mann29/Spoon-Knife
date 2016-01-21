<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:mxs="http://www.mitchell.com/schemas/assignmentdelivery">
<xsl:output omit-xml-declaration="yes"></xsl:output>
<!--xsl:output method="html" encoding="iso-8859-1" indent="yes"/-->

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
Nuevo encargo-<xsl:value-of select="/CreationSubject/CoName"/><xsl:if test="string-length(/CreationSubject/ClaimNumber) > 0"> para el siniestro:<xsl:value-of select="/CreationSubject/ClaimNumber"/></xsl:if>
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
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Nuevo encargo</p>
									<p class="text" style="line-height:22px; color:#333333">Se le ha asignado el siguiente Encargo de evaluaciÃ³n</p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">Si no utiliza RC Connect para comunicarse con este transportista, ignore el email. <ul>
										<li>EXCEPCIÃ“N: si es un Taller ajeno a la red que recibe un encargo de este transportista, guarde este email. Es el Ãºnico enlace que recibirÃ¡ para acceder a la informaciÃ³n.</li>
									</ul>
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Administrativo/InformaciÃ³n del vehÃ­culo<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Tipo de cobertura:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:CauseOfLossDesc"/>
											</td>
										</tr>
										
										<xsl:if test="/mxs:EmailMessage/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Nombre del contacto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:ClaimantName"/>
											</td>
										</tr>

											<xsl:if test="/mxs:EmailMessage/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono particular:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:HomePhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono del trabajo:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:WorkPhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessage/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono mÃ³vil:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessage/mxs:CellPhone"/>
												</td>
											</tr>
											</xsl:if>

										</xsl:if>

										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Marca del vehÃ­culo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:VehMake"/>, Modelo <xsl:value-of select="/mxs:EmailMessage/mxs:VehModel"/>, AÃ±o <xsl:value-of select="/mxs:EmailMessage/mxs:VehYear"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Punto de impacto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:Poi"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Notas sobre daÃ±os:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:DamageMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Notas sobre encargo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessage/mxs:AssignmentMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Fecha preferida:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessage/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessage/mxs:PreferMonth"/>/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferDay"/>/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferYear"/></xsl:if>
											</td>
										</tr>
									</table>
									
									<br />
									<hr/>
									<br />
									<p>
									<xsl:call-template name="url"/><br /> Haga clic en el enlace anterior y especifique el NÃºmero de identificaciÃ³n del vehÃ­culo (VIN) asociado con este siniestro.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									
									<p class="text" style="line-height:22px; color:#333333">Reciba un cordial saludo, <br /><xsl:value-of select="/EmailMessage/CoName"/></p>
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
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Nuevo encargo</p>
									<p class="text" style="line-height:22px; color:#333333">Se le ha asignado el siguiente Encargo de evaluaciÃ³n</p>
									<!--By vb101824 for PBI 292181 -->
									<p class="text" style="line-height:22px; color:#333333">Si no utiliza RC Connect para comunicarse con este transportista, ignore el email. <ul>
										<li>EXCEPCIÃ“N: si es un Taller ajeno a la red que recibe un encargo de este transportista, guarde este email. Es el Ãºnico enlace que recibirÃ¡ para acceder a la informaciÃ³n.</li>
									</ul> 
									</p>
									<p class="text" style="line-height:22px; color:#333333; font-weight: bold;">Administrativo/InformaciÃ³n del vehÃ­culo<hr/></p>
									<table class="text" style="line-height:22px; color:#333333; font-size: 10pt; font-family: Verdana, Geneva, sans-serif;">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top" width="150">
												Tipo de cobertura:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CauseOfLossDesc"/>
											</td>
										</tr>
										<xsl:if test="/mxs:EmailMessageDRP/mxs:ClaimantName">
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Nombre del contacto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:ClaimantName"/>
											</td>
										</tr>
											<xsl:if test="/mxs:EmailMessageDRP/mxs:HomePhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono particular:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:HomePhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:WorkPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono del trabajo:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:WorkPhone"/>
												</td>
											</tr>
											</xsl:if>

											<xsl:if test="/mxs:EmailMessageDRP/mxs:CellPhone">
											<tr>
												<td style="font-weight: bold; text-align: right; vertical-align: top">
													TelÃ©fono mÃ³vil:
												</td>
												<td style="vertical-align: top">
													<xsl:value-of select="/mxs:EmailMessageDRP/mxs:CellPhone"/>
												</td>
											</tr>
											</xsl:if>

										</xsl:if>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Marca del vehÃ­culo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehMake"/>, Modelo <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehModel"/>, AÃ±o <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehYear"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Punto de impacto:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:Poi"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Notas sobre daÃ±os:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:DamageMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Notas sobre encargo:
											</td>
											<td style="vertical-align: top">
												<xsl:value-of select="/mxs:EmailMessageDRP/mxs:AssignmentMemo"/>
											</td>
										</tr>
										<tr>
											<td style="font-weight: bold; text-align: right; vertical-align: top">
												Fecha preferida:
											</td>
											<td style="vertical-align: top">
												<xsl:if test="string-length(/mxs:EmailMessageDRP/mxs:PreferMonth) > 0"><xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferMonth"/>/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferDay"/>/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferYear"/></xsl:if>
											</td>
										</tr>
									</table>
									
									<br />
									<hr/>
									<br />
									<p>
									<xsl:call-template name="urlDRP"/><br /> Haga clic en el enlace anterior y especifique el NÃºmero de identificaciÃ³n del vehÃ­culo (VIN) asociado con este siniestro.
									</p>
									
									
									<!--By vb101824 for PBI 292181 -->
									<!-- p class="text" style="line-height:22px; color:#333333">If you are configured to use Mitchell&#39;s eClaim Manager you may have already received this assignment. Please check your eClaim inbox to access your assignment details.<br/>
									</p -->
									<p class="text" style="line-height:22px; color:#333333">Reciba un cordial saludo, <br /><xsl:value-of select="/EmailMessageDRP/CoName"/></p>
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
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">Ver encargo completo</a>
</xsl:template>

<xsl:template name="urlDRP">
<xsl:variable name="vlink">
<xsl:value-of select="/mxs:EmailMessageDRP/mxs:URLLink"/>
</xsl:variable>
<a href="{$vlink}" style="line-height:22px; font-size:16px; font-weight:bold">Ver encargo completo</a>
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Â¡ValoraciÃ³n cargada con Ã©xito!</p>
									
									
									<p class="text" style="line-height:22px; color:#333333">Su valoraciÃ³n se cargÃ³ con Ã©xito en <xsl:value-of select="/UploadSuccess/CoName"/>. RecibirÃ¡ una notificaciÃ³n por email despuÃ©s de que la empresa reciba la valoraciÃ³n.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Â¡ValoraciÃ³n cargada con Ã©xito!</p>
									
									<p class="text" style="line-height:22px; color:#333333">Su valoraciÃ³n se cargÃ³ con Ã©xito en <xsl:value-of select="/UploadSuccessDRP/CoName"/>. RecibirÃ¡ una notificaciÃ³n por email despuÃ©s de que la empresa reciba la valoraciÃ³n.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Fallo al cargar la valoraciÃ³n</p>
									
									<p class="text" style="line-height:22px; color:#333333">La valoraciÃ³n no pudo procesarla <xsl:value-of select="/UploadFail/CoName"/>.</p>
									<xsl:if test="string-length(/UploadFail/Message)>0"> Por los siguientes motivos:<br />
									<p><xsl:value-of select="/UploadFail/Message"/></p></xsl:if>
									<p>Vuelva a cargar la va usando el enlace proporcionado en el email de encargo original.</p>
									
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
									
									<p class="text" style="line-height:22px; font-size:16px; color:#72a493; font-weight:bold">Fallo al cargar la valoraciÃ³n</p>
									
									<p class="text" style="line-height:22px; color:#333333">La valoraciÃ³n no pudo procesarla <xsl:value-of select="/UploadFailDRP/CoName"/>.</p>
									<xsl:if test="string-length(/UploadFailDRP/Message)>0"> Por los siguientes motivos:<br />
									<p><xsl:value-of select="/UploadFailDRP/Message"/></p></xsl:if>
									<p>Vuelva a cargar la va usando el enlace proporcionado en el email de encargo original.</p>
									
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
ValoraciÃ³n cargada con Ã©xito para <xsl:value-of select="/UploadSuccessSubject/CoName"/> Siniestro: <xsl:value-of select="/UploadSuccessSubject/ClaimNumber"/>
</xsl:template>

<xsl:template match="/UploadFailSubject">
Fallo al cargar valoraciÃ³n para <xsl:value-of select="/UploadFailSubject/CoName"/> Siniestro: <xsl:value-of select="/UploadFailSubject/ClaimNumber"/>
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
Nuevo encargo de modificaciÃ³n de <xsl:value-of select="/NonDrpSuppSubject/CoName"/>, Siniestro: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber"/>			  
        </xsl:when>
		<xsl:otherwise>
Nuevo encargo de modificaciÃ³n, Siniestro: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber"/>		
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<xsl:template name="faxbody">
Nuevo encargo Se le ha asignado el siguiente Encargo de evaluaciÃ³n Administrativo/InformaciÃ³n del vehÃ­culo Tipo: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc"/>
<xsl:if test="/Fax/EmailMessage/ClaimantName">
Nombre del contacto: <xsl:value-of select="/Fax/EmailMessage/ClaimantName"/>
<xsl:if test="/Fax/EmailMessage/HomePhone">
TelÃ©fono particular: <xsl:value-of select="/Fax/EmailMessage/HomePhone"/>
</xsl:if>
<xsl:if test="/Fax/EmailMessage/WorkPhone">
TelÃ©fono del trabajo: <xsl:value-of select="/Fax/EmailMessage/WorkPhone"/>
</xsl:if>
<xsl:if test="/Fax/EmailMessage/CellPhone">
TelÃ©fono mÃ³vil: <xsl:value-of select="/Fax/EmailMessage/CellPhone"/>
</xsl:if>
</xsl:if> Marca del vehÃ­culo: <xsl:value-of select="/Fax/EmailMessage/VehMake"/>, Modelo <xsl:value-of select="/Fax/EmailMessage/VehModel"/>, AÃ±o <xsl:value-of select="/Fax/EmailMessage/VehYear"/> Punto de impacto: <xsl:value-of select="/Fax/EmailMessage/Poi"/> Notas sobre daÃ±os: <xsl:value-of select="/Fax/EmailMessage/DamageMemo"/> Notas sobre encargo: <xsl:value-of select="/Fax/EmailMessage/AssignmentMemo"/> Fecha preferida: <xsl:if test="string-length(/Fax/EmailMessage/PreferMonth) > 0"><xsl:value-of select="/Fax/EmailMessage/PreferMonth"/>/<xsl:value-of select="/Fax/EmailMessage/PreferDay"/>/<xsl:value-of select="/Fax/EmailMessage/PreferYear"/></xsl:if> Notas Si estÃ¡ configurado para usar eClaim Manager de Mitchell, quizÃ¡s haya recibido ya este encargo. Compruebe la bandeja de entrada de eClaim para acceder a los detalles del encargo. Reciba un cordial saludo,  <xsl:value-of select="/Fax/EmailMessage/CoName"/>
</xsl:template>

<xsl:template match="/Fax/EmailMessage">
<xsl:call-template name="faxbody"/>
</xsl:template>

<xsl:template name="faxbodyDRP">
Nuevo encargo Se le ha asignado el siguiente Encargo de evaluaciÃ³n Administrativo/InformaciÃ³n del vehÃ­culo Tipo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc"/>
<xsl:if test="/FaxDRP/EmailMessageDRP/ClaimantName">
Nombre del contacto: <xsl:value-of select="/FaxDRP/EmailMessageDRP/ClaimantName"/>
<xsl:if test="/FaxDRP/EmailMessageDRP/HomePhone">
TelÃ©fono particular: <xsl:value-of select="/FaxDRP/EmailMessageDRP/HomePhone"/>
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/WorkPhone">
TelÃ©fono del trabajo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/WorkPhone"/>
</xsl:if>
<xsl:if test="/FaxDRP/EmailMessageDRP/CellPhone">
TelÃ©fono mÃ³vil: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CellPhone"/>
</xsl:if>
</xsl:if> Marca del vehÃ­culo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehMake"/>, Modelo <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehModel"/>, AÃ±o <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehYear"/> Punto de impacto: <xsl:value-of select="/FaxDRP/EmailMessageDRP/Poi"/> Notas sobre daÃ±os: <xsl:value-of select="/FaxDRP/EmailMessageDRP/DamageMemo"/> Notas sobre encargo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/AssignmentMemo"/> Fecha preferida: <xsl:if test="string-length(/FaxDRP/EmailMessageDRP/PreferMonth) > 0"><xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferMonth"/>/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferDay"/>/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferYear"/></xsl:if> Notas Si estÃ¡ configurado para usar eClaim Manager de Mitchell, quizÃ¡s haya recibido ya este encargo. Compruebe la bandeja de entrada de eClaim para acceder a los detalles del encargo. Reciba un cordial saludo,  <xsl:value-of select="/FaxDRP/EmailMessageDRP/CoName"/>
</xsl:template>

<xsl:template match="/FaxDRP/EmailMessageDRP">
<xsl:call-template name="faxbodyDRP"/>
</xsl:template>
</xsl:transform>