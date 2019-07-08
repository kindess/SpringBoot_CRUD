var layerIndex;
layui.use(['form','table'], function(){
    var form = layui.form;
    var table = layui.table;
    $(function () {
        $.post("/vehicleInfo/queryAllVehicleBrand",function (res) {
            $('#vehicleBrand-select').append(new Option("全部", -1));
            $.each(res.data[0], function (index, item) {
                $('#vehicleBrand-select').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            layui.form.render();
        });
    });
    function loadTable(url,jsonData){
        // 数据表格绘制
        table.render({
            elem: '#demo',
            height: 512,
            url: url, //数据接口
            where: jsonData, //其他请求参数
            method: "post",
            // 对分页请求的参数：page、limit重新设定名称
            request:{
                pageName: 'pageNum', //页码的参数名称，默认：page
                limitName: 'pageSize', //每页数据量的参数名，默认：limit
            },
            // 将请求返回的任意数据格式解析成 table 组件规定的json格式（table 组件所规定的数据，详见： response 参数介绍）
            // 除了使用pareData之外，还可以使用response重新规定返回的数据格式
            parseData: function(res){ //res 即为原始返回的数据
                var data;
                // 请求成功
                if (res.status == 200){
                    data = {
                        "code": 0, //解析接口状态
                        "msg": res.message, //解析提示文本
                        "count":  res.data[0].total, //数据总条数
                        "data": res.data[0].list //解析数据列表
                    };
                } else {
                    data =  {
                        "code": res.status, //解析接口状态
                        "msg": res.message, //解析提示文本
                        "count":0, //解析数据长度
                        "data": null //解析数据列表
                    };
                }
                return data;
            },
            // 表头
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', hide: true},
                {field: 'vehicleLicense', title: '车牌号', width:135} ,
                {field: 'vehicleType', title: '类型', width:80, },
                {field: 'level', title: '等级', width:80},
                {field: 'drivingLicenseNumber', title: '行驶证编号', width: 140},
                {field: 'vehicleBrand', title: '厂牌', width: 120},
                {field: 'seatsOfGuests', title: '载客数', width: 90, sort: true},
                {field: 'annualReviewStatus', title: '状态', width: 90},
                {field: 'dateInitialRegistration', title: '上户日期', width: 180, sort: true},
                {field: 'subordinateSuppliers', title: '所属供应商', width: 135},
                {field: 'dispatchingRight', title: '调度权', width: 80,},
                {field: 'businessPurpose', title: '业务用途', width: 90,},
                {field: 'wealth', title: '最近绑定产品', width: 250,}
            ]],
            cellMinWidth: 80,
            // 分页设置
            page: true ,//开启分页
            limit:10 ,  //默认十条数据一页
            limits:[10,20,30,50],  //数据分页条
            //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            toolbar: '#toolbarDemo'
        });
    }
    //筛选
    form.on('submit(selectForm)', function(data){
        var url = "/vehicleInfo/queryByVehicleLicense";
        loadTable(url,data.field);
        return false;
    });
    // 监听厂牌下拉菜单
    form.on('select(optionSelectForm)', function(data){
        var url = "/vehicleInfo/queryByVehicleBrand";
        loadTable(url,{"vehicleBrandId":data.value});
        return false;
    });
    //监听提交
    form.on('submit(formDemo)', function(data){
        if (data.field.imageUrl == null ||data.field.imageUrl==""||data.field.imageUrl==undefined) {
            layer.msg("图片为空！")
            return false;
        }
        // layer.msg(JSON.stringify(data.field));
        var vehicleId = data.field.id;
        var url = "/vehicleInfo/queryInfoList";
        // 没有id就是添加,否则就是修改
        if (vehicleId == undefined || vehicleId == ""){
            $.post("/vehicleInfo/register",data.field,function(res){
                if (res.status==200){
                    layer.msg("操作成功");
                    // 刷新页面
                    $(function (){
                        layer.close(layerIndex);
                        setTimeout(loadTable(url,{}), 2000); //延迟1秒
                    })
                    return;
                }
                layer.msg("操作失败");
                return false;
            });
        } else {
            $.post("/vehicleInfo/editVehicleInfo",data.field,function(res){
                if (res.status==200){
                    layer.msg("操作成功");
                    // 刷新页面
                    $(function (){
                        layer.close(layerIndex);
                        setTimeout(loadTable(url,data.field), 2000); //延迟1秒
                    })
                    return
                }
                layer.msg("操作失败");
            });
        }
        return false;
    });

    //监听头工具事件
    table.on('toolbar(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        //获得当前行数据
        // console.log(obj.data);
        var checkStatus = table.checkStatus(obj.config.id);
        var element = layui.element;
        switch(obj.event){//获得 lay-event 对应的值
            case 'insert':
                // layer.msg('添加');
                //Ajax获取数据，动态初始化下拉框与单选按钮
                $.get('/vehicleInfo/registerPage', function(res){
                    var data = res.data[0];
                    /*  $.each(data, function (index1, item1) {
                          $.each(item1,function (index2, item2) {
                             console.log(item2)
                          });
                      });*/
                    /* var array = data.vehicleMaintenanceStatus;
                       $.each(array, function(i, value) {
                           console.log(value);
                       });*/
                    //获取后台数据，重新渲染表单
                    formRender(form,data);
                    layerIndex = layer.open({
                        type: 1,
                        title: '车辆注册',
                        area: ['1080px', '700px'],
                        shadeClose: false, //点击遮罩关闭
                        content: $('.add-main'),
                        btn: ['确定', '取消'],
                    });
                });
                break;
            case 'edit':
                // layer.alert(JSON.stringify(data));
                var checkStatus = table.checkStatus(obj.config.id);
                //获取所有选择的数据使用Object.prototype.toString.call(data)，typeof 和 instanceof实际在使用中是有一些问题的
                var data = checkStatus.data;
                // 判断类型
                //未选择
                /* if ( Object.prototype.toString.call(data) === '[object undefined]') {
                     return layer.msg("请选择需要编辑的值！");
                 }*/
                if ( data.length ==0) {
                    return layer.msg("请选择需要编辑的值！");
                }
                if ( data.length > 1 ) {
                    return layer.msg("请不要选择多条数据！");
                }
                $.get("/vehicleInfo/vehicleId/"+data[0].id,function (res) {
                    if (res.status == 200){
                        //原数据
                        var oldData = res.data[0];
                        //选项参数
                        // console.log(res.data[1][0])
                        formRender(form,res.data[1]);
                        layerIndex = layer.open({
                            type: 1,
                            title: '车辆信息编辑',
                            area: ['1080px', '700px'],
                            shadeClose: false, //点击遮罩关闭
                            content: $('.edit-main'),
                            btn: ['确定', '取消'],
                            //弹出层弹出完毕时即执行一些语句
                            success: function (layero, index){
                                var body = layer.getChildFrame('body', index);//获取弹出层的dom元素
                                // 表单初始化
                                // "addForm"是form标签中lay-filter="" 对应的值
                                form.val("addForm", {
                                    "id":oldData.id
                                    ,"vehicleLicense": oldData.vehicleLicense
                                    ,"owener": oldData.owener
                                    ,"optionColorId": oldData.optionColorId
                                    ,"engineNumber": oldData.engineNumber
                                    ,"vehicleBrandId": oldData.vehicleBrandId
                                    ,"vehicleRackNumber": oldData.vehicleRackNumber
                                    ,"vehicleModel": oldData.vehicleModel
                                    ,"registerDate":oldData.registerDate
                                    ,"vehicleTypeId":oldData.vehicleTypeId
                                    ,"dateInitialRegistration":oldData.dateInitialRegistration
                                    ,"bodyColor": oldData.bodyColor
                                    ,"vehicleMaintenanceStatusId": oldData.vehicleMaintenanceStatusId
                                    ,"seatingCapacity": oldData.seatingCapacity
                                    ,"annualReviewStatusId": oldData.annualReviewStatusId
                                    ,"fuelTypeId": oldData.fuelTypeId
                                    ,"nextAnnualInspectionTime":oldData.nextAnnualInspectionTime
                                    ,"displacement": oldData.displacement
                                    ,"levelId": oldData.levelId
                                    ,"businessPurposeId": oldData.businessPurposeId
                                    ,"scopeOfBusinessId":  oldData.scopeOfBusinessId
                                    ,"subordinateSuppliersId": oldData.subordinateSuppliersId
                                    ,"seatsOfGuests": oldData.seatsOfGuests
                                    ,"mileage": oldData.mileage
                                    ,"drivingLicenseNumber": oldData.drivingLicenseNumber
                                    ,"dispatchingRightId": oldData.dispatchingRightId
                                    ,"remarks": oldData.remarks
                                });
                                // 图片回显
                                var uploadImage = $("#uploadImage");
                                //是否返回图片地址
                                if (oldData.imageUrl !=""&&oldData.imageUrl!= undefined){
                                    uploadImage.attr("src",oldData.imageUrl);
                                    uploadImage.attr("style","width:100%;height:100%;display:block");
                                    // 隐藏 i标签中的图标
                                    $(".hide-custom").attr("style","display:none");
                                    // 将返回的图片地址赋值给id="imageUrl"的隐藏框
                                    $("#imageUrl").attr("value",oldData.imageUrl);
                                } else {
                                    uploadImage.attr("style","display:none");
                                    // 显示 i标签中的图标
                                    $(".hide-custom").attr("style","display:block");
                                }
                            }
                        });
                        return;
                    }
                    layer.msg("操作失败");
                });
                break;
            case 'enable':
                layer.msg('启用');
                break;
            case 'disable':
                layer.msg('禁用');
                break;
            case 'delete':
                var checkStatus = table.checkStatus(obj.config.id);
                //获取所有选择的数据
                var data = checkStatus.data;
                if ( data.length ==0) {
                    return layer.msg("请选择需要编辑的值！");
                }
                if ( data.length > 1 ) {
                    return layer.msg("请不要选择多条数据！");
                }
                var url = "/vehicleInfo/queryInfoList";
                var array = new Array();
                $.each(data,function (index,item) {
                    //入栈
                    array.push(item.id)
                });
                console.log(array);
                $.ajax({
                    "url":"/vehicleInfo/deleteVehicleInfo",
                    "type":"post",
                    "traditional":true, // 提交数组
                    "data":{"vehicleIds":array},
                    "success":function(res){
                        if (res.status == 200){
                            layer.msg("操作成功");
                            // 刷新页面
                            $(function (){
                                setTimeout(loadTable(url,{}), 2000); //延迟1秒
                            })
                            return ;
                        }
                        layer.msg("操作失败");
                    }
                })
                break;
        };

        /*        //监听提交
                form.on('submit(formDemo)', function(data){
                    // layer.msg(JSON.stringify(data.field));
                    var vehicleId = data.field.id;
                    var url =
                    // 没有id，添加,否则就是修改
                    if (vehicleId == undefined || vehicleId == ""){
                        $.post("/vehicleInfo/register",data.field,function(res){
                            if (res.status==200){
                                return layer.msg("操作成功");
                                // 刷新页面
                                $(function (){
                                    setTimeout(loadTable(url,data.field), 1000); //延迟1秒
                                })
                            }
                            layer.msg("操作失败");
                        });
                    } else {
                        $.post("/vehicleInfo/editVehicleInfo",data.field,function(res){
                            if (res.status==200){
                                return layer.msg("操作成功");
                                // 刷新页面
                                $(function (){
                                    setTimeout(loadTable(url,data.field), 1000); //延迟1秒
                                })
                            }
                            layer.msg("操作失败");
                        });
                    }
                    return false;
                });*/
        /*upload();
        //文件上传模块（自动上传，返回上传图片的地址）
        function upload(){
            var upload = layui.upload; //引入文件上传模块
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#upload'
                ,url: '/vehicleInfo/uploadFile'
                ,accept: 'images' /!* 只能穿图片 *!/
                ,acceptMime: 'image/!*' /!* 打开文件选择器时 只显示图片*!/
                ,multiple: false /!* 是否允许多文件上传 true是 false否*!/
                ,done: function(res){
                    //上传失败
                    if(res.status == 201){
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    var uploadImage = $("#uploadImage");
                    uploadImage.attr("src",res.data[0][0]);
                    uploadImage.attr("style","width:100%;height:100%;display:block");
                    // 隐藏 i标签中的图标
                    $(".hide-custom").attr("style","display:none");
                    // 将返回的图片地址赋值给id="imageUrl"的隐藏框
                    $("#imageUrl").attr("value",res.data[0][0]);
                }
                ,error: function(){
                    //上传失败
                    return layer.msg('上传失败，请重试!');
                }
            });
        };*/

        //获取后台数据，重新渲染表单（渲染表单中下拉框与单选按钮）
        function formRender(form,data){
            // 1、清除html文本
            /* $('#vehicleMaintenanceStatus').empty();
             $('#vehicleBrand').empty();
             $('#businessPurpose').empty();
             $('#color').empty();
             $('#fuelType').empty();
             $('#level').empty();
             $('#dispatchingRightId').empty();
             $('#subordinateSuppliers').empty();
             $('#annualReviewStatus').empty();
             $('#scopeOfBusiness').empty();
             $('#vehicleType').empty();*/
            $('.clear-input').val("");
            $(".clear-select").empty();
            // 显示 i标签中的图标
            $(".hide-custom").attr("style","display:block");
            // 去掉id="imageUrl"的图片地址
            $("#imageUrl").attr("value","");
            $("#uploadImage").removeAttr("src");
            $("#uploadImage").attr("style","display: none")

            console.log(data.vehicleMaintenanceStatus);
            // 2、重新初始化下拉框与单选按钮html文本
            //车辆检修
            $.each(data.vehicleMaintenanceStatus, function (index, item) {
                // console.log(item);
                $('#vehicleMaintenanceStatus').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 厂牌选项
            $.each(data.vehicleBrand, function (index, item) {
                $('#vehicleBrand').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 业务用途
            $.each(data.businessPurpose, function (index, item) {
                $('#businessPurpose').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 车牌颜色
            $.each(data.color, function (index, item) {
                $('#color').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 燃料类型
            $.each(data.fuelType, function (index, item) {
                $('#fuelType').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //等级选项数据
            $.each(data.level, function (index, item) {
                $('#level').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //调度权
            $.each(data.dispatchingRight, function (index, item) {
                $('#dispatchingRightId').append(item.fieldName+" <input type='radio' name='dispatchingRightId' value='"+item.fieldId+"' checked>");// 下拉菜单里添加元素
            });
            //供应商
            $.each(data.subordinateSuppliers, function (index, item) {
                $('#subordinateSuppliers').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 年审
            $.each(data.annualReviewStatus, function (index, item) {
                $('#annualReviewStatus').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //运营范围
            $.each(data.scopeOfBusiness, function (index, item) {
                $('#scopeOfBusiness').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //车辆类型
            $.each(data.vehicleType, function (index, item) {
                $('#vehicleType').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 更新全部（一定要加上，不然表单不会被重新渲染）
            form.render();
        }
    });
});


// 预先加载表格模块与表单模块
layui.use(['table','form','upload'], function(){
    var $ = layui.jquery;   //引入jq模块
    var table = layui.table;
    var form =layui.form;
    // 数据表格绘制
    table.render({
        elem: '#demo',
        height: 512,
        url: '/vehicleInfo/queryInfoList', //数据接口
        method: "post",
        // 对分页请求的参数：page、limit重新设定名称
        request:{
            pageName: 'pageNum', //页码的参数名称，默认：page
            limitName: 'pageSize', //每页数据量的参数名，默认：limit
        },
        // 将请求返回的任意数据格式解析成 table 组件规定的json格式（table 组件所规定的数据，详见： response 参数介绍）
        // 除了使用pareData之外，还可以使用response重新规定返回的数据格式
        parseData: function(res){ //res 即为原始返回的数据
            var data;
            // 请求成功
            if (res.status == 200){
                data = {
                    "code": 0, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count":  res.data[0].total, //数据总条数
                    "data": res.data[0].list //解析数据列表
                };
            } else {
                data =  {
                    "code": res.status, //解析接口状态
                    "msg": res.message, //解析提示文本
                    "count":0, //解析数据长度
                    "data": null //解析数据列表
                };
            }
            return data;
        },
        // 表头
        // 报错的位置提示cols：[[...]],就是[[]]里面的内容不能渲染。[[]]是thymeleaf的内联表达式，可以在cols的后面换行。或者在script标签里 th:inline="none"
        cols: [[
            {type: 'checkbox', fixed: 'left'},
            {field: 'id', title: 'ID', width:80, sort: true, fixed: 'left', hide: true},
            {field: 'vehicleLicense', title: '车牌号', width:135} ,
            {field: 'vehicleType', title: '类型', width:80, },
            {field: 'level', title: '等级', width:80},
            {field: 'drivingLicenseNumber', title: '行驶证编号', width: 140},
            {field: 'vehicleBrand', title: '厂牌', width: 120},
            {field: 'seatsOfGuests', title: '载客数', width: 90, sort: true},
            {field: 'annualReviewStatus', title: '状态', width: 90},
            {field: 'dateInitialRegistration', title: '上户日期', width: 180, sort: true},
            {field: 'subordinateSuppliers', title: '所属供应商', width: 135},
            {field: 'dispatchingRight', title: '调度权', width: 80,},
            {field: 'businessPurpose', title: '业务用途', width: 90,},
            {field: 'wealth', title: '最近绑定产品', width: 250,}
        ]],
        cellMinWidth: 80,
        // 分页设置
        page: true ,//开启分页
        limit:10 ,  //默认十条数据一页
        limits:[10,20,30,50],  //数据分页条
        //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
        toolbar: '#toolbarDemo'
    });

    //监听复选框选择
    /*  table.on('checkbox(test)', function(obj){
        /!*  console.log(obj.checked); //当前是否选中状态
          console.log(obj.data); //选中行的相关数据
          console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one*!/
          console.log(obj.data.id)
          console.log(obj.data.id)
      });*/

/*    //监听头工具事件
    table.on('toolbar(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        //获得当前行数据
        // console.log(obj.data);
        var checkStatus = table.checkStatus(obj.config.id);
        var element = layui.element;
        switch(obj.event){//获得 lay-event 对应的值
            case 'insert':
                // layer.msg('添加');
                //Ajax获取数据，动态初始化下拉框与单选按钮
                $.get('/vehicleInfo/registerPage', function(res){
                    var data = res.data[0];
                    /!*  $.each(data, function (index1, item1) {
                          $.each(item1,function (index2, item2) {
                             console.log(item2)
                          });
                      });*!/
                    /!* var array = data.vehicleMaintenanceStatus;
                       $.each(array, function(i, value) {
                           console.log(value);
                       });*!/
                    //获取后台数据，重新渲染表单
                    formRender(form,data);
                    layerIndex = layer.open({
                        type: 1,
                        title: '车辆注册',
                        area: ['1080px', '700px'],
                        shadeClose: false, //点击遮罩关闭
                        content: $('.add-main'),
                        btn: ['确定', '取消'],
                    });
                });
                break;
            case 'edit':
                // layer.alert(JSON.stringify(data));
                var checkStatus = table.checkStatus(obj.config.id);
                //获取所有选择的数据使用Object.prototype.toString.call(data)，typeof 和 instanceof实际在使用中是有一些问题的
                var data = checkStatus.data;
                // 判断类型
                //未选择
                /!* if ( Object.prototype.toString.call(data) === '[object undefined]') {
                     return layer.msg("请选择需要编辑的值！");
                 }*!/
                if ( data.length ==0) {
                    return layer.msg("请选择需要编辑的值！");
                }
                if ( data.length > 1 ) {
                    return layer.msg("请不要选择多条数据！");
                }
                $.get("/vehicleInfo/vehicleId/"+data[0].id,function (res) {
                    if (res.status == 200){
                        //原数据
                        var oldData = res.data[0];
                        //选项参数
                        // console.log(res.data[1][0])
                        formRender(form,res.data[1]);
                        layerIndex = layer.open({
                            type: 1,
                            title: '车辆信息编辑',
                            area: ['1080px', '700px'],
                            shadeClose: false, //点击遮罩关闭
                            content: $('.edit-main'),
                            btn: ['确定', '取消'],
                            //弹出层弹出完毕时即执行一些语句
                            success: function (layero, index){
                                var body = layer.getChildFrame('body', index);//获取弹出层的dom元素
                                // 表单初始化
                                // "addForm"是form标签中lay-filter="" 对应的值
                                form.val("addForm", {
                                    "id":oldData.id
                                    ,"vehicleLicense": oldData.vehicleLicense
                                    ,"owener": oldData.owener
                                    ,"optionColorId": oldData.optionColorId
                                    ,"engineNumber": oldData.engineNumber
                                    ,"vehicleBrandId": oldData.vehicleBrandId
                                    ,"vehicleRackNumber": oldData.vehicleRackNumber
                                    ,"vehicleModel": oldData.vehicleModel
                                    ,"registerDate":oldData.registerDate
                                    ,"vehicleTypeId":oldData.vehicleTypeId
                                    ,"dateInitialRegistration":oldData.dateInitialRegistration
                                    ,"bodyColor": oldData.bodyColor
                                    ,"vehicleMaintenanceStatusId": oldData.vehicleMaintenanceStatusId
                                    ,"seatingCapacity": oldData.seatingCapacity
                                    ,"annualReviewStatusId": oldData.annualReviewStatusId
                                    ,"fuelTypeId": oldData.fuelTypeId
                                    ,"nextAnnualInspectionTime":oldData.nextAnnualInspectionTime
                                    ,"displacement": oldData.displacement
                                    ,"levelId": oldData.levelId
                                    ,"businessPurposeId": oldData.businessPurposeId
                                    ,"scopeOfBusinessId":  oldData.scopeOfBusinessId
                                    ,"subordinateSuppliersId": oldData.subordinateSuppliersId
                                    ,"seatsOfGuests": oldData.seatsOfGuests
                                    ,"mileage": oldData.mileage
                                    ,"drivingLicenseNumber": oldData.drivingLicenseNumber
                                    ,"dispatchingRightId": oldData.dispatchingRightId
                                    ,"remarks": oldData.remarks
                                });
                                // 图片回显
                                var uploadImage = $("#uploadImage");
                                //是否返回图片地址
                                if (oldData.imageUrl !=""&&oldData.imageUrl!= undefined){
                                    uploadImage.attr("src",oldData.imageUrl);
                                    uploadImage.attr("style","width:100%;height:100%;display:block");
                                    // 隐藏 i标签中的图标
                                    $(".hide-custom").attr("style","display:none");
                                    // 将返回的图片地址赋值给id="imageUrl"的隐藏框
                                    $("#imageUrl").attr("value",oldData.imageUrl);
                                } else {
                                     uploadImage.attr("style","display:none");
                                    // 显示 i标签中的图标
                                    $(".hide-custom").attr("style","display:block");
                                }
                            }
                        });
                        return;
                    }
                    layer.msg("操作失败");
                });
                break;
            case 'enable':
                layer.msg('启用');
                break;
            case 'disable':
                layer.msg('禁用');
                break;
            case 'delete':
                var checkStatus = table.checkStatus(obj.config.id);
                //获取所有选择的数据
                var data = checkStatus.data;
                var array = new Array();
                $.each(data,function (index,item) {
                    //入栈
                    array.push(item.id)
                });
                console.log(array);
                $.ajax({
                    "url":"/vehicleInfo/deleteVehicleInfo",
                    "type":"post",
                    "traditional":true, // 提交数组
                    "data":{"vehicleIds":array},
                    "success":function(res){
                        if (res.status == 200){
                            return layer.msg("操作成功");
                            // 刷新页面
                            $(function (){
                                setTimeout(loadTable(url,{}), 2000); //延迟1秒
                            })
                        }
                        layer.msg("操作失败");
                    }
                })

                break;
        };

/!*        //监听提交
        form.on('submit(formDemo)', function(data){
            // layer.msg(JSON.stringify(data.field));
            var vehicleId = data.field.id;
            var url =
            // 没有id，添加,否则就是修改
            if (vehicleId == undefined || vehicleId == ""){
                $.post("/vehicleInfo/register",data.field,function(res){
                    if (res.status==200){
                        return layer.msg("操作成功");
                        // 刷新页面
                        $(function (){
                            setTimeout(loadTable(url,data.field), 1000); //延迟1秒
                        })
                    }
                    layer.msg("操作失败");
                });
            } else {
                $.post("/vehicleInfo/editVehicleInfo",data.field,function(res){
                    if (res.status==200){
                        return layer.msg("操作成功");
                        // 刷新页面
                        $(function (){
                            setTimeout(loadTable(url,data.field), 1000); //延迟1秒
                        })
                    }
                    layer.msg("操作失败");
                });
            }
            return false;
        });*!/
        /!*upload();
        //文件上传模块（自动上传，返回上传图片的地址）
        function upload(){
            var upload = layui.upload; //引入文件上传模块
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#upload'
                ,url: '/vehicleInfo/uploadFile'
                ,accept: 'images' /!* 只能穿图片 *!/
                ,acceptMime: 'image/!*' /!* 打开文件选择器时 只显示图片*!/
                ,multiple: false /!* 是否允许多文件上传 true是 false否*!/
                ,done: function(res){
                    //上传失败
                    if(res.status == 201){
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    var uploadImage = $("#uploadImage");
                    uploadImage.attr("src",res.data[0][0]);
                    uploadImage.attr("style","width:100%;height:100%;display:block");
                    // 隐藏 i标签中的图标
                    $(".hide-custom").attr("style","display:none");
                    // 将返回的图片地址赋值给id="imageUrl"的隐藏框
                    $("#imageUrl").attr("value",res.data[0][0]);
                }
                ,error: function(){
                    //上传失败
                    return layer.msg('上传失败，请重试!');
                }
            });
        };*!/

        //获取后台数据，重新渲染表单（渲染表单中下拉框与单选按钮）
        function formRender(form,data){
            // 1、清除html文本
            /!* $('#vehicleMaintenanceStatus').empty();
             $('#vehicleBrand').empty();
             $('#businessPurpose').empty();
             $('#color').empty();
             $('#fuelType').empty();
             $('#level').empty();
             $('#dispatchingRightId').empty();
             $('#subordinateSuppliers').empty();
             $('#annualReviewStatus').empty();
             $('#scopeOfBusiness').empty();
             $('#vehicleType').empty();*!/
            $('.clear-input').val("");
            $(".clear-select").empty();
            // 显示 i标签中的图标
            $(".hide-custom").attr("style","display:block");
            // 去掉id="imageUrl"的图片地址
            $("#imageUrl").attr("value","");
            $("#uploadImage").removeAttr("src");
            $("#uploadImage").attr("style","display: none")

            console.log(data.vehicleMaintenanceStatus);
            // 2、重新初始化下拉框与单选按钮html文本
            //车辆检修
            $.each(data.vehicleMaintenanceStatus, function (index, item) {
                // console.log(item);
                $('#vehicleMaintenanceStatus').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 厂牌选项
            $.each(data.vehicleBrand, function (index, item) {
                $('#vehicleBrand').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 业务用途
            $.each(data.businessPurpose, function (index, item) {
                $('#businessPurpose').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 车牌颜色
            $.each(data.color, function (index, item) {
                $('#color').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 燃料类型
            $.each(data.fuelType, function (index, item) {
                $('#fuelType').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //等级选项数据
            $.each(data.level, function (index, item) {
                $('#level').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //调度权
            $.each(data.dispatchingRight, function (index, item) {
                $('#dispatchingRightId').append(item.fieldName+" <input type='radio' name='dispatchingRightId' value='"+item.fieldId+"' checked>");// 下拉菜单里添加元素
            });
            //供应商
            $.each(data.subordinateSuppliers, function (index, item) {
                $('#subordinateSuppliers').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 年审
            $.each(data.annualReviewStatus, function (index, item) {
                $('#annualReviewStatus').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //运营范围
            $.each(data.scopeOfBusiness, function (index, item) {
                $('#scopeOfBusiness').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            //车辆类型
            $.each(data.vehicleType, function (index, item) {
                $('#vehicleType').append(new Option(item.fieldName, item.fieldId));// 下拉菜单里添加元素
            });
            // 更新全部（一定要加上，不然表单不会被重新渲染）
            form.render();
        }
    });*/
});
layui.use(['upload'],function () {
        //文件上传模块（自动上传，返回上传图片的地址）
            var upload = layui.upload; //引入文件上传模块
            //普通图片上传
            var uploadInst = upload.render({
                elem: '#upload'
                ,url: '/vehicleInfo/uploadFile'
                ,accept: 'images'       /* 只能穿图片 */
                ,acceptMime: 'image/*'  /* 打开文件选择器时 只显示图片*/
                ,multiple: false        /* 是否允许多文件上传 true是 false否*/
                ,done: function(res){
                    //上传失败
                    if(res.status == 201){
                        return layer.msg('上传失败');
                    }
                    //上传成功
                    var uploadImage = $("#uploadImage");
                    uploadImage.attr("src",res.data[0][0]);
                    uploadImage.attr("style","width:100%;height:100%;display:block");
                    // 隐藏 i标签中的图标
                    $(".hide-custom").attr("style","display:none");
                    // 将返回的图片地址赋值给id="imageUrl"的隐藏框
                    $("#imageUrl").attr("value",res.data[0][0]);
                }
                ,error: function(){
                    //上传失败
                    return layer.msg('上传失败，请重试!');
                }
            });
})