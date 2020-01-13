package vt_ip_report;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import com.google.gson.Gson;

import VT_DTO_Sub.DetectedUrls;
import VT_DTO_Sub.Resolutions;
import VT_DTO_Sub.downloaded_samples;

public class httpUrlConnection_json {
	 
	private final String USER_AGENT = "Mozila/5.0";
	private final static String VT_IP = "https://www.virustotal.com/vtapi/v2/ip-address/report";
	
	public static void main(String[] args) throws Exception{

		httpUrlConnection_json httpConn_json = new httpUrlConnection_json();
		
		String apiKey = httpConn_json.getApiKey();
        String searchIp = "searchIp";
        
		System.out.println("GET으로 데이터 가져오기");
		
		VT_DTO vt_dto = httpConn_json.sendGet(VT_IP + "?apikey=" + apiKey + "&ip=" + searchIp);
		
		int response_code = vt_dto.getResponse_code();
		String verbose_msg = vt_dto.getVerbose_msg();
		String asn = vt_dto.getAsn();
		String country = vt_dto.getCountry();
		List<Resolutions> resoultions = vt_dto.getResolutions();
		List<DetectedUrls> detected_url = vt_dto.getDetected_urls();
		List<downloaded_samples> detected_download_samples = vt_dto.getDetected_downloaded_samples();
		List<downloaded_samples> undetected_download_samples = vt_dto.getUndetected_downloaded_samples();
		List<List<String>> undetected_urls = vt_dto.getUndetected_urls();
		
		//json 결과 출력
		System.out.println("*** VirusTotal_ip 검색 결과 ***");
		System.out.println("response_code : " + response_code);
		System.out.println("verbose_msg : " + verbose_msg);
		System.out.println("asn : " + asn);
		System.out.println("country : " + country);
		
		System.out.println("resolutions");
		for(int i=0; i < resoultions.size(); i++)
			System.out.printf(" - last_resolved : %s | hostName : %s\n", resoultions.get(i).getLast_resolved(), resoultions.get(i).getHostname());
		
		System.out.println("detected_url");
		for(int i=0; i < detected_url.size(); i++)
			System.out.printf(" - url : %s | positives : %s | total : %s | scan_date : %s\n", detected_url.get(i).getUrl(), detected_url.get(i).getPositives(), detected_url.get(i).getTotal(), detected_url.get(i).getScan_date());
		
		System.out.println("detected_download_samples");
		for(int i=0; i < detected_download_samples.size(); i++)
			System.out.printf(" - date : %s | positives : %s | total : %s | sha254 : %s\n", detected_download_samples.get(i).getDate(), detected_download_samples.get(i).getPositives(), detected_download_samples.get(i).getTotal(), detected_download_samples.get(i).getSha256());

		System.out.println("undetected_download_samples");
		for(int i=0; i < undetected_download_samples.size(); i++)
			System.out.printf(" - date : %s | positives : %s | total : %s | sha254 : %s\n", undetected_download_samples.get(i).getDate(), undetected_download_samples.get(i).getPositives(), undetected_download_samples.get(i).getTotal(), undetected_download_samples.get(i).getSha256());

		System.out.println("undetected_urls");
		for(int i=0; i < undetected_urls.size(); i++)
			System.out.println(undetected_urls.get(i).toString());
	}
	
	private VT_DTO sendGet(String targetUrl) throws Exception{	
		
		URL url = new URL(targetUrl);
	
		//Http Connection
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		
		con.setRequestMethod("GET");	//optional default is GET
		con.setRequestProperty("User-Agent", USER_AGENT);
		
		int responseCode = con.getResponseCode();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		//json 텍스트 저장
		while((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		String responseData = response.toString(); 
		
		System.out.println("HTTP 응답 코드 : " + responseCode);
		System.out.println("HTTP body : " + responseData);
		
		//json 파싱
		Gson gson = new Gson();
		VT_DTO vt_dto = gson.fromJson(responseData, VT_DTO.class);
		
		return vt_dto;
	}
	
	private String getApiKey() throws Exception{
		FileReader resource = new FileReader("project.properties");
        Properties properties = new Properties();

        properties.load(resource);
      
        return properties.getProperty("virustTotal_apiKey");
	}	
}
