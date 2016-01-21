package com.mitchell.services.business.partialloss.appraisalassignment.util;

import static org.mockito.Mockito.never;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import com.mitchell.common.exception.MitchellException;
import com.mitchell.services.core.geoservice.client.GeoServiceClient;
import com.mitchell.services.core.geoservice.common.exception.GeoServiceException;
import com.mitchell.services.core.geoservice.dto.GeoResult;
import com.mitchell.services.core.geoservice.dto.LatLong;

@RunWith(MockitoJUnitRunner.class)
public class AASCommonUtilsImplTest {

	// Mocks
	@Mock
	private AASCommonUtilsImpl testClass;
	@Mock
	private GeoServiceClient mockedGeoServiceClient;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void validateAddressHappyPath() throws Exception {
		// Variables
		GeoResult validationResult = new GeoResult();
		String street = "8433 Sunset Blvd";
		String city = "Los Angeles";
		String state = "CA";
		String zip = "90069";
		String country = "US";

		// Mocking
		when(this.testClass.validateAddress(street, city, state, zip, country))
				.thenCallRealMethod();

		when(this.testClass.getNewGeoServiceClient()).thenReturn(
				this.mockedGeoServiceClient);

		when(
				this.mockedGeoServiceClient.validateAddressWithCorrection(
						street, city, state, zip, country)).thenReturn(
				validationResult);

		// Call
		GeoResult retVal = this.testClass.validateAddress(street, city, state,
				zip, country);

		// Assertions
		assertNotNull(retVal);
		assertEquals(validationResult, retVal);

		// Verifications
		verify(this.testClass).getNewGeoServiceClient();
		verify(this.mockedGeoServiceClient).validateAddressWithCorrection(
				street, city, state, zip, country);
		verify(this.testClass).normalizeLatitudeAndLongitude(validationResult);
	}

	@Test
	public void validateAddressCallToValidateAddressWithCorrectionThrowsGeoException()
			throws Exception {
		// Variables
		String street = "8433 Sunset Blvd";
		String city = "Los Angeles";
		String state = "CA";
		String zip = "90069";
		String country = "US";
		String thrownGeoServiceExceptionMessage = "A GeoServiceException was thrown while attempting to validate the provided address."
				+ " GeoService method called: validateAddressWithCorrection(street, city, state, zip, country)."
				+ " Street argument: "
				+ street
				+ ". City argument: "
				+ city
				+ ". State argument: "
				+ state
				+ ". Zip argument: "
				+ zip
				+ ". Country argument: " + country;
		MitchellException me = null;
		String METHOD_NAME = "validateAddress";

		// Mocking
		when(this.testClass.validateAddress(street, city, state, zip, country))
				.thenCallRealMethod();

		when(this.testClass.getNewGeoServiceClient()).thenReturn(
				this.mockedGeoServiceClient);

		when(
				this.mockedGeoServiceClient.validateAddressWithCorrection(
						street, city, state, zip, country)).thenThrow(
				new GeoServiceException("", "", ""));

		// Call
		try {
			this.testClass.validateAddress(street, city, state, zip, country);
		} catch (MitchellException e) {
			me = e;
		}

		// Assertions
		assertNotNull(me);
		assertEquals(AASCommonUtilsImpl.class.getName(), me.getClassName());
		assertEquals(METHOD_NAME, me.getMethodName());
		assertEquals(thrownGeoServiceExceptionMessage, me.getMessage());

		// Verifications
		verify(this.testClass).getNewGeoServiceClient();
		verify(this.mockedGeoServiceClient).validateAddressWithCorrection(
				street, city, state, zip, country);
		verify(this.testClass, never()).normalizeLatitudeAndLongitude(
				(GeoResult) Mockito.anyObject());
	}

	@Test
	public void normalizeLatitudeAndLongitudeHappyPath() throws Exception {
		// Variables
		GeoResult geoResultToUpdate = new GeoResult();
		LatLong latLongToUpdate = new LatLong();
		latLongToUpdate.setLatitude(10.0);
		latLongToUpdate.setLongitude(11.0);
		geoResultToUpdate.setLatLong(latLongToUpdate);
		Double expectedNormalizedLatitude = new Double(100000.0);
		Double expectedNormalizedLongitude = new Double(110000.0);

		// Mocking
		Mockito.doCallRealMethod().when(this.testClass)
				.normalizeLatitudeAndLongitude(geoResultToUpdate);

		// Call
		this.testClass.normalizeLatitudeAndLongitude(geoResultToUpdate);

		// Assertions
		assertNotNull(geoResultToUpdate);
		assertNotNull(geoResultToUpdate.getLatLong());

		Double resultLatitude = new Double(geoResultToUpdate.getLatLong()
				.getLatitude());
		Double resultLongitude = new Double(geoResultToUpdate.getLatLong()
				.getLongitude());

		assertEquals(expectedNormalizedLatitude, resultLatitude);
		assertEquals(expectedNormalizedLongitude, resultLongitude);
	}

	@After
	public void tearDown() throws Exception {
	}
}
