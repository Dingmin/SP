import java.net.URLDecoder;
import java.util.Date;

import org.joda.time.DateTime;

import com.sso.client.validation.EncodeUrl;

public class test {

	public static void main(String[] args) throws Exception {
		String key = "dsfni345123oerngkn4563242bkbjkbjncsd4hj32vhdfgt435hkjb345b32b54v4hj12v4j23v532jv5hj32v45hj13v54h132b452v3b5";
		String a = EncodeUrl.encrypt("122011025"+"SSO_IDP"+"20150917-02:34"+"20150918-02:34", key);
		System.out.println(a);
	System.out.println(EncodeUrl.decrypt(a, key));	;
	}
}
