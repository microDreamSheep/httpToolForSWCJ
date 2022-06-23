package com.midream.sheep.swcj.core.executetool.execute.httpforswcj.requests;

import com.midream.sheep.swcj.Exception.ConfigException;
import com.midream.sheep.swcj.core.executetool.SWCJExecute;
import com.midream.sheep.swcj.core.executetool.execute.httpforswcj.pojo.RequestConfigure;
import com.midream.sheep.swcj.core.executetool.execute.httpforswcj.tool.HttpTool;
import com.midream.sheep.swcj.pojo.ExecuteValue;

import java.io.*;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpForSWCJ<T> implements SWCJExecute<T> {
    private static final String jsonRe = "<jsonRe>";
    private static final String jsonReI = "</jsonRe>";
    private static final String jsonJava = "<jsonJava>";
    private static final String jsonJavaI = "</jsonJava>";

    @Override
    public List<T> execute(ExecuteValue executeValue, String... strings) throws Exception {
        String s = "";
        try {
            s = strings[0].substring(strings[0].indexOf(jsonJava) + jsonJava.length(), strings[0].indexOf(jsonJavaI));
        } catch (Exception ignored) {
        }
        List<T> list = new LinkedList<>();
        InputStream is = null;
        switch (executeValue.getType()) {
            case GET -> is = GETJSON(executeValue, strings[0]);
            case POST -> is = POST(executeValue, strings[0]);
            default -> throw new ConfigException("Unexpected value: " + executeValue.getType());
        }
        if(executeValue.getClassNameReturn().equals("java.lang.String[]")){
            StringBuilder sb = new StringBuilder();
            try  {
                //get the response data by inputStream
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    sb.append(new String(buffer, 0, len,getRequestConfigure(s).getCharset()));
                }
            }catch (Exception ignored){}
            list.add((T) sb.toString());
            is.close();
            return list;
        }
        list.add((T) is);
        return list;
    }

    /**
     * send a GET request  to the url to get json data
     */
    public InputStream GETJSON(ExecuteValue executeValue, String in ) throws IOException {//build a connection
        URLConnection connection = HttpTool.buildConnection(executeValue.getUrl());
        String s = "";
        try {
            s = in.substring(in.indexOf(jsonRe) + jsonRe.length(), in.indexOf(jsonReI));
        } catch (Exception ignored) {
        }
        //set the request property
        HttpTool.setRequestProperty(connection, executeValue, s);
        //send request
        connection.connect();
        return connection.getInputStream();
    }

    public InputStream POST(ExecuteValue executeValue, String in ) throws IOException {
        //build a connection
        URLConnection connection = HttpTool.buildConnection(executeValue.getUrl());
        //set the request property
        String s = "";
        try {
            s = in.substring(in.indexOf(jsonRe) + jsonRe.length(), in.indexOf(jsonReI));
        } catch (Exception ignored) {
        }
        //set the request property
        HttpTool.setRequestProperty(connection, executeValue, s);
        connection.setDoOutput(true);
        connection.setDoInput(true);

        // 获取URLConnection对象对应的输出流
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            // 发送请求参数
            out.print(mapToString(executeValue.getValues()));
            // flush输出流的缓冲
            out.flush();
        }
        // 读取响应数据
        return connection.getInputStream();
    }

    /**
     * change map to String
     */
    public String mapToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(map.get(key));
            sb.append("&");
        }
        return sb.toString();
    }

    /**
     * 分析字符获取请求参数类
     */
    public RequestConfigure getRequestConfigure(String in) {
        RequestConfigure requestConfigure = new RequestConfigure();
        String[] split = in.split("\n");
        for (String s : split) {
            if (s.contains("charset")) {
                requestConfigure.setCharset(s.split("=")[1]);
            }
        }
        return requestConfigure;
    }
}
