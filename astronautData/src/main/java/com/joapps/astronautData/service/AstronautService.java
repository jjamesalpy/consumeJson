package com.joapps.astronautData.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.joapps.astronautData.model.AstonautModel;
/**
 * Main service class which handles
 * the parsing logic
 * @author JOSEPH JAMES
 *
 */
@SuppressWarnings("deprecation")
@Service
public class AstronautService {


	private String jsonUrl = "http://spacelaunchnow.me/api/3.5.0/astronaut/?limit=100&offset="; 

	/*
	 * This is the main public method which will 
	 * build the Astronaut object and returns to controller	   *
	 * 
	 */
	public List<AstonautModel>getAstronautData(){
		List<AstonautModel> astmodelL = new ArrayList<AstonautModel>();
		AstonautModel astmodel = null;
		List<JSONArray> listObj =new ArrayList<JSONArray>();
		JSONObject job =  null;
		String jsonText =null;
		try {
			jsonText =   fetchAllJsonData();
			listObj = buildJsonObject(jsonText);
			JSONArray jb = listObj.get(0);
			if(null!=jb) {
				for(int j=0; j<jb.length();j++) {
					job = jb.getJSONObject(j);
					astmodel = new AstonautModel();
					if(Objects.nonNull(job.get("name")) && !job.get("name").equals(null)) {
						astmodel.setName((String)job.get("name"));
					}
					if(Objects.nonNull(job.get("profile_image_thumbnail")) && !job.get("profile_image_thumbnail").equals(null)) {
						astmodel.setProfileImageThumbnail((String)job.get("profile_image_thumbnail"));
					}
					if(Objects.nonNull(job.get("nationality")) && !job.get("nationality").equals(null)) {
						astmodel.setNationality((String)job.get("nationality"));
					}
					if(Objects.nonNull(job.get("id")) && !job.get("id").equals(null)) {
						astmodel.setId(new Long((Integer)job.get("id")));
					}
					astmodelL.add(astmodel);
				}
			}



		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		return astmodelL;

	}

	/**
	 * This method will create the json object 
	 * from the json strin build from fetchAllJsonData
	 * method
	 * @param jsonText
	 * @return
	 */
	private List<JSONArray> buildJsonObject(String jsonText) {
		JSONObject json = new JSONObject(jsonText);
		JSONArray jsonResultset = null;
		List<JSONArray> objlisy = new ArrayList<JSONArray>();
		for(int i=0;i<json.length();i++) {
			jsonResultset =(JSONArray)json.get("results");
			objlisy.add(jsonResultset);
		}	      
		return objlisy;
	}

	/**
	 * This method was written to get the 
	 * full json data through building the URL with limit as 100 
	 * and offset as 600
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	private String fetchAllJsonData() throws JSONException, IOException {
		String sb = "";
		String modURL = null;
		if(null!=jsonUrl) {
			for(int i=0;i<7;i++) {
				modURL=jsonUrl+(i*100);
				sb=sb+readJsonStrinFromUrl(modURL);				
			}
		}
		return sb;

	}
	/**
	 * 
	 * @param jsonUrl2
	 * @return
	 * @throws IOException
	 */
	@Async
	private String readJsonStrinFromUrl(String jsonUrl2) throws IOException {
		InputStream is = new URL(jsonUrl2).openStream();
		String jsonText=null;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(jsonUrl2);

		HttpResponse httpResponse = (HttpResponse) httpClient.execute(httpget);
		HttpEntity httpEntity =  (HttpEntity) httpResponse.getEntity();
		is = ((org.apache.http.HttpEntity) httpEntity).getContent();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonText = readAll(rd);
		} finally {
			is.close();
			httpClient.close();
		}
		return jsonText;
	}

	/**
	 * 
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * GET THE SPECIFIC ASTRONAUT OBJECT
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	public AstonautModel getAstronaut(String id) throws Exception {
		AstonautModel  asnew =null;
		String url,josnText;
		List<JSONArray>  jArray = null;
		JSONObject jobj = null;
		if(Objects.nonNull(id)) {
			 url = "http://spacelaunchnow.me/api/3.5.0/astronaut/"+id;
			try {
				josnText = readJsonStrinFromUrl(url);
				if(Objects.nonNull(josnText) ) {
					 jobj = new JSONObject(josnText);
					if(Objects.isNull(jobj)) {
						throw new Exception("No Astronaut found with the id provided");
					}else {
						asnew = new AstonautModel();
						try {
						if(Objects.nonNull(jobj.get("name")) && !jobj.get("name").equals(null)) {
							asnew.setName((String)jobj.get("name"));
						}
						if(Objects.nonNull(jobj.get("profile_image_thumbnail")) && !jobj.get("profile_image_thumbnail").equals(null)) {
							asnew.setProfileImageThumbnail((String)jobj.get("profile_image_thumbnail"));
						}
						if(Objects.nonNull(jobj.get("nationality")) && !jobj.get("nationality").equals(null)) {
							asnew.setNationality((String)jobj.get("nationality"));
						}
						if(Objects.nonNull(jobj.get("id")) && !jobj.get("id").equals(null)) {
							asnew.setId(new Long((Integer)jobj.get("id")));
						}
						if(Objects.nonNull(jobj.get("date_of_birth")) && !jobj.get("date_of_birth").equals(null)) {
							asnew.setDateOfBirth((String)jobj.get("date_of_birth"));
						}
						}
						catch(JSONException je) {
							throw new Exception("No Astronaut found with the id provided");
							}
						}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return asnew;
	}
}
