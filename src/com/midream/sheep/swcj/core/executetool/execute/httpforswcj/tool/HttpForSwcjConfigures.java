package com.midream.sheep.swcj.core.executetool.execute.httpforswcj.tool;

import com.midream.sheep.swcj.core.APIClassInter.ExecuteConfigurationClass;

import java.util.LinkedHashMap;
import java.util.Map;

public class HttpForSwcjConfigures implements ExecuteConfigurationClass {
    @Override
    public Map<String, String> getExecuteConfiguration() {
        Map<String,String> maps = new LinkedHashMap<>();
        maps.put("http","com.midream.sheep.swcj.core.executetool.execute.httpforswcj.requests.HttpForSWCJ");
        return maps;
    }
}
