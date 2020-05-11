package hw05GarbageCollectors;


import com.sun.management.GarbageCollectionNotificationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.List;

/* 1 запуск
-Xms1024m
-Xmx1024m
-Xlog:gc=debug:file=./hw05-GarbageCollectors/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+UseG1GC
*/

/* 2 запуск
-Xms1024m
-Xmx1024m
-Xlog:gc=debug:file=./hw05-GarbageCollectors/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+UseSerialGC
*/

/* 3 запуск
-Xms1024m
-Xmx1024m
-Xlog:gc=debug:file=./hw05-GarbageCollectors/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+UseParallelGC
*/

/* 4 запуск
-Xms1024m
-Xmx1024m
-Xlog:gc=debug:file=./hw05-GarbageCollectors/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+UseConcMarkSweepGC
*/

/* 5 запуск
-Xms1024m
-Xmx1024m
-Xlog:gc=debug:file=./hw05-GarbageCollectors/logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+UnlockExperimentalVMOptions -XX:+UseZGC
*/


public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        switchOnMonitoring();
        System.out.println("end");
        BigProcess bigProcess = new BigProcess();
        bigProcess.run();
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    logger.info("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
