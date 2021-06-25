package com.nanami.kafka.service;

import com.baidu.aip.face.AipFace;
import com.nanami.kafka.utils.GsonUtils;
import com.nanami.kafka.utils.HttpUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengyu
 * @version 1.0
 * @Title: 百度人脸识别api调用
 * @date 2020/11/30 15:59
 */
@Service
public class BaiduService {

    @Value("${baidu.APP_ID}")
    private String APP_ID;

    @Value("${baidu.API_KEY}")
    private String API_KEY;

    @Value("${baidu.SECRET_KEY}")
    private String SECRET_KEY;

    private String token;

    private AipFace client;

    public  BaiduService() {
        // 初始化一个AipFace
        this.client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }


    /**
     * @Description:人脸识别
     *
     * @param file
     * @author fengyu
     * @date 2020/12/1 9:39
     * @return org.json.JSONObject
     */
    public JSONObject detect(MultipartFile file) throws IOException {
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 转为base64格式
        BASE64Encoder encoder = new BASE64Encoder();
        String image = encoder.encode(file.getBytes());
        String imageType = "BASE64";

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("face_field", "age");
        options.put("max_face_num", "1");
        options.put("face_type", "LIVE");
        options.put("liveness_control", "NONE");

        // 人脸检测

        JSONObject res = client.detect(image, imageType, options);
        //System.out.println(res.toString(2));
        return null;
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getAuth() {
        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + API_KEY
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + SECRET_KEY;
        try {
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.err.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            /**
             * 返回结果示例
             */
            //System.err.println("result:" + result);
            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }

    /**
     * @Description:添加人脸识别组
     *
     * @param file
     * @author fengyu
     * @date 2020/11/30 17:07
     * @return java.lang.String
     */
    public  String add(MultipartFile file) throws IOException {
        // 请求url
        BASE64Encoder encoder = new BASE64Encoder();
        String image = encoder.encode(file.getBytes());
        this.token = getAuth();
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("image_type", "BASE64");
            //定义 group_id和user_id
            map.put("group_id", "test");
            map.put("user_id", "test_user_1");
            map.put("user_info", "abc");
            //活体检测
            map.put("liveness_control", "NONE");
            map.put("quality_control", "LOW");
            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = this.token;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description:在指定人脸库中查询人员
     *
     * @param file
     * @author fengyu
     * @date 2020/12/1 9:41
     * @return java.lang.String
     */
    public String faceSearch(MultipartFile file) throws IOException {
        // 请求url
        BASE64Encoder encoder = new BASE64Encoder();
        String image = encoder.encode(file.getBytes());
        this.token = getAuth();
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", image);
            map.put("image_type", "BASE64");
            // 活体检测控制 NONE: 不进行控制 LOW:较低的活体要求(高通过率 低攻击拒绝率) NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率) HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
            map.put("liveness_control", "NONE");
            // 图片质量控制 NONE: 不进行控制 LOW:较低的质量要求 NORMAL: 一般的质量要求 HIGH: 较高的质量要求 默认 NONE
            map.put("quality_control", "LOW");
            // 指定人员信息
            map.put("group_id_list", "test");
            map.put("user_id", "test_user_1");
            String param = GsonUtils.toJson(map);
            // 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = this.token;
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
