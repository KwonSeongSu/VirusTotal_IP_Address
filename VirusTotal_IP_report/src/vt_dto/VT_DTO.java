package vt_dto;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VT_DTO {
	private List<List<String>> undetected_urls;
    private List<downloaded_samples> undetected_downloaded_samples;
    private List<downloaded_samples> detected_downloaded_samples;
	private List<DetectedUrls> detected_urls;
    private List<Resolutions> resolutions;
    private String country;
    private String asn;
    private String verbose_msg;
    private int response_code;
}
