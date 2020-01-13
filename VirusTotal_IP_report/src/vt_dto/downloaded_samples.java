package vt_dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class downloaded_samples {
	private String date;
	private String positives;
	private String total;
	private String sha256;
}
