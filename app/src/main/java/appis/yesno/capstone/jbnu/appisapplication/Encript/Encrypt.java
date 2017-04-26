package appis.yesno.capstone.jbnu.appisapplication.Encript;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by charlesn on 2017-04-17.
 */

public class Encrypt {
    public static String encrypt_SHA1(String strData) { // 암호화 시킬 데이터
        String strENCData = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1"); // "SHA1 형식으로 암호화"
            byte[] bytData = strData.getBytes();
            md.update(bytData);
            byte[] digest = md.digest();  //배열로 저장
            for (int i = 0; i < digest.length; i++) {
                strENCData = strENCData + Integer.toHexString(digest[i] & 0xFF).toUpperCase();
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.print(e.toString()); //암호화 에러
        }
        return strENCData;  // 암호화 데이터 리턴
    }
}
