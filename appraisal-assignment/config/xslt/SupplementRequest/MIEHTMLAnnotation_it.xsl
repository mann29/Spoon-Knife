<xsl:transform xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0" xmlns:sup="http://www.mitchell.com/schemas/appraisalassignment/supplementrequestemail">
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
	width:100%;
}

.data-table-head-first{
	font-size: 12px;
	height: 20px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 7px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
}

.data-table-head{
	font-size: 12px;
	height: 20px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 2px, 2px, 2px;
	border-bottom: 1px solid black;
	border-right: 1px solid #bebebe;
}

.data-table-head-right{
	font-size: 12px;
	height: 20px;
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
	height: 20px;
	font-weight: 800;
	vertical-align: top;
	font-family: arial, sans-serif;
	padding: 2px, 7px, 2px, 2px;
	border-bottom: 1px solid black;
}

.data-table-head-end-right{
	font-size: 12px;
	height: 20px;
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
	<xsl:template name="estimateprofile">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:EstimateProfile/sup:RateInfo) > 0">
			<tr>
				<td class="section-padding">
					<table border="0" cellpadding="0" cellspacing="0" width="50%" ID="Table5">
						<tr>
							<td class="subsection">
								<!--Subsection--> Profilo preventivo:
		</td>
							<td class="subsection" align="left">
								<!--Subsection-->
								<xsl:value-of select="/sup:SupplementRequest/sup:EstimateProfile/sup:ProfileName"/>
							</td>
							<tr>
								<td class="data-table-head-first" colspan="2" style="border-style: none">Tariffa manodopera ($/h)</td>
							</tr>
							<tr>
								<td class="subsection-content">
									<!--div class="col-bg"></div-->
									<table width="50%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table4">
										<xsl:for-each select="//sup:RateInfo">
											<tr>
												<td class="data-field">
													<xsl:value-of select="sup:Type"/>:</td>
												<xsl:if test="string-length(sup:PreviousRate) > 0">
													<td class="data" style="text-decoration: line-through;">
														<xsl:value-of select="sup:PreviousRate"/>
													</td>
												</xsl:if>
												<xsl:choose>
													<xsl:when test="string-length(sup:PreviousRate) > 0">
														<td class="data" style="color: #3399FF">
															<xsl:value-of select="sup:Rate"/>
														</td>
													</xsl:when>
													<xsl:otherwise>
														<td class="data">
															<xsl:value-of select="sup:Rate"/>
														</td>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:if test="string-length(sup:RateDelta) > 0">
													<td class="data" style="color: #FF0000">[<xsl:value-of select="sup:RateDelta"/>]</td>
												</xsl:if>
											</tr>
										</xsl:for-each>
									</table>
								</td>
							</tr>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="deskreviewinfo">
		<xsl:if test="count(/sup:SupplementRequest/sup:LineItemsInfo/sup:LineItemInfo) > 0 or string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0 or
count(/sup:SupplementRequest/sup:EstimateProfile/sup:RateInfo) > 0">
			<tr>
				<td class="section">
					<!--Section--> Informazioni revisione a tavolino
	</td>
			</tr>
			<xsl:call-template name="estimateprofile"/>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
				<tr>
					<td class="subsection">
						<!--Subsection--> Commenti revisione a tavolino
		</td>
				</tr>
				<tr>
					<td class="subsection-content">
						<!--Subsection Content-->
						<pre>
							<span class="data">
								<xsl:value-of select="/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments"/>
							</span>
						</pre>
					</td>
				</tr>
			</xsl:if>
			<xsl:call-template name="lineitem"/>
			<xsl:call-template name="partssubtotal"/>
			<xsl:call-template name="laborsubtotal"/>
			<xsl:call-template name="otherchargerssubtotal"/>
			<xsl:call-template name="grosstotal"/>
			<xsl:call-template name="adjustments"/>
			<xsl:call-template name="nettotal"/>
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
			<td class="data-table-row-first">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:LineNum"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:Operation"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:LineDesc"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartType"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartNumber"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartQuantity"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:PartInfo/sup:PreviousPartQuantity"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:PartInfo/sup:PartQuantityDelta"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:UnitPartPrice"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:PartInfo/sup:PreviousUnitPartPrice"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:PartInfo/sup:UnitPartPriceDelta"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row-right">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:PartInfo/sup:PartPrice"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:PartInfo/sup:PreviousPartPrice"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:PartInfo/sup:PartPriceDelta"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row-right">
				<xsl:call-template name="outputnumber">
					<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborType"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row-right">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborHours"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:LaborInfo/sup:PreviousLaborHours"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:LaborInfo/sup:LaborHoursDelta"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row-right">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:LaborInfo/sup:LaborCost"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:LaborInfo/sup:PreviousLaborCost"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:LaborInfo/sup:LaborCostDelta"/>
				</xsl:call-template>
			</td>
			<td class="data-table-row-end-right">
				<xsl:call-template name="opnWithDelta">
					<xsl:with-param name="nm" select="sup:LineItem/sup:Amount"/>
					<xsl:with-param name="pnm" select="sup:LineItem/sup:PreviousAmount"/>
					<xsl:with-param name="dnm" select="sup:LineItem/sup:AmountDelta"/>
				</xsl:call-template>
			</td>
			<xsl:text disable-output-escaping="yes">&lt;tr></xsl:text>
			<xsl:if test="count(sup:Annotation) > 0">
				<xsl:for-each select="sup:Annotation">
					<tr>
						<td class="data-table-row-first">
							<br/>
						</td>
						<td class="data-table-row">
							<b>
								<xsl:call-template name="outputnumber">
									<xsl:with-param name="nm" select="sup:LineDesc"/>
								</xsl:call-template>
							</b>
						</td>
						<td class="data-table-row-end" colspan="10">
							<xsl:call-template name="outputnumber">
								<xsl:with-param name="nm" select="sup:Operation"/>
							</xsl:call-template>
						</td>
					</tr>
				</xsl:for-each>
			</xsl:if>
			<xsl:if test="count(sup:Compliance/sup:Operation) > 0">
				<tr>
					<td class="data-table-row-first">
						<br/>
					</td>
					<td class="data-table-row">
						<xsl:value-of select="sup:Compliance/sup:Operation"/>
					</td>
					<td class="data-table-row-end" colspan="10">
						<xsl:value-of select="sup:Compliance/sup:LineDesc"/>
					</td>
				</tr>
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
	<xsl:template name="opnWithDelta">
		<xsl:param name="nm"/>
		<xsl:param name="pnm"/>
		<xsl:param name="dnm"/>
		<xsl:choose>
			<xsl:when test="string-length($nm) > 0">
				<xsl:if test="string-length($pnm) > 0">
					<span style="text-decoration: line-through;">
						<xsl:value-of select="$pnm"/>
					</span>
					<br/>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="string-length($pnm) > 0">
						<span style="color: #3399FF">
							<xsl:value-of select="$nm"/>
						</span>
					</xsl:when>
					<xsl:otherwise>
						<span>
							<xsl:value-of select="$nm"/>
						</span>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="string-length($dnm) > 0">
					<br/>
					<span style="color: #FF0000;text-align: left;">[<xsl:value-of select="$dnm"/>]</span>
				</xsl:if>
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
					<div class="col-bg-large">
						<span class="subsection">Annotazioni voce</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table3">
						<tr>
							<td class="data-table-head-first" rowspan="2">Linea</td>
							<td class="data-table-head" rowspan="2">Operazione</td>
							<td class="data-table-head" rowspan="2">Descrizione</td>
							<td class="data-table-head" colspan="3" style="border-bottom: 0px;">Ricambi</td>
							<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Prezzo</td>
							<td class="data-table-head" colspan="3" style="border-bottom: 0px;">Manodopera</td>
							<td class="data-table-head-end" rowspan="2">Importo</td>
						</tr>
						<tr>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Tipo</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Numero</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Quantità</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Unità</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Esteso</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Tipo</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Ore</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Costo</td>
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
<a href="{$vlink}">Collegamento</a>
</xsl:template>
	
	<xsl:template name="claimheader">
		<!--Section Supplement Request Info -->
		<tr>
			<td class="section">
				<!--Section--> Richiesta di supplemento per N. denuncia <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber"/>
			</td>
		</tr>
		<tr> 
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) > 0">
Per vedere l'intero incarico, fare clic sul seguente link e inserire il numero d'immatricolazione (VIN) associato a questa denuncia.<br/>
			<xsl:call-template name="url"/><br/>
			</xsl:if>
		
</tr>
	</xsl:template>
	<xsl:template name="date">
		<xsl:param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/>
		<xsl:value-of select="substring($datestr,9,2)"/>/<xsl:value-of select="substring($datestr,6,2)"/>/<xsl:value-of select="substring($datestr,1,4)"/>
	</xsl:template>
	<xsl:template name="adminvehicle">
		<tr>
			<td class="section-content">
				<!--Section Content-->
				<table border="0" cellpadding="0" cellspacing="0" width="100%" ID="Table1">
					<tr>
						<td class="subsection">
							<!--Subsection--> Informazioni admin
		</td>
					</tr>
					<tr>
						<td class="subsection-content">
							<!--Subsection Content-->
							<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table2">
								<tr>
									<td width="25%" class="data-field">N. denuncia:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber"/>
									</td>
									<td width="25%" class="data-field">ID preventivo:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClientEstimateId"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Inviato:</td>
									<td width="25%" class="data">
										<xsl:call-template name="date">
											<!--xsl:with-param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/-->
										</xsl:call-template>
									</td>
									<td width="25%" class="data-field">Società di assicurazione:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:InsuranceCo"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">
										<br/>
									</td>
									<td width="25%" class="data">
										<br/>
									</td>
									<td width="25%" class="data-field">
										<br/>
									</td>
									<td width="25%" class="data">
										<br/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Destinatario:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Name"/>
									</td>
									<td width="25%" class="data-field">Mittente:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Name"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Telefono:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Phone"/>
									</td>
									<td width="25%" class="data-field">Telefono:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Phone"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Email:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Email"/>
									</td>
									<td width="25%" class="data-field">Email:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Email"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Fax:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Fax"/>
									</td>
									<td width="25%" class="data-field">Fax:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Fax"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Indirizzo:</td>
									<td width="25%" class="data">
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1"/>
											<br/>
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2"/>
											<br/>
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City"/>
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State) > 0 or string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip) > 0">
			, <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State"/>
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip"/>
											<br/>
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
							<!--Subsection--> Informazioni veicolo
		</td>
					</tr>
					<tr>
						<td class="subsection-content">
							<!--Subsection Content-->
							<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table6">
								<tr>
									<td width="25%" class="data-field">Anno:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Year"/>
									</td>
									<td width="25%" class="data-field"> Nome titolare veicolo:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:OwnerName"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Marca:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Make"/>
									</td>
									<td width="25%" class="data-field">Colore:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Color"/>
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Modello:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Model"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
</xsl:template>
<xsl:template name="instruction">
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) &lt;= 0">
		<!--Section Instructions -->
		<tr>
			<td class="section">
				<!--Section--> Istruzioni supplementari per preventivi

	</td>
		</tr>
		<tr>
			<td class="subsection-content">
				<!--Subsection Content-->
				<table>
					<tr>
						<td class="data-right">1. </td>
						<td class="data">Aprire eClaim Manager.</td>
					</tr>
					<tr>
						<td class="data-right">2. </td>
						<td class="data">Individuare e aprire la cartella denunce in eClaim Manager.</td>
					</tr>
					<tr>
						<td class="data-right">3. </td>
						<td class="data">Procedere in uno dei modi seguenti:</td>
					</tr>
					<tr>
						<td class="data-right"> </td>
						<td class="data">Se si utilizza UltraMate:</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3a. Avviare UltraMate da eClaim Manager.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3b. Aggiornare e calcolare il preventivo, quindi fare clic sul pulsante eCM per confermarlo e tornare a eClaim Manager.</td>
					</tr>
					<tr>
						<td class="data-right"> </td>
						<td class="data">Se non si utilizza UltraMate:</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3a. Aprire il software di preventivazione.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3b. Aggiornare, quindi confermare/bloccare il preventivo.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3c. Esportare l'EMS per il preventivo dal software.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3d. Creare un'immagine stampabile del preventivo in formato PDF.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3e. In eClaim Manager, importare l'EMS aggiornato e il PDF.</td>
					</tr>
					<tr>
						<td class="data-right">4. </td>
						<td class="data">In eClaim Manager, contrassegnare la cartella come Pronta per l'invio, quindi fare clic sul pulsante con il simbolo del fulmine.</td>
					</tr>
				</table>
			</td>
		</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="conf">
		<tr>
			<td class="section">
				<!--Section--> Dichiarazione di riservatezza
	</td>
		</tr>
		<tr>
			<td class="subsection-content">
				<!--Subsection Content-->
				<span class="data" style="text-align: center;">
		Le informazioni contenute in questo messaggio sono riservate e destinate unicamente al destinatario previsto. <br/> Si vieta qualsiasi uso non autorizzato, la divulgazione delle informazioni o la copia di questo messaggio. <br/> Se il vostro nome non è il destinatario previsto, siete pregati di avvisare immediatamente il mittente e di cancellare questo messaggio.<br/>
				</span>
			</td>
		</tr>
</xsl:template>
<xsl:template name="laborsubtotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:LaborSubTotals/sup:LaborCost/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table14">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Totali parziali manodopera
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:LaborSubTotals/sup:LaborCost/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:LaborSubTotals/sup:LaborCost/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:LaborSubTotals/sup:LaborCost/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
		<xsl:if test="count(/sup:SupplementRequest/sup:LaborSubTotals/sup:LaborLine) > 0">
			<tr>
				<td>
					<table width="80%" cellspacing="0" cellpadding="0" border="0" id="Table15">
						<tr>
							<td/>
							<td class="data-right">Ore</td>
							<td class="data-right">Tariffa</td>
							<td class="data-right">Agg</td>
							<td class="data-right">Subaffitto</td>
							<td class="data-right">Totale</td>
						</tr>
						<xsl:for-each select="sup:SupplementRequest/sup:LaborSubTotals/sup:LaborLine">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Type"/>:</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Hours/sup:Hour"/>
										<xsl:with-param name="ptot" select="sup:Hours/sup:PreviousHour"/>
										<xsl:with-param name="dtot" select="sup:Hours/sup:HourDelta"/>
									</xsl:call-template>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Rate/sup:Rate"/>
										<xsl:with-param name="ptot" select="sup:Rate/sup:PreviousRate"/>
										<xsl:with-param name="dtot" select="sup:Rate/sup:RateDelta"/>
									</xsl:call-template>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Additional/sup:Additional"/>
										<xsl:with-param name="ptot" select="sup:Additional/sup:PreviousAdditional"/>
										<xsl:with-param name="dtot" select="sup:Additional/sup:AdditionalDelta"/>
									</xsl:call-template>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Sublet/sup:Sublet"/>
										<xsl:with-param name="ptot" select="sup:Sublet/sup:PreviousSublet"/>
										<xsl:with-param name="dtot" select="sup:Sublet/sup:SubletDelta"/>
									</xsl:call-template>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Total/sup:Total"/>
										<xsl:with-param name="ptot" select="sup:Total/sup:PreviousTotal"/>
										<xsl:with-param name="dtot" select="sup:Total/sup:TotalDelta"/>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="partssubtotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:PartsSubTotals/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table7">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Totali parziali ricambi
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:PartsSubTotals/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:PartsSubTotals/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:PartsSubTotals/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
		<xsl:if test="string-length(/sup:SupplementRequest/sup:PartsSubTotals/sup:Item) > 0">
			<tr>
				<td>
					<table width="80%" cellspacing="0" cellpadding="0" border="0" id="Table16">
						<tr>
							<td/>
							<td class="data-right">
								Tariffa
							</td>
							<td class="data-right">Totale</td>
						</tr>
						<xsl:for-each select="sup:SupplementRequest/sup:PartsSubTotals/sup:Item">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Name"/>:</td>
								<td class="data-right">
										<xsl:call-template name="totalamt">
											<xsl:with-param name="tot" select="sup:RatePercentage"/>
										</xsl:call-template>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Value"/>
										<xsl:with-param name="ptot" select="sup:PreviousValue"/>
										<xsl:with-param name="dtot" select="sup:ValueDelta"/>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="otherchargerssubtotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table9">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Costi aggiuntivi
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
		<xsl:if test="string-length(/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item) > 0">
			<tr>
				<td>
					<table width="80%" cellspacing="0" cellpadding="0" border="0" id="Table17">
						<tr>
							<td/>
							<td class="data-right">
								<xsl:for-each select="sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item">
									<xsl:if test="string-length(/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item/sup:RatePercentage) > 0">									
							Rate
									</xsl:if>
								</xsl:for-each>
							</td>
							<td class="data-right">Totale</td>
						</tr>
						<xsl:for-each select="sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Name"/>:</td>
								<td class="data-right">
									<xsl:if test="string-length(/sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item/sup:RatePercentage) > 0">
										<xsl:call-template name="totalamt">
											<xsl:with-param name="tot" select="sup:RatePercentage"/>
										</xsl:call-template>
									</xsl:if>
								</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Value"/>
										<xsl:with-param name="ptot" select="sup:PreviousValue"/>
										<xsl:with-param name="dtot" select="sup:ValueDelta"/>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="adjustments">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:Adjustments/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table12">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Adeguamenti
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:Adjustments/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:Adjustments/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:Adjustments/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
		<xsl:if test="string-length(sup:SupplementRequest/sup:Adjustments/sup:Item) > 0">
			<tr>
				<td>
					<table width="80%" cellspacing="0" cellpadding="0" border="0" ID="Table12_1">
						<xsl:for-each select="sup:SupplementRequest/sup:Adjustments/sup:Item">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Name"/>:</td>
								<td class="data-right">
									<xsl:call-template name="totalamt">
										<xsl:with-param name="tot" select="sup:Value"/>
										<xsl:with-param name="ptot" select="sup:PreviousValue"/>
										<xsl:with-param name="dtot" select="sup:ValueDelta"/>
									</xsl:call-template>
								</td>
							</tr>
						</xsl:for-each>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="grosstotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:GrossTotal/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table11">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		
		</td>
							<td class="data-right">
								<span class="data-field" style="font-size:14px;">Totale lordo:</span>
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:GrossTotal/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:GrossTotal/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:GrossTotal/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="nettotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:NetTotal/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table13">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		
		</td>
							<td class="data-right">
								<span class="data-field" style="font-size:14px;">Totale netto:</span>
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:NetTotal/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:NetTotal/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:NetTotal/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="totalamt">
		<xsl:param name="tot"/>
		<xsl:param name="ptot"/>
		<xsl:param name="dtot"/>
		<xsl:if test="string-length($ptot) >0">
			<span style="text-decoration: line-through;">
				<xsl:value-of select="$ptot"/>
			</span>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="string-length($ptot) >0">
				<span style="color: #3399FF">
					<xsl:value-of select="$tot"/>
				</span>
			</xsl:when>
			<xsl:otherwise>
				<span>
					<xsl:value-of select="$tot"/>
				</span>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="string-length($dtot) > 0">
			<span style="color: #FF0000;text-align: left;">[<xsl:value-of select="$dtot"/>]</span>
		</xsl:if>
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