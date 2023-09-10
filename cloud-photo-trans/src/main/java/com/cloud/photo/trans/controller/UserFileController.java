package com.cloud.photo.trans.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cloud.photo.common.bo.AlbumPageBo;
import com.cloud.photo.common.common.ResultBody;
import com.cloud.photo.trans.entity.UserFile;
import com.cloud.photo.trans.service.IUserFileService;
import com.cloud.photo.trans.util.KafkaServiceImpl;
import com.cloud.photo.trans.util.RequestUtil;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whh
 * @since 2023-07-13
 */
@RestController
@RequestMapping("/trans")
public class UserFileController {
    @Autowired
    IUserFileService iUserFileService;

    @Autowired
    private KafkaServiceImpl kafkaService;

    @RequestMapping("/userFilelist")
    public ResultBody userFilelist(HttpServletRequest request, HttpServletResponse response, @RequestBody AlbumPageBo pageBo){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request, pageBo);

        //1.设置查询Mapper
        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();

        //2.组装查询条件
        HashMap<String, Object> param = new HashMap<>();
        if(pageBo.getCategory()!=null){
            param.put("category", pageBo.getCategory());
        }
        param.put("user_id", pageBo.getUserId());
        queryWrapper.allEq(param);
        queryWrapper.eq("audit_status", 1);
        Integer current = pageBo.getCurrent();
        Integer pageSize = pageBo.getPageSize();
        if(current == null){
            current = 1;
        }
        if(pageSize == null){
            pageSize = 20;
        }

        // 3. 分页
        Page<UserFile> page = new Page<>(current, pageSize);

        // 4. 查询列表数据
        IPage<UserFile> userFilePage = iUserFileService.page(page, queryWrapper.orderByDesc("user_id", "create_time"));

        return ResultBody.success(userFilePage);
    }

    /**
     * 根据文件id查询文件
     * @param request
     * @param response
     * @param fileId
     * @return
     */
    @RequestMapping("/getUserFileById")
    public UserFile getUserFileById(HttpServletRequest request, HttpServletResponse response,
                                    @RequestParam("fileId") String fileId){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);

        return iUserFileService.getById(fileId);
    }

    @RequestMapping("/getUserFileByFileName")
    public ResultBody getUserFileByFileName(HttpServletRequest request, HttpServletResponse response,
                                            @RequestParam("fileName") String fileName){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request);

        QueryWrapper<UserFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("file_name", fileName);

        Page<UserFile> page = new Page<>();
        IPage<UserFile> userFilePage = iUserFileService.page(page, queryWrapper);
        List<UserFile> userFileList = userFilePage.getRecords();
        return ResultBody.success(userFileList);
    }

    /**
     * 修改文件
     * @param request
     * @param response
     * @param userFileBoList
     * @return
     */
    @RequestMapping("/updateUserFile")
    public Boolean updateUserFile(HttpServletRequest request, HttpServletResponse response,
                                  @RequestBody List<UserFile> userFileBoList){
        String requestId = RequestUtil.getRequestId(request);
        RequestUtil.printQequestInfo(request, userFileBoList);
        for (UserFile userFile:userFileBoList){
            UpdateWrapper<UserFile> updateWrapper = new UpdateWrapper<UserFile>();

            //通过存储id更新审核状态
            if(StrUtil.isNotBlank(userFile.getStorageObjectId())){
                updateWrapper.eq("storage_object_id", userFile.getStorageObjectId());
            }
            //通过文件id更新审核状态
            if(StrUtil.isNotBlank(userFile.getUserFileId())){
                updateWrapper.eq("user_file_id", userFile.getUserFileId());
            }
            updateWrapper.set("audit_status", userFile.getAuditStatus());
            iUserFileService.update(updateWrapper);
        }
        return true;
    }
}
