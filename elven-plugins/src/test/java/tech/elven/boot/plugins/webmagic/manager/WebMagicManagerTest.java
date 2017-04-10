package tech.elven.boot.plugins.webmagic.manager;

import org.junit.Test;
import tech.elven.boot.plugins.webmagic.enums.SelectableOpType;

/**
 * @Description
 * @Params
 * @Return
 * @Exceptions
 */
public class WebMagicManagerTest {
    @Test
    public void spiderXpathTester() throws Exception {
        String url = "http://xueshu.baidu.com/s?wd=paperuri%3A%28861e4d0498bd9b65a673e1d41f00bd34%29&filter=sc_long_sign&sc_ks_para=q%3DSpark%20Plasma%20Sintering%20of%20Alumina&sc_us=543466628458669838&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8";
        String xpath = "//div[@class='c_content content_hidden']/div[@class='abstract_wr']/p[@class='abstract']/text()";
        SelectableOpType opType = SelectableOpType.GET;
        new WebMagicManager().spiderXpathTester(url, xpath, opType);
    }

    @Test
    public void spiderLinksTester() throws Exception {
        String url = "http://xueshu.baidu.com/s?wd=spark&rsv_bp=0&tn=SE_baiduxueshu_c1gjeupa&rsv_spt=3&ie=utf-8&f=8&rsv_sug2=0&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D";
        String regex = "http://xueshu.baidu.com/s\\?wd=\\w+&pn=\\w+&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D&sc_hit=1";
        new WebMagicManager().spiderLinksTester(url, regex);
    }

}