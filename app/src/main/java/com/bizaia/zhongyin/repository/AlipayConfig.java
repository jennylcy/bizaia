package com.bizaia.zhongyin.repository;

/**
 * Created by LynnChan on 2016/12/17.
 */

public class AlipayConfig {

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2016121704357027";

    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";

    /** 商户私钥，pkcs8格式 */
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALQRC9FQRaE8MMm2\n" +
            "d+bhZHRibboFjPJlLiBAELuQavar4ArDbyCD/aRwl/XZvlo6j2kbMe7sA3FVvACx\n" +
            "ISfIp5muXr5j9lfmqivQhgWlNOjLzQoi8OA3LU2CbKqPn1zEnX4cc2S7F9fTWng4\n" +
            "wAVDm1A5IGFJBGPbFu9Z6imhVri5AgMBAAECgYBGGpjaPgFN26w27BZUSg4dDbEg\n" +
            "ikasnF3P6oFZNMmgDZxlNF9QbnLSkY9oBQUKBeCNkCvpk/NnLXTLZCq/XWx6TaxZ\n" +
            "nu6g+V/dBEhK/XB9hQyPDS/1i97MLrlp169W/uCgJCDuh0z1drdr+iDw0AZcTexo\n" +
            "xvWFQ4aYOE0yAQOYMQJBAOweIbAfJywtMSSLAoHbkzTPTaFDo/f13WsoRR5T1yUO\n" +
            "PzXs03SuvixUaCzZDw9cDJBYT5P6UtaeWPaXjy1TZUUCQQDDOqY9yT63Ah4JqyJi\n" +
            "bwG3Rw8NnHjri5jFTyBP4dq0HyIeDJ2xptj7H8LVqbV9U/rTGttnN8vME0Dnumep\n" +
            "lbrlAkEAqeT0cHm6AftyhMzRPQb1YOZZAVReQyjR7SzIrw28nJknunFxpV+mztbd\n" +
            "LofwYijlkWc5u0w/FtZRRP3As+hx0QJBAI9sPMjTWWF1CsArmx0ZU/djGqnzM9pH\n" +
            "ObpqQOCumc8NuDC/L/JdEraaRLZW8N/bnD8OUfToRq3rzxjuFk29lAkCQQCA5QOm\n" +
            "KZfcGCm/TAnh6Yi7jj4jlOwxsvDTazxU/bilz7Zl+g3QBKY6yg5WHgAvJRW1G3Ba\n" +
            "wvbOcuPVqHiiYrmN";

    public static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;



}
