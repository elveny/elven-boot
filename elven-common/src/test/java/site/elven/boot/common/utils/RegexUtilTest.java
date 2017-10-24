package site.elven.boot.common.utils;

import org.junit.Test;

/**
 * Created by elven on 17-4-8.
 */
public class RegexUtilTest {
    @Test
    public void matches1() throws Exception {
    }

    @Test
    public void find() throws Exception {
    }

    @Test
    public void checkChineseCharacter() throws Exception {
        System.out.println(RegexUtil.checkChineseCharacter("中"));
    }

    @Test
    public void findChineseCharacter() throws Exception {
        System.out.println(RegexUtil.findChineseCharacter("中ss"));
    }

    @Test
    public void checkEmail() throws Exception {
        System.out.println(RegexUtil.checkEmail("ddd@dd.c"));

    }

    @Test
    public void matches() throws Exception {
    }


}