<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sch="http://www.mitchell.com/schemas" xmlns:bms="http://www.cieca.com/BMS" xmlns:app="http://www.mitchell.com/schemas/appraisalassignment" xmlns:wor="http://www.mitchell.com/schemas/workassignment">
	<xsl:output method="xml" version="1.0" encoding="UTF-8" indent="yes"/>
	<xsl:template match="/">
		<xsl:element name="root">
			<xsl:apply-templates select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq"/>
	<xsl:apply-templates select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo"/>

<xsl:apply-templates select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/wor:AdditionalTaskConstraints"/>

	</xsl:element>
	</xsl:template>
	<xsl:template match="bms:AssignmentAddRq">
		<xsl:if test="string(bms:AdminInfo/bms:Sender/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum)">
			<xsl:element name="Assignment_Send_By">
				<xsl:value-of select="bms:AdminInfo/bms:Sender/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum"/>
			</xsl:element>
		</xsl:if>
		
		<xsl:for-each select="/sch:MitchellEnvelope/sch:EnvelopeContext/sch:NameValuePair">
		
		<xsl:if test="sch:Name= 'ClaimId'">
		<xsl:element name="CLAIM_ID">
				<xsl:value-of select="sch:Value"/>
		</xsl:element>
		</xsl:if>
		
		<xsl:if test="sch:Name= 'ExposureId'">
		<xsl:element name="CLAIM_EXPOSURE_ID">
				<xsl:value-of select="sch:Value"/>
		</xsl:element>
		</xsl:if>
		
	

		
		</xsl:for-each>
		
		<xsl:if test="string(bms:EventInfo/bms:AssignmentEvent/bms:CreateDateTime)">
			<xsl:element name="Assignment_Created_Date">
				<xsl:value-of select="bms:EventInfo/bms:AssignmentEvent/bms:CreateDateTime"/>
			</xsl:element>
		</xsl:if>
		
		
		
			<xsl:if test="string(bms:DocumentInfo/bms:DocumentVer/bms:DocumentVerCode)">
			<xsl:element name="Document_Version_Code">
				<xsl:value-of select="bms:DocumentInfo/bms:DocumentVer/bms:DocumentVerCode"/>
			</xsl:element>
		</xsl:if>
		
		<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:RelatedEstimateDocumentID)">
			<xsl:element name="Related_Est_Doc_ID_of_Supp">
				<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:RelatedEstimateDocumentID"/>
			</xsl:element>
		</xsl:if>
		
				<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:SupplementConvertedToOriginalFlag)">
			<xsl:element name="Supp_Converted_To_Original_Flag">
				<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:SupplementConvertedToOriginalFlag"/>
			</xsl:element>
		</xsl:if>
		
		<xsl:if test="string(bms:AdminInfo/bms:Submitter/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDQualifierCode)">
			<xsl:element name="Created_By_UserID_Qualifier">
				<xsl:value-of select="bms:AdminInfo/bms:Submitter/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDQualifierCode
"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Submitter/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum
)">
			<xsl:element name="Created_By_UserID">
				<xsl:value-of select="bms:AdminInfo/bms:Submitter/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum

"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDQualifierCode
)">
			<xsl:element name="Adjuster_User_ID_Qualifier">
				<xsl:value-of select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDQualifierCode
"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum)">
			<xsl:element name="Adjuster_UserID">
				<xsl:value-of select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:IDInfo/bms:IDNum"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Adjuster/bms:Affiliation)">
			<xsl:element name="Adjuster_Affiliation">
				<xsl:value-of select="bms:AdminInfo/bms:Adjuster/bms:Affiliation"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName)">
			<xsl:element name="Adjuster_Last_Name">
				<xsl:value-of select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName)">
			<xsl:element name="Adjuster_First_Name">
				<xsl:value-of select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName"/>
			</xsl:element>
		</xsl:if>
		<xsl:for-each select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:PersonInfo/bms:Communications">
			<xsl:if test="bms:CommQualifier= 'AL'">
				<xsl:element name="Adjuster_Address_CommQualifier">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:Address/bms:Address1)">
					<xsl:element name="Adjuster_Address_Line_1">
						<xsl:value-of select="bms:Address/bms:Address1"/>
					</xsl:element>
				</xsl:if>
				<xsl:if test="string(bms:Address/bms:City)">
					<xsl:element name="Adjuster_City">
						<xsl:value-of select="bms:Address/bms:City"/>
					</xsl:element>
				</xsl:if>
				<xsl:if test="string(bms:Address/bms:StateProvince)">
					<xsl:element name="Adjuster_State_Province">
						<xsl:value-of select="bms:Address/bms:StateProvince"/>
					</xsl:element>
				</xsl:if>
				<xsl:if test="string(bms:Address/bms:PostalCode)">
					<xsl:element name="Adjuster_Zip">
						<xsl:value-of select="bms:Address/bms:PostalCode"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
			</xsl:for-each>
			<xsl:for-each select="bms:AdminInfo/bms:Adjuster/bms:Party/bms:ContactInfo/bms:Communications">
			<xsl:if test="bms:CommQualifier= 'HP'">
				<xsl:element name="Adjuster_CommQualifier_HP">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:CommPhone)">
					<xsl:element name="Adjuster_Home_Phone">
						<xsl:value-of select="bms:CommPhone"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
			<xsl:if test="bms:CommQualifier= 'WP'">
				<xsl:element name="Adjuster_CommQualifier_WP">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:CommPhone)">
					<xsl:element name="Adjuster_Work_Phone">
						<xsl:value-of select="bms:CommPhone"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
			<xsl:if test="bms:CommQualifier= 'CP'">
				<xsl:element name="Adjuster_CommQualifier_CP">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:CommPhone)">
					<xsl:element name="Adjuster_Cell_Phone">
						<xsl:value-of select="bms:CommPhone"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
			<xsl:if test="bms:CommQualifier= 'FX'">
				<xsl:element name="Adjuster_CommQualifier_FX">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:CommPhone)">
					<xsl:element name="Adjuster_Fax_Number">
						<xsl:value-of select="bms:CommPhone"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
			<xsl:if test="bms:CommQualifier= 'EM'">
				<xsl:element name="Adjuster_Email_CommQualifier">
					<xsl:value-of select="bms:CommQualifier"/>
				</xsl:element>
				<xsl:if test="string(bms:CommEmail)">
					<xsl:element name="Adjuster_Email">
						<xsl:value-of select="bms:CommEmail"/>
					</xsl:element>
				</xsl:if>
			</xsl:if>
		</xsl:for-each>
		<xsl:if test="string(bms:ClaimInfo/bms:PolicyInfo/bms:PolicyNum)">
			<xsl:element name="Policy_Number">
				<xsl:value-of select="bms:ClaimInfo/bms:PolicyInfo/bms:PolicyNum"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:DeductibleInfo/bms:DeductibleStatus)">
			<xsl:element name="Deductible_Status">
				<xsl:value-of select="bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:DeductibleInfo/bms:DeductibleStatus"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="string(bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:DeductibleInfo/bms:DeductibleAmt)">
			<xsl:element name="Deductible_Amount">
				<xsl:value-of select="bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:DeductibleInfo/bms:DeductibleAmt"/>
			</xsl:element>
		</xsl:if>
		<xsl:if test="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:PrimaryContactType='Insured'">
			<xsl:element name="Insured_Primary_Contact">
				<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:PrimaryContactType"/>
			</xsl:element>
			</xsl:if>
			
			<xsl:if test="string(bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName)">
				<xsl:element name="Insured_First_Name">
					<xsl:value-of select="bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName)">
				<xsl:element name="Insured_Last_Name">
					<xsl:value-of select="bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName"/>
				</xsl:element>
			</xsl:if>
			<xsl:for-each select="bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:PersonInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Insured_Address_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Insured_Address_Line_1">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:Address2)">
						<xsl:element name="Insured_Address_Line_2">
							<xsl:value-of select="bms:Address/bms:Address2"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Insured_City">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Insured_State_Province">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Insured_Zip">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="bms:AdminInfo/bms:PolicyHolder/bms:Party/bms:ContactInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'HP'">
					<xsl:element name="Insured_CommQualifier_HP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Insured_Home_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'WP'">
					<xsl:element name="Insured_CommQualifier_WP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Insured_Work_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'CP'">
					<xsl:element name="Insured_CommQualifier_CP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Insured_Cell_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'FX'">
					<xsl:element name="Insured_CommQualifier_FX">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Insured_Fax_Number">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'EM'">
					<xsl:element name="Insured_Email_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommEmail)">
						<xsl:element name="Insured_Email">
							<xsl:value-of select="bms:CommEmail"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
		<xsl:if test="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:PrimaryContactType='Claimant'">
			<xsl:element name="Claimant_Primary_Contact">
				<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:PrimaryContactType"/>
			</xsl:element>
		</xsl:if>
			<xsl:if test="string(bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName)">
				<xsl:element name="Claimant_First_Name">
					<xsl:value-of select="bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName)">
				<xsl:element name="Claimant_Last_Name">
					<xsl:value-of select="bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName"/>
				</xsl:element>
			</xsl:if>
			<xsl:for-each select="bms:AdminInfo/bms:Claimant/bms:Party/bms:PersonInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Claimant_Address_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Claimant_Address_Line_1">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:Address2)">
						<xsl:element name="Claimant_Address_Line_2">
							<xsl:value-of select="bms:Address/bms:Address2"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Claimant_City">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Claimant_State_Province">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Claimant_Zip">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="bms:AdminInfo/bms:Claimant/bms:Party/bms:ContactInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'HP'">
					<xsl:element name="Claimant_CommQualifier_HP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Claimant_Home_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'WP'">
					<xsl:element name="Claimant_CommQualifier_WP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Claimant_Work_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'CP'">
					<xsl:element name="Claimant_CommQualifier_CP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Claimant_Cell_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'FX'">
					<xsl:element name="Claimant_CommQualifier_FX">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Claimant_Fax_Number">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'EM'">
					<xsl:element name="Claimant_Email_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommEmail)">
						<xsl:element name="Claimant_Email">
							<xsl:value-of select="bms:CommEmail"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="string(bms:AdminInfo/bms:Owner/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName)">
				<xsl:element name="Owner_First_Name">
					<xsl:value-of select="bms:AdminInfo/bms:Owner/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:AdminInfo/bms:Owner/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName)">
				<xsl:element name="Owner_Last_Name">
					<xsl:value-of select="bms:AdminInfo/bms:Owner/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName"/>
				</xsl:element>
			</xsl:if>
			<xsl:for-each select="bms:AdminInfo/bms:Owner/bms:Party/bms:PersonInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Owner_Address_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Owner_Address_Line_1">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:Address2)">
						<xsl:element name="Owner_Address_Line_2">
							<xsl:value-of select="bms:Address/bms:Address2"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Owner_City">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Owner_State_Province">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Owner_Zip">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="bms:AdminInfo/bms:Owner/bms:Party/bms:ContactInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'HP'">
					<xsl:element name="Owner_CommQualifier_HP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Owner_Home_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'WP'">
					<xsl:element name="Owner_CommQualifier_WP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Owner_Work_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'CP'">
					<xsl:element name="Owner_CommQualifier_CP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Owner_Cell_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'FX'">
					<xsl:element name="Owner_CommQualifier_FX">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Owner_Fax_Number">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'EM'">
					<xsl:element name="Owner_Email_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommEmail)">
						<xsl:element name="Owner_Email">
							<xsl:value-of select="bms:CommEmail"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VINInfo/bms:VINAvailabilityCode)">
				<xsl:element name="VIN_Availability">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VINInfo/bms:VINAvailabilityCode"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VINInfo/bms:VIN/bms:VINNum)">
				<xsl:element name="VIN_Number">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VINInfo/bms:VIN/bms:VINNum"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:ModelYear
)">
				<xsl:element name="Vehicle_Year">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:ModelYear
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:MakeDesc
)">
				<xsl:element name="Vehicle_Make">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:MakeDesc
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:ModelName
)">
				<xsl:element name="Vehicle_Model">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:ModelName
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:SubModelDesc
)">
				<xsl:element name="Vehicle_Sub_Model">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:SubModelDesc
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:VehicleType
)">
				<xsl:element name="Vehicle_Type">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:VehicleType
"/>
				</xsl:element>
			</xsl:if>
			
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Body/bms:BodyStyle
)">
				<xsl:element name="Vehicle_Body_Style">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Body/bms:BodyStyle
"/>
				</xsl:element>
			</xsl:if>
			
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:EngineDesc
)">
				<xsl:element name="Vehicle_Engine_Desc">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:EngineDesc
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:TransmissionInfo/bms:TransmissionDesc
)">
				<xsl:element name="Vehicle_Transmission">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:TransmissionInfo/bms:TransmissionDesc
"/>
				</xsl:element>
			</xsl:if>
			<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:Configuration
)">
				<xsl:element name="Vehicle_Drive_Train">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Powertrain/bms:Configuration
"/>
				</xsl:element>
			</xsl:if>
		<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerInd
)">
				<xsl:element name="Vehicle_Mileage_Indicator">
				<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerInd
"/>
				</xsl:element>
			</xsl:if>
		<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerReadingCode)">	
		<xsl:element name="Vehicle_Mileage_Reading">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerReadingCode"/>	
	</xsl:element>
		</xsl:if>
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerReading)">		<xsl:element name="Vehicle_Mileage">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:VehicleDesc/bms:OdometerInfo/bms:OdometerReading"/>	
	</xsl:element>
	</xsl:if>
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Paint/bms:Exterior/bms:Color/bms:ColorName)">		<xsl:element name="Vehicle_Exterior_Color">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Paint/bms:Exterior/bms:Color/bms:ColorName"/>	</xsl:element>
	</xsl:if>
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateNum)">		<xsl:element name="Vehicle_License_Plate">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateNum"/>	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateExpirationDate)">		<xsl:element name="Vehicle_License_Expiration">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateExpirationDate"/>	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateStateProvince)">		<xsl:element name="Vehicle_License_Plate_State">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:License/bms:LicensePlateStateProvince"/>	</xsl:element>
	</xsl:if>
	

	<xsl:if test="string(bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Condition/bms:DrivableInd)">		<xsl:element name="Drivable_Indicator">
		<xsl:value-of select="bms:VehicleDamageAssignment/bms:VehicleInfo/bms:Condition/bms:DrivableInd"/>	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:VehicleDetails/app:VehicleTypeAmbiguity/app:Value)">
	<xsl:element name="Vehicle_Type_Ambiguity_Value">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:VehicleDetails/app:VehicleTypeAmbiguity/app:Value"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:TotalLossInd)">
	<xsl:element name="Total_Loss_Indicator">
	<xsl:value-of select="bms:ClaimInfo/bms:LossInfo/bms:TotalLossInd"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:PrimaryPOI/bms:POICode)">
	<xsl:element name="Primary_POI">
	<xsl:value-of select="bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:PrimaryPOI/bms:POICode"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:SecondaryPOI/bms:POICode)">
	<xsl:element name="Secondary_POI">
    <xsl:for-each select="bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:SecondaryPOI/bms:POICode">
    <xsl:value-of select="."/>
        <xsl:if test="not (position() = last())">
            <xsl:text>,</xsl:text>
        </xsl:if>
    </xsl:for-each>
    </xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:CoverageCategory)">
	<xsl:element name="Type_of_Loss">
	<xsl:value-of select="bms:ClaimInfo/bms:PolicyInfo/bms:CoverageInfo/bms:Coverage/bms:CoverageCategory"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:LossDateTime)">
	<xsl:element name="Date_of_Loss">
	<xsl:value-of select="bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:LossDateTime"/>
	</xsl:element>
	</xsl:if>

	<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:ReportedDateTime)">
	<xsl:element name="Date_Reported">
	<xsl:value-of select="bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:ReportedDateTime"/>
	</xsl:element>
	</xsl:if>
	

	
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:EstimatorIDs/bms:RoutingIDInfo/bms:IDNum)">
	<xsl:element name="Dispatch_Center">
	<xsl:value-of select="bms:VehicleDamageAssignment/bms:EstimatorIDs/bms:RoutingIDInfo/bms:IDNum"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:AdminInfo/bms:Estimator/bms:Affiliation)">
	<xsl:element name="APPRAISAL_AFFLIATION">
	<xsl:value-of select="bms:AdminInfo/bms:Estimator/bms:Affiliation"/>
	</xsl:element>
	</xsl:if>
	
	
	<xsl:if test="string(bms:VehicleDamageAssignment/bms:EstimatorIDs/bms:CurrentEstimatorID)">
	<xsl:element name="ASSIGNEE_ID">
	<xsl:value-of select="bms:VehicleDamageAssignment/bms:EstimatorIDs/bms:CurrentEstimatorID"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:AdminInfo/bms:Estimator/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName)">
	<xsl:element name="Appraisal_Resource_Last_Name">
	<xsl:value-of select="bms:AdminInfo/bms:Estimator/bms:Party/bms:PersonInfo/bms:PersonName/bms:LastName"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:AdminInfo/bms:Estimator/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName)">
	<xsl:element name="Appraisal_Resource_First_Name">
	<xsl:value-of select="bms:AdminInfo/bms:Estimator/bms:Party/bms:PersonInfo/bms:PersonName/bms:FirstName"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:for-each select="bms:AdminInfo/bms:Estimator/bms:Party/bms:PersonInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Appraisal_Resource_Address_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Appraisal_Resource_Address_Line_1">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Appraisal_Resource_City">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Appraisal_Resource_State">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Appraisal_Resource_Zip">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				</xsl:for-each>
				<xsl:for-each select="bms:AdminInfo/bms:Estimator/bms:Party/bms:ContactInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'HP'">
					<xsl:element name="Appraisal_Resource_CommQualifier_HP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Appraisal_Resource_Home_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'WP'">
					<xsl:element name="Appraisal_Resource_CommQualifier_WP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Appraisal_Resource_Work_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'CP'">
					<xsl:element name="Appraisal_Resource_CommQualifier_CP">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Appraisal_Resource_Cell_Phone">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'FX'">
					<xsl:element name="Appraisal_Resource_CommQualifier_FX">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommPhone)">
						<xsl:element name="Appraisal_Resource_Fax_Number">
							<xsl:value-of select="bms:CommPhone"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
				<xsl:if test="bms:CommQualifier= 'EM'">
					<xsl:element name="Appraisal_Resource_Email_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:CommEmail)">
						<xsl:element name="Appraisal_Resource_Email">
							<xsl:value-of select="bms:CommEmail"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
			</xsl:for-each>
	
	<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:InspectionSiteType)">
	<xsl:element name="Vehicle_Inspection_Type">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:InspectionSiteType"/>
	</xsl:element>
	</xsl:if>

	<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:InspectionSiteGeoCode/app:AddressValidStatus)">
	<xsl:element name="Vehicle_Inspection_Site_Address_Validation">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssignmentDetails/app:InspectionSiteGeoCode/app:AddressValidStatus"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:AdminInfo/bms:InspectionSite/bms:Party/bms:ContactInfo/bms:ContactName/bms:FirstName)">
	<xsl:element name="Vehicle_Inspection_Site_Contact_First_Name_Home">
	<xsl:value-of select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:ContactInfo/bms:ContactName/bms:FirstName"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:if test="string(bms:AdminInfo/bms:InspectionSite/bms:Party/bms:ContactInfo/bms:ContactName/bms:LastName)">
	<xsl:element name="Vehicle_Inspection_Site_Contact_Last_Name_Home">
	<xsl:value-of select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:ContactInfo/bms:ContactName/bms:LastName"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:for-each select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:PersonInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Vehicle_Inspection_Site_Address_CommQualifier_Home">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Vehicle_Inspection_Site_Address_Line_1_Home">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:Address2)">
						<xsl:element name="Vehicle_Inspection_Site_Address_line_2_Home">
							<xsl:value-of select="bms:Address/bms:Address2"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Vehicle_Inspection_Site_City_Home">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Vehicle_Inspection_Site_State_Province_Home">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Vehicle_Inspection_Site_Zip_Home">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
		</xsl:for-each>
	
	<xsl:for-each select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:ContactInfo/bms:Communications">
		<xsl:if test="bms:CommQualifier= 'HP'">
			<xsl:element name="Vehicle_Inspection_Site_CommQualifier">
			<xsl:value-of select="bms:CommQualifier"/>
			</xsl:element>
			<xsl:if test="string(bms:CommPhone)">
			<xsl:element name="Vehicle_Inspection_Site_Contact_Phone">
			<xsl:value-of select="bms:CommPhone"/>
			</xsl:element>
			</xsl:if>
		</xsl:if>
	</xsl:for-each>
	
	<xsl:if test="string(bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:CompanyName)">
	<xsl:element name="Vehicle_Inspection_Site_Company_Name">
	<xsl:value-of select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:CompanyName"/>
	</xsl:element>
	</xsl:if>
	
	<xsl:for-each select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:Communications">
				<xsl:if test="bms:CommQualifier= 'AL'">
					<xsl:element name="Vehicle_Inspection_Site_Address_CommQualifier">
						<xsl:value-of select="bms:CommQualifier"/>
					</xsl:element>
					<xsl:if test="string(bms:Address/bms:Address1)">
						<xsl:element name="Vehicle_Inspection_Site_Address_Line_1">
							<xsl:value-of select="bms:Address/bms:Address1"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:Address2)">
						<xsl:element name="Vehicle_Inspection_Site_Address_line_2">
							<xsl:value-of select="bms:Address/bms:Address2"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:City)">
						<xsl:element name="Vehicle_Inspection_Site_City">
							<xsl:value-of select="bms:Address/bms:City"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:StateProvince)">
						<xsl:element name="Vehicle_Inspection_Site_State_Province">
							<xsl:value-of select="bms:Address/bms:StateProvince"/>
						</xsl:element>
					</xsl:if>
					<xsl:if test="string(bms:Address/bms:PostalCode)">
						<xsl:element name="Vehicle_Inspection_Site_Zip">
							<xsl:value-of select="bms:Address/bms:PostalCode"/>
						</xsl:element>
					</xsl:if>
				</xsl:if>
		</xsl:for-each>
	<xsl:if test="string(bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:IDInfo/bms:IDQualifierCode)">
	<xsl:element name="Vehicle_Inspection_Site_DRP_UserID_Qualifier">
	<xsl:value-of select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:IDInfo/bms:IDQualifierCode"/>
	</xsl:element>
	</xsl:if>

<xsl:if test="string(bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:IDInfo/bms:IDNum)">
	<xsl:element name="Vehicle_Inspection_Site_DRP_UserID">
	<xsl:value-of select="bms:AdminInfo/bms:InspectionSite/bms:Party/bms:OrgInfo/bms:IDInfo/bms:IDNum"/>
	</xsl:element>
	</xsl:if>

<xsl:if test="string(bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:DamageMemo)">
	<xsl:element name="DamageDescription">
	<xsl:value-of select="bms:ClaimInfo/bms:LossInfo/bms:Facts/bms:DamageMemo"/>
	</xsl:element>
	</xsl:if>

<!-- Adding ExternalAssignmentId in appLogging -->
<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:PartnerKey)">
	<xsl:element name="EXTERNAL_ASSIGNMENT_ID">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:PartnerKey"/>
	</xsl:element>
</xsl:if>	
	
	
</xsl:template>

<xsl:template match="app:AdditionalAppraisalAssignmentInfo">

<xsl:if test="string(app:AssignmentDetails/app:InspectionSiteGeoCode/app:Longitude)">
	<xsl:element name="Vehicle_Inspection_Site_Longitude">
	<xsl:value-of select="app:AssignmentDetails/app:InspectionSiteGeoCode/app:Longitude"/>
	</xsl:element>
	</xsl:if>
	
	
<xsl:if test="string(app:AssignmentDetails/app:InspectionSiteGeoCode/app:Latitude)">
	<xsl:element name="Vehicle_Inspection_Site_Latitude">
	<xsl:value-of select="app:AssignmentDetails/app:InspectionSiteGeoCode/app:Latitude"/>
	</xsl:element>
</xsl:if>
	
<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:AssignmentMemo)">
	<xsl:element name="Assignment_Notes">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:VehicleDamageAssignment/bms:AssignmentMemo"/>
	</xsl:element>
</xsl:if>

<!-- Adding ExternalAssignmentId in appLogging -->
<!--<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:PartnerKey)">
	<xsl:element name="EXTERNAL_ASSIGNMENT_ID">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/bms:CIECA/bms:AssignmentAddRq/bms:PartnerKey"/>
	</xsl:element>
</xsl:if>-->

<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/wor:AdditionalTaskConstraints/wor:ScheduleConstraints/wor:Duration
)">
	<xsl:element name="Assignment_Duration">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/wor:AdditionalTaskConstraints/wor:ScheduleConstraints/wor:Duration
"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/wor:AdditionalTaskConstraints/wor:ScheduleConstraints/wor:Priority
)">
	<xsl:element name="Schedule__Preferences_Urgency">
	<xsl:value-of select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/wor:AdditionalTaskConstraints/wor:ScheduleConstraints/wor:Priority
"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AssignmentDetails/app:IsTravelRequiredFlag)">
	<xsl:element name="Travel_Time_Required">
	<xsl:value-of select="app:AssignmentDetails/app:IsTravelRequiredFlag"/>
	</xsl:element>
</xsl:if>
	
<xsl:if test="string(app:AssociatedAttachments/app:EstAnnotationsAttachment/app:DocumentID)">
	<xsl:element name="Attach_Estimate_Anotations">
	<xsl:value-of select="app:AssociatedAttachments/app:EstAnnotationsAttachment/app:DocumentID"/>
	</xsl:element>
</xsl:if>

<xsl:for-each select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssociatedAttachments/app:FileAttachments/app:FileAttachment">

<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssociatedAttachments/app:FileAttachments/app:FileAttachment/app:AttachmentType)">
	<xsl:element name="Attachment_Type">
	<xsl:value-of select="app:AttachmentType"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:AssociatedAttachments/app:FileAttachments/app:FileAttachment/app:FileName)">
	<xsl:element name="Artifacts_FileName">
	<xsl:value-of select="app:FileName"/>
	</xsl:element>
</xsl:if>

</xsl:for-each>

<xsl:variable name="tempSelectedMOI" select="/sch:MitchellEnvelope/sch:EnvelopeBodyList/sch:EnvelopeBody/sch:Content/app:AdditionalAppraisalAssignmentInfo/app:MOIDetails/app:TempUserSelectedMOI/app:MethodOfInspection"></xsl:variable>

<xsl:if test="$tempSelectedMOI= 'FI' or $tempSelectedMOI= 'IA' or $tempSelectedMOI= 'NNS' or $tempSelectedMOI= 'NSDO'  ">

	<xsl:if test="string(app:NotificationDetails/app:NotificationEmailTo/@NotifyRecipients)">
		<xsl:element name="Secondary_Notification_To_Notify_Recipients">
		<xsl:value-of select="app:NotificationDetails/app:NotificationEmailTo/@NotifyRecipients"/>
		</xsl:element>
	</xsl:if>

	<xsl:if test="string(app:NotificationDetails/app:NotificationEmailTo/app:EmailAddress)">
		<xsl:element name="Secondary_Notification_To_Email_Address">
		<xsl:value-of select="app:NotificationDetails/app:NotificationEmailTo/app:EmailAddress"/>
		</xsl:element>
	</xsl:if>

	<xsl:if test="string(app:NotificationDetails/app:NotificationEmailCC/@NotifyRecipients)">
		<xsl:element name="Secondary_Notification_CC_Notify_Recipients">
		<xsl:value-of select="app:NotificationDetails/app:NotificationEmailCC/@NotifyRecipients"/>
		</xsl:element>
	</xsl:if>

	<xsl:if test="string(app:NotificationDetails/app:NotificationEmailCC/app:EmailAddress)">
		<xsl:element name="Secondary_Notification_CC_Email_Address">
		<xsl:value-of select="app:NotificationDetails/app:NotificationEmailCC/app:EmailAddress"/>
		</xsl:element>
	</xsl:if>

	<xsl:if test="string(app:NotificationDetails/app:NotificationFax/@NotifyRecipients)">
		<xsl:element name="Secondary_Notification_Fax__Notify_Recipients">
		<xsl:value-of select="app:NotificationDetails/app:NotificationFax/@NotifyRecipients"/>
		</xsl:element>
	</xsl:if>

	<xsl:if test="string(app:NotificationDetails/app:NotificationFax/app:FaxNumber)">
		<xsl:element name="Secondary_Notification_Fax_Number">
		<xsl:value-of select="app:NotificationDetails/app:NotificationFax/app:FaxNumber"/>
		</xsl:element>
	</xsl:if>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyType)">
	<xsl:element name="PropertyTypeCd">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyType"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyDescription)">
	<xsl:element name="PropertyDescription">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyDescription"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:Address1)">
	<xsl:element name="PropertyAddress1">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:Address1"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:Address2)">
	<xsl:element name="PropertyAddress2">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:Address2"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:City)">
	<xsl:element name="PropertyCity">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:City"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:StateProvince)">
	<xsl:element name="PropertyState">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:StateProvince"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:PostalCode)">
	<xsl:element name="PropertyZIPCode">
	<xsl:value-of select="app:PropertyInfo/app:PropertyDamageAssignment/app:PropertyAddress/app:PostalCode"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AdditionalAssignmentDetails/app:ArrangeRentalFlag)">
	<xsl:element name="ArrangeRentalInd">
	<xsl:value-of select="app:AdditionalAssignmentDetails/app:ArrangeRentalFlag"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AdditionalAssignmentDetails/app:ArrangeTowFlag)">
	<xsl:element name="ArrangeTowInd">
	<xsl:value-of select="app:AdditionalAssignmentDetails/app:ArrangeTowFlag"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AdditionalAssignmentDetails/app:TowDetails/app:PermissionGivenBy)">
	<xsl:element name="PermissionGivenBy">
	<xsl:value-of select="app:AdditionalAssignmentDetails/app:TowDetails/app:PermissionGivenBy"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AdditionalAssignmentDetails/app:TowDetails/app:TowNotes)">
	<xsl:element name="TowNotes">
	<xsl:value-of select="app:AdditionalAssignmentDetails/app:TowDetails/app:TowNotes"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AssignmentDetails/app:VehicleLocationZIPCode)">
	<xsl:element name="VehicleLocationZIPCode">
	<xsl:value-of select="app:AssignmentDetails/app:VehicleLocationZIPCode"/>
	</xsl:element>
</xsl:if>


<xsl:if test="string(app:AssignmentDetails/app:VehicleLocationZIPCodeSource)">
	<xsl:element name="VehicleLocationZIPCodeSource">
	<xsl:value-of select="app:AssignmentDetails/app:VehicleLocationZIPCodeSource"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:AssignmentDetails/app:RentalVehicleClass)">
	<xsl:element name="Rental_Vehicle_Class">
	<xsl:value-of select="app:AssignmentDetails/app:RentalVehicleClass"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:MOIDetails/app:SystemRecommendedMethodOfInspection)">
	<xsl:element name="SystemRecommendedMethodOfInspection">
	<xsl:value-of select="app:MOIDetails/app:SystemRecommendedMethodOfInspection"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:MOIDetails/app:UserSelectedMOI/app:MethodOfInspection)">
	<xsl:element name="User_Selected_MethodOfInspection">
	<xsl:value-of select="app:MOIDetails/app:UserSelectedMOI/app:MethodOfInspection"/>
	</xsl:element>
</xsl:if>


<xsl:if test="string(app:MOIDetails/app:UserSelectedMOI/app:ResourceSelectionDeferredFlag)">
	<xsl:element name="IsReferredToDispatchCenter">
	<xsl:value-of select="app:MOIDetails/app:UserSelectedMOI/app:ResourceSelectionDeferredFlag"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:MOIDetails/app:UserSelectedMOI/app:OverrideReason/app:OverrideReasonCd)">
	<xsl:element name="OverrideReasonCd">
	<xsl:value-of select="app:MOIDetails/app:UserSelectedMOI/app:OverrideReason/app:OverrideReasonCd"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(app:MOIDetails/app:UserSelectedMOI/app:OverrideReason/app:OverrideReasonDesc)">
	<xsl:element name="OverrideReasonDesc">
	<xsl:value-of select="app:MOIDetails/app:UserSelectedMOI/app:OverrideReason/app:OverrideReasonDesc"/>
	</xsl:element>
</xsl:if>


<xsl:if test="string(app:AdditionalAssignmentDetails/app:IsCAT)">
	<xsl:element name="IsCat">
	<xsl:value-of select="app:AdditionalAssignmentDetails/app:IsCAT"/>
	</xsl:element>
</xsl:if>




</xsl:template>

<xsl:template match="wor:AdditionalTaskConstraints">

<xsl:if test="string(wor:ScheduleConstraints/wor:PreferredScheduleDate)">
	<xsl:element name="Preferred_Schedule_Date">
	<xsl:value-of select="wor:ScheduleConstraints/wor:PreferredScheduleDate"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(wor:ScheduleConstraints/wor:PreferredScheduleStartTime)">
	<xsl:element name="Preferred_Schedule_Start_Time">
	<xsl:value-of select="wor:ScheduleConstraints/wor:PreferredScheduleStartTime"/>
	</xsl:element>
</xsl:if>

<xsl:if test="string(wor:ScheduleConstraints/wor:PreferredScheduleEndTime)">
	<xsl:element name="Preferred_Schedule_End_Time">
	<xsl:value-of select="wor:ScheduleConstraints/wor:PreferredScheduleEndTime"/>
	</xsl:element>
</xsl:if>


<xsl:if test="string(wor:ScheduleConstraints/wor:RequiredSkillsList/wor:Skill)">

    <xsl:element name="ExpertiseList">
    <xsl:for-each select="wor:ScheduleConstraints/wor:RequiredSkillsList/wor:Skill">
    <xsl:value-of select="."/>
        <xsl:if test="not (position() = last())">
            <xsl:text>, </xsl:text>
        </xsl:if>
    </xsl:for-each>
    </xsl:element>

</xsl:if>
</xsl:template>


</xsl:stylesheet>
