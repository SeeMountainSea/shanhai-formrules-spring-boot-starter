package com.shanhai.examples.api;

import com.shanhai.examples.form.ChildForm;
import com.wangshanhai.formrules.annotation.SqlLock;
import org.springframework.web.bind.annotation.*;

/**
 * The {@code SqlSockController} class
 *
 * @author Fly.Sky
 * @since 2022/12/16 9:58
 */
@RestController
@RequestMapping("/sql")
public class SqlSockController {

    @PostMapping("/lock/{id}")
    @SqlLock(expressionValueTargets ={"id","childForm.projectName"},expression = "select id from t_sql_lock a where a.id={} and a.projectName={}")
    public String lockMulti(@PathVariable("id") Integer id, @RequestBody ChildForm childForm) throws InterruptedException {
        Thread.sleep(8000);
        return "success:"+id;
    }

}
