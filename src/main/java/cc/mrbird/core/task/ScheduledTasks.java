package cc.mrbird.core.task;

import cc.mrbird.core.domain.service.DomainMgrService;
import cc.mrbird.core.log.BusinessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static cc.mrbird.core.domain.service.impl.DomainMgrServiceImpl.SUFFIX_LIST;

/**
 * @author leo on 30/01/2018.
 */
//@Component
public class ScheduledTasks {

    @Autowired
    private DomainMgrService domainMgrService;

    @Scheduled(cron = "0 0 22 * * ?")
    public void loadLength1() {
        BusinessLog.info("start loadLength 1");
        domainMgrService.loadTotalPage(1);
        BusinessLog.info("end loadLength 1");
    }

    @Scheduled(cron = "0 0 23 * * ?")
    public void loadLength2() {
        BusinessLog.info("start loadLength 2");
        domainMgrService.loadTotalPage(2);
        BusinessLog.info("end loadLength 2");
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void loadLength3() {
        BusinessLog.info("start loadLength 3");
        domainMgrService.loadTotalPage(3);
        BusinessLog.info("end loadLength 3");
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void loadLength4() {
        BusinessLog.info("start loadLength 4");
        domainMgrService.loadTotalPage(4);
        BusinessLog.info("end loadLength 4");
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void loadLength5() {
        BusinessLog.info("start loadLength 5");
        domainMgrService.loadTotalPage(5);
        BusinessLog.info("end loadLength 5");
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void loadSuffix0() {
        String suffix = SUFFIX_LIST.get(0);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void loadSuffix1() {
        String suffix = SUFFIX_LIST.get(1);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void loadSuffix2() {
        String suffix = SUFFIX_LIST.get(2);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }

    @Scheduled(cron = "0 0 6 * * ?")
    public void loadSuffix3() {
        String suffix = SUFFIX_LIST.get(3);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }


    @Scheduled(cron = "0 0 7 * * ?")
    public void loadSuffix4() {
        String suffix = SUFFIX_LIST.get(4);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }

    @Scheduled(cron = "0 0 8 * * ?")
    public void loadSuffix5() {
        String suffix = SUFFIX_LIST.get(5);
        BusinessLog.info("start loadSuffix %s ", suffix);
        domainMgrService.loadDomain(suffix);
        BusinessLog.info("end loadSuffix %s ", suffix);
    }

    @Scheduled(cron = "0 0 9 * * ?")
    public void loadAll() {
        BusinessLog.info("start loadAll");
        domainMgrService.loadDomain();
        BusinessLog.info("end loadAll");
    }
}
