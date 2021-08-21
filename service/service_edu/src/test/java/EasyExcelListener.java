import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.ConverterUtils;
import excel.DemoData;

import java.util.Map;

public class EasyExcelListener extends AnalysisEventListener<DemoData>
{
    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext)
    {
        System.out.println("**********"+demoData);
    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {

    }
}
