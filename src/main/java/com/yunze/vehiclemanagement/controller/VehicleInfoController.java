package com.yunze.vehiclemanagement.controller;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yunze.vehiclemanagement.pojo.OptionField;
import com.yunze.vehiclemanagement.pojo.VehicleInfo;
import com.yunze.vehiclemanagement.service.OptionService;
import com.yunze.vehiclemanagement.service.VehicleInfoService;
import com.yunze.vehiclemanagement.util.ResultCode;
import com.yunze.vehiclemanagement.util.UploadUtil;
import com.yunze.vehiclemanagement.vo.VehicleInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/vehicleInfo")
public class VehicleInfoController {

    @Autowired
    private VehicleInfoService vehicleInfoService;

    @Autowired
    private OptionService optionService;

    /**
     *  文件上传路径与映射地址
     */
    @Value("${uploadFile.urlMapping}")
    private String urlMapping;

    @Value("${uploadFile.location}")
    private String uploadFileLocation;

    /**
     * 查询所有的厂牌
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryAllVehicleBrand")
    public ResultCode queryAllVehicleBrandList(){
        List<OptionField> optionFields = optionService.selectOptionalFieldByOptionId(2);
        return ResultCode.success(optionFields);
    }

    /**
     * 查询某一页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryInfoList")
    public ResultCode queryByPageNumber(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                                        @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟着的第一个select方法会被分页，插件会自动拦截和添加 limit 分页
        List<VehicleInfoVO> vehicleInfoVOS = vehicleInfoService.selectRecordByPage(pageNum,pageSize);
        // 封装分页详细数据
        PageInfo pageInfo = new PageInfo(vehicleInfoVOS);
        return ResultCode.success(pageInfo);
    }

    /**
     * 查看车辆详情
     * @param vehicleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/vehicleId/{vehicleId}",method = RequestMethod.GET)
    public ResultCode queryVehicleInfoById(@PathVariable("vehicleId") String vehicleId){
        if (vehicleId != null){
           return ResultCode.success(vehicleInfoService.selectByPrimaryKey(vehicleId));
        }
        return ResultCode.failing();
    }

    /**
     * 请求被修改车辆原数据信息
     * @param vehicleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/vehicleId/{vehicleId}",method = RequestMethod.POST)
    public ResultCode oldData(@PathVariable("vehicleId")String vehicleId){
        return vehicleInfoService.selectOldDataByVehicleId(vehicleId);
    }

    /**
     * 修改车辆信息
     * @param vehicleInfo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editVehicleInfo",method = RequestMethod.POST)
    public ResultCode editVehicleInfoById(@ModelAttribute("vehicleInfo") VehicleInfo vehicleInfo){
        if (vehicleInfo != null ){
            return ResultCode.success(vehicleInfoService.editVehicleInfo(vehicleInfo));
        }
        return ResultCode.failing();
    }

    /**
     * 删除车辆信息
     * @param vehicleId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/vehicleId/{vehicleId}",method = RequestMethod.DELETE)
    public ResultCode deleteVehicleInfoById(@PathVariable("vehicleId")String vehicleId){
        return vehicleInfoService.deleteByPrimaryKey(vehicleId);
    }

    /**
     * 删除车辆信息
     * @param vehicleIds
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deleteVehicleInfo",method = RequestMethod.POST)
    public ResultCode deleteVehicleInfosByIds(String[] vehicleIds){
        return vehicleInfoService.deleteByPrimaryKeys(vehicleIds);
    }


    /**
     * 注册
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public ResultCode VehicleInfoRegister(VehicleInfoVO vehicleInfoVO){
        if(vehicleInfoVO != null){
            VehicleInfoVO result = vehicleInfoService.insert(vehicleInfoVO);
            if ( result != null){
                return ResultCode.success();
            }

        }
        return ResultCode.failing();
    }

    /**
     * 注册页（查询可选选项）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/registerPage",method = RequestMethod.GET)
    public ResultCode VehicleInfoAddPage(){
        return ResultCode.success(optionService.selectAllOptionalField());
    }

    /**
     * 单图上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public ResultCode uploadFile(MultipartFile file, HttpServletRequest request){
        List<String> paths = UploadUtil.uploadfiles(uploadFileLocation,urlMapping,file);
        if (paths.size()>0){
            return ResultCode.success(paths);
        }
        return ResultCode.failing();
    }

//    ############# 检索 ###############

    /**
     * 根据车牌号筛选数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryByVehicleLicense")
    public ResultCode queryByVehicleLicense(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                                        @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize,
                                       String vehicleLicense){
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟着的第一个select方法会被分页，插件会自动拦截和添加 limit 分页
        List<VehicleInfoVO> vehicleInfoVOS = vehicleInfoService.selectRecordByCondition(pageNum,pageSize,null,vehicleLicense);
        // 封装分页详细数据
        PageInfo pageInfo = new PageInfo(vehicleInfoVOS);
        return ResultCode.success(pageInfo);
    }

    /**
     * 根据厂牌筛选数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryByVehicleBrand")
    public ResultCode queryByVehicleBrand(@RequestParam(value = "pageNum",required = false,defaultValue = "1")int pageNum,
                                       @RequestParam(value = "pageSize",required = false,defaultValue = "15") int pageSize,
                                       Integer vehicleBrandId){
        PageHelper.startPage(pageNum,pageSize);
        // 紧跟着的第一个select方法会被分页，插件会自动拦截和添加 limit 分页
        List<VehicleInfoVO> vehicleInfoVOS = vehicleInfoService.selectRecordByCondition(pageNum,pageSize,vehicleBrandId,null);
        // 封装分页详细数据
        PageInfo pageInfo = new PageInfo(vehicleInfoVOS);
        return ResultCode.success(pageInfo);
    }
}