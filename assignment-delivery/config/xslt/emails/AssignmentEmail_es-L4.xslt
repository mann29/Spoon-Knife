<xsl:transform xmlns:xsl='http://www.w3.org/1999/XSL/Transform' version='1.0' xmlns:mxs="http://www.mitchell.com/schemas/assignmentdelivery">
  <xsl:output omit-xml-declaration="yes"></xsl:output>
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
	      .LDAMasterForeColor
	      {
	      color: #FF0000;
	      font-family:Verdana;
	      }
	      .LDAMasterBackGroundColor
	      {
	      background-color: #FF0000;
	      font-family:Verdana;
	      }
	      .LabelValue
	      {
	      color:#302e2c;
	      font-size:12px;
	      text-align:left;
	      font-family:Verdana;
	      }
	      .LabelText
	      {
	      Color:#7f7d79;
	      font-size:12px;
	      text-align:right;
	      font-family:Verdana;
	      }
	      .Divider
	      {
	      height:1px;
	      background-color:#dfddde;
	      font-family:Verdana;
	      }
	      .Footer
	      {
	      color:#9f9d9b;
	      font-size:12px;
	      text-align:left;
	      font-family:Verdana;
	      }
    </style>
  </xsl:template>
  
 <xsl:template match="/CreationSubject">
<xsl:choose><xsl:when test="$assignmentSubTypeDesc!=''">Nuevo <xsl:value-of select="$assignmentSubTypeDesc" /> encargo-</xsl:when>
<xsl:otherwise>
Nuevo encargo-</xsl:otherwise></xsl:choose><xsl:value-of select="/CreationSubject/CoName" /><xsl:if test="string-length(/CreationSubject/ClaimNumber) > 0"> para el siniestro:<xsl:value-of select="/CreationSubject/ClaimNumber" /></xsl:if>
</xsl:template>

  <xsl:template name="body">
   <xsl:variable name="staticImageUrl" select="/mxs:EmailMessage/mxs:StaticImageBaseUrl"/>
   <xsl:variable name="currYear" select="/mxs:EmailMessage/mxs:CurrentYear"/>
    <body style="background-color:#DFDEDE">
      <table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
        <tr>
          <td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td>
        </tr>
        <tr>
          <td style="width:20px"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
          <td align="center" width="600">
            <table style="text-align:Center">
              <tr>
                <td style="text-align: left;" colspan="2">
                  <img alt="Linea Directa Aseguradora" src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" />
                </td>
              </tr>
              <tr >
                <td style="padding-left:15px; text-align: left;font-size: 48px;  font-weight: bold;" colspan="2">
                  <span class="LDAMasterForeColor">Nuevo Encargo</span>
                </td>
              </tr>
              <tr>
                <td style="padding-left:15px; font-size: 24px; text-align: left;" colspan="2">
                  <span class="LDAMasterForeColor">Se le ha asignado el siguiente Encargo de Valoración</span>
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 14px;" colspan="2">
                  Si no utiliza RC Connect para comunicarse con esta compañía, ignore este correo.
                  <ul>
                    <li>
                      NOTA: si es un Taller no colaborador que recibe un encargo de esta compañía, guarde
                      este email. Es el único enlace que recibirá para acceder a la información del encargo.
                    </li>
                  </ul>
                </td>
              </tr>
              
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 20px; font-weight:bold;" colspan="2">
                  Datos administrativos/Información del vehículo
                </td>
              </tr>
              <tr>
                <td colspan="2"></td>
              </tr>
			  <tr>
                <td class="LabelText" style="padding-left:15px;width:35%">
                  Tipo de encargo:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
				<xsl:choose>
				<xsl:when test="/mxs:EmailMessage/mxs:AssignmentSubTypeCode != null">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:AssignmentSubTypeCode" />
				</xsl:when>
				
				<xsl:otherwise>
				  <xsl:value-of select="/mxs:EmailMessage/mxs:AssignmentSubType" />
				</xsl:otherwise>
				</xsl:choose>
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Siniestro #:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:ClaimId" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:SuffixLabel" />:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:Suffix" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Matrícula:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:LicensePlate" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Fabricante:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:VehMake" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Modelo:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:VehModel" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Fecha preferida:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:if test="string-length(/mxs:EmailMessage/mxs:PreferMonth) > 0">
                    <xsl:value-of select="/mxs:EmailMessage/mxs:PreferDay" />/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferMonth" />/<xsl:value-of select="/mxs:EmailMessage/mxs:PreferYear" />
                  </xsl:if>

                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Notas sobre daños:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessage/mxs:DamageMemo" />
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td colspan="2" style="padding-left:15px; font-size: 24px; font-weight: bold;" align="center">
                  <xsl:call-template name="url"/>
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td colspan="2" class="Divider"></td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 14px;" colspan="2">
                  Haga clic en el enlace anterior y especifique el Número de identificación del vehículo (VIN) asociado con este siniestro.
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td style="text-align: left;" colspan="2">
                  <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
                </td>
              </tr>
            </table>
          </td>
          <td style="width:20px"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
        </tr>
        <tr>
          <td><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
          <td colspan="2">
            <table style="text-align:Center">
                <tr class="Footer">
                  <td>
                    <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
                  </td>
                  <td>
                    © <xsl:value-of select="$currYear" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/mxs:EmailMessage/mxs:ShopName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
                  </td>
                </tr>
              </table>
          </td>
        </tr>
      </table>
    </body>
  </xsl:template>

  <xsl:template name="bodyDRP">
  <xsl:variable name="staticImageUrl" select="/mxs:EmailMessageDRP/mxs:StaticImageBaseUrl"/>
  <xsl:variable name="currYearDRP" select="/mxs:EmailMessageDRP/mxs:CurrentYear"/>
    <body style="background-color:#DFDEDE">
      <table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
        <tr>
          <td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td>
        </tr>
        <tr>
          <td style="width:20px"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
          <td align="center" width="600">
            <table style="text-align:Center">
              <tr>
                <td style="text-align: left;" colspan="2">
                  <img alt="Linea Directa Aseguradora" src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" />
                </td>
              </tr>
              <tr >
                <td style="padding-left:15px; text-align: left;font-size: 48px;" colspan="2">
                  <span class="LDAMasterForeColor">Nuevo Encargo</span>
                </td>
              </tr>
              <tr>
                <td style="padding-left:15px; font-size: 24px; text-align: left;" colspan="2">
                  <span class="LDAMasterForeColor">Se le ha asignado el siguiente Encargo de Valoración</span>
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 14px;" colspan="2">
                  Si no utiliza RC Connect para comunicarse con esta compañía, ignore este correo.
                  <ul>
                    <li>
                      NOTA: si es un Taller no colaborador que recibe un encargo de esta compañía, guarde
                      este email. Es el único enlace que recibirá para acceder a la información del encargo.
                    </li>
                  </ul>
                </td>
              </tr>
              
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 20px; font-weight:bold;" colspan="2">
                  Datos administrativos/Información del vehículo
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
			  <tr>
                <td class="LabelText" style="padding-left:15px;width:35%">
                  Tipo de encargo:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
				<xsl:choose>
                  <xsl:when test="/mxs:EmailMessageDRP/mxs:AssignmentSubTypeCode != null">
                   <xsl:value-of select="/mxs:EmailMessageDRP/mxs:AssignmentSubTypeCode" />
				  </xsl:when>
				  <xsl:otherwise>
				   <xsl:value-of select="/mxs:EmailMessageDRP/mxs:AssignmentSubType" />
				  </xsl:otherwise>
				 </xsl:choose>
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Siniestro #:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:ClaimId" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:SuffixLabel" />:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:Suffix" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Matrícula:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:LicensePlate" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Fabricante:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehMake" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Modelo:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:VehModel" />
                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Fecha preferida:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:if test="string-length(/mxs:EmailMessageDRP/mxs:PreferMonth) > 0">
                    <xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferDay" />/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferMonth" />/<xsl:value-of select="/mxs:EmailMessageDRP/mxs:PreferYear" />
                  </xsl:if>

                </td>
              </tr>
              <tr>
                <td class="LabelText" style="padding-left:15px;">
                  Notas sobre daños:
                </td>
                <td class="LabelValue" style="font-weight: bold;">
                  <xsl:value-of select="/mxs:EmailMessageDRP/mxs:DamageMemo" />
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td colspan="2" style="padding-left:15px; font-size: 24px; font-weight: bold;" align="center">
                  <xsl:call-template name="urlDRP"/>
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td colspan="2" class="Divider"></td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
              <tr>
                <td class="LabelValue" style="padding-left:15px; text-align: left; font-size: 14px;" colspan="2">
                  Haga clic en el enlace anterior y especifique el Número de identificación del vehículo (VIN) asociado con este siniestro.
                </td>
              </tr>
              <tr>
                <td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
              </tr>
			  <tr>
				<td style="padding-left:15px; text-align: left;font-weight: bold;" colspan="2">
					<p class="LabelValue">Para cualquier aclaración o incidencia sobre este proceso, puede enviarnos un correo electrónico a través de 
					<a href="mailto:INCIDENCIA_TALLERES@lineadirecta.es?subject=Consulta de taller">este enlace</a> o llamando al teléfono 
					<span style="color: #FF0000;"> 902 123 202</span>
					, de lunes a viernes de 8:30h a 18:30h.
					</p>
				</td>
			   </tr>
			   <tr><td colspan="2"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td></tr>
              <tr>
                <td style="text-align: left;" colspan="2">
                  <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
                </td>
              </tr>
            </table>
          </td>
          <td style="width:20px"><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
        </tr>
        <tr>
          <td><xsl:text disable-output-escaping="yes"><![CDATA[&nbsp;]]></xsl:text></td>
          <td colspan="2">
            <table style="text-align:Center">
                <tr class="Footer">
                  <td>
                    <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
                  </td>
                  <td>
                    © <xsl:value-of select="$currYearDRP" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/mxs:EmailMessageDRP/mxs:ShopName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
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
    <a href="{$vlink}" class="LDAMasterForeColor">Acceder al encargo</a>
  </xsl:template>

  <xsl:template name="urlDRP">
    <xsl:variable name="vlink">
      <xsl:value-of select="/mxs:EmailMessageDRP/mxs:URLLink"/>
    </xsl:variable>
    <a href="{$vlink}" class="LDAMasterForeColor">Acceder al encargo</a>
  </xsl:template>

  <xsl:template name="uploadbody">  
	<xsl:variable name="staticImageUrl" select="/UploadSuccess/StaticImageBaseUrl"/>
    <xsl:variable name="currYear" select="/UploadSuccess/CurrentYear"/>
	<body style="background-color:#DFDEDE">
      <table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
         <tr>
		    <td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td></tr>
         <tr>
            <td style="width:20px"></td>
            <td align="center" width="600">
            <table style="text-align:Center; width:100%">
            <tr>
                <td colspan="2" style="text-align: left;">
                    <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" alt="Linea Directa Aseguradora" />
                </td>
            </tr>
			<tr><td colspan="2">&#160;</td></tr>
            <tr>
			   <td style="padding-left:15px; text-align: left;font-size: 24px;" colspan="2">
                    <span class="LDAMasterForeColor">¡Valoración enviada con éxito!</span>
               </td>                
			</tr>
			<tr>
                <td colspan="2" style="padding-left:15px; text-align: left;font-size: 24px;  font-weight: bold;">
				    <span class="LabelValue">Su valoración se envió con éxito a <xsl:value-of select="/UploadSuccess/CoName" />.</span>
			    </td>
			</tr>	
            <tr><td colspan="2">&#160;</td></tr>
			<tr><td colspan="2" class="Divider"></td></tr>
			<tr><td colspan="2">&#160;</td></tr>
			<tr>
				<td style="padding-left:15px; text-align: left;font-weight: bold;" colspan="2">
					<p class="LabelValue">Para cualquier aclaración o incidencia sobre este proceso, puede enviarnos un correo electrónico a través de 
					<a href="mailto:INCIDENCIA_TALLERES@lineadirecta.es?subject=Consulta de taller">este enlace</a> o llamando al teléfono 
					<span style="color: #FF0000;"> 902 123 202</span>
					, de lunes a viernes de 8:30h a 18:30h.
					</p>
				</td>
			</tr>
			<tr><td colspan="2">&#160;</td></tr>
		    <tr>
				<td style="text-align: left;" colspan="2">
					<img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
				</td>
			</tr>
        </table>
        </td>
            <td style="width:20px"></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2">
                <table style="text-align:center" >
                    <tr class="Footer"> 
                        <td>
                            <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
                        </td>
                        <td>
                            © <xsl:value-of select="$currYear" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/UploadSuccess/RecipientName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
      </table>   

    </body>
  </xsl:template>

  <xsl:template name="uploadbodyDRP">
    <xsl:variable name="staticImageUrl" select="/UploadSuccessDRP/StaticImageBaseUrl"/>
    <xsl:variable name="currYear" select="/UploadSuccessDRP/CurrentYear"/>
	<body style="background-color:#DFDEDE">
      <table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
         <tr>
		    <td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td></tr>
         <tr>
            <td style="width:20px"></td>
            <td align="center" width="600">
            <table style="text-align:Center; width:100%">
            <tr>
                <td colspan="2" style="text-align: left;">
                    <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" alt="Linea Directa Aseguradora" />
                </td>
            </tr>
			<tr><td colspan="2">&#160;</td></tr>
            <tr>
			   <td style="padding-left:15px; text-align: left;font-size: 24px;" colspan="2">
                            <span class="LDAMasterForeColor">¡Valoración enviada con éxito!</span>
               </td>
                
			</tr>
			<tr>
                <td colspan="2" style="padding-left:15px; text-align: left;font-size: 24px;  font-weight: bold;">
				           <span class="LabelValue">Su valoración se envió con éxito a <xsl:value-of select="/UploadSuccessDRP/CoName" />.</span>
			    </td>
			</tr>	
            <tr><td colspan="2">&#160;</td></tr>
			<tr><td colspan="2" class="Divider"></td></tr>
			<tr><td colspan="2">&#160;</td></tr>
			<tr>
				<td style="padding-left:15px; text-align: left;font-weight: bold;" colspan="2">
					<p class="LabelValue">Para cualquier aclaración o incidencia sobre este proceso, puede enviarnos un correo electrónico a través de 
					<a href="mailto:INCIDENCIA_TALLERES@lineadirecta.es?subject=Consulta de taller">este enlace</a> o llamando al teléfono 
					<span style="color: #FF0000;"> 902 123 202</span>
					, de lunes a viernes de 8:30h a 18:30h.
					</p>
				</td>
			</tr>
			<tr><td colspan="2">&#160;</td></tr>
		    <tr>
				<td style="text-align: left;" colspan="2">
					<img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
				</td>
			</tr>
        </table>
        </td>
            <td style="width:20px"></td>
        </tr>
        <tr>
            <td></td>
            <td colspan="2">
                <table style="text-align: center" >
                    <tr class="Footer"> 
                        <td>
                            <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
                        </td>
                        <td>
                            © <xsl:value-of select="$currYear" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/UploadSuccessDRP/RecipientName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
      </table>   

    </body>
  </xsl:template>

  <xsl:template name="uploadFailbody">
  	<xsl:variable name="staticImageUrl" select="/UploadFail/StaticImageBaseUrl"/>
    <xsl:variable name="currYear" select="/UploadFail/CurrentYear"/>
    <body style="background-color:#DFDEDE">
    	<table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
    		<tr>
		    	<td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td>
		    </tr>
         	<tr>
	            <td style="width:20px"></td>
	            <td align="center" width="600">
		            <table style="text-align:Center; width:100%">
		            	<tr>
			                <td colspan="2" style="text-align: left;">
			                    <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" alt="Linea Directa Aseguradora" />
			                </td>
			            </tr>
						<tr><td colspan="2">&#160;</td></tr>
						<tr>
			                <td colspan="2" style="padding-left:15px; text-align: left;font-size: 24px; ">
			                	 <span class="LDAMasterForeColor">Fallo al cargar la valoración</span>
							     <span class="LabelValue">La valoración no pudo procesarla <xsl:value-of select="/UploadFail/CoName" />.
							     <xsl:if test="string-length(/UploadFail/Message)>0">
	                                Por los siguientes motivos:<br/>
	                                <p>
	                                  <xsl:value-of select="/UploadFail/Message" />
	                                </p>
	                              </xsl:if>
                              	<p>Vuelva a cargar la valoración usando el enlace proporcionado en el email de encargo original.<br/></p>
                              	</span>
						    </td>
						</tr>
						<tr><td colspan="2">&#160;</td></tr>
						<tr><td colspan="2" class="Divider"></td></tr>
						<tr><td colspan="2">&#160;</td></tr>
						<tr>
							<td style="padding-left:15px; text-align: left;font-weight: bold;" colspan="2">
								<p class="LabelValue">Para cualquier aclaración o incidencia sobre este proceso, puede enviarnos un correo electrónico a través de 
								<a href="mailto:INCIDENCIA_TALLERES@lineadirecta.es?subject=Consulta de taller">este enlace</a> o llamando al teléfono 
								<span style="color: #FF0000;"> 902 123 202</span>
								, de lunes a viernes de 8:30h a 18:30h.
								</p>
							</td>
						</tr>
						<tr><td colspan="2">&#160;</td></tr>
					    <tr>
							<td style="text-align: left;" colspan="2">
								<img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
							</td>
						</tr>
						<tr>
				            <td></td>
				            <td colspan="2">
				                <table style="text-align:center" >
				                    <tr class="Footer"> 
				                        <td>
				                            <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
				                        </td>
				                        <td>
				                            © <xsl:value-of select="$currYear" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/UploadFail/RecipientName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
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

  <xsl:template name="uploadFailbodyDRP">
  	<xsl:variable name="staticImageUrl" select="/UploadFailDRP/StaticImageBaseUrl"/>
    <xsl:variable name="currYear" select="/UploadFailDRP/CurrentYear"/>
    <body style="background-color:#DFDEDE">
    	<table align="center" style="width:724; font-family:Verdana; background-color: #fff;">
    		<tr>
		    	<td colspan="3" class="LDAMasterBackGroundColor" style="height: 7px; text-align: left;"></td>
		    </tr>
		    <tr>
		    	<td style="width:20px"></td>
	            <td align="center" width="600">
	            	 <table style="text-align:Center; width:100%">
	            	 	<tr>
			                <td colspan="2" style="text-align: left;">
			                    <img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Logo.jpg" alt="Linea Directa Aseguradora" />
			                </td>
			            </tr>
						<tr><td colspan="2">&#160;</td></tr>
			            <tr>
			            	<td colspan="2" style="padding-left:15px; text-align: left;font-size: 24px; ">
			                	 <span class="LDAMasterForeColor">Fallo al cargar la valoración</span>
							     <span class="LabelValue">La valoración no pudo procesarla <xsl:value-of select="/UploadFailDRP/CoName" />.
							     <xsl:if test="string-length(/UploadFailDRP/Message)>0">
	                                Por los siguientes motivos:<br/>
	                                <p>
	                                  <xsl:value-of select="/UploadFailDRP/Message" />
	                                </p>
	                              </xsl:if>
                              	<p>Vuelva a cargar la valoración usando el enlace proporcionado en el email de encargo original.<br/></p>
                              	</span>
						    </td>			            
			            </tr>
	            	 </table>	            
	            </td>
		    </tr>
		    <tr><td colspan="2">&#160;</td></tr>
			<tr><td colspan="2" class="Divider"></td></tr>
			<tr><td colspan="2">&#160;</td></tr>
			<tr>
				<td style="padding-left:15px; text-align: left;font-weight: bold;" colspan="2">
					<p class="LabelValue">Para cualquier aclaración o incidencia sobre este proceso, puede enviarnos un correo electrónico a través de 
					<a href="mailto:INCIDENCIA_TALLERES@lineadirecta.es?subject=Consulta de taller">este enlace</a> o llamando al teléfono 
					<span style="color: #FF0000;"> 902 123 202</span>
					, de lunes a viernes de 8:30h a 18:30h.
					</p>
				</td>
			</tr>
			<tr><td colspan="2">&#160;</td></tr>
			<tr>
				<td style="text-align: left;" colspan="2">
					<img src="{$staticImageUrl}/static/workbench/images/Notifications/L4_Signature.gif" />
				</td>
			</tr>
			<tr>
				<td colspan="2">
				   <table style="text-align:center" >
				        <tr class="Footer"> 
				            <td>
				                <img height="80" src="{$staticImageUrl}/static/workbench/images/Notifications/EU_Footer.jpg" />
				            </td>
				            <td>
				                © <xsl:value-of select="$currYear" /> Mitchell International, Inc. All Rights Reserved. This message is intended only for recipient <xsl:value-of select="/UploadFailDRP/RecipientName" />. If you are not the intended recipient you are notified that disclosing, copying, distributing or taking any action in reliance on the contents of this information is strictly prohibited.
				            </td>
				        </tr>
				   </table>
				</td>
			</tr>
    	</table>
    </body>    
  </xsl:template>

  <xsl:template match="/mxs:EmailMessage">
    <html>
      <head>

        <xsl:call-template name="css"/>
      </head>
      <xsl:call-template name="body"/>
    </html>
  </xsl:template>

  <xsl:template match="/mxs:EmailMessageDRP">
    <html>
      <head>

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
    Valoración enviada con éxito para <xsl:value-of select="/UploadSuccessSubject/CoName" /> Siniestro: <xsl:value-of select="/UploadSuccessSubject/ClaimNumber" />
  </xsl:template>

  <xsl:template match="/UploadFailSubject">
    Fallo al cargar valoración para <xsl:value-of select="/UploadFailSubject/CoName" /> Siniestro: <xsl:value-of select="/UploadFailSubject/ClaimNumber" />
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
        Nuevo encargo de modificación de <xsl:value-of select="/NonDrpSuppSubject/CoName" />, Siniestro: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber" />
      </xsl:when>
      <xsl:otherwise>
        Nuevo encargo de modificación, Siniestro: <xsl:value-of select="/NonDrpSuppSubject/ClaimNumber" />
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

   
  <xsl:template name="faxbody">
    Nuevo encargo Se le ha asignado el siguiente <xsl:choose>
      <xsl:when test="$assignmentSubTypeDescFax!=''">
        <b>
          <xsl:value-of select="$assignmentSubTypeDescFax" />
        </b> Encargo de valoración
      </xsl:when>
      <xsl:otherwise>Encargo de Valoración</xsl:otherwise>
    </xsl:choose> Datos administrativos/Tipo de información del vehículo: <xsl:value-of select="/Fax/EmailMessage/CauseOfLossDesc" />
    <xsl:if test="/Fax/EmailMessage/ClaimantName">
      Nombre del contacto: <xsl:value-of select="/Fax/EmailMessage/ClaimantName" />
      <xsl:if test="/Fax/EmailMessage/HomePhone">
        Teléfono particular: <xsl:value-of select="/Fax/EmailMessage/HomePhone" />
      </xsl:if>
      <xsl:if test="/Fax/EmailMessage/WorkPhone">
        Teléfono del trabajo: <xsl:value-of select="/Fax/EmailMessage/WorkPhone" />
      </xsl:if>
      <xsl:if test="/Fax/EmailMessage/CellPhone">
        Teléfono móvil: <xsl:value-of select="/Fax/EmailMessage/CellPhone" />
      </xsl:if>
    </xsl:if> Fabricante del vehículo: <xsl:value-of select="/Fax/EmailMessage/VehMake" />, Modelo <xsl:value-of select="/Fax/EmailMessage/VehModel" />, Año<xsl:value-of select="/Fax/EmailMessage/VehYear" /> Punto de impacto: <xsl:value-of select="/Fax/EmailMessage/Poi" /> Notas sobre daños:<xsl:value-of select="/Fax/EmailMessage/DamageMemo" /> Notas sobre encargo: <xsl:value-of select="/Fax/EmailMessage/AssignmentMemo" /> Fecha preferida: <xsl:if test="string-length(/Fax/EmailMessage/PreferMonth) > 0">
      <xsl:value-of select="/Fax/EmailMessage/PreferMonth" />/<xsl:value-of select="/Fax/EmailMessage/PreferDay" />/<xsl:value-of select="/Fax/EmailMessage/PreferYear" />
    </xsl:if> Notas Si está configurado para usar eClaim Manager de Mitchell, quizás haya recibido ya este encargo. Compruebe la bandeja de entrada de eClaim para acceder a los detalles del encargo. Reciba un cordial saludo, <xsl:value-of select="/Fax/EmailMessage/CoName" />
  </xsl:template>

  <xsl:template match="/Fax/EmailMessage">
    <xsl:call-template name="faxbody"/>
  </xsl:template>

  <xsl:template name="faxbodyDRP">
    Nuevo encargo Se le ha asignado el siguiente <xsl:choose>
      <xsl:when test="$assignmentSubTypeDescFaxDRP!=''">
        <b>
          <xsl:value-of select="$assignmentSubTypeDescFaxDRP" />
        </b> Encargo de valoración
      </xsl:when>
      <xsl:otherwise>Encargo de Valoración</xsl:otherwise>
    </xsl:choose> Datos administrativos/Tipo de información del vehículo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CauseOfLossDesc" />
    <xsl:if test="/FaxDRP/EmailMessageDRP/ClaimantName">
      Nombre del contacto: <xsl:value-of select="/FaxDRP/EmailMessageDRP/ClaimantName" />
      <xsl:if test="/FaxDRP/EmailMessageDRP/HomePhone">
        Teléfono particular: <xsl:value-of select="/FaxDRP/EmailMessageDRP/HomePhone" />
      </xsl:if>
      <xsl:if test="/FaxDRP/EmailMessageDRP/WorkPhone">
        Teléfono del trabajo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/WorkPhone" />
      </xsl:if>
      <xsl:if test="/FaxDRP/EmailMessageDRP/CellPhone">
        Teléfono móvil: <xsl:value-of select="/FaxDRP/EmailMessageDRP/CellPhone" />
      </xsl:if>
    </xsl:if> Fabricante del vehículo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehMake" />, Modelo <xsl:value-of select="/FaxDRP/EmailMessageDRP/VehModel" />, Año<xsl:value-of select="/FaxDRP/EmailMessageDRP/VehYear" /> Punto de impacto: <xsl:value-of select="/FaxDRP/EmailMessageDRP/Poi" /> Notas sobre daños:<xsl:value-of select="/FaxDRP/EmailMessageDRP/DamageMemo" /> Notas sobre encargo: <xsl:value-of select="/FaxDRP/EmailMessageDRP/AssignmentMemo" /> Fecha preferida: <xsl:if test="string-length(/FaxDRP/EmailMessageDRP/PreferMonth) > 0">
      <xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferMonth" />/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferDay" />/<xsl:value-of select="/FaxDRP/EmailMessageDRP/PreferYear" />
    </xsl:if> Notas Si está configurado para usar eClaim Manager de Mitchell, quizás haya recibido ya este encargo. Compruebe la bandeja de entrada de eClaim para acceder a los detalles del encargo. Reciba un cordial saludo, <xsl:value-of select="/FaxDRP/EmailMessageDRP/CoName" />
  </xsl:template>

  <xsl:template match="/FaxDRP/EmailMessageDRP">
    <xsl:call-template name="faxbodyDRP"/>
  </xsl:template>
</xsl:transform>