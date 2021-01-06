package com.wangshuai.crawler.job;

import com.wangshuai.crawler.manager.stock.hk.jisilu.JisiluManager;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * XxlJob开发示例（Bean模式）
 *
 * 开发步骤：
 * 1、在Spring Bean实例中，开发Job方法，方式格式要求为 "public ReturnT<String> execute(String param)"
 * 2、为Job方法添加注解 "@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy = "JobHandler销毁方法")"，注解value值对应的是调度中心新建任务的JobHandler属性的值。
 * 3、执行日志：需要通过 "XxlJobLogger.log" 打印执行日志；
 *
 * @author xuxueli 2019-12-11 21:52:51
 */
@Component
@Slf4j
public class UpdateJisiluHkNewStockJobHandler extends IJobHandler {

    @Resource
    private JisiluManager jisiluManager;

    /**
     * 更新数据
     *
     * @param arg
     * @return
     * @throws Exception
     */
    @XxlJob(value = "updateJisiluHkNewStockJob", init = "init", destroy = "destroy")
    @Override
    public ReturnT<String> execute(String arg) throws Exception {
        XxlJobLogger.log("============== UpdateJisiluHkNewStockJobHandler.execute begin ==============");
        log.info("============== UpdateJisiluHkNewStockJobHandler.execute begin ==============");
        jisiluManager.updateData();
        XxlJobLogger.log("============== UpdateJisiluHkNewStockJobHandler.execute end ==============");
        log.info("============== UpdateJisiluHkNewStockJobHandler.execute end ==============");
        return ReturnT.SUCCESS;
    }

    @Override
    public void init(){
        log.info("============== UpdateJisiluHkNewStockJobHandler init ==============");
    }

    @Override
    public void destroy(){
        log.info("============== UpdateJisiluHkNewStockJobHandler destory ==============");
    }


}
