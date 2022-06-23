package com.midream.sheep.swcj.core.executetool.execute.httpforswcj.tool;

import com.midream.sheep.swcj.pojo.ExecuteValue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpTool {
    /**
     * build a connection
     * */
    public static URLConnection buildConnection(String url) throws IOException {
        URL url1 = new URL(url);
        return url1.openConnection();
    }
    /**
     * set the request property
     * */
    public static void setRequestProperty(URLConnection connection, ExecuteValue executeValue,String in) {
        //set the request accept
        connection.setRequestProperty("accept", "application/json");
        //set the request content type
        connection.setRequestProperty("content-type", "application/json");
        //set the request user agent
        connection.setRequestProperty("user-agent",executeValue.getUserAge());
        //set the request cookie
        connection.setRequestProperty("cookie",executeValue.getCookies());
        //set the request timeout
        connection.setConnectTimeout(Integer.parseInt(executeValue.getTimeout()));
        //set the request method
        connection.setRequestProperty("method", executeValue.getType().getValue());
        //set the values
        connection.setRequestProperty("connection", "Keep-Alive");
        try {
            for (String s : in.split("\n")) {
                String[] split = s.split(":");
                connection.setRequestProperty(split[0], split[1]);
            }
        }catch (Exception ignored){
        }
    }
}
