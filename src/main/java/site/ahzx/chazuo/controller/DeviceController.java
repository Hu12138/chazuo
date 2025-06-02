package site.ahzx.chazuo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.ahzx.chazuo.aop.UserContext;
import site.ahzx.chazuo.domain.BO.DeviceBO;
import site.ahzx.chazuo.domain.VO.DeviceVO;
import site.ahzx.chazuo.service.DeviceService;
import site.ahzx.chazuo.service.UserService;
import site.ahzx.chazuo.util.R;

@RestController
@RequestMapping("/device")
@Slf4j
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    
    @Autowired
    private UserContext userContext;
    
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public R addDevice(@RequestBody DeviceBO deviceBO) {
        try {
            log.info("新增设备: {}", deviceBO);
            String openid = userContext.getCurrentUser();
            Long userId = userService.getUserIdByOpenid(openid);
            if (userId == null) {
                return R.fail("用户不存在");
            }
            deviceBO.setCreatedBy(userId);
            deviceService.addDevice(deviceBO);
            return R.ok("设备添加成功");
        } catch (Exception e) {
            log.error("新增设备失败", e);
            return R.fail("设备添加失败: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public R updateDevice(@RequestBody DeviceBO deviceBO) {
        try {
            log.info("更新设备: {}", deviceBO);
            deviceService.updateDevice(deviceBO);
            return R.ok("设备更新成功");
        } catch (Exception e) {
            log.error("更新设备失败", e);
            return R.fail("设备更新失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public R deleteDevice(@PathVariable Long id) {
        try {
            log.info("删除设备: {}", id);
            deviceService.deleteDevice(id);
            return R.ok("设备删除成功");
        } catch (Exception e) {
            log.error("删除设备失败", e);
            return R.fail("设备删除失败: " + e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public R getDeviceDetail(@PathVariable Long id) {
        try {
            log.info("查询设备详情: {}", id);
            DeviceVO deviceVO = deviceService.getDeviceDetail(id);
            return R.ok("查询成功", deviceVO);
        } catch (Exception e) {
            log.error("查询设备详情失败", e);
            return R.fail("查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/list")
    public R getDeviceList() {
        try {
            log.info("查询设备列表");
            return R.ok("查询成功", deviceService.getDeviceList());
        } catch (Exception e) {
            log.error("查询设备列表失败", e);
            return R.fail("查询失败: " + e.getMessage());
        }
    }
}
