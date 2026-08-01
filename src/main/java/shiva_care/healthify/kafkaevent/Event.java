package shiva_care.healthify.kafkaevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Event {
    String phoneNo;
    String gmail;
    String smsOTO;
    String gmailOTO;
}
