package VT_DTO_Sub;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DetectedUrls {
	private String url;
	private String positives;
	private String total;
	private String scan_date;
}
