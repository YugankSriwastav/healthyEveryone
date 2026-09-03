package shiva_care.healthify.kafkaevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    String phoneNo;
    String gmail;
    String smsOTP;
    String message;
    String to;
    String gmailOTP;
}
