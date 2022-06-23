package com.midream.sheep.swcj.core.executetool.execute.httpforswcj.pojo;

public class RequestConfigure {
    private String charset;

    public String getCharset() {
        if(this.charset==null||this.charset.equals("")){
            return "GBK";
        }
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset.trim();
    }
}
