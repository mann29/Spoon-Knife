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
	
	<xsl:template name="estimateprofile">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:EstimateProfile/sup:RateInfo) > 0">
			<tr>
				<td class="section-padding">
					<table border="0" cellpadding="0" cellspacing="0" width="50%" ID="Table5">
						<tr>
							<td class="subsection">
								<!--Subsection--> Profil de l'estimation&#160;:
		</td>
							<td class="subsection" align="left">
								<!--Subsection-->
								<xsl:value-of select="/sup:SupplementRequest/sup:EstimateProfile/sup:ProfileName" />
							</td>
							<tr>
								<td class="data-table-head-first" colspan="2" style="border-style: none">Taux de la main d'œuvre ($/h)</td>
							</tr>
							<tr>
								<td class="subsection-content">
									<!--div class="col-bg"></div-->
									<table width="50%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table4">
										<xsl:for-each select="//sup:RateInfo">
											<tr>
												<td class="data-field">
													<xsl:value-of select="sup:Type" />&#160;:</td>
												<xsl:if test="string-length(sup:PreviousRate) > 0">
													<td class="data" style="text-decoration: line-through;">
														<xsl:call-template name="convertPriceToFrenchFormat">
															<xsl:with-param name="value" select="sup:PreviousRate"/>
														</xsl:call-template>
													</td>
												</xsl:if>
												<xsl:choose>
													<xsl:when test="string-length(sup:PreviousRate) > 0">
														<td class="data" style="color: #3399FF">
															<xsl:call-template name="convertPriceToFrenchFormat">
																<xsl:with-param name="value" select="sup:Rate"/>
															</xsl:call-template>
														</td>
													</xsl:when>
													<xsl:otherwise>
														<td class="data">
															<xsl:call-template name="convertPriceToFrenchFormat">
																<xsl:with-param name="value" select="sup:Rate"/>
															</xsl:call-template>
														</td>
													</xsl:otherwise>
												</xsl:choose>
												<xsl:if test="string-length(sup:RateDelta) > 0">
													<td class="data" style="color: #FF0000">[<xsl:call-template name="convertPriceToFrenchFormat"><xsl:with-param name="value" select="sup:RateDelta"/></xsl:call-template>]</td>
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
					<!--Section--> Informations d'examen sur dossier
	</td>
			</tr>
			<xsl:call-template name="estimateprofile"/>
			<xsl:if test="string-length(/sup:SupplementRequest/sup:DeskReviewInfo/sup:Comments) > 0">
				<tr>
					<td class="subsection">
						<!--Subsection--> Commentaires d'examen sur dossier
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
			<xsl:call-template name="partssubtotal"/>
			<xsl:call-template name="laborsubtotal"/>
			<xsl:call-template name="materialssubtotal"/>
			<xsl:call-template name="otherchargerssubtotal"/>
			<xsl:call-template name="taxes"/>
			<xsl:call-template name="grosstotal"/>
			<xsl:call-template name="adjustments"/>
			<xsl:call-template name="nettotal"/>
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
						<xsl:value-of select="sup:Compliance/sup:Operation" />
					</td>
					<td class="data-table-row-end" colspan="10">
						<xsl:value-of select="sup:Compliance/sup:LineDesc" />
					</td>
				</tr>
			</xsl:if>
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
	<xsl:template name="opnWithDelta">
		<xsl:param name="nm"/>
		<xsl:param name="pnm"/>
		<xsl:param name="dnm"/>
		<xsl:choose>
			<xsl:when test="string-length($nm) > 0">
				<xsl:if test="string-length($pnm) > 0">
					<span style="text-decoration: line-through;">
						<xsl:call-template name="convertPriceToFrenchFormat">
							<xsl:with-param name="value" select="$pnm"/>
						</xsl:call-template> 
					</span>
					<br/>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="string-length($pnm) > 0">
						<span style="color: #3399FF">
							<xsl:call-template name="convertPriceToFrenchFormat">
								<xsl:with-param name="value" select="$nm"/>
							</xsl:call-template> 
						</span>
					</xsl:when>
					<xsl:otherwise>
						<span>
							<xsl:call-template name="convertPriceToFrenchFormat">
								<xsl:with-param name="value" select="$nm"/>
							</xsl:call-template> 
						</span>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:if test="string-length($dnm) > 0">
					<br/>
					<span style="color: #FF0000;text-align: left;">[<xsl:call-template name="convertPriceToFrenchFormat"><xsl:with-param name="value" select="$dnm"/></xsl:call-template>]</span>
				</xsl:if>
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
					<div class="col-bg-large">
						<span class="subsection">Annotations des rubriques</span>
					</div>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" class="data-table" ID="Table3">
						<tr>
							<td class="data-table-head-first" rowspan="2">Ligne</td>
							<td class="data-table-head" rowspan="2">Opération</td>
							<td class="data-table-head" rowspan="2">Description</td>
							<td class="data-table-head" colspan="3" style="border-bottom: 0px;">Pièces</td>
							<td class="data-table-head" colspan="2" style="border-bottom: 0px;">Prix</td>
							<td class="data-table-head" colspan="3" style="border-bottom: 0px;">Main d'œuvre</td>
							<td class="data-table-head-end" rowspan="2">Montant</td>
						</tr>
						<tr>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Type</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Nombre</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Quantité</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Unité</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Prolongé</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Type</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Heures</td>
							<td class="data-table-head" style="border-top: 1px solid #bebebe;">Coût</td>
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
<a href="{$vlink}">Lien</a>
</xsl:template>
	
	<xsl:template name="claimheader">
		<!--Section Supplement Request Info -->
		<tr>
			<td class="section">
				<!--Section--> Demande de supplément pour le numéro de sinistre <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" />
			</td>
		</tr>
		<tr>
<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:URLLink) > 0">
Pour voir l'affectation dans son ensemble, veuillez cliquer sur le lien suivant et entrer le numéro d'identification du véhicule associé à ce sinistre.<br/>
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
							<!--Subsection--> Informations administratives
		</td>
					</tr>
					<tr>
						<td class="subsection-content">
							<!--Subsection Content-->
							<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table2">
								<tr>
									<td width="25%" class="data-field">N° de sinistre:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClaimNumber" />
									</td>
									<td width="25%" class="data-field">ID estimation:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ClientEstimateId" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Envoyé&#160;:</td>
									<td width="25%" class="data">
										<xsl:call-template name="date">
											<!--xsl:with-param name="datestr" select="string(/sup:SupplementRequest/sup:AdminInfo/sup:SentDate)"/-->
										</xsl:call-template>
									</td>
									<td width="25%" class="data-field">Compagnie d'assurance:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:InsuranceCo" />
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
									<td width="25%" class="data-field">Téléphone:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Phone" />
									</td>
									<td width="25%" class="data-field">Téléphone:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Phone" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">E-mail&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Email" />
									</td>
									<td width="25%" class="data-field">E-mail&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Email" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Fax:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Fax" />
									</td>
									<td width="25%" class="data-field">Fax:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:SenderInfo/sup:Fax" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Adresse&#160;:</td>
									<td width="25%" class="data">
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address1" />
											<br/>
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Address2" />
											<br/>
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City) > 0">
											<xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:City" />
										</xsl:if>
										<xsl:if test="string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State) > 0 or string-length(/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip) > 0">
			, <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:State" /> <xsl:value-of select="/sup:SupplementRequest/sup:AdminInfo/sup:ReceipientInfo/sup:Zip" />
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
							<!--Subsection--> Information sur le véhicule
		</td>
					</tr>
					<tr>
						<td class="subsection-content">
							<!--Subsection Content-->
							<table width="100%" border="0" cellspacing="0" cellpadding="0" ID="Table6">
								<tr>
									<td width="25%" class="data-field">Année&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Year" />
									</td>
									<td width="25%" class="data-field"> Nom du propriétaire du véhicule&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:OwnerName" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Marque&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Make" />
									</td>
									<td width="25%" class="data-field">Couleur:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Color" />
									</td>
								</tr>
								<tr>
									<td width="25%" class="data-field">Modèle&#160;:</td>
									<td width="25%" class="data">
										<xsl:value-of select="/sup:SupplementRequest/sup:VehicleInfo/sup:Model" />
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
				<!--Section--> Instructions du supplément pour les estimations

	</td>
		</tr>
		<tr>
			<td class="subsection-content">
				<!--Subsection Content-->
				<table>
					<tr>
						<td class="data-right">1.&#160;</td>
						<td class="data">Ouvrir le gestionnaire eClaim</td>
					</tr>
					<tr>
						<td class="data-right">2.&#160;</td>
						<td class="data">Trouvez le dossier Sinistre dans le gestionnaire eClaim et ouvrez-le</td>
					</tr>
					<tr>
						<td class="data-right">3.&#160;</td>
						<td class="data">Faites l'une des opérations suivantes&#160;:</td>
					</tr>
					<tr>
						<td class="data-right"> </td>
						<td class="data">Si vous utilisez Ultramate&#160;:</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3a.&#160;Lancez Ultramate à partir du gestionnaire eClaim.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3b.&#160;Mettez à jour et calculez l'estimation, puis cliquez sur le bouton eCM pour valider l'estimation et revenir au gestionnaire eClaim.</td>
					</tr>
					<tr>
						<td class="data-right"> </td>
						<td class="data">Si vous n'utilisez pas Ultramate&#160;:</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3a.&#160;Ouvrez votre logiciel d'estimation.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3b.&#160;Mettez à jour puis validez ou verrouillez l'estimation.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3c.&#160;Exportez l'EMS de l'estimation à partir de votre logiciel d'estimation.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3d.&#160;Créez une image d'impression de l'estimation au format PDF.</td>
					</tr>
					<tr>
						<td class="data-right">   </td>
						<td class="data">3e.&#160;Dans le gestionnaire eClaim, importez le supplément EMS et le fichier PDF.</td>
					</tr>
					<tr>
						<td class="data-right">4.&#160;</td>
						<td class="data">Dans le gestionnaire eClaim, marquez le dossier comment étant Prêt à l'envoi puis cliquez sur le bouton éclair.</td>
					</tr>
				</table>
			</td>
		</tr>
		</xsl:if>
	</xsl:template>
	<xsl:template name="conf">
		<tr>
			<td class="section">
				<!--Section--> Déclaration de confidentialité
	</td>
		</tr>
		<tr>
			<td class="subsection-content">
				<!--Subsection Content-->
				<span class="data" style="text-align: center;">Les informations contenues dans ce message sont CONFIDENTIELLES et uniquement à l'intention du destinataire. <br/> Toute utilisation non autorisée et dissémination des informations ou copie de ce message est interdite. <br/> Si vous n'êtes pas le destinataire prévu, veuillez aviser l'expéditeur immédiatement et supprimez ce message.<br/>
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
		
		Sous-totaux main-d'œuvre
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
							<td class="data-right">Heures</td>
							<td class="data-right">Taux</td>
							<td class="data-right">Total</td>
						</tr>
						<xsl:for-each select="sup:SupplementRequest/sup:LaborSubTotals/sup:LaborLine">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Type" />&#160;:</td>
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
		
		Sous-totaux pièces
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
	</xsl:template>
	<xsl:template name="materialssubtotal">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:MaterialsSubtotals/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table8">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Sous-totaux des matériaux
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:MaterialsSubtotals/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:MaterialsSubtotals/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:MaterialsSubtotals/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
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
		
		Sous-totaux autres charges
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
					<table width="80%" cellspacing="0" cellpadding="0" border="0" ID="Table9_1">
						<xsl:for-each select="sup:SupplementRequest/sup:OtherChargesSubtotals/sup:Item">
							<tr>
								<td class="data-right">
									<xsl:value-of select="sup:Name" />&#160;:</td>
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
	<xsl:template name="taxes">
		<xsl:if test="string-length(/sup:SupplementRequest/sup:Taxes/sup:Total) > 0">
			<tr>
				<td>
					<table width="100%" cellspacing="0" cellpadding="0" border="0" ID="Table10">
						<tr>
							<td class="data-field" style="text-align: left; font-size: 14px;">
		
		Taxes
		</td>
							<td class="data-right">
								<xsl:call-template name="totalamt">
									<xsl:with-param name="tot" select="/sup:SupplementRequest/sup:Taxes/sup:Total"/>
									<xsl:with-param name="ptot" select="/sup:SupplementRequest/sup:Taxes/sup:PreviousTotal"/>
									<xsl:with-param name="dtot" select="/sup:SupplementRequest/sup:Taxes/sup:TotalDelta"/>
								</xsl:call-template>
							</td>
						</tr>
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
		
		Ajustements
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
									<xsl:value-of select="sup:Name" />&#160;:</td>
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
								<span class="data-field" style="font-size:14px;">Total brut&#160;:</span>
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
								<span class="data-field" style="font-size:14px;">Total net&#160;:</span>
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
				<!-- <xsl:value-of select="$ptot" /> -->
				<xsl:call-template name="convertPriceToFrenchFormat">
					<xsl:with-param name="value" select="$ptot"/>
				</xsl:call-template> 
			</span>
		</xsl:if>
		<xsl:choose>
			<xsl:when test="string-length($ptot) >0">
				<span style="color: #3399FF"> 
					<!-- <xsl:value-of select="$tot" /> -->
					<xsl:call-template name="convertPriceToFrenchFormat">
						<xsl:with-param name="value" select="$tot"/>
					</xsl:call-template>  
				</span>
			</xsl:when>
			<xsl:otherwise>
				<span> 
					<!-- <xsl:value-of select="$tot" /> -->
					<xsl:call-template name="convertPriceToFrenchFormat">
						<xsl:with-param name="value" select="$tot"/>
					</xsl:call-template>  
				</span>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:if test="string-length($dtot) > 0">
			<span style="color: #FF0000;text-align: left;">[<xsl:call-template name="convertPriceToFrenchFormat"><xsl:with-param name="value" select="$dtot"/></xsl:call-template>]</span>
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