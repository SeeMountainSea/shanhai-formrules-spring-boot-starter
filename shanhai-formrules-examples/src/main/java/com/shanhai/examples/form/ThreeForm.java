package com.shanhai.examples.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目类型查询参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThreeForm implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *分类id
     */
    private String projectNameThree;

    /**
     * 父id 0:顶级条目
     */
    private Integer projectStatusThree;
}
