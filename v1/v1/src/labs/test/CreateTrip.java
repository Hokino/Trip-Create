package labs.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.walmart.trans.bo.FleetLoad;
import com.walmart.trans.bo.Load;
import com.walmart.trans.bo.Order;
import com.walmart.trans.bo.Orders;
import com.walmart.trans.bo.Trip;
import com.walmart.trans.bo.common.CompositeTime;
import com.walmart.trans.bo.common.Location;
import com.walmart.trans.common.client.response.Trips;
import com.walmart.trans.common.exception.TransSystemException;
import com.walmart.trans.preview.LocationPreview;

public class CreateTrip {

	private static List<String> inputList = new ArrayList<String>();
	private static String fileLoc ="/Users/h0a00a8/Desktop/TripCreate/v1/input.txt";

	private void buildMyTrips(String [] tripInput) throws TransSystemException{
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		for (int i=0; i<1 ; i++) {
			System.out.println(i);
			TripThread tripThread = new TripThread(tripInput);
			executorService.execute(tripThread);
		}
		executorService.shutdown();

	}

	public static void main(String[] args) throws TransSystemException{
		CreateTrip cp = new CreateTrip();
		cp.readFromInputFile(fileLoc);
		for (String inputTripString : inputList) {

			System.out.println(inputTripString);

			String[] commaSeparatedArr = inputTripString.split("\\s*,\\s*");
			System.out.println(commaSeparatedArr[0]);
			System.out.println(commaSeparatedArr[1]);
			System.out.println(commaSeparatedArr[2]);
			System.out.println(commaSeparatedArr[3]);
			System.out.println(commaSeparatedArr[4]);
			System.out.println(commaSeparatedArr[5]);
			System.out.println(commaSeparatedArr[6]);
			System.out.println(commaSeparatedArr[7]);
			System.out.println(commaSeparatedArr[8]);
			System.out.println(commaSeparatedArr[9]);
			System.out.println(commaSeparatedArr[10]);
			System.out.println(commaSeparatedArr[11]);
			System.out.println(commaSeparatedArr[12]);
			System.out.println(commaSeparatedArr[13]);
			cp.buildMyTrips(commaSeparatedArr);
		}
		System.out.println("Done");
	}

	private  void readFromInputFile(String fileloc){
		File file = new File(fileloc); 
		System.out.println(fileloc);
		try {
			Scanner sc = new Scanner(file);
			while (sc.hasNext()) {
				inputList.add(sc.next());
			}
			sc.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}





}
