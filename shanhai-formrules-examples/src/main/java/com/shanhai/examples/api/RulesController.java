package com.shanhai.examples.api;

import com.shanhai.examples.form.RulesForm;
import com.wangshanhai.formrules.annotation.RequestFormRules;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class RulesController {

    @RequestMapping("/restRules")
    @RequestFormRules(target = {"rulesForm"},rule="shf.notEmpty(rulesForm,'1001','参数不能为空！');")
    public String restRules(@RequestBody RulesForm rulesForm){
       return "success";
    }

    @RequestMapping("/formRules")
    @RequestFormRules(target = {"rulesForm"})
    public String formRules(@RequestParam MultipartFile file, @RequestParam Integer projectType, @RequestParam String projectPid){
        return "success";
    }

    @RequestMapping("/formMap")
    @RequestFormRules(target = {"rulesForm"})
    public String formRules(@RequestParam Map requestParam){
        return "success";
    }
    @RequestMapping("/pathRules/{id}/{type}")
    @RequestFormRules(target = {"rulesForm"})
    public String pathRules(@PathVariable("id") Integer id,@PathVariable("type") String type){
        return "success";
    }
}
