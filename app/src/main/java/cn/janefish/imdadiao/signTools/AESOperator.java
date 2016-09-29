package cn.janefish.imdadiao.signTools;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESOperator {
    private static final String sKey = "8183878756300635";
    private static final String sIvs = "1861218900688990";
    private static volatile AESOperator instance = null;

    public static AESOperator getInstance() {
        if (instance == null) {
            synchronized (AESOperator.class) {
                if (instance == null)
                    instance = new AESOperator();
            }
        }
        return instance;
    }

    public static String encrypt(String encData, String secretKey, String vector) {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = secretKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
            cipher.init(1, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(encData.getBytes("utf-8"));
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String sSrc, String key, String ivs) {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(2, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /*
     * 加密
     */
    public String encrypt(String sSrc) {
        return encrypt(sSrc, sKey, sIvs);
    }

    /*
     * 解密
     */
    public String decrypt(String sSrc) throws Exception {
        return decrypt(sSrc, sKey, sIvs);
    }
}