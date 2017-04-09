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
        String url = "http://xueshu.baidu.com/s?wd=spark&rsv_bp=0&tn=SE_baiduxueshu_c1gjeupa&rsv_spt=3&ie=utf-8&f=8&rsv_sug2=0&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D";
        String xpath = "//div[@class='result sc_default_result xpath-log']/div[@class='sc_content']/h3[@class='t c_font']/a/allText()";
        SelectableOpType opType = SelectableOpType.ALL;
        new WebMagicManager().spiderXpathTester(url, xpath, opType);

    }

    @Test
    public void spiderLinksTester() throws Exception {
        String url = "http://xueshu.baidu.com/s?wd=spark&rsv_bp=0&tn=SE_baiduxueshu_c1gjeupa&rsv_spt=3&ie=utf-8&f=8&rsv_sug2=0&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D";
        String regex = "http://xueshu.baidu.com/s\\?wd=\\w+&pn=\\w+&tn=SE_baiduxueshu_c1gjeupa&ie=utf-8&sc_f_para=sc_tasktype%3D%7BfirstSimpleSearch%7D&sc_hit=1";
        new WebMagicManager().spiderLinksTester(url, regex);
    }

}