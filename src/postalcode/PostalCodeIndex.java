package postalcode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import postalcode.PostalCode.CompareLatitude;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Class PostalCodeIndex used to initialize the ObservableList objects and sort the lists. Five observable lists are created for each instance variable that needs to be
 * sorted. Getter methods for each postalCode are created to return the ObservableList. Method load, 
 * @author Brodie
 *
 */
public class PostalCodeIndex {
	//Create ObservableList's for each instance variable that must be sorted by
	private ObservableList<PostalCode> codeOrder = FXCollections.observableList(new ArrayList<PostalCode>(760000));
	private ObservableList<PostalCode> cityOrderList; // TODO eliminate memory waste
	private ObservableList<PostalCode> provinceOrderList = FXCollections.observableList(new ArrayList<PostalCode>(760000));
	private ObservableList<PostalCode> latitudeOrderList = FXCollections.observableArrayList(new ArrayList<PostalCode>(760000));
	private ObservableList<PostalCode> longitudeOrderList = FXCollections.observableArrayList(new ArrayList<PostalCode>(760000));

	// Getter methods to return reference to each ObservableList object.
	public ObservableList<PostalCode> getCodeOrder() { return FXCollections.unmodifiableObservableList(codeOrder); }
	public ObservableList<PostalCode> getCityOrder() { return FXCollections.unmodifiableObservableList(cityOrderList); }
	public ObservableList<PostalCode> getProvinceOrder() { return FXCollections.unmodifiableObservableList(provinceOrderList); }
	public ObservableList<PostalCode> getLatitudeOrder() { return FXCollections.unmodifiableObservableList(latitudeOrderList); }
	public ObservableList<PostalCode> getLongitudeOrder() { return FXCollections.unmodifiableObservableList(longitudeOrderList); }
	
	// Scan in input file and parse data into codeOrder
	public void load(File file) {
		try (Scanner fileInput = new Scanner(file)) {
			fileInput.useDelimiter("\\||\\r\\n");
			fileInput.nextLine();
			while (fileInput.hasNext()) 
				codeOrder.add(new PostalCode(fileInput));
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//Sort Array list's with our new Comparable methods, cityOrderList, provinceOrderList, latitudeOrderList
		//copy codeOrder into cityOrderList, making an exact copy of the original array
		cityOrderList = FXCollections.observableArrayList(codeOrder);
		//sort cityOrderList with our comparator method
		Collections.sort(cityOrderList, PostalCode.CompareCity.instance);
		//copy codeOrder into provinceOrderList, making an exact copy of the original array
		provinceOrderList = FXCollections.observableArrayList(codeOrder);
		//sort provinceOrderList with our comparator method
		Collections.sort(provinceOrderList, PostalCode.CompareProvince.instance);
		//copy codeOrder into latitudeOrderList, making an exact copy of the original array
		latitudeOrderList = FXCollections.observableArrayList(codeOrder);
		//sort latitudeOrderList with our comparator method
		Collections.sort(latitudeOrderList,PostalCode.CompareLatitude.instance);
		//copy codeOrder into longitudeOrderList, making an exact copy of the original array
		longitudeOrderList = FXCollections.observableArrayList(codeOrder);
		//sort longitudeOrderList with our comparator method
		Collections.sort(longitudeOrderList,PostalCode.CompareLongitude.instance);

	}
	
	public PostalCode findClosestCoordinate(double latitude, double longitude) {
		double closestDistance = Double.MAX_VALUE;
		PostalCode closestPostalCode = null;
		double distance;
		for(PostalCode postalCode : latitudeOrderList) {
			distance = distance(latitude,longitude,postalCode.getLatitude(),postalCode.getLongitude(),'K');
			if(distance < closestDistance) {
				closestDistance = distance;
				closestPostalCode = postalCode;
			}	
		}
		//System.out.printf("Closest Distance: %f\n", closestDistance);
		return closestPostalCode;	
	}
	
	public void findLatitudeRange(double latitude, double longitude) {
		PostalCode postalcode = new PostalCode(latitude, longitude);
		int EntryPoint = Collections.binarySearch(getLatitudeOrder(),postalcode, PostalCode.CompareLatitude.instance);
		ArrayList<PostalCode> postalCodes = new ArrayList<PostalCode>();
		System.out.printf("FICK");
		for(int i=0;i<50;i++) {
			PostalCode postalcodes = getLatitudeOrder().get(EntryPoint *-1);
			postalCodes.add(postalcodes);
			System.out.printf("Test");
		}
	}
	
	
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  Calculate the Difference Between two lats and longs   				 */
  /*     Code Taken from stack overflow, user dommer								   */
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
    double theta = lon1 - lon2;
    double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
    dist = Math.acos(dist);
    dist = rad2deg(dist);
    dist = dist * 60 * 1.1515;
    if (unit == 'K') {
      dist = dist * 1.609344;
    } else if (unit == 'N') {
      dist = dist * 0.8684;
      }
    return (dist);
  }
	
	
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts decimal degrees to radians 						 */
  /* 		Code Taken from stack overflow, user dommer								    :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  private double deg2rad(double deg) {
    return (deg * Math.PI / 180.0);
  }

  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  /*::  This function converts radians to decimal degrees             :*/
  /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
  private double rad2deg(double rad) {
    return (rad * 180.0 / Math.PI);
  }
  
  public PostalCode testSearch(double latitude) {
  	PostalCode searchObject = new PostalCode(latitude,0.0);
  	Collections.binarySearch(latitudeOrderList,
  			searchObject,
  			(PostalCode pc1, PostalCode pc2) -> 
  		 pc1.getLatitude() < pc2.getLatitude() ? -1: pc1.getLatitude() > pc2.getLatitude() ? 1 : 0
				);
		return searchObject;	
  }

 }

	
	


