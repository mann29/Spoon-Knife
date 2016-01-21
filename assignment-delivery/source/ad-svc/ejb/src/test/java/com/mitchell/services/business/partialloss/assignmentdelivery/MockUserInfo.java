package com.mitchell.services.business.partialloss.assignmentdelivery;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;

import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlDocumentProperties;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlString;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;


import com.mitchell.common.types.CrossOverInsuranceCompanyType;
import com.mitchell.common.types.UserHierType;
import com.mitchell.common.types.UserInfoType;
import com.mitchell.common.types.VersionNumberType;

public class MockUserInfo implements  UserInfoType{

	private static final long serialVersionUID = 1L;

	private java.lang.String orgID;
	private java.lang.String orgCode;
	private java.lang.String userID;
	private java.lang.String guid;
	private java.lang.String firstName;
	private java.lang.String lastName;
	private java.lang.String email;
	private com.mitchell.common.types.UserHierType userHier;
	private java.lang.String[] appCodeArray;
	private com.mitchell.common.types.CrossOverInsuranceCompanyType[] crossOverInsuranceCompanyArray;
	private java.lang.String staffType;
	private java.lang.String schemaVersion;
	@Override
	public XmlObject changeType(SchemaType arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int compareValue(XmlObject arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public XmlObject copy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] execQuery(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] execQuery(String arg0, XmlOptions arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isImmutable() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isNil() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public SchemaType schemaType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject selectAttribute(QName arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject selectAttribute(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectAttributes(QNameSet arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectChildren(QName arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectChildren(QNameSet arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectChildren(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject[] selectPath(String arg0, XmlOptions arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlObject set(XmlObject arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setNil() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public XmlObject substitute(QName arg0, SchemaType arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean validate(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean valueEquals(XmlObject arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int valueHashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public XmlDocumentProperties documentProperties() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void dump() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Node getDomNode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object monitor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlCursor newCursor() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node newDomNode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Node newDomNode(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public InputStream newInputStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public InputStream newInputStream(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Reader newReader() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Reader newReader(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XMLInputStream newXMLInputStream() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XMLInputStream newXMLInputStream(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XMLStreamReader newXMLStreamReader() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XMLStreamReader newXMLStreamReader(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void save(File arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(OutputStream arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Writer arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(ContentHandler arg0, LexicalHandler arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(File arg0, XmlOptions arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(OutputStream arg0, XmlOptions arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(Writer arg0, XmlOptions arg1) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(ContentHandler arg0, LexicalHandler arg1, XmlOptions arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String xmlText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String xmlText(XmlOptions arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addAppCode(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public XmlString addNewAppCode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CrossOverInsuranceCompanyType addNewCrossOverInsuranceCompany() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserHierType addNewUserHier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String[] getAppCodeArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getAppCodeArray(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CrossOverInsuranceCompanyType[] getCrossOverInsuranceCompanyArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CrossOverInsuranceCompanyType getCrossOverInsuranceCompanyArray(
			int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getGuid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOrgCode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getOrgID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSchemaVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getStaffType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserHierType getUserHier() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getUserID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertAppCode(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public XmlString insertNewAppCode(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public CrossOverInsuranceCompanyType insertNewCrossOverInsuranceCompany(
			int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isSetEmail() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSetFirstName() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSetGuid() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSetLastName() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSetSchemaVersion() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isSetStaffType() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void removeAppCode(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeCrossOverInsuranceCompany(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAppCodeArray(String[] arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAppCodeArray(int arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCrossOverInsuranceCompanyArray(
			CrossOverInsuranceCompanyType[] arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setCrossOverInsuranceCompanyArray(int arg0,
			CrossOverInsuranceCompanyType arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setEmail(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setFirstName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setGuid(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setLastName(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOrgCode(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setOrgID(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSchemaVersion(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setStaffType(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUserHier(UserHierType arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setUserID(String arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int sizeOfAppCodeArray() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int sizeOfCrossOverInsuranceCompanyArray() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void unsetEmail() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsetFirstName() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsetGuid() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsetLastName() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsetSchemaVersion() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void unsetStaffType() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public XmlString[] xgetAppCodeArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetAppCodeArray(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetEmail() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetFirstName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetGuid() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetLastName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetOrgCode() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetOrgID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public VersionNumberType xgetSchemaVersion() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetStaffType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public XmlString xgetUserID() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void xsetAppCodeArray(XmlString[] arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetAppCodeArray(int arg0, XmlString arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetEmail(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetFirstName(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetGuid(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetLastName(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetOrgCode(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetOrgID(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetSchemaVersion(VersionNumberType arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetStaffType(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void xsetUserID(XmlString arg0) {
		// TODO Auto-generated method stub
		
	}


		
	}
