package com.shanhai.examples.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目类型查询参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RulesForm  implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *分类id
     */
    private String projectType;

    /**
     * 父id 0:顶级条目
     */
    private int projectPid;
    private Date ruleDate;
    private ChildForm childForm;
}
