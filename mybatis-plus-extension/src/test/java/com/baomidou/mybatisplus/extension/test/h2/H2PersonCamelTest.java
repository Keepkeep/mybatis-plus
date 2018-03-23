package com.baomidou.mybatisplus.extension.test.h2;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.extension.test.h2.base.AbstractH2UserTest;
import com.baomidou.mybatisplus.extension.test.h2.entity.mapper.H2PersonCamelMapper;
import com.baomidou.mybatisplus.extension.test.h2.entity.persistent.H2Person;


/**
 * <p>
 * Mybatis Plus H2 Junit Test
 * </p>
 *
 * @author Caratacus
 * @date 2017/4/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:h2/spring-test-h2-mvc-camel.xml"})
public class H2PersonCamelTest extends AbstractH2UserTest {

    @Autowired
    H2PersonCamelMapper personCamelMapper;

    @BeforeClass
    public static void initDB() throws SQLException, IOException {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:h2/spring-test-h2-mvc-camel.xml");
        DataSource ds = (DataSource) context.getBean("dataSource");
        try (Connection conn = ds.getConnection()) {
            initData(conn, "person.ddl.sql", "person.insert.sql", "h2person");
        }
    }

    @Test
    public void testInsert() {
        H2Person person = new H2Person();
        person.setName("person");
        person.setTestType(1);
        person.setLastUpdatedDt(new Date());
        Assert.assertEquals(1, personCamelMapper.insert(person).intValue());
        Long id = person.getId();
        Assert.assertNotNull(personCamelMapper.selectById(id));
    }

}
