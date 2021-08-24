package com.epam.esm;

import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;

public class Runner {
    public static void main(String[] args) {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("jdbctemplate-developer-config.xml");

        TagDaoImpl tagDao =
                (TagDaoImpl) context.getBean("tagDao");
        tagDao.add(new Tag("newTag"));

    }
}
