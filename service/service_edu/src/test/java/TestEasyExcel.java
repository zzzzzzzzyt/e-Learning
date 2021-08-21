import com.alibaba.excel.EasyExcel;
import excel.DemoData;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class TestEasyExcel
{
    @Test
    public static void main(String[] args)
    {
        //easyexcel写入操作
        //String fileName = "E:\\easyExcel.xlsx";
        //EasyExcel.write(fileName, DemoData.class).sheet("学生列表").doWrite(data());

        //easyexcel读取操作  读取第一个sheet之后会自动关闭
        String fileName = "E:/easyExcel.xlsx";
        EasyExcel.read(fileName,DemoData.class,new EasyExcelListener()).sheet("学生列表").doRead();
    }

    private static List<DemoData> data()
    {
        List<DemoData> list = new ArrayList<>();

        for (int i = 0; i < 10; i++)
        {
            DemoData stu = new DemoData();
            stu.setStuNo(i);
            stu.setStuName("陈书田"+i);
            list.add(stu);
        }
        return list;
    }
}
