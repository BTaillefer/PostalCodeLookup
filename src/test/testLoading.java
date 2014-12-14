package test;

import java.io.File;
import postalcode.PostalCodeIndex;

public class testLoading {

	public static void main(String[] args) {
		PostalCodeIndex postalCodeIndex = new PostalCodeIndex();
		postalCodeIndex.load(new File("postal_codes_short.csv"));
		postalCodeIndex.findLatitudeRange(48.334, -65.122);
		

	}

}
