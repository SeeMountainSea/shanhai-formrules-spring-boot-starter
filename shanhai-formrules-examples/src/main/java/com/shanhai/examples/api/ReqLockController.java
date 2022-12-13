package com.shanhai.examples.api;

import com.shanhai.examples.form.ChildForm;
import com.wangshanhai.formrules.annotation.ReqLock;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code ReqLockController} class
 *
 * @author Fly.Sky
 * @since 2022/12/12 14:03
 */
@RestController
@RequestMapping("/req")
public class ReqLockController {
    @GetMapping("/lock/{id}")
    @ReqLock(lockTarget ="id",lockName = "demoLock",lockValueStrategy ="",lockExpireTime = 100,lockTimeOut = 3)
    public String lock(@PathVariable("id") Integer id) throws InterruptedException {
      Thread.sleep(8000);
      return "success:"+id;
    }

    @PostMapping("/lockMulti/{id}")
    @ReqLock(multiLockTarget ={"id","childForm.projectName"},lockName = "demoMultiLock",lockValueStrategy ="",lockExpireTime = 100,lockTimeOut = 3)
    public String lockMulti(@PathVariable("id") Integer id,@RequestBody ChildForm childForm) throws InterruptedException {
        Thread.sleep(8000);
        return "success:"+id;
    }

}
