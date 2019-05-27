package labs.test;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.walmart.trans.bo.FleetLoad;
import com.walmart.trans.bo.Load;
import com.walmart.trans.bo.Order;
import com.walmart.trans.bo.Trip;
import com.walmart.trans.bo.common.CompositeTime;
import com.walmart.trans.bo.common.Location;
import com.walmart.trans.common.client.response.Trips;
import com.walmart.trans.preview.LocationPreview;

public class TripThread implements Runnable {
	private String origlocId;
	private String destlocId;
	private String carrierId;
	private String startTime;
	private String mustdeptTime;
	private String delTime;
	private String loadType;
	private String trailerType;
	private String trailerLen;
	private String cases;
	private String cubes;
	private String pallets;
	private String weights;
	private String tripType;

//6040,100,W-6801,2019-05-23,2019-05-23,2019-05-27,STR,DRYV,53,10,10,10,100
	public TripThread(String[] tripinput) {
		this.origlocId = tripinput[0];
		this.destlocId = tripinput[1];
		this.carrierId = tripinput[2];
		this.startTime = tripinput[3];
		this.mustdeptTime = tripinput[4];
		this.delTime = tripinput[5];
		this.loadType = tripinput[6];
		this.trailerType = tripinput[7];
		this.trailerLen = tripinput[8];
		this.cases = tripinput[9];
		this.cubes = tripinput[10];
		this.pallets = tripinput[11];
		this.weights = tripinput[12];
		this.tripType = tripinput[13];
//			System.out.println("Starting new thread");
//			System.out.println("origlocId " +origlocId);
//			System.out.println("destlocId " +destlocId);
//			System.out.println("carrierId " +carrierId);
//			System.out.println("startTime " +startTime);
//			System.out.println("mustdeptTime " +mustdeptTime);
//			System.out.println("delTime " +delTime);
//			System.out.println("loadType " +loadType);
//			System.out.println("trailerType " +trailerType);
//			System.out.println("trailerLen " +trailerLen);
//			System.out.println("cases " +cases);
//			System.out.println("cubes " +cubes);
//			System.out.println("pallets " +pallets);
//			System.out.println("weights " +weights);
//			System.out.println("tripType " +tripType);

	}

	public void run() {

		List<Trip> trips = new ArrayList<Trip>();
		Trip trip = new Trip();
		CompositeTime beginTs = new CompositeTime(startTime + " " + "08:00:00", "CT");
		trip.setBeginTs(beginTs);
		CompositeTime mustDepartTS = new CompositeTime(mustdeptTime + " " + "08:00:00", "CT");
		trip.setMustDepartTS(mustDepartTS);
		trip.setBeginTs(beginTs);
		trip.setTripId(-1);
		trip.setTripTypeCode(tripType);
		CompositeTime delTS = new CompositeTime(delTime + " " + "23:23:00", "CT");
		trip.setDeliveryTime(delTS);
		FleetLoad fltLoad = new FleetLoad();
		Load load = new Load();
		load.setCarrierAssigned(this.carrierId);
		load.setLoadType(this.loadType);
		load.setTrailerId("P-");
		load.setTrailerLength(new Integer(trailerLen));
		load.setTrailerType(this.trailerType);
		Location destLoc = new Location();
		destLoc.setLocationId(new Integer(this.destlocId));
		destLoc.setLocationType("STORE");

		Location origLoc = new Location();
		origLoc.setLocationId(new Integer(this.origlocId));
		origLoc.setLocationType("DC");
		load.setDestination(destLoc);
		load.setOrigin(origLoc);
		Order[] ordersArr = new Order[1];
		Order order = new Order();
		order.setCases(new Integer(this.cases));
		order.setCube(new Integer(this.cubes));
		order.setPallets(new Integer(this.pallets));
		order.setWeight(new Integer(this.weights));
		LocationPreview destOrderLoc = new LocationPreview();
		destOrderLoc.setLocationId(new Integer(this.destlocId));
		destOrderLoc.setLocationTypeCode("STORE");
		LocationPreview origOrderLoc = new LocationPreview();
		origOrderLoc.setLocationId(new Integer(this.origlocId));
		origOrderLoc.setLocationTypeCode("DC");
		order.setDestination(destOrderLoc);
		order.setOrigin(origOrderLoc);
		ordersArr[0] = order;
		load.setOrders(ordersArr);
		fltLoad.setLoad(load);
		List<FleetLoad> fleetLoads = new ArrayList<FleetLoad>();
		fleetLoads.add(fltLoad);
		trip.setFleetLoads(fleetLoads);
		trips.add(trip);
		WebClient client = WebClient.create("http://test-transportation.wal-mart.com/us/fleetOps/v1/trips");
		client.header("userPrincipalName", "a2nair@homeoffice.wal-mart.com");
		client.header("languageCode", "en-US");
		client.header("countryCode", "US");
		client.header("userId", "a2nair");
		client.header("Content-Type", "application/xml");
		client.header("Accept", "*/*");
		client.header("WMT-RETURN-RESPONSE-ENTITY", "false");
		Response clientResponse = client.postCollection(trips, Trips.class);
		if (clientResponse.getStatus() == 201) {
			System.out.println("success-->");
		} else {
			System.out.println("failed---------------->");
		}

	}

}
