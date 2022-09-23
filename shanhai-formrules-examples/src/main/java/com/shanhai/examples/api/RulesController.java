package com.shanhai.examples.api;

import com.shanhai.examples.form.ChildForm;
import com.shanhai.examples.form.RulesForm;
import com.shanhai.examples.form.ThreeForm;
import com.wangshanhai.formrules.annotation.RequestFormRules;
import com.wangshanhai.formrules.utils.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RulesController {

    @RequestMapping("/restRules")
    @RequestFormRules(target = {"rulesForm"}, ruleScope = {"r_zhangsan"})
    public String restRules(@RequestBody RulesForm rulesForm) {
        return "success";
    }

    @RequestMapping("/formRules")
    @RequestFormRules(target = {"rulesForm"})
    public String formRules(@RequestParam MultipartFile file, @RequestParam Integer projectType, @RequestParam String projectPid) {
        return "success";
    }

    @RequestMapping("/formMap")
    @RequestFormRules(target = {"rulesForm"})
    public String formRules(@RequestParam Map requestParam) {
        return "success";
    }

    @RequestMapping("/pathRules/{id}/{type}")
    @RequestFormRules(target = {"rulesForm"})
    public String pathRules(@PathVariable("id") Integer id, @PathVariable("type") String type) {
        return "success";
    }

    public static void main(String[] args) {
        RulesForm rulesForm = new RulesForm();
        rulesForm.setProjectPid(1);
        rulesForm.setProjectType("一级项目");
        ChildForm childForm = new ChildForm();
        childForm.setProjectName("二级项目");
        childForm.setProjectStatus(1);
        childForm.setCreateDate(new Date());
        ThreeForm threeForm = new ThreeForm();
//        threeForm.setProjectNameThree("三级项目");
        threeForm.setProjectStatusThree(1);
        childForm.setThreeForm(threeForm);
        rulesForm.setChildForm(childForm);
        System.out.println(ObjectUtils.getFieldValueByName(rulesForm, "childForm.threeForm.projectNameThree"));
    }
}
