<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:sup="http://www.mitchell.com/schemas/appraisalassignment/supplementrequestemail">

<xsl:output method="html" encoding="iso-8859-1" indent="yes"/>

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
<!-- fixing bug:290010 <xsl:call-template name="instruction"/>  -->
<xsl:call-template name="conf"/>
</table>
</body>
</xsl:template>

<xsl:template name="deskreviewinfo">
<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0 or string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
<tr>
	<td class="section">
	<!--Section--> Información de la compañía
	</td>
</tr>

	<xsl:if test="string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
	<tr>
		<td class="subsection">
		<!--Subsection--> Comentarios de la compañía
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
    <xsl:call-template name="lineitem"/>
 </xsl:if>   
</xsl:template>

<xsl:template name="outputline" match="/sup:SupplementRequest/sup:LineItemsInfo">
  <xsl:for-each select="//sup:LineItemInfo">
  <xsl:sort select="sup:LineItem/sup:LineNum" data-type="number"/>
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
				<td class="data-table-row"><xsl:call-template name="outputnumber">
				  <xsl:with-param name="nm" select="sup:LineItem/sup:Operation"/>
				</xsl:call-template></td>
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
				<td class="data-table-row"><xsl:value-of select="sup:Compliance/sup:Operation" /></td>
				<td class="data-table-row-end" colspan="6"><xsl:value-of select="sup:Compliance/sup:LineDesc" /></td>
			</tr>
			</xsl:if>
  </xsl:for-each>
</xsl:template>

<xsl:template name="outputnumber">
<xsl:param name="nm"/>
<xsl:choose>
	<xsl:when test="string-length($nm) > 0">
	<xsl:value-of select="$nm" />
	</xsl:when>
	<xsl:otherwise>
	 
	</xsl:otherwise>
</xsl:choose>
</xsl:template>

<xsl:template name="lineitem">
	<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0">
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<div class="col-bg-large"><span class="subsection">Anotaciones por operación</span></div>
			<table width="100%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table3">
			<tr>
				<td class="data-table-head-first" rowspan="2">Línea</td>
				<td class="data-table-head" rowspan="2">Operación</td>
				<td class="data-table-head" rowspan="2">Descripción</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Piezas</td>
				<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Mano de obra</td>
				<td class="data-table-head-end" rowspan="2">Importe</td>
			</tr>
			<tr>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Tipo</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Precio</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Horas</td>
				<td class="data-table-head" style="border-top: 1px solid #bebebe;">Coste</td>
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
<a href="{$vlink}">Enlace</a>
</xsl:template>

<xsl:template name="claimheader">
<!--Section Supplement Request Info -->
<tr>
	<td class="section">
	<!--Section--> Solicitud de modificación para Nº de siniestro <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" />
	</td>
</tr>
<tr> 
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) > 0">
Para ver el encargo completo, haga clic en el siguiente enlace y especifique el Número de identificación del vehículo (VIN) asociado con este siniestro.<br/>
			<xsl:call-template name="url"/><br/>
			</xsl:if>
</tr>
</xsl:template>

<xsl:template name="date">
<xsl:param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/>
<xsl:value-of select="substring($datestr,6,2)" />/<xsl:value-of select="substring($datestr,9,2)" />/<xsl:value-of select="substring($datestr,1,4)" />
</xsl:template>

<xsl:template name="adminvehicle">
<tr>
	<td class="section-content">
	<!--Section Content-->
	<table border="0" cellpadding="0" cellspacing="0" width="100%" ID="Table1">
	<tr>
		<td class="subsection">
		<!--Subsection--> Datos administrativos
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table2">
		<tr>
			<td width="25%" class="data-field">Nº de siniestro:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" /></td>
			<td width="25%" class="data-field">ID de valoración:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClientEstimateId" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Enviado:</td>
			<td width="25%" class="data"><xsl:call-template name="date">
			<!--xsl:with-param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/-->
			</xsl:call-template></td>
			<td width="25%" class="data-field">Compañía de seguros:</td>
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
			<td width="25%" class="data-field">Destinatario:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Name" />
			</td>
			<td width="25%" class="data-field">Remitente:</td>
			<td width="25%" class="data">
			<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Name" />
			</td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Teléfono:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Phone" /></td>
			<td width="25%" class="data-field">Teléfono:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Phone" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Email:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Email" /></td>
			<td width="25%" class="data-field">Email:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Email" /></td>
		</tr>

		<tr>
			<td width="25%" class="data-field">Fax:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Fax" /></td>
			<td width="25%" class="data-field">Fax:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Fax" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Dirección:</td>
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
		<!--Subsection--> Información del vehículo
		</td>
	</tr>
	<tr>
		<td class="subsection-content">
		<!--Subsection Content-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table6">
		<tr>
			<td width="25%" class="data-field">Año:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Year" /></td>
			<td width="25%" class="data-field"> Nombre del propietario del vehículo:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:OwnerName" /></td>

		</tr>
		<tr>
			<td width="25%" class="data-field">Fabricante:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Make" /></td>
			<td width="25%" class="data-field">Color:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Color" /></td>
		</tr>
		<tr>
			<td width="25%" class="data-field">Modelo:</td>
			<td width="25%" class="data"><xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Model" /></td>
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
	<!--Section--> Instrucciones de modificación para valoraciones

	</td>
</tr>

<tr>
	<td class="subsection-content">
	<!--Subsection Content-->


	<table>
	<tr><td class="data-right">1.&#160;</td><td class="data">Abra eClaim Manager</td></tr>
	<tr><td class="data-right">2.&#160;</td><td class="data">Localice y abra la Carpeta de siniestros en eClaim Manager</td></tr>
	<tr><td class="data-right">3.&#160;</td><td class="data">Realice una de las siguientes acciones:</td></tr>
	<tr><td class="data-right"> </td><td class="data">Si utiliza UltraMate:</td></tr>
	<tr><td class="data-right">   </td><td class="data">3a.&#160;Inicie UltraMate desde eClaim Manager.</td></tr>
	<tr><td class="data-right">   </td><td class="data">3b.&#160;Actualice y calcule la valoración, luego haga clic en el botón eCM para confirmar la valoración y volver a eClaim Manager.</td></tr>
    <tr><td class="data-right"> </td><td class="data">Si no utiliza UltraMate:</td></tr>
	<tr><td class="data-right">   </td><td class="data">3a.&#160;Abra el software de valoración.</td></tr>
	<tr><td class="data-right">   </td><td class="data">3b.&#160;Actualice y luego confirme o bloquee la valoración.</td></tr>
    <tr><td class="data-right">   </td><td class="data">3c.&#160;Exporte el EMS para la valoración desde el software de valoración.</td></tr>
    <tr><td class="data-right">   </td><td class="data">3d.&#160;Cree una imagen de impresión de la valoración en formato PDF.</td></tr>	
    <tr><td class="data-right">   </td><td class="data">3e.&#160;En eClaim Manager, importe el EMS modificado y el PDF.</td></tr>	    
    <tr><td class="data-right">4.&#160;</td><td class="data">En eClaim Manager, marque la carpeta como Lista para enviar, luego haga clic en el botón con la bombilla.</td></tr>    
	</table>
	</td>
</tr>
</xsl:if>
</xsl:template>

<xsl:template name="conf">
<tr>
	<td class="section">
	<!--Section--> Declaración de confidencialidad
	</td>
</tr>
<tr>
	<td class="subsection-content">
	<!--Subsection Content-->
	<span class="data" style="text-align: center;">La información incluida en este mensaje es CONFIDENCIAL y solo para el destinatario previsto. <br/> Está totalmente prohibido cualquier uso no autorizado, reenvío de la información o copia del mensaje. <br/> Si no es el destinatario previsto, por favor hágaselo saber al remitente de inmediato y borre el mensaje.<br/>
	</span>
	</td>
</tr>
</xsl:template>

<xsl:template match="/">
<html>
<head>
<xsl:call-template name="css"/>
</head>
<xsl:call-template name="body"/>
</html>
</xsl:template>

</xsl:transform>