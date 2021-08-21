package excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DemoData
{
    @ExcelProperty(value = "学生编号",index = 0)
    private int stuNo;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String stuName;
}
