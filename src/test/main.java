import com.midream.sheep.swcj.Exception.ConfigException;
import com.midream.sheep.swcj.Exception.EmptyMatchMethodException;
import com.midream.sheep.swcj.Exception.InterfaceIllegal;
import com.midream.sheep.swcj.core.build.builds.effecient.EffecientBuilder;
import com.midream.sheep.swcj.core.executetool.execute.httpforswcj.GETASD;
import com.midream.sheep.swcj.core.factory.SWCJXmlFactory;
import com.midream.sheep.swcj.core.factory.parse.BetterXmlParseTool;
import com.midream.sheep.swcj.core.factory.xmlfactory.CoreXmlFactory;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class main {
    public static void main(String[] args) throws ConfigException, IOException, ParserConfigurationException, SAXException, EmptyMatchMethodException, InterfaceIllegal, InterruptedException {
        SWCJXmlFactory swcjXmlFactory = new CoreXmlFactory();
        swcjXmlFactory.setBuilder(new EffecientBuilder());
        swcjXmlFactory.setParseTool(new BetterXmlParseTool());
        long start = System.currentTimeMillis();
        swcjXmlFactory.parse(new File("E:\\SWCJ\\core\\httpToolForSWCJ\\src\\eff.xml"));
        GETASD html = (GETASD) swcjXmlFactory.getWebSpiderById("getHtml");
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));
        OutputStream os = new FileOutputStream("e://a.exe");
        try (InputStream inputStream = html.getJson()[0]) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) != -1) {
                os.write(buffer,0,len);
            }
        }
    }
}
