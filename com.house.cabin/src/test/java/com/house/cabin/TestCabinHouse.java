package com.house.cabin;

import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-context.xml")
//@TransactionConfiguration(transactionManager="txManager")
//@Transactional
public class TestCabinHouse {
 @Autowired
 private SqlMapClientTemplate sqlMapClientTemplate;
 
 @Test
 public void testApplicationContext() throws Exception { 
 
 }
 @Test
 public void testQuery1() throws Exception { 
      System.err.println(sqlMapClientTemplate);
      HashMap paraHm = new HashMap();
      paraHm.put("phone_number", "01049042758");
      int a = (Integer) sqlMapClientTemplate.queryForObject("isHeMember?", paraHm);
      System.out.println(a);
 }
}