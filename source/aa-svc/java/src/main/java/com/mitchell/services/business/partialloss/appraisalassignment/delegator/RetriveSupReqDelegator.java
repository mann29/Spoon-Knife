package com.mitchell.services.business.partialloss.appraisalassignment.delegator;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mitchell.common.exception.MitchellException;
import com.mitchell.common.types.UserInfoDocument;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EmailMessageDocument;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.EstimatePlatformType;
import com.mitchell.schemas.appraisalassignment.supplementrequestemail.SupplementRequestDocument;
import com.mitchell.services.business.partialloss.appraisalassignment.AppraisalAssignmentConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.IAppraisalAssignmentUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.dao.CultureDAO;
import com.mitchell.services.business.partialloss.appraisalassignment.proxy.ShopIdentifierProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestSystemConfig;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SuppRequestUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementNotification;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementReqBO;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestConstants;
import com.mitchell.services.business.partialloss.appraisalassignment.supprequest.SupplementRequestDocBuildr;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASCommonUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.AASEmailProxy;
import com.mitchell.services.business.partialloss.appraisalassignment.util.BeanLocator;
import com.mitchell.services.business.partialloss.appraisalassignment.util.UserInfoUtils;
import com.mitchell.services.business.partialloss.appraisalassignment.util.XsltTransformer;
import com.mitchell.services.core.userinfo.types.UserDetailDocument;
import com.mitchell.services.technical.claim.common.utils.ParseHelper;
import com.mitchell.services.technical.claim.exception.ClaimException;
import com.mitchell.utils.misc.FileUtils;

public class RetriveSupReqDelegator
{ 
  private static String CLASS_NAME = RetriveSupReqDelegator.class
	      .getName();
  private static Logger logger = Logger.getLogger(CLASS_NAME);
  private static final String SETTING_NOT_SET = "NOT_SET";

  private final long estimateDocId;
  private final long estimatorOrgId;
  private final long reviewerOrgId;

  private SupplementReqBO suppBO;
  private SupplementRequestDocument suppReqDocument;
  private UserInfoDocument estimatorUserInfo;
  private UserInfoDocument reviewerUserInfo;
  private UserDetailDocument estimatorUserDetail;
  private UserDetailDocument reviewerUserDetail;
  private String supplementRequestDocText;
  private String htmlFormat;
  private boolean isTextOnly;

  private EmailMessageDocument emailMessageDocument;

  private String taskId;
  private boolean isNonNetworkShop;
  private boolean isShopPremium;
  // Not ~isNonNetworkShop
  private boolean isNetWorkShop;
  
  // All beans which are required to be injected in this class
  IAppraisalAssignmentUtils utils;
  AASCommonUtils aasCommonUtils;
  UserInfoUtils userInfoUtils;
  CultureDAO cultureDAO;
  SupplementRequestDocBuildr suppBldDoc;
  SupplementNotification suppNotification;
  AASEmailProxy aasEmailUtils;
  ShopIdentifierProxy shopIdentifierProxy;

  public RetriveSupReqDelegator(final long estimateDocId,
      final long estimatorOrgId, final long reviewerOrgId) throws MitchellException{
    injectDependencies();
    this.estimateDocId = estimateDocId;
    this.estimatorOrgId = estimatorOrgId;
    this.reviewerOrgId = reviewerOrgId;
  }

  public RetriveSupReqDelegator(final long estimateDocId,
      final long estimatorOrgId, final long reviewerOrgId,
      final boolean isTextOnly)throws MitchellException{
	injectDependencies();
    this.estimateDocId = estimateDocId;
    this.estimatorOrgId = estimatorOrgId;
    this.reviewerOrgId = reviewerOrgId;
    this.isTextOnly = isTextOnly;
    
  }
  /*
   * To inject dependencies of spring beans at a common place
   */
   public void injectDependencies() throws MitchellException{
	   this.utils = (IAppraisalAssignmentUtils) getSpringBean(AppraisalAssignmentConstants.APPRAISAL_ASSIGNMENT_UTILS_BEAN);
	   this.aasCommonUtils = (AASCommonUtils) getSpringBean(AppraisalAssignmentConstants.AAS_COMMON_UTILS_BEAN);
	   this.userInfoUtils = (UserInfoUtils) getSpringBean(AppraisalAssignmentConstants.USER_INFO_UTILS_BEAN);
	   this.cultureDAO = (CultureDAO) getSpringBean(AppraisalAssignmentConstants.CULTURE_DAO_BEAN);
	   this.suppBldDoc = (SupplementRequestDocBuildr) getSpringBean(AppraisalAssignmentConstants.SUPPLEMENT_REQUEST_DOC_BUILDR_BEAN);
	   this.suppNotification = (SupplementNotification) getSpringBean(AppraisalAssignmentConstants.SUPPLEMENT_NOTIFICATION_BEAN);
	   this.aasEmailUtils = (AASEmailProxy) getSpringBean(AppraisalAssignmentConstants.AAS_EMAIL_UTILS_BEAN);
       this.shopIdentifierProxy = (ShopIdentifierProxy) getSpringBean(AppraisalAssignmentConstants.DAYTONA_SHOP_IDENTIFIER_BEAN);
   }

  public void setTextOnly(final boolean flag)
  {

    this.isTextOnly = flag;
  }

  public void setTaskId(final String taskId)
  {
    this.taskId = taskId;
  }

  public String getTaskId()
  {
    return this.taskId;
  }

  public SupplementRequestDocument getSuppReqDocument()
  {
    return this.suppReqDocument;
  }

  public void setSuppReqDocument(
      SupplementRequestDocument supplementRequestDocument)
  {
    this.suppReqDocument = supplementRequestDocument;
  }

  public EmailMessageDocument retriveSupplementRequest()
      throws MitchellException
  {
    logger.info("Entering in retriveSupplementRequest");
   
    try {

      this.estimatorUserInfo = userInfoUtils
          .getUserInfoDoc(this.estimatorOrgId);
      this.reviewerUserInfo = userInfoUtils.getUserInfoDoc(this.reviewerOrgId);

      this.estimatorUserDetail = userInfoUtils.getUserDetailDoc(
          estimatorUserInfo.getUserInfo().getOrgCode(), estimatorUserInfo
              .getUserInfo().getUserID());
      this.reviewerUserDetail = userInfoUtils.getUserDetailDoc(reviewerUserInfo
          .getUserInfo().getOrgCode(), reviewerUserInfo.getUserInfo()
          .getUserID());
   
      String coCode = estimatorUserInfo.getUserInfo().getOrgCode();
	  if (logger.isLoggable(java.util.logging.Level.INFO)) {
		  logger.info("In retriveSupplementRequest, coCode from estimatorUserInfo " + coCode);
	  }
      String language = null;
      
      String customXsltSuffix = utils.retrieveCustomSettings(coCode, coCode,
    		  AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME,
    		  AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME);
      
      if (customXsltSuffix != null && !customXsltSuffix.isEmpty()) {
    	  language = customXsltSuffix;
      } else {
    	  String culture = cultureDAO.getCultureByCompany(reviewerUserInfo.getUserInfo()
    			  .getOrgCode());
    	  language = getLanguageFromCulture(culture);
      }
      this.checkInput();
      this.populateSupplementBO(language);

      String defaultCurrency = "$";

      String currency = cultureDAO.getCurrency(reviewerUserInfo.getUserInfo()
          .getOrgCode());

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Currency Symbol :::" + currency);
      }
      
      if (currency != null && !currency.isEmpty()) {
        suppBldDoc.setCurrency(currency);
      } else {
        suppBldDoc.setCurrency(defaultCurrency);
      }

      boolean isDaytonaShop = shopIdentifierProxy.isDaytonaShop(estimatorUserInfo);
      suppBO.setDaytonaShop(isDaytonaShop);

      suppReqDocument = suppBldDoc.populateSupplementRequest(suppBO);

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("Suppliment doc " + suppReqDocument);
      }
      
      logger.info("calling retrieveNotificationDoc");

      supplementRequestDocText = suppNotification.retrieveNotificationDoc(
          suppBO, suppReqDocument);

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info(" generated  supplementRequestDocText-->"
            + supplementRequestDocText);
      }

      this.emailMessageDocument = EmailMessageDocument.Factory.newInstance();
      this.emailMessageDocument.addNewEmailMessage();
      this.emailMessageDocument.getEmailMessage().setTextFormat(
          supplementRequestDocText);

      if (logger.isLoggable(java.util.logging.Level.INFO)) {
        logger.info("isTextOnly:" + isTextOnly);
      }

      if (!isTextOnly) {
        logger.info("tranforming...");
        this.transformHTML(language);
        this.emailMessageDocument.getEmailMessage().setHTMLFormat(
            this.htmlFormat);

        if (logger.isLoggable(java.util.logging.Level.INFO)) {
          logger.info("transformed..." + this.htmlFormat);
        }
      }

    } catch (final MitchellException me) {

      throw me;

    } catch (final Exception e) {

      aasCommonUtils.logAndThrowError(
          SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST.getCode(),
          this.getClass().getName(), "submitSupplementRequest", e.getMessage(),
          e, logger);
    }

    return emailMessageDocument;
  }
  
	/**
	 * This method is used to fetch language from culture.
	 * Example : For "fr-CA" as culture, this method will return "fr" as language.
	 * 
	 * @param culture The culture as String
	 * @return language as String
	 */
	private String getLanguageFromCulture(String culture) {
		int pos = 0;
		String lang = null;
		if (null != culture) {
			pos = culture.indexOf(AppraisalAssignmentConstants.HYPHEN_STRING);
			if (pos > 0) {
				lang = culture.substring(0, pos);
			} else {
				lang = culture;
			}
		}
		return lang;
	}

  public void transformHTML(String culture)
      throws MitchellException
  {

    final String xsltPath = this.getXSLTPath(culture);

    final XsltTransformer xsltTransformer = new XsltTransformer(xsltPath);

    this.htmlFormat = xsltTransformer.transformXmlString(this.suppReqDocument
        .toString());
  }

  public String getXSLTPath(String language)
      throws MitchellException
  {

    String path = aasCommonUtils.getSystemConfigurationSettingValue(
        "/AppraisalAssignment/SuppRequest/HTMLFormat/XSLTPath",
        "HTMLAnnotation");

    if (this.suppReqDocument.getSupplementRequest().getEstimateType() == EstimatePlatformType.CCC) {
      path = aasCommonUtils.getSystemConfigurationSettingValue(
          "/AppraisalAssignment/SuppRequest/HTMLFormat/XSLTPath",
          "CCCHTMLAnnotation");
    } else if (this.suppReqDocument.getSupplementRequest().getEstimateType() == EstimatePlatformType.MIE) {
      path = aasCommonUtils.getSystemConfigurationSettingValue(
          "/AppraisalAssignment/SuppRequest/HTMLFormat/XSLTPath",
          "MIEHTMLAnnotation");
    } else if (this.suppReqDocument.getSupplementRequest().getEstimateType() == EstimatePlatformType.GTMOTIVE) {
      path = aasCommonUtils.getSystemConfigurationSettingValue(
          "/AppraisalAssignment/SuppRequest/HTMLFormat/XSLTPath",
          "GTEHTMLAnnotation");
    }

    if (logger.isLoggable(Level.INFO)) {
      logger.info("getXSLTPath-path:" + path);
    }

    String tempPath = null;
    if (logger.isLoggable(Level.INFO)) {
      logger.info("path value:" + path);
      logger.info("language:" + language);
    }
    tempPath = SuppRequestSystemConfig.getTemplateBaseDir()
        + java.io.File.separator + path + "_" + language + ".xsl";

    if (logger.isLoggable(Level.INFO)) {
      logger.info("getXSLTPath-tempPath:" + tempPath);
    }

    if (FileUtils.isExistingFile(tempPath)) {
      if (logger.isLoggable(Level.INFO)) {
        logger.info("file exists-" + tempPath);
      }
      //moving ahead with language specific xslt
      path = tempPath;
    } else {
      if (logger.isLoggable(Level.INFO)) {
        logger.info("file does not exists:" + tempPath);
      }
      //moving ahead with english xslt
      path = SuppRequestSystemConfig.getTemplateBaseDir()
          + java.io.File.separator + path + ".xsl";
    }
    return path;

  }

  public void populateSupplementBO(String culture)
      throws MitchellException
  {
	if (logger.isLoggable(Level.INFO)) {
		logger.info("Enter in populateSupplementBO");
	}
    
    this.suppBO = new SupplementReqBO();

    this.suppBO.setEmail(true);
    this.suppBO.setEstimateDocId(this.estimateDocId);
    this.suppBO.setEstimatorOrgId(this.estimatorOrgId);
    this.suppBO.setReviewerOrgId(this.reviewerOrgId);
    this.suppBO.setEstimatorUserInfo(this.estimatorUserInfo);
    this.suppBO.setEstimatorUserDetail(this.estimatorUserDetail);
    this.suppBO.setReviewerUserDetail(this.reviewerUserDetail);
    this.suppBO.setReviewerUserInfo(this.reviewerUserInfo);
    this.suppBO.setCulture(culture);
    
    
    /**
     * Add for non-network/shop premium supplement email
     */
    /**
     * Add for non-network/network shop supplement email
     */
    if (isNonNetworkShop || isNetWorkShop) {
      String url = null;
      
      if (isNonNetworkShop) {
        this.suppBO.setTaskId(taskId);
        url = aasCommonUtils.getSystemConfigurationSettingValue(
            "/AssignmentDelivery/AssignmentEmail/URLLink", SETTING_NOT_SET);
      } else {
        url = aasCommonUtils.getSystemConfigurationSettingValue(
            "/AssignmentDelivery/AssignmentEmail/URLLink4DRP", SETTING_NOT_SET);
      }

      if (url != null && !url.trim().equalsIgnoreCase(SETTING_NOT_SET)) {
        this.suppBO.setEmailLink(url);
      }
    }

    try {
      if (logger.isLoggable(Level.INFO)) {
      logger.info("Entering  in populateEstimateInfo");
      }
      aasEmailUtils.populateEstimateInfo(this.suppBO);
      UserInfoDocument estUserInfoDoc = userInfoUtils
              .getUserInfoDoc(this.estimatorOrgId);
      String coCode = estUserInfoDoc.getUserInfo().getOrgCode();
      String revUserId = estUserInfoDoc.getUserInfo().getUserID();
      //set images urls for header/footer and signature
      populateStaticImageBaseUrl(this.suppBO);
      // To split claim number with claim mask setting
   	  // to get claimID and suffix(exposureId) distinctly
      populateClaimSuffixData(coCode, suppBO, revUserId);
    } catch (final Exception e) {

      logger.severe(e.getMessage());
      final MitchellException me = aasCommonUtils.logError(
          SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST.getCode(),
          this.getClass().getName(), "populateSupplementBO", e.getMessage(), e);
      throw me;
    }

    logger.info("Exiting in populateSupplementBO");
  }
    /**
	 * This method is to populate static url
	 * fetched from SET file
	 * @param aasCommonUtils- to get SET file value for image urls
	 * @param suppBO-to be populated
	 */
	private SupplementReqBO populateStaticImageBaseUrl(SupplementReqBO suppBO) throws MitchellException {
		if(null != suppBO){
		String staticBaseUrl = aasCommonUtils
				.getSystemConfigurationSettingValue(
						AppraisalAssignmentConstants.STATIC_IMAGE_BASE_URL,
						SETTING_NOT_SET);
		
		suppBO.setStaticBaseUrl(staticBaseUrl);
		
		
		}
		return suppBO;
	}

	/**
	 * This method fetches 3 custom setting values
	 * 1. Claim mask
	 * 2. Suffix label(heading to be displayed in supp email)
	 * populates claim data in SuppBO i.e. claim id and suffix number
	 * (by splitting claim num with claim mask setting and to get
	 * claim id and exposure id separately) And Suffix label
	 * 3. Signature image Url coming from custom setting
	 * @param utils-to retrieve cust setting
	 * @param SupplementReqBO-which contains clain number to be splitted
	 * @param coCode-used to retrieve cust setting
	 * @throws MitchellException
	 */
	private SupplementReqBO populateClaimSuffixData(String coCode,
			SupplementReqBO suppBO, String revUserId) throws MitchellException {
		final String methodName = "populateClaimSuffixData";
		if(null != suppBO){
		String[] claimSuffix = new String[2];
		String claimNum = suppBO.getClientClaimId();
		try {
				String claimParsingRule = utils
						.getCustomSettingValue(
								coCode,
								revUserId,
								AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
								AppraisalAssignmentConstants.SETTING_CLAIM_MASK);
			
			if (claimParsingRule != null && null != claimNum) {
				claimSuffix = ParseHelper.parseClaimNo(claimNum,
						claimParsingRule);
				suppBO.setClaimNum(claimSuffix[0]);
				suppBO.setSuffixNum(claimSuffix[1]);
			}
			
			//set suffix label fetched from custom setting
				String suffLabel = utils
						.getCustomSettingValue(
								coCode,
								revUserId,
								AppraisalAssignmentConstants.CUSTOM_SETTING_CLAIM_SETTINGS_GRP,
								AppraisalAssignmentConstants.SETTING_SUFFIX_LABEL);
		    
		    suppBO.setSuffixLabel(suffLabel);
		    
		    //set signature image fetched from custom setting
		    //TODO - Custom setting name for SIGNATURE IMAGE to be changed in future once will be created (Creation in progress)
		    //On temp basis SETTING_NOTIFICATION_XSLT_SETTING_NAME is being used and at xslt template its being hardcoded
		    String signatureImgUrl = utils
					.getCustomSettingValue(
							coCode,
							revUserId,
							AppraisalAssignmentConstants.CUSTOM_SETTING_CARRIER_GLOBAL_GROUP_NAME,
							AppraisalAssignmentConstants.SETTING_NOTIFICATION_XSLT_SETTING_NAME);
		    suppBO.setSignatureImage(signatureImgUrl);
		} catch (ClaimException ce) {
			throw new MitchellException(CLASS_NAME, methodName,
					"Error in populating claim-suffix data.");
		}
		}
     return suppBO;
	}
  private void checkInput()
      throws MitchellException
  {

    try {
      // validate the EstDocID
      if (estimateDocId <= 0) {
        throw new Exception("The EstimateDocument ID is not valid:"
            + estimateDocId);
      }
    } catch (final Exception e) {
      logger.severe(e.getMessage());
      final MitchellException me = aasCommonUtils.logError(
          SupplementRequestConstants.ERR_SUBMIT_SUPPLEMENT_REQUEST.getCode(),
          this.getClass().getName(), "checkInput", e.getMessage(), e);
      throw me;
    }
  }

  public boolean isNonNetworkShop()
  {
    return isNonNetworkShop;
  }

  public void setNonNetworkShop(boolean isNonNetworkShop)
  {
    this.isNonNetworkShop = isNonNetworkShop;
  }

  public boolean isShopPremium()
  {
    return isShopPremium;
  }

  public void setShopPremium(boolean isShopPremium)
  {
    this.isShopPremium = isShopPremium;
  }

  public boolean isNetWorkShop()
  {
    return isNetWorkShop;
  }

  public void setNetWorkShop(boolean isNetWorkShop)
  {
    this.isNetWorkShop = isNetWorkShop;
  }
  public void setUtils(IAppraisalAssignmentUtils utils) {
		this.utils = utils;
	}

	public void setAasCommonUtils(AASCommonUtils aasCommonUtils) {
		this.aasCommonUtils = aasCommonUtils;
	}

	public void setUserInfoUtils(UserInfoUtils userInfoUtils) {
		this.userInfoUtils = userInfoUtils;
	}

	public void setCultureDAO(CultureDAO cultureDAO) {
		this.cultureDAO = cultureDAO;
	}

	public void setSuppBldDoc(SupplementRequestDocBuildr suppBldDoc) {
		this.suppBldDoc = suppBldDoc;
	}

	public void setSuppNotification(SupplementNotification suppNotification) {
		this.suppNotification = suppNotification;
	}
	public void setAasEmailUtils(AASEmailProxy aasEmailUtils) {
		this.aasEmailUtils = aasEmailUtils;
	}


  protected Object getSpringBean(String beanName)
      throws MitchellException
  {
    Object o = null;
    try {
      o = BeanLocator.getBean(beanName);
    } catch (IllegalAccessException e) {
      throw new MitchellException(
          this.getClass().getName(),
          "getSpringBean",
          "Exception getting Spring bean, likely not properly initialized/reference counted.",
          e);
    }
    return o;
  }
}
