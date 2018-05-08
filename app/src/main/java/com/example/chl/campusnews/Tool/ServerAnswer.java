package com.example.chl.campusnews.Tool;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerAnswer {
    public String msg;
    public int result;
    public JSONObject obj;//获取整个JSON结构数据

    public JSONObject data;
    private JSONArray dataArray;

    public ServerAnswer(String jsonString) throws JSONException {
        if (jsonString != null && jsonString.startsWith("\ufeff")) {
            jsonString = jsonString.substring(1);
        }
        obj = new JSONObject(jsonString);
        this.result = obj.getInt("result");
        this.msg = obj.getString("msg");
        //防止数组越界异常
        if (obj.getString("data") != null && !obj.getString("data").equals("")) {
            if (obj.getString("data").charAt(0) == '{') {
                this.data = obj.getJSONObject("data");
            }

            if (obj.getString("data").charAt(0) == '[') {
                this.dataArray = obj.getJSONArray("data");
            }
        }
    }

    public JSONArray ArrayData() {
        try {
            return obj.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getResult() {
        return this.result;
    }

    public String getMsg() {
        return this.msg;
    }

    public JSONArray getDataArray() {
        return dataArray;
    }

    public JSONObject getDataObject() {
        return data;
    }
}