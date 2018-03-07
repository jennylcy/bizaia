package com.bizaia.zhongyin.util;

import android.util.Log;

import com.bizaia.zhongyin.module.video.data.PlayUrlDetailBean;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyz on 2017/3/18.
 */

public class GsonUtils {

    public static <T> T format(String text, Class<T> clazz) {
        String json=null;
        try {
//            json = OtherUtils.restore(text);
            return new GsonBuilder().create().fromJson(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return (T) new GsonBuilder().create().fromJson(json, ErrorEntity.class);
        }
    }

    public static <T> T formatChange(String text, Class<T> clazz) {

        try {

            return new GsonBuilder().create().fromJson(text, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return (T) new GsonBuilder().create().fromJson(text, ErrorEntity.class);
        }
    }

    public static ErrorEntity format(String text){

        try {
//            String json = OtherUtils.restore(text);
            return new GsonBuilder().create().fromJson(text, ErrorEntity.class);
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

    public static List<PlayUrlDetailBean> transStringToList(String jsonData){
        Log.e("GsonUtils", "transStringToList: ------------>"+jsonData);
        List<PlayUrlDetailBean> listData = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            Log.e("GsonUtils", "transStringToList: ------------>size+-------->"+jsonArray.isNull(0));
            if(jsonArray.getJSONObject(0)!=null){
                PlayUrlDetailBean bean = new PlayUrlDetailBean();
                bean.setUrl(jsonArray.getJSONObject(0).getString("url"));
                bean.setClarity(jsonArray.getJSONObject(0).getString("clarity"));
                listData.add(bean);
            }
            if(!jsonArray.isNull(1)&&jsonArray.getJSONObject(1)!=null){
                PlayUrlDetailBean bean = new PlayUrlDetailBean();
                bean.setUrl(jsonArray.getJSONObject(1).getString("url"));
                bean.setClarity(jsonArray.getJSONObject(1).getString("clarity"));
                listData.add(bean);
                Log.e("GsonUtils", "transStringToList: ----->"+jsonArray.getJSONObject(1).getString("clarity") );
            }
            if(!jsonArray.isNull(2)&&jsonArray.getJSONObject(2)!=null){
                PlayUrlDetailBean bean = new PlayUrlDetailBean();
                bean.setUrl(jsonArray.getJSONObject(2).getString("url"));
                bean.setClarity(jsonArray.getJSONObject(2).getString("clarity"));
                listData.add(bean);
                Log.e("GsonUtils", "transStringToList: ----->"+jsonArray.getJSONObject(2).getString("clarity") );
            }
            return  listData;
        }catch (Exception e){
            Log.e("GsonUtils", "transStringToList: ----->"+e.getMessage() );
        }
//                JsonReader reader = new JsonReader(new StringReader(jsonData));
//        reader.setLenient(true); // 在宽松模式下解析
//        try {
//            reader.beginArray(); // 开始解析数组（包含一个或多个Json对象）
//            while (reader.hasNext()) { // 如果有下一个数据就继续解析
//                reader.beginObject(); // 开始解析一个新的对象
//                while (reader.hasNext()) {
//                    PlayUrlDetailBean bean = new PlayUrlDetailBean();
//                    String tagName = reader.nextName(); // 得到下一个属性名
//                    if (tagName.equals("url")) {
//                        Log.e("GsonUtils", "transString: ------>"+reader.nextString() );
//                        bean.setUrl(reader.nextString());
//                    } else if (tagName.equals("name")) {
//                        Log.e("GsonUtils", "transString: ------>"+reader.nextString() );
//                        bean.setName(reader.nextString());
//                    } else if (tagName.equals("clarity")) {
//                        Log.e("GsonUtils", "transString: ------>"+reader.nextString() );
//                        bean.setClarity(reader.nextString());
//                    }
//                    listData.add(bean);
//                }
//                reader.endObject(); // 结束对象的解析
//            }
//            reader.endArray(); // 结束解析当前数组
//        } catch (IOException e) {
//            // TODO 自动生成的 catch 块
//            e.printStackTrace();
//        }
        return listData;
    }

    public static String transformMsg(String imgPath,String content,String name,String id){
//        {"nickName":"cxx","sendId":"657333DF95595A6DF3BC03F5BD1F70C1","headImg":"http://192.168.4.205:8090//upload/img/1490002382361eSffR.jpg","news":"weedfff"}
        return "{\"headImg\":\""+imgPath+"\",\"news\":\""+content+"\",\"nickName\":\""+name+"\",\"sendId\":\""+id+"\"}";
    }

}
