package com.daniu.model.dto.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author FangDaniu
 * @since  2024/05/4
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    @Min(value = 1, message = "无效id")
    private Long id;

    private static final long serialVersionUID = 1L;
}