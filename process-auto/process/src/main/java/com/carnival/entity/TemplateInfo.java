package com.carnival.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-09-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("template_info")
public class TemplateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "my_row_id", type = IdType.AUTO)
    private Long myRowId;

    @TableField("source_end")
    private String sourceEnd;

    @TableField("destination")
    private String destination;

    @TableField("source")
    private String source;

    @TableField("destination_end")
    private String destinationEnd;

    @TableField("template_name")
    private String templateName;


}
