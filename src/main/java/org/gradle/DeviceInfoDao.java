package org.gradle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.logging.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class DeviceInfoDao {

	Logger log = Logger.getLogger("DeviceInfoDao");
	Properties prop = null;

	DB db = null;
	List<Device> data = null;
	Connection conn = null;

	public DeviceInfoDao() {

		prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("config.properties"));
			conn = new Connection();
			db = conn.connectMongoDB();
			conn.authenticateDB();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Device> getAllDevices() {
		try {

			String deviceCollection = prop.getProperty("deviceCollection");
			DBCollection coll = db.getCollection(deviceCollection);
			log.info("Collection UE_Measurements selected successfully");

			DBCursor cursor = coll.find();
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setLast_scanned((String) o.get("last_scanned"));
				device.setMac((String) o.get("mac"));
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_channel_scanned((String) o.get("ue_channel_scanned"));
				device.setPilot((String) o.get("pilot"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				BasicDBList dbObjectFFT = (BasicDBList) o.get("fft");
				ListIterator<Object> fftList = dbObjectFFT.listIterator();
				String[] fftValues = new String[512];

				int i = 0;
				while (fftList.hasNext() && i < 512) {
					Object nextItem = fftList.next();
					fftValues[i++] = nextItem.toString();
				}
				device.setFft(fftValues);

				data.add(device);
			}

			log.info("data retrieved successfully");

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}

		finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getAllOnlineDevices() {
		try {

			String onlineDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(onlineDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ue_status", "ONLINE");
			DBCursor cursor = coll.find(whereQuery);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getAllOfflineDevices() {
		try {

			String offlineDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(offlineDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ue_status", "OFFLINE");
			DBCursor cursor = coll.find(whereQuery);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

	public List<Device> getDevicesByRadius(String x, String y, String radius) {
		try {

			String rangeDeviceCollection = prop.getProperty("deviceCollectionReg");
			DBCollection coll = db.getCollection(rangeDeviceCollection);
			log.info("Collection Registered_UE selected successfully");

			List circle = new ArrayList();
			circle.add(new double[] { Double.parseDouble(x), Double.parseDouble(y) });
			circle.add(Double.parseDouble(radius)/3959.0);
			BasicDBObject query = new BasicDBObject("loc",
					new BasicDBObject("$within", new BasicDBObject("$center", circle)));
			DBCursor cursor = coll.find(query);
			log.info("Retrieving data");

			data = new ArrayList<Device>();
			while (cursor.hasNext()) {
				DBObject o = cursor.next();
				Device device = new Device();
				device.setCount(o.get("count").toString());
				device.setUe_battery_power((String) o.get("ue_battery_power"));
				device.setUe_model((String) o.get("ue_model"));
				device.setUe_status((String) o.get("ue_status"));

				DBObject dbObject = (DBObject) o.get("loc");
				Location location = new Location();
				location.setType((String) dbObject.get("type"));
				location.setCoordinates((List<Double>) dbObject.get("coordinates"));
				device.setLoct(location);

				data.add(device);
			}

		} catch (Exception e) {
			System.out.println("Error occured");
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			db.cleanCursors(true);
			conn.mongoClient.close();
		}
		return data;
	}

}