package cn.janefish.imdadiao.signTools;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class SignUtils {


    /**
     * jspapiticket 签名
     *
     */
    public static String sha1Sign(SortedMap<Object, Object> parameters) {
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）  
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        SHA1 sha = new SHA1();
        //去掉最后一个字符
        String data = sb.toString().substring(0, sb.toString().length() - 1);
        String sign = sha.getDigestOfString(data.getBytes());
        //转换小写
        sign = sign.toLowerCase();
        return sign;
    }

}
