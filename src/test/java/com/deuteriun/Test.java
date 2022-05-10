package com.deuteriun;

import com.deuteriun.common.utils.FilesUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TEsT
 * @Description TODO
 * @Author gavin
 * @Date 9/5/2022 18:15
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Test.class})
public class Test {

    @Autowired
    FilesUtils filesUtils;

    @org.junit.Test
    public void myTest() {
    }
}
