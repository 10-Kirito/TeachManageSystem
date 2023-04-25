package com.example.teachmanagesystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Kirito
 * @since 2023-04-25
 */
@TableName("select_class")
@ApiModel(value = "SelectClass对象", description = "")
public class SelectClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    private Integer studentId;

    private Integer openRecord;

    private Integer usuallyScore;

    private Integer testScore;

    private Integer totalScore;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }
    public Integer getOpenRecord() {
        return openRecord;
    }

    public void setOpenRecord(Integer openRecord) {
        this.openRecord = openRecord;
    }
    public Integer getUsuallyScore() {
        return usuallyScore;
    }

    public void setUsuallyScore(Integer usuallyScore) {
        this.usuallyScore = usuallyScore;
    }
    public Integer getTestScore() {
        return testScore;
    }

    public void setTestScore(Integer testScore) {
        this.testScore = testScore;
    }
    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "SelectClass{" +
            "recordId=" + recordId +
            ", studentId=" + studentId +
            ", openRecord=" + openRecord +
            ", usuallyScore=" + usuallyScore +
            ", testScore=" + testScore +
            ", totalScore=" + totalScore +
        "}";
    }
}
