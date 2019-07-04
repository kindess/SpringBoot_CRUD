package com.yunze.vehiclemanagement;

import com.yunze.vehiclemanagement.mapper.VehicleInfoMapper;
import com.yunze.vehiclemanagement.service.OptionFieldService;
import com.yunze.vehiclemanagement.service.OptionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VehicleManagementApplicationTests {

    @Autowired
    private VehicleInfoMapper vehicleInfoMapper;

    @Autowired
    OptionService optiondService;

    @Test
    public void contextLoads() {
//        System.out.println(vehicleInfoMapper.deleteByPrimaryKey(null));
        System.out.println(optiondService.selectAllOptionalField());
    }
}
